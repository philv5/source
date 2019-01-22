//2014005014_¿Ã¡ˆπŒ_A
#include <stdio.h>
#include <stdlib.h>
#include<vector>
#include<queue>

#define INFINIT 32767

using namespace std;

void printMLW(int *S, int size) {
	int i;
	int max = -INFINIT;
	for (i = 0; i < size; i++)
		if (max < S[i])
			max = S[i];

	printf("%d\n", max);
}

int main() {
	priority_queue <pair<int, int> > Queue;
	int numOfVertex, val, i, j, edges, index, dest, weight;
	vector < vector < int > > dst,wei;
	int *S;

	val = scanf("%d", &numOfVertex);
	S = (int*)malloc(sizeof(int)*numOfVertex);
	dst.resize(numOfVertex + 1);
	wei.resize(numOfVertex + 1);


	for (i = 1; i <= numOfVertex; i++) {
		val = scanf("%d %d", &index, &edges);
		dst[index].resize(edges);
		wei[index].resize(edges);
		for (j = 0; j < edges; j++) {
			val = scanf("%d %d", &dest, &weight);
			dst[index][j] = dest;
			wei[index][j] = weight;
		}
	}

	Queue.push(make_pair(1, 0));

	while (!Queue.empty()) {
		int u = Queue.top().first;
		int value = -Queue.top().second;
		Queue.pop();
		for (unsigned int i = 0; i < dst[u].size(); i++) {
			if ((dst[u][i] != 1 && S[dst[u][i]] == 0) || S[dst[u][i]] > value + wei[u][i]) {
				S[dst[u][i]] = value + wei[u][i];
				Queue.push(make_pair(dst[u][i], -S[dst[u][i]]));
			}
		}
	}
	printMLW(S, numOfVertex);
	return 0;
}
