//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>

int main() {
	int n, m, k, i, j, val, count;
	int* A;
	int* B;
	int* C;
	int* keys;


	val = scanf("%d", &n);
	val = scanf("%d", &m);
	val = scanf("%d", &k);

	A = (int*)malloc(sizeof(int) * k);
	B = (int*)malloc(sizeof(int) * k);
	C = (int*)malloc((sizeof(int) * m) + 1);
	keys = (int*)malloc((sizeof(int) * n) + 1);

	for (i = 0; i < k; i++) {
		val = scanf("%d", &A[i]);
		val = scanf("%d", &B[i]);
	}

	for (i = 1; i <= n; i < i++) {
		val = scanf("%d", &keys[i]);
	}

	for (i = 0; i <= m; i++) {
		C[i] = 0;
	}

	for (i = 1; i <= n; i++) {
		C[keys[i]]++;
	}

	for (i = 1; i <= m; i++) {
		C[i] = C[i] + C[i - 1];
	}

	for (i = 0; i < k; i++) {
		count = 0;
		count = C[B[i]] - C[A[i] - 1];
		printf("%d\n", count);
	}

	free(A);
	free(B);
	free(C);
	free(keys);

	return 0;
}

