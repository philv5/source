	lw	0	2	A
	lw	0	3	B
	lw	0	4	norer
	lw	0	5	one
	nor	3	0	6
	add	6	5	6	// r6 = -B
	add	2	6	6	// r6 = A - B
	nor	6	4	5	// masking
	beq	0	5	swap	// B is bigeer than A
Done	add	0	2	1	// r1 = the bigger of A and B
	halt
one	.fill	1
A	.fill	13
B	.fill	25
norer	.fill	-16385	// maskinig for sign bit of 15bit integer (11111111111111111011111111111111)
swap	add	0	3	5	// temp = B
	add	0	2	3	// B = A
	add	0	5	2	// A = temp
	beq	0	0	Done