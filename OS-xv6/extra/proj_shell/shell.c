#include<stdio.h>
#include<string.h>
#include<stdlib.h>
#include<unistd.h>
#include <sys/wait.h>
#include <err.h>
#include <errno.h>

#define MAX_SIZE 256

char cmdLine[MAX_SIZE];
char* cmd[MAX_SIZE];

void multi_wait(){
	int pid;
	int status = 0;
		
	while(1){
		pid = wait(&status);
		
		if(pid == -1){
			if(errno == ECHILD)
				break;
			else if(errno == EINTR)
				continue;
		}
	}
}


void exe_command(char* pstr){
	int pid;
	int i = 0;

	cmd[i] = strtok(pstr," ");
	while(cmd[i] != NULL){
		cmd[++i] = strtok(NULL," ");
	}
	
	pid = fork();
	if(pid == 0){
		if((strcmp(cmd[0],"quit") == 0) && (cmd[1] == NULL))
				exit(0);	
		else if(execvp(cmd[0],cmd) < 0){
			perror("execvp error");
			exit(1);
		}
		else
			exit(0);
	}
	
}


void read_cmdline(){
	int i = 0;
	char* notNL;
	char* divcmd[MAX_SIZE];	
	int quitBit = 0;	
	
	notNL = strtok(cmdLine,"\n");
	
	divcmd[i] = strtok(notNL,";");
	while(divcmd[i] != NULL){
		divcmd[++i] = strtok(NULL,";");
	}
	
	for(int k = i-1; k >= 0; k--){
		if(strcmp(divcmd[k],"quit") == 0)
			quitBit = 1;

		exe_command(divcmd[k]);
	}

	multi_wait();

	if(quitBit)
		exit(0);
}

int main(int argc, char* argv[]){
	FILE* fp;
	
	if(argc > 1){
		fp = fopen(argv[1], "r");
		if(fp == NULL){
			perror("fopen error");
			exit(1);
		}
		while(fgets(cmdLine,sizeof(cmdLine),fp) != NULL){
			printf("%s",cmdLine);

			read_cmdline();
		}
		fclose(fp);
	}
	else{
		while(1){
			printf("prompt> ");

			fgets(cmdLine,sizeof(cmdLine),stdin);
			
			read_cmdline();
		}
	}
	
	return 0;
}
