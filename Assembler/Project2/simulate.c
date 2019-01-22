#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#define NUMMEMORY 65536 /* maximum number of words in memory */
#define NUMREGS 8 /* number of machine registers */
#define ADD 0
#define NOR 1
#define LW 2
#define SW 3
#define BEQ 4
#define JALR 5 /* JALR will not implemented for this project */
#define HALT 6
#define NOOP 7

#define NOOPINSTRUCTION 0x1c00000

#define MAXLINELENGTH 1000

typedef struct IFIDStruct {
	int instr;
	int pcPlus1;
} IFIDType;

typedef struct IDEXStruct {
	int instr;
	int pcPlus1;
	int readRegA;
	int readRegB;
	int offset;
} IDEXType;

typedef struct EXMEMStruct {
	int instr;
	int branchTarget;
	int aluResult;
	int readRegB;
} EXMEMType;

typedef struct MEMWBStruct {
	int instr;
	int writeData;
} MEMWBType;

typedef struct WBENDStruct {
	int instr;
	int writeData;
} WBENDType;

typedef struct stateStruct {
	int pc;
	int instrMem[NUMMEMORY];
	int dataMem[NUMMEMORY];
	int reg[NUMREGS];
	int numMemory;
	IFIDType IFID;
	IDEXType IDEX;
	EXMEMType EXMEM;
	MEMWBType MEMWB;
	WBENDType WBEND;
	int cycles; /* number of cycles run so far */
} stateType;

int
field0(int instruction)
{
	return((instruction >> 19) & 0x7);
}

int
field1(int instruction)
{
	return((instruction >> 16) & 0x7);
}

int
field2(int instruction)
{
	return(instruction & 0xFFFF);
}

int
opcode(int instruction)
{
	return(instruction >> 22);
}

void
printInstruction(int instr)
{

	char opcodeString[10];

	if (opcode(instr) == ADD) {
		strcpy(opcodeString, "add");
	}
	else if (opcode(instr) == NOR) {
		strcpy(opcodeString, "nor");
	}
	else if (opcode(instr) == LW) {
		strcpy(opcodeString, "lw");
	}
	else if (opcode(instr) == SW) {
		strcpy(opcodeString, "sw");
	}
	else if (opcode(instr) == BEQ) {
		strcpy(opcodeString, "beq");
	}
	else if (opcode(instr) == JALR) {
		strcpy(opcodeString, "jalr");
	}
	else if (opcode(instr) == HALT) {
		strcpy(opcodeString, "halt");
	}
	else if (opcode(instr) == NOOP) {
		strcpy(opcodeString, "noop");
	}
	else {
		strcpy(opcodeString, "data");
	}
	printf("%s %d %d %d\n", opcodeString, field0(instr), field1(instr),
		field2(instr));
}

void
printState(stateType *statePtr)
{
	int i;
	printf("\n@@@\nstate before cycle %d starts\n", statePtr->cycles);
	printf("\tpc %d\n", statePtr->pc);

	printf("\tdata memory:\n");
	for (i = 0; i<statePtr->numMemory; i++) {
		printf("\t\tdataMem[ %d ] %d\n", i, statePtr->dataMem[i]);
	}
	printf("\tregisters:\n");
	for (i = 0; i<NUMREGS; i++) {
		printf("\t\treg[ %d ] %d\n", i, statePtr->reg[i]);
	}
	printf("\tIFID:\n");
	printf("\t\tinstruction ");
	printInstruction(statePtr->IFID.instr);
	printf("\t\tpcPlus1 %d\n", statePtr->IFID.pcPlus1);
	printf("\tIDEX:\n");
	printf("\t\tinstruction ");
	printInstruction(statePtr->IDEX.instr);
	printf("\t\tpcPlus1 %d\n", statePtr->IDEX.pcPlus1);
	printf("\t\treadRegA %d\n", statePtr->IDEX.readRegA);
	printf("\t\treadRegB %d\n", statePtr->IDEX.readRegB);
	printf("\t\toffset %d\n", statePtr->IDEX.offset);
	printf("\tEXMEM:\n");
	printf("\t\tinstruction ");
	printInstruction(statePtr->EXMEM.instr);
	printf("\t\tbranchTarget %d\n", statePtr->EXMEM.branchTarget);
	printf("\t\taluResult %d\n", statePtr->EXMEM.aluResult);
	printf("\t\treadRegB %d\n", statePtr->EXMEM.readRegB);
	printf("\tMEMWB:\n");
	printf("\t\tinstruction ");
	printInstruction(statePtr->MEMWB.instr);
	printf("\t\twriteData %d\n", statePtr->MEMWB.writeData);
	printf("\tWBEND:\n");
	printf("\t\tinstruction ");
	printInstruction(statePtr->WBEND.instr);
	printf("\t\twriteData %d\n", statePtr->WBEND.writeData);
}

int
isRegWrite(int instr)
{
	if (opcode(instr) == ADD || opcode(instr) == NOR)
		return 1;
	else
		return 0;
}

int
convertNum(int num)
{
	/* convert a 16-bit number into a 32-bit Linux integer */
	if (num & (1 << 15)) {
		num -= (1 << 16);
	}
	return(num);
}

void
initState(stateType *statePtr)
{
	int i;

	statePtr->cycles = 0;
	statePtr->pc = 0;
	for (i = 0; i<NUMREGS; i++) {
		statePtr->reg[i] = 0;
	}
	statePtr->IFID.instr = NOOPINSTRUCTION;
	statePtr->IDEX.instr = NOOPINSTRUCTION;
	statePtr->EXMEM.instr = NOOPINSTRUCTION;
	statePtr->MEMWB.instr = NOOPINSTRUCTION;
	statePtr->WBEND.instr = NOOPINSTRUCTION;
}

int
main(int argc, char *argv[])
{
	char line[MAXLINELENGTH];
	stateType state;
	stateType newState;
	FILE *filePtr;
	int i;
	int regA;
	int regB;

	/* initialized state */
	initState(&state);

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

		if (sscanf(line, "%d", state.instrMem + state.numMemory) != 1) {
			printf("error in reading address %d\n", state.numMemory);
			exit(1);
		}
		if (sscanf(line, "%d", state.dataMem + state.numMemory) != 1) {
			printf("error in reading address %d\n", state.numMemory);
			exit(1);
		}
		printf("memory[%d]=%d\n", state.numMemory, state.instrMem[state.numMemory]);
	}

	printf("%d memory words\n", state.numMemory);
	printf("\tinstruction memory:\n");
	for (i = 0; i < state.numMemory; i++) {
		printf("\t\tinstrMem[ %d ] ", i);
		printInstruction(state.instrMem[i]);
	}

	/* check
	printState(&state);
	*/

	while (1) {

		printState(&state);

		/* check for halt */
		if (opcode(state.MEMWB.instr) == HALT) {
			printf("machine halted\n");
			printf("total of %d cycles executed\n", state.cycles);
			exit(0);
		}

		newState = state;
		newState.cycles++;

		/* --------------------- IF stage --------------------- */

		newState.IFID.instr = state.instrMem[state.pc];
		newState.IFID.pcPlus1 = ++state.pc;
		newState.pc = state.pc;

		/* --------------------- ID stage --------------------- */

		newState.IDEX.instr = state.IFID.instr;
		newState.IDEX.pcPlus1 = state.IFID.pcPlus1;
		newState.IDEX.readRegA = state.reg[field0(state.IFID.instr)];
		newState.IDEX.readRegB = state.reg[field1(state.IFID.instr)];
		newState.IDEX.offset = convertNum(field2(state.IFID.instr));

		/* --------------------- EX stage --------------------- */

		newState.EXMEM.instr = state.IDEX.instr;

		// check data hazard 

		// case of regA
		if (isRegWrite(state.EXMEM.instr) && (field2(state.EXMEM.instr) == field0(state.IDEX.instr))) {
			regA = state.EXMEM.aluResult;
		}
		else if (isRegWrite(state.MEMWB.instr) && (field2(state.MEMWB.instr) == field0(state.IDEX.instr))) {
			regA = state.MEMWB.writeData;
		}
		else if (isRegWrite(state.WBEND.instr) && (field2(state.WBEND.instr) == field0(state.IDEX.instr))) {
			regA = state.WBEND.writeData;
		}
		else {
			regA = state.IDEX.readRegA;
		}

		// case of regB
		if (isRegWrite(state.EXMEM.instr) && (field2(state.EXMEM.instr) == field1(state.IDEX.instr))) {
			regB = state.EXMEM.aluResult;
		}
		else if (isRegWrite(state.MEMWB.instr) && (field2(state.MEMWB.instr) == field1(state.IDEX.instr))) {
			regB = state.MEMWB.writeData;
		}
		else if (isRegWrite(state.WBEND.instr) && (field2(state.WBEND.instr) == field1(state.IDEX.instr))) {
			regB = state.WBEND.writeData;
		}
		else {
			regB = state.IDEX.readRegB;
		}

		// ALU
		if (opcode(state.IDEX.instr) == ADD) {
			newState.EXMEM.aluResult = regA + regB;
		}
		else if (opcode(state.IDEX.instr) == NOR) {
			newState.EXMEM.aluResult = ~(regA | regB);
		}
		else if (opcode(state.IDEX.instr) == LW || opcode(state.IDEX.instr) == SW) {
			newState.EXMEM.aluResult = regA + state.IDEX.offset;
		}
		else if (opcode(state.IDEX.instr) == BEQ) {
			newState.EXMEM.aluResult = regA - regB;
		}

		newState.EXMEM.branchTarget = state.IDEX.pcPlus1 + state.IDEX.offset;
		newState.EXMEM.readRegB = state.IDEX.readRegB;

		/* --------------------- MEM stage --------------------- */

		newState.MEMWB.instr = state.EXMEM.instr;

		// branch jump
		if (state.EXMEM.aluResult == 0 && opcode(state.EXMEM.instr) == BEQ) {
			newState.pc = state.EXMEM.branchTarget;
			newState.IFID.instr = NOOPINSTRUCTION;
			newState.IDEX.instr = NOOPINSTRUCTION;
			newState.EXMEM.instr = NOOPINSTRUCTION;
		}
		else {
			if (opcode(state.EXMEM.instr) == ADD || opcode(state.EXMEM.instr) == NOR) {
				newState.MEMWB.writeData = state.EXMEM.aluResult;
			}
			else if (opcode(state.EXMEM.instr) == LW) {
				newState.MEMWB.writeData = state.dataMem[state.EXMEM.aluResult];
			}
			else if (opcode(state.EXMEM.instr) == SW) {
				newState.dataMem[state.EXMEM.aluResult] = state.EXMEM.readRegB;
			}
		}

		/* --------------------- WB stage --------------------- */

		newState.WBEND.instr = state.MEMWB.instr;
		newState.WBEND.writeData = state.MEMWB.writeData;
		if (opcode(state.MEMWB.instr) == ADD || opcode(state.MEMWB.instr) == NOR) {
			newState.reg[field2(state.MEMWB.instr)] = state.MEMWB.writeData;
		}
		else if (opcode(state.MEMWB.instr) == LW) {
			newState.reg[field1(state.MEMWB.instr)] = state.MEMWB.writeData;
		}

		state = newState; /* this is the last statement before end of the loop.
						  It marks the end of the cycle and updates the
						  current state with the values calculated in this
						  cycle */
	}

	return(0);
}

