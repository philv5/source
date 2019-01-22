	lw	0	1	A
	lw	0	2	B
	lw	0	3	C
	noop
	noop
	noop
	beq	0	0	Done	// branch hazard
	add	1	2	4	// will be noop
	add	2	3	5	// will be noop
	nor	1	0	6	// will be noop
	nor	2	0	7
	noop
	noop
Done	halt				// jump here
A	.fill	10
B	.fill	20
C	.fill	30
