//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define MAX(a, b) ((a) > (b) ? (a) : (b))

int main() {
	int i, j, count, val, x_length, y_length;
	char x[500];
	char y[500];
	char tmp[500];
	char* result;
	int** c;
	int** d;

	val = scanf("%s", x);
	val = scanf("%s", y);

	x_length = strlen(x);
	y_length = strlen(y);

	if (x_length > y_length) {
		strcpy(tmp, x);
		strcpy(x,y);
		strcpy(y,tmp);
		x_length = strlen(x);
		y_length = strlen(y);
	}

	c = (int**)malloc(sizeof(int*) * (x_length+1));
	for(i = 0; i < x_length+1; i++)
		c[i] = (int*)malloc(sizeof(int) * (y_length+1));

	d = (int**)malloc(sizeof(int*) * (x_length + 1));
	for (i = 0; i < x_length + 1; i++)
		d[i] = (int*)malloc(sizeof(int) * (y_length + 1));

	for (i = 0; i < y_length + 1; i++){
		c[0][i] = 0;
		d[0][i] = -1;
	}
	for (i = 0; i < x_length + 1; i++){
		c[i][0] = 0;
		d[i][0] = -1;
	}

	for(i = 1; i < x_length+1;i++){
		for (j = 1; j < y_length+1; j++) {
			if (x[i - 1] == y[j - 1]){
				c[i][j] = c[i - 1][j - 1] + 1;
				d[i][j] = 2;
			}
			else{
				c[i][j] = MAX(c[i][j - 1], c[i - 1][j]);
				if (c[i][j - 1] <= c[i - 1][j])
					d[i][j] = 1;

				else
					d[i][j] = 0;
			}
		}
	}

	result = (char*)malloc(sizeof(char) * (c[x_length][y_length] + 1));
	result[c[x_length][y_length]] = '\0';
	count = c[x_length][y_length] - 1;
	i = x_length;
	j = y_length;
	while (i >= 0 && j >= 0) {
		if (d[i][j] == 2) {
			result[count--] = x[i-1];
			i--;
			j--;
		}
		else if (d[i][j] == 1) {
			i--;
		}
		else
			j--;
	}

	printf("%s\n", result);

	for (i = 0; i < x_length+1; i++)
		free(c[i]);
	free(c);

	for (i = 0; i < x_length + 1; i++)
		free(d[i]);
	free(d);

	free(result);

	return 0;
}
