//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>

void InsertionSort(int[], int);

int main() {
	int i, val;
	int numOfKey;
	int* list = NULL;

	val = scanf("%d", &numOfKey);
	if (numOfKey < 1 || numOfKey > 30000) {
		printf("Range mismatch\n");
		return 0;
	}

	list = (int*)malloc(sizeof(int)*numOfKey);

	for (i = 0; i < numOfKey; i++) {
		val = scanf("%d", &list[i]);
	}

	InsertionSort(list, numOfKey);
	
	for (i = 0; i < numOfKey; i++) {
		printf("%d\n", list[i]);
	}
	
	return 0;
}

void InsertionSort(int list[], int n) {
	int i, j, key;
	for (i = 1; i < n; i++) {
		key = list[i];
		for (j = i - 1; j >= 0 && list[j] < key; j--)
			list[j + 1] = list[j];
		list[j + 1] = key;
	}
}

