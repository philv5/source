        lw      0       1       five    //1th and 7th instruction is field mismatch. 5th is not exist opcode.
        lw      1       2               
start   add     1       2       1       
        beq     0       1       2       
        beq     0       0       start   
					
done    halt                            
five    .fill 				
neg1    .fill   -1
stAddr  .fill   start                   