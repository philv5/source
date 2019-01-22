	lw	0	1	A
	lw	0	2	B
	noop
	noop
	noop
	add	1	2	4
	add	1	4	5	// reg4 set by first add
Done	halt
A	.fill	10
B	.fill	20
