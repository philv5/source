//2014005014_이지민_A
#include <stdio.h>
#include <math.h>
#include <queue>
#include <functional>
#include <algorithm>
#include <vector>
using namespace std;


#define MAX_NODES 60000

class Node {
public:
	int frq;
	int isleaf;
	int left;
	int right;
	int Heigth;
	bool operator <(const Node &Node1)const {
		return frq > Node1.frq;
	}
}Huffman[MAX_NODES];

typedef struct Heap {
	Node* nodes[MAX_NODES];
	int lastIndex;		//다음으로 확장해야될 위치
}Heap;

priority_queue<Node> q;

void heightpp(int); 
int HuffmanCost(int);


int main() {
	int i, n,val,frq,s,fl,root,result;
	char str[5];
	

	val = scanf("%d", &n);
	for (i = 0; i < n; i++) {
		val = scanf("%s", str);
		val = scanf("%d", &frq);
		Huffman[i].frq = frq;
		Huffman[i].left = -1;
		Huffman[i].right = -1;
		Huffman[i].Heigth = 0;
		Huffman[i].isleaf = 1;

		q.push(Huffman[i]);
	}
	val = scanf("%d", &s);

	for (i = 0; i < n - 1; i++) {
		Huffman[i * 2] = q.top();
		q.pop();
		Huffman[i * 2 + 1] = q.top();
		q.pop();
		Huffman[n + i].left = i * 2;
		Huffman[n + i].right = i * 2 + 1;
		Huffman[n + i].frq = Huffman[i * 2].frq + Huffman[i * 2 + 1].frq;
		Huffman[n + i].Heigth = 0;
		Huffman[n + i].isleaf = 0;
		q.push(Huffman[n + i]);
		heightpp(i * 2);
		heightpp(i * 2 + 1);
	}

	fl = (int)log2(n) + 1;
	printf("%d\n", fl*s);
	
	root = 2 * n - 2;
	result = HuffmanCost(root);
	printf("%d\n", result);

	return 0;
}

void heightpp(int index) {
	Huffman[index].Heigth++;
	if (Huffman[index].left != -1) {
		heightpp(Huffman[index].left);
	}
	if (Huffman[index].right != -1) {
		heightpp(Huffman[index].right);
	}

}

int HuffmanCost(int index) {
	int sum = 0;
	if (Huffman[index].isleaf == 1) {
		sum += Huffman[index].Heigth * Huffman[index].frq;
	}
	if (Huffman[index].left != -1) {
		sum += HuffmanCost(Huffman[index].left);
	}
	if (Huffman[index].right != -1) {
		sum += HuffmanCost(Huffman[index].right);
	}
	return sum;
}

