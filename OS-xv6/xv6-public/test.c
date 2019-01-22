#include "types.h"
#include "stat.h"
#include "user.h"

int
main(int argc, char* argv[])
{
	int pid = getpid();
	int ppid = getppid();
	printf(2,"My pid is %d\nMy ppid is %d\n",pid,ppid);
	exit();
}
