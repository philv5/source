	lw	0	2	A
	lw	0	3	B
	lw	0	4	15	// r4 = 10
	add	0	2	1	// r1: argument
	jalr	4	7		// r7: return address
	add	0	6	2
	add	0	3	1
	jalr	4	7
	add	0	6	3
	halt				// r2 = A * 2, r3 = B * 2
doub	add	1	1	1
	add	0	1	6	// r6: v0
	jalr	7	5
A	.fill	14
B	.fill	25
ten	.fill	10