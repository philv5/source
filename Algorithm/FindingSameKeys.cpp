//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>

void merge(int[], int, int, int);
void mergeSort(int[], int, int);


int main() {
	int n, m, i, j, val;
	int* A;
	int* B;
	int count = 0;

	val = scanf("%d", &n);
	val = scanf("%d", &m);

	A = (int*)malloc(sizeof(int) * n);
	B = (int*)malloc(sizeof(int) * m);

	for (i = 0; i < n; i++) {
		val = scanf("%d", &A[i]);
	}

	for (i = 0; i < m; i++) {
		val = scanf("%d", &B[i]);
	}

	mergeSort(A, 0, n - 1);
	mergeSort(B, 0, m - 1);
	
	i = 0;
	j = 0;
	while (i < n && j < m) {
		if (A[i] == B[j]) {
			count++;
			i++;
			j++;
		}
		else if (A[i] > B[j]) {
			j++;
		}
		else
			i++;
	}
	
	printf("%d", count);

	return 0;
}

void merge(int list[], int left, int mid, int right) {
	int i, j, k, l;
	i = left; j = mid + 1; k = left;
	int* temp = (int*)malloc(sizeof(int) * (right + 1));

	while (i <= mid && j <= right) {
		if (list[i] <= list[j])
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

