#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

#define SIZE	sizeof (struct sockaddr_in)

#define MSGSIZE 1024

int main(){
	int sockfd, i, c;
	char temp[15];
	char nickname[15] = "0002";
	char client_nickname[15];
	char msg[MSGSIZE];
	char recv[MSGSIZE];
	struct sockaddr_in server = {AF_INET, 1234, INADDR_ANY};
	struct sockaddr_in client;
	int client_len = SIZE;
	
	sockfd = socket (AF_INET, SOCK_DGRAM, 0);
	bind(sockfd, (struct sockaddr *)&server, SIZE);
	
	printf("\n*********** Chatting ***********\n");
	while(1){

		recvfrom(sockfd, &client_nickname, MSGSIZE, 0, (struct sockaddr *)&client, &client_len);
		recvfrom(sockfd, &recv, MSGSIZE, 0, (struct sockaddr *)&client, &client_len);
		//exit check
		if(strcmp(recv,"exit") == 0){
			printf("guset_%s has left....\nFinish chatting\n\n",client_nickname);
			break;
		}
		printf("\nguest_%s: %s\n",client_nickname,recv);
		
		//Input message
		printf("\nguest_%s(myself): ",nickname);
		i = 0;
		while((msg[i] = getchar()) != '\n'){
			i++;
		}
		msg[i] = '\0';

		if(strcmp(msg,"exit") == 0){
			sendto(sockfd, &nickname, MSGSIZE, 0, (struct sockaddr *)&client, client_len);
			sendto(sockfd, &msg, MSGSIZE, 0, (struct sockaddr *)&client, client_len);
			break;
		}
		else if(strcmp(msg,"change_nick") == 0){
			while(1){
				printf("Input new nickname: ");
				scanf("%s",temp);
				c = getchar();
				if(strcmp(temp,client_nickname) == 0){
					printf("It is invalid name (Duplicate)\n");
					continue;
				}
				else{
					strcpy(nickname,temp);
					break;
				}
			}
			strcpy(msg, "Server's nickname is modified");
		}
		
		sendto(sockfd, &nickname, MSGSIZE, 0, (struct sockaddr *)&client, client_len);
		sendto(sockfd, &msg, MSGSIZE, 0, (struct sockaddr *)&client, client_len);
	}
	
	close(sockfd);
	return 0;
}
