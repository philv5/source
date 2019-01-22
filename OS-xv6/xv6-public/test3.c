#include "types.h"
#include "stat.h"
#include "user.h"


int
main(int argc, char* argv[])
{
  int pid;
  int* array;

  printf(1, "Free pages before fork: %d\n", get_n_free_pages());

  pid = fork();

  if(pid == 0){
    printf(0,"Child: forked\n");
    printf(1, "Free pages before malloc: %d\n", get_n_free_pages());
    array = (int*)malloc(sizeof(int)*5000);
    printf(1, "Free pages after malloc: %d\n", get_n_free_pages());
    array[0] = 1;
    array[1000] = 1;
    array[2000] = 1;
    array[3000] = 1;
    array[4000] = 1;
    array[4999] = 1;
    printf(1, "Free pages after access: %d\n", get_n_free_pages());
    exit();
  }

  wait();
  printf(1, "Free pages after wait(): %d\n", get_n_free_pages());

  exit();
}

