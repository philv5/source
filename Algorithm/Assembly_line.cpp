//2014005014_ÀÌÁö¹Î_A
#include <stdio.h>
#include <stdlib.h>

#define MIN(a, b) ((a) <= (b) ? (a) : (b))

int main() {
	int n, e1, e2, x1, x2, s_result, l_result, val, i;
	int* line1;
	int* line2;
	int* t1;
	int* t2;
	int* path;
	int* s[2];
	int* l[2];

	val = scanf("%d", &n);

	val = scanf("%d", &e1);
	val = scanf("%d", &e2);

	val = scanf("%d", &x1);
	val = scanf("%d", &x2);

	line1 = (int*)malloc(sizeof(int) * n);
	line2 = (int*)malloc(sizeof(int) * n);

	t1 = (int*)malloc(sizeof(int) * (n - 1));
	t2 = (int*)malloc(sizeof(int) * (n - 1));

	s[0] = (int*)malloc(sizeof(int) * n);
	s[1] = (int*)malloc(sizeof(int) * n);

	l[0] = (int*)malloc(sizeof(int) * (n - 1));
	l[1] = (int*)malloc(sizeof(int) * (n - 1));

	path = (int*)malloc(sizeof(int) * n);

	for (i = 0; i < n; i++)
		val = scanf("%d", &line1[i]);

	for (i = 0; i < n; i++)
		val = scanf("%d", &line2[i]);

	for (i = 0; i < n - 1; i++)
		val = scanf("%d", &t1[i]);

	for (i = 0; i < n - 1; i++)
		val = scanf("%d", &t2[i]);


	s[0][0] = e1 + line1[0];
	s[1][0] = e2 + line2[0];

	for (i = 1; i < n; i++) {
		s[0][i] = MIN((s[0][i - 1] + line1[i]), (s[1][i - 1] + t2[i - 1] + line1[i]));
		if (s[0][i] == (s[0][i - 1] + line1[i]))
			l[0][i - 1] = 1;
		else
			l[0][i - 1] = 2;

		s[1][i] = MIN((s[1][i - 1] + line2[i]), (s[0][i - 1] + t1[i - 1] + line2[i]));
		if (s[1][i] == (s[1][i - 1] + line2[i]))
			l[1][i - 1] = 2;
		else
			l[1][i - 1] = 1;
	}

	s_result = MIN((s[0][n - 1] + x1), (s[1][n - 1] + x2));
	if (s_result == (s[0][n - 1] + x1))
		l_result = 1;
	else
		l_result = 2;

	path[n - 1] = l_result;
	for (i = n - 2; i >= 0; i--) {
		path[i] = l[path[i+1] - 1][i];
	}

	printf("%d\n", s_result);
	for (i = 0; i < n; i++)
		printf("%d %d\n", path[i], i+1);

	free(line1);
	free(line2);
	free(t1);
	free(t2);
	free(s[0]);
	free(s[1]);
	free(l[0]);
	free(l[1]);
	free(path);

	return 0;
}

