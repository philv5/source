	lw	0	2	mcand
	lw	0	3	mplier
	lw	0	4	norer
	lw	0	6	end
	lw	0	7	one
	beq	0	2	done
	beq	0	3	done
loop	nor	3	4	4
	beq	0	4	ADD
cal	add	2	2	2	//shift left
	lw	0	4	norer
	add	4	4	4	// shift left
	add	4	7	4	// norer + 1
	sw	0	4	norer
	add	5	7	5	// count + 1
	beq	5	6	done
	beq	0	0	loop
ADD	add	2	1	1
	beq	0	0	cal
done	halt
mcand	.fill	32766
mplier	.fill	10383
norer	.fill	-2
end	.fill	15
one	.fill	1