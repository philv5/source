//2014005014_ÀÌÁö¹Î_A
#include <stdio.h>
#include <stdlib.h>

void mult_order(int i, int j, int **P);
int min_mult(int n, int *d, int **P, int **M);

int main(){

	int *d, **P, n, i, **M;
	int number, result;

	n = scanf("%d", &number);

	P = (int**)malloc(sizeof(int*)*number);
	for (i = 0; i < number; i++)
		P[i] = (int*)malloc(sizeof(int)*number);

	d = (int*)malloc(sizeof(int)*(number + 1));
	for (i = 0; i <= number; i++)
		n = scanf("%d", &d[i]);

	M = (int**)malloc(sizeof(int*)*(number + 1));
	for (i = 0; i < number + 1; i++)
		M[i] = (int*)malloc(sizeof(int)*(number + 1));

	result = min_mult(number, d, P, M);
	printf("%d\n", result);
	mult_order(1, number, P);

	return 0;
}

void mult_order(int i, int j, int **P){
	int k;
	if (i == j)
		printf("%d", i);
	else {
		k = P[i][j];
		printf("(");
		mult_order(i, k, P);
		mult_order(k + 1, j, P);
		printf(")");
	}
}

int min_mult(int n, int *d, int **P, int **M){
	int i, j, k, dia;

	for (i = 1; i <= n; i++)
		M[i][i] = 0;
	for (dia = 1; dia <= n - 1; dia++)
		for (i = 1; i <= n - dia; i++) {
			j = i + dia;
			M[i][j] = M[i][i] + M[i + 1][j] + (d[i - 1] * d[i] * d[j]);
			P[i][j] = i;
			for (k = i + 1; k <= j - 1; k++) {
				if (M[i][j] > M[i][k] + M[k + 1][j] + (d[i - 1] * d[k] * d[j])) {
					M[i][j] = M[i][k] + M[k + 1][j] + (d[i - 1] * d[k] * d[j]);
					P[i][j] = k;
				}
			}
		}
	return M[1][n];
}