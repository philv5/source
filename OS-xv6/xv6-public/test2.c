#include "types.h"
#include "stat.h"
#include "user.h"

int global = 1;

int
main(int argc, char* argv[])
{
  int pid;

  printf(1, "Free pages before fork: %d\n", get_n_free_pages());

  pid = fork();

  if(pid == 0){
    printf(1,"Child: global = %d\n",global);
    printf(1, "Free pages before any changes: %d\n", get_n_free_pages());
    global = 5;
    printf(1,"Child: global = %d\n",global);
    printf(1, "Free pages after change: %d\n", get_n_free_pages());
    exit();
  }

  printf(1,"Parent: global = %d\n",global);
  wait();
  printf(1,"Parent: global = %d\n",global);
  printf(1, "Free pages after wait(): %d\n", get_n_free_pages());

  exit();
}

