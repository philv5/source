//2014005014_������_A
#include <stdio.h>
#include <stdlib.h>

#define MAX_NODES 100000

//priority queue�� heap�� ����
typedef struct Node {
	int nodes[MAX_NODES];
	int lastIndex;		//�������� Ȯ���ؾߵ� ��ġ
}Heap;

void insertData(Heap*, int);
void substituteData(Heap*, int, int);
int deleteData(Heap*);
void printHeap(Heap);


int main() {
	int data, index, cmd, val;
	Heap heap;
	heap.lastIndex = 0;

	while (1) {
		val = scanf("%d", &cmd);

		if (cmd == 0) {
			printHeap(heap);
			return 0;
		}
		else if (cmd == 1) {
			val = scanf("%d", &data);
			insertData(&heap, data);
		}
		else if (cmd == 2 && heap.lastIndex != 0) {
			data = deleteData(&heap);
			printf("%d\n", data);
		}
		else if (cmd == 3) {
			val = scanf("%d", &index);
			val = scanf("%d", &data);
			if(index != 0 && index <= heap.lastIndex)
				substituteData(&heap, index, data);
		}
	}

	return 0;
}

void insertData(Heap* heap, int data) {
	int index;
	
	if (heap->lastIndex == MAX_NODES - 1) {
		printf("Heap is full\n");
		return;
	}

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

void substituteData(Heap* heap, int index, int data) {
	int childIndex;

	//�θ�� ��
	if (heap->nodes[index] < data) {
		while ((index != 1) && (data > heap->nodes[index / 2])) {
			heap->nodes[index] = heap->nodes[index / 2];
			index = index / 2;
		}
	}
	/*
	if ((index != 1) && (data > heap->nodes[index / 2])) {
		while ((index != 1) && (data > heap->nodes[index / 2])) {
			heap->nodes[index] = heap->nodes[index / 2];
			index = index / 2;
		}
	}
	*/

	//�ڽİ� ��
	else if(heap->nodes[index] > data) {
		childIndex = index * 2;
		while (childIndex <= heap->lastIndex) {
			if (heap->nodes[childIndex] <= heap->nodes[childIndex + 1])
				childIndex++;

			if (data > heap->nodes[childIndex])
				break;

			heap->nodes[index] = heap->nodes[childIndex];
			index = childIndex;
			childIndex = childIndex * 2;
		}
	}
	/*
	else {
		childIndex = index * 2;
		while (childIndex <= heap->lastIndex) {
			if (heap->nodes[childIndex] < heap->nodes[childIndex + 1])
				childIndex++;

			if (data > heap->nodes[childIndex])
				break;

			heap->nodes[index] = heap->nodes[childIndex];
			index = childIndex;
			childIndex = childIndex * 2;
		}
	}
	*/
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

void printHeap(Heap heap) {
	int i;
	for (i = 1; i <= heap.lastIndex; i++) {
		printf("%d ", heap.nodes[i]);
	}
	printf("\n");
}
