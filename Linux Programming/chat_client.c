#include <stdio.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define SIZE	sizeof (struct sockaddr_in)
#define MSGSIZE 1024

int main(){
	int sockfd, i, c;
	char nickname[15] = "0001";
	char temp[15];
	char server_nickname[15];
	char msg[MSGSIZE];
	char recv[MSGSIZE];
	struct sockaddr_in client = {AF_INET, 1234, INADDR_ANY};
	int server_len = SIZE;
	struct sockaddr_in server = {AF_INET, 1234};
	server.sin_addr.s_addr = inet_addr("127.0.0.1");

	sockfd = socket(AF_INET, SOCK_DGRAM, 0);

	printf("\n*********** Chatting ***********\n");
	while(1){
		//Input message
		printf("\nguest_%s(myself): ",nickname);
		i = 0;
		while((msg[i] = getchar()) != '\n'){
			i++;
		}
		msg[i] = '\0';
		
		if(strcmp(msg,"exit") == 0){
			sendto(sockfd, &nickname, MSGSIZE, 0, (struct sockaddr *)&server, server_len);
			sendto(sockfd, &msg, MSGSIZE, 0, (struct sockaddr *)&server, server_len);
			break;
		}
		if(strcmp(msg,"change_nick") == 0){
			while(1){
				printf("Input new nickname: ");
				scanf("%s",temp);
				c = getchar();
				if(strcmp(temp,server_nickname) == 0){
					printf("It is invalid name (Duplicate)\n");
					continue;
				}
				else{
					strcpy(nickname,temp);
					break;
				}
			}
			strcpy(msg, "Client's nickname is modified");
		}
		
		sendto(sockfd, &nickname, MSGSIZE, 0, (struct sockaddr *)&server, server_len);
		sendto(sockfd, &msg, MSGSIZE, 0, (struct sockaddr *)&server, server_len);
		
		recvfrom(sockfd, &server_nickname, MSGSIZE, 0, (struct sockaddr *)&server, &server_len);
		recvfrom(sockfd, &recv, MSGSIZE, 0, (struct sockaddr *)&server, &server_len);
		//exit check
		if(strcmp(recv,"exit") == 0){
			printf("guset_%s has left....\nFinish chatting\n\n",server_nickname);
			break;
		}
		printf("\nguest_%s: %s\n",server_nickname,recv);
	}

	close(sockfd);
	return 0;
}
