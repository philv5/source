#include "types.h"
#include "stat.h"
#include "user.h"

int
main(int argc, char* argv[])
{
  int num;
  int* array;

  num = get_n_free_pages();
  printf(1, "Before malloc: %d\n", num);

  num = get_n_free_pages();
  array = (int*)malloc(sizeof(int) * 100);
  printf(1, "After malloc: %d\n", num);

  array[0] = 1;
  num = get_n_free_pages();
  printf(1, "After access array: %d\n", num);

  exit();
}

