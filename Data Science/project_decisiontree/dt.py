import sys
import math
from collections import Counter

max_depth = None

class DecisionTree:
    def __init__(self,data,target):
        self.data = data
        self.target = target
        self.depth = None
        self.attribute_var = None
        self.classification_val = None
        self.entropy = entropy(data[target])
        self.children = {}
        self.isLeaf = False

    def split(self,depth):
        self.depth = depth

        if self.entropy == 0 or self.depth == max_depth:
            self.isLeaf = True
            self.classification_val = Counter(self.data[self.target]).most_common()[0][0] #most common value
            return

        best = self.__search_best_split()
        for x in set(self.data[best]):
            s_data = {}
            for i,value in enumerate(self.data[best]):
                if value == x:
                    for key in self.data.keys():
                        s_data.setdefault(key,[])
                        s_data[key].append(self.data[key][i])
            del s_data[best]
            
            child = DecisionTree(s_data,self.target)
            self.children.setdefault(x,child) #branch

        for key in self.children.keys():
            self.children[key].split(depth+1)

    def __search_best_split(self):
        maximize = -1
        for k in self.data.keys():
            if self.__information_gain(k) > maximize:
                maximize = self.__information_gain(k)
                best = k
                
        self.attribute_var = best
        return best

    def __information_gain(self,a):
        if a == self.target:
            return -1

        s = 0
        for x in set(self.data[a]):
            c_data = []
            for i,value in enumerate(self.data[a]):
                if value == x:
                    c_data.append(self.data[self.target][i])
            s += (len(c_data)/len(self.data[a]))*entropy(c_data)

        return self.entropy - s

    def predict(self,test):
        if self.isLeaf:
            return self.classification_val

        if not test[self.attribute_var] in self.children:
            return Counter(self.data[self.target]).most_common()[0][0] #most common value
            
        return self.children[test[self.attribute_var]].predict(test)
    

def entropy(c_data):
    e = 0
    n = len(c_data)
    count = {}

    for d in set(c_data):
        for v in c_data:
            if d == v:
                count.setdefault(d,0)
                count[d] += 1

    for d in set(c_data):
        e += -(count[d]/n)*math.log2(count[d]/n)

    return e


def read_file(path):
    global max_depth
    
    labels = []
    train_data = {}
    
    f = open(path,'r')
    meta_data = f.readline()

    for label in meta_data.split():
        labels.append(label)

    for line in f:
        for i in range(len(labels)):
            train_data.setdefault(labels[i],[])
            train_data[labels[i]].append(line.split()[i])
            
    f.close
    max_depth = len(train_data) - 1
    
    return train_data,list(train_data.keys())[-1]


def create_result(test_path,result_path,dt,target):
    test_file = open(test_path,'r')
    result_file = open(result_path,'w')

    labels = test_file.readline().split('\n')[0]
    result_file.write(labels + '\t' +target + '\n')
    
    for line in test_file:
        test = {}
        for i,value in enumerate(line.split()):
            test.setdefault(labels.split()[i],value)
        result_file.write("{}\t{}\n".format(line.split('\n')[0],dt.predict(test))) 
        
    test_file.close
    result_file.close
    print('Success!')


def main():
    train_path = sys.argv[1]
    test_path = sys.argv[2]
    result_path = sys.argv[3]
    initial_depth = 0

    train_data,target = read_file(train_path)

    dt = DecisionTree(train_data,target)
    dt.split(initial_depth)

    create_result(test_path,result_path,dt,target)

if __name__=="__main__":
    main()
