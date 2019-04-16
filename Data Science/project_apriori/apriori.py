from itertools import combinations
import sys

class Apriori:
    def __init__(self):
        self.tran_idata = {}
        self.tran_num = 0
        self.items = set()
        self.minSup = 0

    def train(self,tran_data,minSup):
        self.tran_idata = {}
        self.tran_num = len(tran_data)
        self.items = set()
        self.minSup = minSup
        
        for t in tran_data:
            for i in t:
                self.items.add(i)

        for i in self.items:
            for t_id in range(self.tran_num):
                if i in tran_data[t_id]:
                    self.tran_idata.setdefault(i,[])
                    self.tran_idata[i].append(t_id)

    def get_association_rules(self):
        sol = []
        fp = self.__mine_frequent_itemsets()

        for k in range(len(fp)):
            if k == 1:
                for c in fp[k]:
                    x = [c[0]]
                    y = [c[1]]
                    sol.append([x,y,self.__support(list(set(x) | set(y))),self.__confidence(x,y)])
                    sol.append([y,x,self.__support(list(set(y) | set(x))),self.__confidence(y,x)])
                        
                    
            if k >= 2:
                for items in fp[k]:
                    for i in range(1,k+1):
                        for c in combinations(items,i):
                            x = sorted(list(c))
                            y = sorted(list(set(items) - set(x)))
                            sol.append([x,y,self.__support(list(set(x) | set(y))),self.__confidence(x,y)])
                            
        return sol

    def __mine_frequent_itemsets(self):
        sol = []
        f = []
        items = sorted([ [i] for i in self.items])
        for x in items:
            if self.__support(x) >= self.minSup:
                f.append(x)
                
        sol.append(f)

        k = 1
        while True:
            c = self.__genereate_candidate(f,k)
            f = c[:]
            
            for x in c:
                if self.__support(x) < self.minSup:
                    f.remove(x)
                    
            if f == []:
                break
            sol.append(f)
            
            k += 1
            
        return sol

    def __support(self,x):
        s = set(self.tran_idata[x[0]])
        for i in range(1,len(x)):
            s = s & set(self.tran_idata[x[i]])

        return 100*len(s)/self.tran_num

    def __confidence(self,x,y):
        return 100*self.__support(list(set(x) | set(y))) / self.__support(x)

    def __genereate_candidate(self,f,k):
        #self-join
        c = []
        for x in f:
            for y in f:
                s = sorted(list(set(x) | set(y)))
                if len(s) == k+1 and s not in c:
                    c.append(s)

        c.sort()

        #pruning
        cp = c[:]
        for x in c:
            for y in combinations(x,k):
                if list(y) not in f:
                    cp.remove(x)
                    break
        return cp


def read_file(path):
    f = open(path,"r")
    tran_data = []

    for line in f:
        tran = []
        for id in line.split():
            tran.append(int(id))
        tran_data.append(tran)

    f.close

    return tran_data

def write_file(path,rules):
    f = open(path,"w")
    
    for rule in rules:
        x = str(rule[0]).replace('[','{').replace(']','}').replace(' ','')
        y = str(rule[1]).replace('[','{').replace(']','}').replace(' ','')
        supp = "{0:.2f}".format(round(rule[2],2))
        conf = "{0:.2f}".format(round(rule[3],2))
        f.write(x + '\t' + y + '\t' + supp+ '\t' + conf + '\n')
        
    f.close
    print('Success!')

def main():
    min_sup = int(sys.argv[1])
    input_path = sys.argv[2]
    output_path = sys.argv[3]

    data = read_file(input_path)
    
    ap = Apriori()
    ap.train(data,min_sup)
    rules = ap.get_association_rules()

    write_file(output_path,rules)

if __name__=="__main__":
    main()
