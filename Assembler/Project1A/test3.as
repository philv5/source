	lw	0	2	A
	lw	0	3	B
	beq	0	0	swap
Done	halt
A	.fill	3
B	.fill	4
swap	add	0	3	4	// temp = B
	add	0	2	3	// B = A
	add	0	4	2	// A = temp
	add	0	0	4	// temp = 0
	beq	0	0	Done