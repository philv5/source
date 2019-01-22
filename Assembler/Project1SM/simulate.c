#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define NUMMEMORY 65536 /* maximum number of words in memory */
#define NUMREGS 8 /* number of machine registers */
#define MAXLINELENGTH 1000

typedef struct BitFieldR {
	unsigned int destReg : 3;
	unsigned int unused2 : 13;
	unsigned int regB : 3;
	unsigned int regA : 3;
	unsigned int opcode : 3;
	unsigned int unused1 : 7;
}BFR;

typedef struct BitFieldI {
	int offsetField : 16;
	unsigned int regB : 3;
	unsigned int regA : 3;
	unsigned int opcode : 3;
	unsigned int unused : 7;
}BFI;

typedef struct BitFieldJ {
	unsigned int unused2 : 16;
	unsigned int regB : 3;
	unsigned int regA : 3;
	unsigned int opcode : 3;
	unsigned int unused1 : 7;
}BFJ;

typedef struct BitFieldO {
	unsigned int unused2 : 22;
	unsigned int opcode : 3;
	unsigned int unused1 : 7;
}BFO;

typedef union InstructionCode {
	BFR rtype;
	BFI itype;
	BFJ jtype;
	BFO otype;
	int mcode;
}Instruction;

typedef struct stateStruct {
	int pc;
	int mem[NUMMEMORY];
	int reg[NUMREGS];
	int numMemory;
} stateType;

void printState(stateType *statePtr) {
	int i;
	printf("\n@@@\nstate:\n");
	printf("\tpc %d\n", statePtr->pc);
	printf("\tmemory:\n");
	for (i = 0; i<statePtr->numMemory; i++) {
		printf("\t\tmem[ %d ] %d\n", i, statePtr->mem[i]);
	}
	printf("\tregisters:\n");
	for (i = 0; i<NUMREGS; i++) {
		printf("\t\treg[ %d ] %d\n", i, statePtr->reg[i]);
	}
	printf("end state\n");
}

void initState(stateType *statePtr) {
	int i;
	statePtr->pc = 0;
	for (i = 0; i<NUMREGS; i++) {
		statePtr->reg[i] = 0;
	}
}

void startProgram(stateType *statePtr) {
	Instruction inst;
	int pc, count, opcode, A, B, dest, offset;

	count = 0;

	do {
		//Instruction fetch
		pc = statePtr->pc;
		inst.mcode = statePtr->mem[pc];
		statePtr->pc++;

		//Decode
		opcode = inst.rtype.opcode;

		//Execute
		count++;

		//case of "add"
		if (opcode == 0) {
			A = statePtr->reg[inst.rtype.regA];
			B = statePtr->reg[inst.rtype.regB];
			dest = inst.rtype.destReg;
			statePtr->reg[dest] = A + B;
		}
		//case of "nor"
		else if (opcode == 1) {
			A = statePtr->reg[inst.rtype.regA];
			B = statePtr->reg[inst.rtype.regB];
			dest = inst.rtype.destReg;
			statePtr->reg[dest] = ~(A | B);
		}
		//case of "lw"
		else if (opcode == 2) {
			A = statePtr->reg[inst.itype.regA];
			B = inst.itype.regB;
			offset = inst.itype.offsetField;
			statePtr->reg[B] = statePtr->mem[A + offset];
		}
		//case of "sw"
		else if (opcode == 3) {
			A = statePtr->reg[inst.itype.regA];
			B = statePtr->reg[inst.itype.regB];
			offset = inst.itype.offsetField;
			statePtr->mem[A + offset] = B;
		}
		//case of "beq"
		else if (opcode == 4) {
			A = statePtr->reg[inst.itype.regA];
			B = statePtr->reg[inst.itype.regB];
			offset = inst.itype.offsetField;
			if (A == B) {
				statePtr->pc = statePtr->pc + offset;
			}
		}
		//case of "jalr"
		else if (opcode == 5) {
			B = inst.jtype.regB;
			statePtr->reg[B] = statePtr->pc;
			A = statePtr->reg[inst.jtype.regA];
			statePtr->pc = A;
		}
		//case of "halt"
		else if (opcode == 6) {
			printf("machine halted\n");
			printf("total of %d instructions executed\n", count);
			return;
		}
		//case of "noop"
		else if (opcode == 7) {
			//Do nothing
		}
		//error
		else {
			printf("error unknown opcode: %d\n", opcode);
			exit(1);
		}

		//print state
		printState(statePtr);
	} while (1);
}

int main(int argc, char *argv[]) {
	char line[MAXLINELENGTH];
	stateType state;
	FILE *filePtr;

	if (argc != 2) {
		printf("error: usage: %s <machine-code file>\n", argv[0]);
		exit(1);
	}

	filePtr = fopen(argv[1], "r");
	if (filePtr == NULL) {
		printf("error: can't open file %s", argv[1]);
		perror("fopen");
		exit(1);
	}

	/* read in the entire machine-code file into memory */
	for (state.numMemory = 0; fgets(line, MAXLINELENGTH, filePtr) != NULL; state.numMemory++) {

		if (sscanf(line, "%d", state.mem + state.numMemory) != 1) {
			printf("error in reading address %d\n", state.numMemory);
			exit(1);
		}
		printf("memory[%d]=%d\n", state.numMemory, state.mem[state.numMemory]);
	}

	initState(&state);
	printState(&state);

	startProgram(&state);

	printf("final state of machine:\n");
	printState(&state);

	return(0);
}

