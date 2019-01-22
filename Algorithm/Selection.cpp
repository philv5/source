//2014005014_이지민_A
#include <stdio.h>
#include <stdlib.h>

void selectionSort(int[], int, int);

int main() {
	int i, val, step;
	int numOfKey;
	int* list = NULL;

	val = scanf("%d", &numOfKey);

	val = scanf("%d", &step);

	list = (int*)malloc(sizeof(int)*numOfKey);

	for (i = 0; i < numOfKey; i++) {
		val = scanf("%d", &list[i]);
	}

	selectionSort(list, numOfKey, step);

	for (i = 0; i < numOfKey; i++) {
		printf("%d\n", list[i]);
	}

	return 0;
}

void selectionSort(int list[], int n, int step) {
	int i, j, least, temp;
	if (n - 1 < step) step = n-1;	//step이 배열 사이즈보다 크면 최대step으로 한다.

	for (i = 0; i < step; i++) {
		least = i;
		for (j = i + 1; j < n; j++)
			if (list[j] < list[least]) least = j;
		temp = list[i];
		list[i] = list[least];
		list[least] = temp;
	}
}
