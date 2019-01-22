#include<stdio.h>
#include<string.h>
#include<stdlib.h>

#define MAX_MEMORY_SIZE 65536
#define MAX_STRING_SIZE 256
#define MAX_COMMENT_SIZE 500

typedef struct BitFieldR {
	int destReg : 3;
	int unused2 : 13;
	int regB : 3;
	int regA : 3;
	int opcode : 3;
	int unused1 : 7;
}BFR;

typedef struct BitFieldI {
	int offsetField : 16;
	int regB : 3;
	int regA : 3;
	int opcode : 3;
	int unused : 7;
}BFI;

typedef struct BitFieldJ {
	int unused2 : 16;
	int regB : 3;
	int regA : 3;
	int opcode : 3;
	int unused1 : 7;
}BFJ;

typedef struct BitFieldO {
	int unused2 : 22;
	int opcode : 3;
	int unused1 : 7;
}BFO;

typedef union InstructionCode {
	BFR rtype;
	BFI itype;
	BFJ jtype;
	BFO otype;

	int code;
}InstructionCode;

typedef struct Instruction {
	char label[MAX_STRING_SIZE];
	char opcode[MAX_STRING_SIZE];
	char field0[MAX_STRING_SIZE];
	char field1[MAX_STRING_SIZE];
	char field2[MAX_STRING_SIZE];
}Instruction;

int numI = 0;
int errorBit = 0;
InstructionCode icode[MAX_MEMORY_SIZE];
Instruction inst[MAX_MEMORY_SIZE];

int check_symbolic(char* p) {
	if (p == NULL)
		return -1;

	for (int i = 0; i < numI; i++) {
		if ((inst[i].label != NULL) && (strcmp(inst[i].label, p) == 0)) {
			return i;
		}
	}
	return -1;
}

int check_label(char p1[], char p2[]) {
	if ((p1[0] == '\0') || (p2[0] == '\0'))
		return 0;

	if (strcmp(p1, p2) == 0)
		return 1;
	else
		return 0;
}

void readInstruction(char cmd[]) {
	char* ptr;

	//initialize
	inst[numI].label[0] = '\0';
	inst[numI].opcode[0] = '\0';
	inst[numI].field0[0] = '\0';
	inst[numI].field1[0] = '\0';
	inst[numI].field2[0] = '\0';

	ptr = cmd;
	if (sscanf(ptr, "%[^\t\n\r ]", inst[numI].label)) {
		ptr += strlen(inst[numI].label);
	}
	sscanf(ptr, "%*[\t\n\r ]%[^\t\n\r ]%*[\t\n\r ]%[^\t\n\r ]%*[\t\n\r ]%[^\t\n\r ]%*[\t\n\r ]%[^\t\n\r ]",
		inst[numI].opcode, inst[numI].field0, inst[numI].field1, inst[numI].field2);

	//error check

	//check length of label
	if (strlen(inst[numI].label) > 6) {
		printf("error: label is too long in %dth instruction\n", numI);
		errorBit = 1;
		return;
	}
	//chcke label duplication
	for (int i = 0; i < numI; i++) {
		if (check_label(inst[numI].label, inst[i].label)) {
			printf("error: label duplicate [%s] with %dth and %dth instruction\n", inst[i].label, i, numI);
			errorBit = 1;
		}
	}

	//check opcode
	if (inst[numI].opcode[0] == '\0') {
		printf("error: opcode is not exist in %dth instruction\n", numI);
		errorBit = 1;
		return;
	}

	//check field with opcode
	if (strcmp(inst[numI].opcode, "halt") == 0 || strcmp(inst[numI].opcode, "noop") == 0) {
		//require no fields
	}
	else if (strcmp(inst[numI].opcode, ".fill") == 0) {
		if (inst[numI].field0[0] == '\0') {
			printf("error: field mismatch in %dth instruction\n", numI);
			errorBit = 1;
			return;
		}
	}
	else if (strcmp(inst[numI].opcode, "jalr") == 0) {
		if (inst[numI].field0[0] == '\0' || inst[numI].field1[0] == '\0') {
			printf("error: field mismatch in %dth instruction\n", numI);
			errorBit = 1;
			return;
		}
	}
	else {
		if (inst[numI].field0[0] == '\0' || inst[numI].field1[0] == '\0' || inst[numI].field2[0] == '\0') {
			printf("error: field mismatch in %dth instruction\n", numI);
			errorBit = 1;
			return;
		}
	}
}

void assembler() {
	int pc = 0;
	int regA, regB, destReg, offsetField;

	for (pc = 0; pc != numI; pc++) {
		//initialize 0
		icode[pc].code = 0;

		//O-type
		if ((strcmp(inst[pc].opcode, "halt") == 0) || (strcmp(inst[pc].opcode, "noop") == 0)) {
			if (strcmp(inst[pc].opcode, "halt") == 0)
				icode[pc].otype.opcode = 6;
			else
				icode[pc].otype.opcode = 7;
		}
		//J-type
		else if (strcmp(inst[pc].opcode, "jalr") == 0) {
			icode[pc].jtype.opcode = 5;

			regA = atoi(inst[pc].field0);
			if (regA == 0 && (strcmp(inst[pc].field0, "0") != 0)) {
				printf("error: illegal field in %dth instruction\n", pc);
				errorBit = 1;
			}
			else if (regA < 0 || regA > 7) {
				printf("error: illegal register number in %dth instruction\n", pc);
				errorBit = 1;
			}
			else
				icode[pc].jtype.regA = regA;

			regB = atoi(inst[pc].field1);
			if (regB == 0 && (strcmp(inst[pc].field1, "0") != 0)) {
				printf("error: illegal field in %dth instruction\n", pc);
				errorBit = 1;
			}
			else if (regB < 0 || regB > 7) {
				printf("error: illegal register number in %dth instruction\n", pc);
				errorBit = 1;
			}
			else
				icode[pc].jtype.regB = regB;
		}
		//R-type
		else if ((strcmp(inst[pc].opcode, "add") == 0) || (strcmp(inst[pc].opcode, "nor") == 0)) {
			if (strcmp(inst[pc].opcode, "nor") == 0)
				icode[pc].otype.opcode = 1;

			regA = atoi(inst[pc].field0);
			if (regA == 0 && (strcmp(inst[pc].field0, "0") != 0)) {
				printf("error: illegal field in %dth instruction\n", pc);
				errorBit = 1;
			}
			else if (regA < 0 || regA > 7) {
				printf("error: illegal register number in %dth instruction\n", pc);
				errorBit = 1;
			}
			else
				icode[pc].rtype.regA = regA;

			regB = atoi(inst[pc].field1);
			if (regB == 0 && (strcmp(inst[pc].field1, "0") != 0)) {
				printf("error: illegal field in %dth instruction\n", pc);
				errorBit = 1;
			}
			else if (regB < 0 || regB > 7) {
				printf("error: illegal register number in %dth instruction\n", pc);
				errorBit = 1;
			}
			else
				icode[pc].rtype.regB = regB;

			destReg = atoi(inst[pc].field2);
			if (destReg == 0 && (strcmp(inst[pc].field2, "0") != 0)) {
				printf("error: illegal field in %dth instruction\n", pc);
				errorBit = 1;
			}
			else if (destReg < 0 || destReg > 7) {
				printf("error: illegal register number in %dth instruction\n", pc);
				errorBit = 1;
			}
			else
				icode[pc].rtype.destReg = destReg;
		}
		//I-type
		else if ((strcmp(inst[pc].opcode, "lw") == 0) || (strcmp(inst[pc].opcode, "sw") == 0) || (strcmp(inst[pc].opcode, "beq") == 0)) {
			if (strcmp(inst[pc].opcode, "lw") == 0)
				icode[pc].itype.opcode = 2;
			else if (strcmp(inst[pc].opcode, "sw") == 0)
				icode[pc].itype.opcode = 3;
			else
				icode[pc].itype.opcode = 4;

			regA = atoi(inst[pc].field0);
			if (regA == 0 && (strcmp(inst[pc].field0, "0") != 0)) {
				printf("error: illegal field in %dth instruction\n", pc);
				errorBit = 1;
			}
			else if (regA < 0 || regA > 7) {
				printf("error: illegal register number in %dth instruction\n", pc);
				errorBit = 1;
			}
			else
				icode[pc].itype.regA = regA;

			regB = atoi(inst[pc].field1);
			if (regB == 0 && (strcmp(inst[pc].field1, "0") != 0)) {
				printf("error: illegal field in %dth instruction\n", pc);
				errorBit = 1;
			}
			else if (regB < 0 || regB > 7) {
				printf("error: illegal register number in %dth instruction\n", pc);
				errorBit = 1;
			}
			else
				icode[pc].itype.regB = regB;

			//check symbolic
			if ((offsetField = check_symbolic(inst[pc].field2)) != -1) {
				if (strcmp(inst[pc].opcode, "beq") == 0) {
					offsetField = offsetField - (pc + 1);

					//check infinite loop
					if (offsetField == -1) {
						printf("error: infinite loop in %dth instruction\n", pc);
						errorBit = 1;
					}

					icode[pc].itype.offsetField = offsetField;
				}
				else
					icode[pc].itype.offsetField = offsetField;
			}
			else {
				offsetField = atoi(inst[pc].field2);
				if (offsetField == 0 && (strcmp(inst[pc].field2, "0") != 0)) {
					printf("error: use undefined label in %dth instruction\n", pc);
					errorBit = 1;
				}
				//check range of 16bit numeric value 
				else if (offsetField < -32768 || offsetField > 32767) {
					printf("error: offsetField overflow in %dth instruction\n", pc);
					errorBit = 1;
				}
				//check beq address
				else if (strcmp(inst[pc].opcode, "beq") == 0) {
					if (((offsetField + (pc + 1)) < 0 || (offsetField + (pc + 1)) >= numI)) {
						printf("error: illegal address in %dth instruction\n", pc);
						errorBit = 1;
					}
					//check infinite loop
					if (offsetField == -1) {
						printf("error: infinite loop in %dth instruction\n", pc);
						errorBit = 1;
					}
					icode[pc].itype.offsetField = offsetField;
				}
				else
					icode[pc].itype.offsetField = offsetField;
			}
		}
		//.fill
		else if (strcmp(inst[pc].opcode, ".fill") == 0) {
			if ((offsetField = check_symbolic(inst[pc].field0)) != -1)
				icode[pc].code = offsetField;
			else {
				offsetField = atoi(inst[pc].field0);
				if (offsetField == 0 && (strcmp(inst[pc].field0, "0") != 0)) {
					printf("error: use undefined label in %dth instruction\n", pc);
					errorBit = 1;
				}
				else
					icode[pc].code = offsetField;
			}
		}
		else {
			printf("error: unrecognized opcode in %dth instruction\n", pc);
			errorBit = 1;
		}
	}

}

void main(int argc, char* argv[]) {
	FILE* fp;
	char cmd[MAX_MEMORY_SIZE];

	if (argc != 3) {
		printf("error: parameter mismatch\n");
		exit(1);
	}

	if ((fp = fopen(argv[1], "r")) == NULL) {
		printf("error: input file is not exist\n");
		exit(1);
	}

	while (fgets(cmd, MAX_STRING_SIZE, fp) != NULL) {
		readInstruction(cmd);
		numI++;
	}
	fclose(fp);

	//error check
	if (errorBit)
		exit(1);


	assembler();

	//error check
	if (errorBit)
		exit(1);

	if ((fp = fopen(argv[2], "w")) == NULL) {
		printf("error: input file is not exist\n");
		exit(1);
	}

	for (int i = 0; i < numI; i++) {
		printf("%d\n", icode[i].code);
		fprintf(fp, "%d\n", icode[i].code);
	}
	printf("Succesfully make %s!\n", argv[2]);
	fclose(fp);



	exit(0);
}
