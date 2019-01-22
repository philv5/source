//2014005014_������_A
#include <stdio.h>
#include <stdlib.h>

#define MAX_NODES 100000

//Heap�� �迭�� ������ index 0���ڸ��� �Ⱦ�
typedef struct Node {
	int nodes[MAX_NODES];
	int lastIndex;		//�������� Ȯ���ؾߵ� ��ġ
}Heap;

void insertData(Heap*, int);
int deleteData(Heap*);


int main() {
	int i, numOfKey, k, data, val;
	Heap heap;
	heap.lastIndex = 0;

	val = scanf("%d", &numOfKey);

	val = scanf("%d", &k);

	for (i = 0; i < numOfKey; i++) {
		val = scanf("%d", &data);
		insertData(&heap, data);
	}

	//k�� ���
	for (i = 0; i < k; i++)
		printf("%d ", deleteData(&heap));
	printf("\n");
	//����������
	while(1){
		data = deleteData(&heap);
		if (data == -1) break;
		printf("%d ", data);
	}
	printf("\n");
	
	return 0;
}

void insertData(Heap* heap, int data) {
	int index;

	//������ Ȯ��
	heap->lastIndex = heap->lastIndex + 1;
	index = heap->lastIndex;

	//������ �߰�
	while ((index != 1) && (data > heap->nodes[index / 2])) {
		heap->nodes[index] = heap->nodes[index / 2];
		index = index / 2;
	}

	heap->nodes[index] = data;
}

int deleteData(Heap* heap) {
	int data, temp, parentIndex, childIndex;

	if (heap->lastIndex == 0) {
		return -1;
	}

	if (heap->lastIndex == 1) {
		data = heap->nodes[heap->lastIndex];
		heap->nodes[heap->lastIndex] = 0;
		heap->lastIndex = 0;
		return data;
	}

	data = heap->nodes[1];

	//temp�� ������ ��� �� ������ ����
	temp = heap->nodes[heap->lastIndex];
	heap->nodes[heap->lastIndex] = 0;
	heap->lastIndex--;

	parentIndex = 1;
	childIndex = 2;

	//root���� ����, parent�ڸ��� temp���� ���� ������ ���� parent�ڸ� �̵�
	while (childIndex <= heap->lastIndex) {
		if (heap->nodes[childIndex] < heap->nodes[childIndex + 1])
			childIndex++;

		if (temp > heap->nodes[childIndex])
			break;

		heap->nodes[parentIndex] = heap->nodes[childIndex];
		parentIndex = childIndex;
		childIndex = childIndex * 2;
	}
	//�������� parent�ڸ��� temp�� ����
	heap->nodes[parentIndex] = temp;

	return data;
}