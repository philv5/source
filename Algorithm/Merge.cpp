//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>


void merge(int[], int, int, int);
void mergeSort(int[], int, int);


int main() {
	int i, val;
	int numOfKey;
	int* list = NULL;

	val = scanf("%d", &numOfKey);

	list = (int*)malloc(sizeof(int)*numOfKey);

	for (i = 0; i < numOfKey; i++) {
		val = scanf("%d", &list[i]);
	}

	mergeSort(list, 0, numOfKey-1);

	for (i = 0; i < numOfKey; i++) {
		printf("%d\n", list[i]);
	}
	return 0;
}

void merge(int list[], int left, int mid, int right) {
	int i, j, k, l;
	i = left; j = mid + 1; k = left;
	int* temp = (int*)malloc(sizeof(int) * (right+1)); 

	while (i <= mid && j <= right) {
		if (list[i] >= list[j])
			temp[k++] = list[i++];
		else
			temp[k++] = list[j++];
	}

	while (i <= mid)
		temp[k++] = list[i++];
	while (j <= right)
		temp[k++] = list[j++];

	for (l = left; l <= right; l++) {
		list[l] = temp[l];
	}

	free(temp);

}

void mergeSort(int list[], int left, int right) {
	int mid;
	if (left < right) {
		mid = (left + right) / 2;
		mergeSort(list, left, mid);
		mergeSort(list, mid + 1, right);
		merge(list, left, mid, right);
	}
}

