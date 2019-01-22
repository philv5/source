//2014005014_이지민_A
#include <stdio.h>
#include <stdlib.h>

#define MAX_NODES 100000

//Heap를 배열로 구현시 index 0번자리는 안씀
typedef struct Node {
	int nodes[MAX_NODES];
	int lastIndex;		//다음으로 확장해야될 위치
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

	//k개 출력
	for (i = 0; i < k; i++)
		printf("%d ", deleteData(&heap));
	printf("\n");
	//마지막까지
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

	//데이터 확장
	heap->lastIndex = heap->lastIndex + 1;
	index = heap->lastIndex;

	//데이터 추가
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

	//temp에 마지막 노드 값 저장후 삭제
	temp = heap->nodes[heap->lastIndex];
	heap->nodes[heap->lastIndex] = 0;
	heap->lastIndex--;

	parentIndex = 1;
	childIndex = 2;

	//root부터 시작, parent자리에 temp값이 들어가기 떼문에 먼저 parent자리 이동
	while (childIndex <= heap->lastIndex) {
		if (heap->nodes[childIndex] < heap->nodes[childIndex + 1])
			childIndex++;

		if (temp > heap->nodes[childIndex])
			break;

		heap->nodes[parentIndex] = heap->nodes[childIndex];
		parentIndex = childIndex;
		childIndex = childIndex * 2;
	}
	//마지막에 parent자리에 temp값 대입
	heap->nodes[parentIndex] = temp;

	return data;
}