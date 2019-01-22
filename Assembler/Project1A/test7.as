	lw	0	1	A
	lw	0	3	end
	lw	0	4	norer
	lw	0	5	one	// r5 = count
	lw	0	6	one
loop	beq	5	3	Exit	// if count == end then exit
	lw	5	2	A	// the nth from A
	nor	2	0	7
	add	7	6	7
	add	1	7	7
	nor	7	4	7	// masking
	beq	0	7	swap	// r2 is bigger than r1
Done	add	5	6	5	// count = count + 1
	beq	0	0	loop
Exit	halt				// r1 = the biggest value
one	.fill	1
A	.fill	13
B	.fill	25
C	.fill	4
D	.fill	9
E	.fill	3
end	.fill	5	// number of val
norer	.fill	-16385	// maskinig for sign bit of 15bit integer (11111111111111111011111111111111)
swap	add	0	2	1
	beq	0	0	Done