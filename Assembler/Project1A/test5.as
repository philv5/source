	lw	0	2	n	// r2 = n
	lw	0	3	norer
	lw	0	5	dec	// r5 = -1
	beq	0	0	sum
Done	halt
n	.fill	10
dec	.fill	-1
norer	.fill	-2
sum	nor	2	3	4
	beq	4	0	skip	// if n is odd, then skip
	add	1	2	1	// v0 = v0 + a0
skip	beq	2	0	Done
	add	2	5	2	// n = n - 1
	beq	0	0	sum