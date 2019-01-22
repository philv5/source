	lw	0	1	A
	lw	0	2	B
	lw	0	3	C
	noop
	noop
	noop
	add	1	2	4	// reg4 = 30
	add	2	3	5	// reg 5 = 50
	add	4	5	6	// reg4 set by first add, reg5 set by second add
Done	halt
A	.fill	10
B	.fill	20
C	.fill	30
