//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>

int main() {
	int n, val, i, j, q;
	int* p;
	int* r;
	int* s;

	val = scanf("%d", &n);

	p = (int*)malloc(sizeof(int) * (n + 1));
	r = (int*)malloc(sizeof(int) * (n + 1));
	s = (int*)malloc(sizeof(int) * (n + 1));

	p[0] = 0;
	r[0] = 0;
	s[0] = 0;

	for (i = 1; i <= n; i++) 
		val = scanf("%d", &p[i]);

	for (j = 1; j <= n; j++) {
		q = -1;
		for (i = 1; i <= j; i++) {
			if (q < p[i] + r[j - i]) {
				q = p[i] + r[j - i];
				s[j] = i;
			}
		}
		r[j] = q;
	}

	printf("%d\n", r[n]);

	while (n > 0) {
		printf("%d ", s[n]);
		n = n - s[n];
	}

	return 0;
}

