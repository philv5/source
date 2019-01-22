        lw      0       1       thre
        lw      1       2       neg1
	noop
	noop
	noop
start   add     1       2       1      // decrement reg1
        beq     0       1       done   // reg1 set by first add, and branch hazard
        beq     0       0       start  // branch hazard
done    halt                           // end of program
thre    .fill   3
neg1    .fill   -1