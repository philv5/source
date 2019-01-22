	lw	0	6	one	// r6 = 1
	lw	0	2	g	// r2 = g
	lw	0	3	h	// r3 = h
	lw	0	4	i	// r4 = i
	lw	0	5	j	// r5 = j
	beq	0	0	leaf
Exit	halt				// r1 = 15
one	.fill	1
g	.fill	12
h	.fill	13
i	.fill	4
j	.fill	6
leaf	add	2	3	2	// r2 = g + h
	add	4	5	4	// r4 = i + j
	nor	4	0	4
	add	4	6	4	// r4 = - (i + j)
	add	2	4	1	// r1 = (g + h) - (i + j)
	beq	0	0	Exit