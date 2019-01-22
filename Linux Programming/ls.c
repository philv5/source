#include<stdio.h>
#include<stdlib.h>
#include <string.h>
#include<unistd.h>
#include<dirent.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <pwd.h>
#include <grp.h>
#include <time.h>

#define BUFSIZE 256

char* get_filename(char* p_path);
int get_filesize(char* p_path);
void print_monthDayTime(char* time);
void print_sort(char* array[], int count);
char* read_sym(char* p_path);
void print_stat(struct stat p_st, char* p_path);
void ls(char p_option, char* p_path);

int main(int argc, char* argv[]){
	int i;
	char option;
	char* p;
	char* pathname;	
	
	//initialize to null
	pathname = NULL; 
	option = '\0';	
	
	
	for(i = 1; i < argc; i++){
		p = argv[i];
		if(*p == '-')
			option = *(p+1);
		else
			pathname = p;

	}

	ls(option, pathname);
	
	return 0;
}


char* get_filename(char* p_path){
	char* filename;
	char* tp;
	
	tp = strtok(p_path,"/");
	filename = tp;
	
	while((tp = strtok(NULL,"/"))){
		filename = tp;
	}

	return filename;
}

int get_filesize(char* p_path){
	int ret;
	struct stat st;
	
	if((ret = lstat(p_path,&st)) == -1){
		perror("lstat : ");
		exit(-1);
	}

	return (int)st.st_size;
}


void print_monthDayTime(char* time){
	char* tp;

	tp = strtok(time," :");
	tp = strtok(NULL," :");
	printf("%s ",tp);
	tp = strtok(NULL," :");
	printf("%2s ",tp);
	tp = strtok(NULL," :");
	printf("%s:",tp);
	tp = strtok(NULL," :");
	printf("%s ",tp);
}

void print_sort(char* array[], int count){
	int i, j, key;
	char* str;
	//insertion sort
	for (i = 1; i < count; i++) {
		key = get_filesize(array[i]);
		str = array[i];
		
		for (j = i - 1; j >= 0 && get_filesize(array[j]) < key; j--)
			array[j + 1] = array[j];
		array[j + 1] = str;
	}
	
	//print 
	for(i = 0; i < count; i++){
		printf("%s\n",array[i]);
	}

}

char* read_sym(char* p_path){
	ssize_t len;
    	char buf[BUFSIZE];

	while((len = readlink(p_path, buf, BUFSIZE-1)) >= 0){
		buf[len] = '\0';
		p_path = buf;
	}
	
	return p_path;
}

void print_stat(struct stat p_st, char* p_path){
	char str[11];
	struct passwd *passwd;
	struct group *group;

	//set mode to array and print
	str[0] = (S_ISBLK(p_st.st_mode))  ? 'b' :
           	 (S_ISCHR(p_st.st_mode))  ? 'c' :
           	 (S_ISDIR(p_st.st_mode))  ? 'd' :
           	 (S_ISREG(p_st.st_mode))  ? '-' :
           	 (S_ISFIFO(p_st.st_mode)) ? 'p' :
          	 (S_ISLNK(p_st.st_mode))  ? 'l' :
          	 (S_ISSOCK(p_st.st_mode)) ? 's' : '?';
  	str[1] = p_st.st_mode & S_IRUSR ? 'r' : '-';
  	str[2] = p_st.st_mode & S_IWUSR ? 'w' : '-';
  	str[3] = p_st.st_mode & S_ISUID ? (p_st.st_mode & S_IXUSR ? 's' : 'S')
                          	: (p_st.st_mode & S_IXUSR ? 'x' : '-');
  	str[4] = p_st.st_mode & S_IRGRP ? 'r' : '-';
  	str[5] = p_st.st_mode & S_IWGRP ? 'w' : '-';
  	str[6] = p_st.st_mode & S_ISGID ? (p_st.st_mode & S_IXGRP ? 's' : 'S')
                          	: (p_st.st_mode & S_IXGRP ? 'x' : '-');
  	str[7] = p_st.st_mode & S_IROTH ? 'r' : '-';
  	str[8] = p_st.st_mode & S_IWOTH ? 'w' : '-';
  	str[9] = p_st.st_mode & S_ISVTX ? (p_st.st_mode & S_IXOTH ? 't' : 'T')
                          	: (p_st.st_mode & S_IXOTH ? 'x' : '-');
  	str[10] = '\0';
	printf("%s ",str);

	//print the number of link
	printf("%d ",(int)p_st.st_nlink);

	//get name of owner and name of group to print
	passwd = getpwuid(p_st.st_uid);
  	if (passwd != NULL) 
    		printf("%s ", passwd->pw_name);
  	else 
    		printf("%d ", (int)p_st.st_uid);
  	
	group = getgrgid(p_st.st_gid);
  	if (group != NULL) 
    		printf("%s ", group->gr_name);
  	else 
    		printf("%d ", (int)p_st.st_gid);
  	
	//print size
	printf("%5d ", (int)p_st.st_size);

	//print time(month, day, time)
	print_monthDayTime(ctime(&p_st.st_mtime));
	
	//print filename
	printf("%s ",p_path);

	//case of symbolic link
	if(S_ISLNK(p_st.st_mode))
		printf("-> %s\n", read_sym(p_path));
	else
		printf("\n");
	


}

void ls(char p_option, char* p_path){
	int i, ret, count;
	char* path;
	DIR* dir;
	struct dirent* dp;
	struct stat st;
	char* array[BUFSIZE];
	
	count = 0;

	//no path
	if(p_path == NULL){
		//open current directory
		if((dir = opendir(".")) == NULL){
			perror("opendir : ");
			exit(-1);
		}
		switch(p_option){
			//no option
			case '\0':	
				for(dp = readdir(dir); dp != NULL; dp = readdir(dir)){
					if(*(dp->d_name) != '.')
						printf("%s\n",dp->d_name);
				}

				break;

			case 'l':
				for(dp = readdir(dir); dp != NULL; dp = readdir(dir)){
					if(*(dp->d_name) != '.'){
						//get file information of file
						if((ret = lstat(dp->d_name,&st)) == -1){
							perror("lstat : ");
							exit(-1);
						}
						//print long format of the file
						print_stat(st,dp->d_name);
					}
				}
				
				break;
			case 'S':
				//set in array
				for(dp = readdir(dir); dp != NULL; dp = readdir(dir)){
					if(*(dp->d_name) != '.'){
						array[count++] = dp->d_name;
					}
				}
				
				print_sort(array, count);

				break;

			case 'L':	
				for(dp = readdir(dir); dp != NULL; dp = readdir(dir)){
					if(*(dp->d_name) != '.')
						printf("%s\n",dp->d_name);
				}

				break;
		}
		closedir(dir);
	}
	
	//there is a path
	else{	
		switch(p_option){
			//no option
			case '\0':
				//get file information of pahname
				if((ret = lstat(p_path,&st)) == -1){
					perror("stat : ");
					exit(-1);
				}
				//check of directory
				if(S_ISDIR(st.st_mode)){
					if((dir = opendir(p_path)) == NULL){
						perror("opendir : ");
						exit(-1);
					}

					printf("%s :\n",get_filename(p_path));	
					for(dp = readdir(dir); dp != NULL; dp = readdir(dir)){
						if(*(dp->d_name) != '.')
							printf("%s\n",dp->d_name);
					}
					closedir(dir);
				}
				else{
					printf("%s\n",get_filename(p_path));
				}
				break;

			case 'l':
				//get file information of pahname
				if((ret = lstat(p_path,&st)) == -1){
					perror("stat : ");
					exit(-1);
				}
				//check of directory 
				if(S_ISDIR(st.st_mode)){
					if((dir = opendir(p_path)) == NULL){
						perror("opendir : ");
						exit(-1);
					}

					printf("%s :\n",get_filename(p_path));
					//move on the directory
					chdir(p_path);	
					for(dp = readdir(dir); dp != NULL; dp = readdir(dir)){
						if(*(dp->d_name) != '.'){
							if((ret = stat(dp->d_name,&st)) == -1){
								perror("stat : ");
								exit(-1);
							}
							print_stat(st,dp->d_name);
						}
					}
					closedir(dir);
				}
				else{
					print_stat(st,p_path);
				}
				break;

			case 'S':
				if((ret = lstat(p_path,&st)) == -1){
					perror("stat : ");
					exit(-1);
				}
				//check of directory 
				if(S_ISDIR(st.st_mode)){
					if((dir = opendir(p_path)) == NULL){
						perror("opendir : ");
						exit(-1);
					}

					printf("%s :\n",get_filename(p_path));
					//move on the directory
					chdir(p_path);	
					for(dp = readdir(dir); dp != NULL; dp = readdir(dir)){
						if(*(dp->d_name) != '.'){
							array[count++] = dp->d_name;
						}
					}
					print_sort(array, count);
					closedir(dir);
				}

				else{
					printf("%s\n",p_path);
				}
				break;
			case 'L':
				//using stat function to open link
				if((ret = stat(p_path,&st)) == -1){
					perror("stat : ");
					exit(-1);
				}
				//check of directory
				if(S_ISDIR(st.st_mode)){
					if((dir = opendir(p_path)) == NULL){
						perror("opendir : ");
						exit(-1);
					}

					printf("%s :\n",get_filename(p_path));	
					for(dp = readdir(dir); dp != NULL; dp = readdir(dir)){
						if(*(dp->d_name) != '.')
							printf("%s\n",dp->d_name);
					}
					closedir(dir);
				}
				else{
					printf("%s\n",get_filename(p_path));
				}
				break;
		}
	}
	

}







