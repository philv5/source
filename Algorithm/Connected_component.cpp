//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>

int numVertex;
int * temp;
int * List;

typedef struct _vertex;

typedef struct _vertex
{
	int groupname;
	int value;
	int rank;
	struct _vertex * parent;
} Vertex;

Vertex * vertex;

void MAKE_SET(Vertex * x, int value)
{
	x->value = value;
	x->parent = x;
	x->rank = 0;
}

Vertex * FIND_SET(Vertex * x)
{
	if (x != x->parent)
		x->parent = FIND_SET(x->parent);
	return x->parent;
}

void LINK(Vertex * x, Vertex * y)
{
	if (x->rank > y->rank) {
		y->parent = x;			
	}
	else {
		x->parent = y;
		if (x->rank == y->rank) {
			y->rank = y->rank + 1;
		}
	}
}

void UNION(Vertex * x, Vertex * y)
{
	LINK(FIND_SET(x), FIND_SET(y));
}

int main(void)
{
	int i = 0, j = 0, tmp = 0;
	int * tmp_list;
	int v1, v2;
	int order = 0;
	int count = 0;

	scanf("%d", &numVertex);
	vertex = (Vertex*)malloc(sizeof(Vertex)*(numVertex + 1));
	temp = (int*)malloc(sizeof(int)*(numVertex + 1));
	tmp_list = (int*)malloc(sizeof(int)*(numVertex + 1));

	for (i = 1; i <= numVertex; i++)
		MAKE_SET(&vertex[i], i);

	while (scanf("%d %d", &v1, &v2) != EOF) {
		if (FIND_SET(&vertex[v1]) != FIND_SET(&vertex[v2])) {
			UNION(&vertex[v1], &vertex[v2]);
		}
	}
	for (i = 1; i <= numVertex; i++) {
		temp[i] = FIND_SET(&vertex[i])->value;
		tmp_list[i] = FIND_SET(&vertex[i])->value;
	}

	for (i = 1; i <= numVertex; i++)
	{
		tmp = temp[i];
		if (tmp != 0)
		{
			count++;
			for (j = i + 1; j <= numVertex; j++) {
				if (tmp == temp[j])
					temp[j] = 0;
			}
		}
	}
	List = (int*)malloc(sizeof(int)*(count + 1));
	order = 1;
	for (i = 1; i <= numVertex; i++) {
		if (temp[i] != 0) {
			List[order++] = temp[i];
		}
	}
	for (i = 1; i <= numVertex; i++) {
		for (j = 1; j <= numVertex; j++) {
			if (tmp_list[i] == List[j]) {
				temp[i] = j;
			}
		}
	}

	printf("%d\n", count);
	for (i = 1; i <= numVertex; i++)
		printf("%d\n", temp[i]);

	return 0;
}