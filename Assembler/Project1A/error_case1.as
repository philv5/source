        lw      0       1       five    
        lw      1       2       3       
notI
start   add     1       2       1       
        beq     0       1       2       
        beq     0       0       start
        noop
done123 halt                            //label length error
five    .fill   5
neg1    .fill   -1
stAddr  .fill   start
start	.fill	10			//label duplication