from konlpy.tag import *
import sys
import math

alpha = 1.0

class NaiveBayes:
    
    def __init__(self):
        self.vocab = set()
        self.word_count = {}
        self.document_count = {}

    def train(self,document,label):
        okt = Okt()
        word_list = okt.morphs(document)

        for word in word_list:
            self.__word_count_up(word,label)
            self.vocab.add(word)

        self.__document_count_up(label)

    def __word_count_up(self,word,label):
        self.word_count.setdefault(label,{})
        self.word_count[label].setdefault(word,0)
        self.word_count[label][word] += 1

    def __document_count_up(self,label):
        self.document_count.setdefault(label,0)
        self.document_count[label] += 1

    def classifier(self,document):
        best_label = None        
        okt = Okt()
        word_list = okt.morphs(document)
        
        max_prob = -(sys.maxsize)
        for label in self.document_count.keys():
            prob = self.__score(label,word_list)
            if prob > max_prob:
                max_prob = prob
                best_label = label

        return best_label

    def __score(self,label,word_list):
        score = math.log(self.__label_prob(label))
        for word in word_list:
            score += math.log(self.__word_prob(word,label))

        return score

    def __label_prob(self,label):
        return float(self.document_count[label]/sum(self.document_count.values()))

    def __word_prob(self,word,label):
        probD = sum(self.word_count[label].values())+len(self.vocab)*alpha
        probN = self.__word_count_inclueded(word,label)+alpha
        return probN/probD

    def __word_count_inclueded(self,word,label):
        if word in self.word_count[label]:
            return float(self.word_count[label][word])
        return 0.0
    

def training_data(nb,path):
    f = open(path,'r', encoding='UTF8')
    meta_data = f.readline()

    for line in f:
        doc = line.split('\t')[1]
        label = line.split('\t')[2]
        nb.train(doc,label)  
            
    f.close()

    
def evaluation(nb,path):
    f = open(path,'r', encoding='UTF8')
    meta_data = f.readline()
    
    count = 0
    correct = 0
    for line in f:
        doc = line.split('\t')[1]
        label = line.split('\t')[2]
        best_label = nb.classifier(doc)
        if label == best_label:
            correct += 1
        count += 1
        
    f.close()
    result = correct/count * 100
    print('result: '+str(result)+'%')
    

def test(nb,input_p,output_p):
    input_f = open(input_p,'r', encoding='UTF8')
    output_f = open(output_p,'w', encoding='UTF8')
    meta_data = input_f.readline()
    output_f.write(meta_data)

    for line in input_f:
        ID = line.split('\t')[0]
        doc = line.split('\t')[1]
        best_label = nb.classifier(doc)
        output_f.write(ID+'\t'+doc+'\t'+best_label.strip()+'\n')
        
    input_f.close()
    output_f.close()
    print('Success for create file: '+output_p)
    
    

def main():
    nb = NaiveBayes()
    train_p = 'ratings_train.txt'
    input_p = 'ratings_test.txt'
    output_p = 'ratings_result.txt'
    
    training_data(nb,train_p)
    test(nb,input_p,output_p)
    

if __name__=="__main__":
    main()
