//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define TRUE 1
#define FALSE 0

#define WHITE 0
#define GRAY 1
#define BLACK 2

#define TREE_EDGE 1
#define BACK_EDGE 2
#define FORWARD_EDGE 3
#define CROSS_EDGE 4



typedef struct vertex {
	int color;
	int ftime;
	int dtime;
} Vertex;

typedef struct vertexInfo {
	int out_degree;
	int * list;
} VertexInfo;

Vertex * vertex;
VertexInfo * vInfo;
int numV, time;
int * topoList;
int topo_num = 0;
int flag = TRUE;

void addEdge(int fromV, int toV) {
	int i = 0;
	int cur = 0;

	for (i = 0; i < numV; i++)
	{
		if (vInfo[fromV].list[i] == 0)
			break;
		cur++;
	}
	vInfo[fromV].list[cur] = toV;
}

void DFS_Visit(int fromV) {
	int i = 0, toV;

	time++;
	vertex[fromV].color = GRAY;
	vertex[fromV].dtime = time;

	for (i = 0; i < vInfo[fromV].out_degree; i++) {
		toV = vInfo[fromV].list[i];
		switch (vertex[toV].color)
		{
		case WHITE:
			DFS_Visit(toV);
			break;

		case GRAY:
			flag = FALSE;
			break;

		case BLACK:
			break;
		default:
			break;
		}
	}

	vertex[fromV].color = BLACK;
	topoList[numV - (topo_num++)] = fromV;
	time++;
	vertex[fromV].ftime = time;
}

void Sort(int * list, int num)
{
	int i = 0, j = 0, tmp;

	for (i = 0; i < num; i++)
	{
		for (j = i + 1; j < num; j++)
		{
			if (list[i] > list[j])
			{
				tmp = list[i];
				list[i] = list[j];
				list[j] = tmp;
			}
		}
	}
}


void DFS(void)
{
	int i;
	for (i = 1; i <= numV; i++)
	{
		vertex[i].dtime = 0;
		vertex[i].ftime = 0;
		vertex[i].color = WHITE;
		Sort(vInfo[i].list, vInfo[i].out_degree);
	}

	time = 0;
	for (i = 1; i <= numV; i++)
	{
		if (vertex[i].color == WHITE)
		{
			DFS_Visit(i);
		}
	}
}


int main(void)
{
	int i = 0, j = 0;
	int fromV, toV, val;

	val = scanf("%d", &numV);

	vInfo = (VertexInfo*)malloc(sizeof(VertexInfo)*(numV + 1));
	for (i = 1; i <= numV; i++) {
		vInfo[i].list = (int*)malloc(sizeof(int)*numV);
		vInfo[i].out_degree = 0;
	}
	for (i = 1; i <= numV; i++) {
		for (j = 0; j < numV; j++) {
			vInfo[i].list[j] = 0;
		}
	}
	vertex = (Vertex*)malloc(sizeof(Vertex)*(numV + 1));
	topoList = (int*)malloc(sizeof(int)*(numV + 1));

	while (scanf("%d %d", &fromV, &toV) != EOF) {
		addEdge(fromV, toV);
		vInfo[fromV].out_degree++;
	}
	DFS();

	printf("%d\n", flag);
	if (flag)
	{
		for (i = 1; i <= numV; i++)
			printf("%d ", topoList[i]);
	}

	return 0;
}