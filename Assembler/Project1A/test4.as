	lw	0	2	n	// r2 = n
	lw	0	5	dec	// r5 = -1
	beq	0	0	sum
Done	halt
n	.fill	5
dec	.fill	-1
sum	add	1	2	1	// r1 = v0
	beq	2	0	Done
	add	2	5	2	// n = n - 1
	beq	0	0	sum