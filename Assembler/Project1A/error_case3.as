        lw      0       1       six     //use undefined label
        lw      1       8       3       //illegal register number
start   add     1       2       a       //illegal filed
        beq     0       1       2       
        beq     0       0       start   
        noop
done    halt                            
five    .fill   5
neg1    .fill   -1
stAddr  .fill   start                   