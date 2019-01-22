	lw	0	2	targt
	lw	0	4	end
	lw	0	5	one
loop	lw	0	6	count
	lw	6	3	A
	beq	4	5	Exit
	add	6	5	6	// count = count + 1
	sw	0	6	count	// save count
	nor	3	0	3
	add	3	5	3
	add	2	3	3
	beq	0	3	Hit
	beq	0	0	loop
Exit	halt				// if target is exist in them, then r1 = 1, else r1 = 0
Hit	add	0	5	1
	beq	0	0	Exit
targt	.fill	2
one	.fill	1
count	.fill	0
A	.fill	10
B	.fill	3
C	.fill	4
D	.fill	2
E	.fill	8
end	.fill	5