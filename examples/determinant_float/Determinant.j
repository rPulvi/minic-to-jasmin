	.class public Determinant
	.super java/lang/Object
	
	; standard initializer
	
	
	.method public <init>()V
	aload 0
	invokenonvirtual java/lang/Object/<init>()V
	return
	.end method
	
	
	.method public static main()V
	.limit locals 39
	.limit stack 39
	ldc 0
	istore 13
; float det ----------------------------
	fconst_0
	fstore 9
; int SIZE1 ----------------------------
	iconst_0
	istore 11
; int SIZE2 ----------------------------
	iconst_0
	istore 12
; SIZE1 = 2 ----------------------------
	iconst_2
	istore 11
; SIZE2 = 3 ----------------------------
	iconst_3
	istore 12
; matrix = new float[SIZE1] [SIZE1] ----------------------------
	iload 11
	iload 11
	multianewarray [[F 2
	astore 10
; matrix [0] [0] = 0.0 ----------------------------
	aload 10
	iconst_0
	aaload
	iconst_0
	fconst_0
	fastore
; matrix [1] [0] = 3.3 ----------------------------
	aload 10
	iconst_1
	aaload
	iconst_0
	ldc 3.3
	fastore
; matrix [0] [1] = 1.1 ----------------------------
	aload 10
	iconst_0
	aaload
	iconst_1
	ldc 1.1
	fastore
; matrix [1] [1] = -1.1 ----------------------------
	aload 10
	iconst_1
	aaload
	iconst_1
	ldc 1.1
	fneg
	fastore
	aload 10
	iload 11
	invokestatic MyMinicLib/print_matrix([[FI)V
; det = bidim(matrix) ----------------------------
	aload 10
	invokestatic MyMinicLib/bidim([[F)F
	fstore 9
	ldc "Il suo determinante e':  "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	fload 9
	invokestatic MinicLib/float2string(F)Ljava/lang/String;
	invokestatic MinicLib/println(Ljava/lang/String;)V
; matrix = new float[SIZE2] [SIZE2] ----------------------------
	iload 12
	iload 12
	multianewarray [[F 2
	astore 10
; matrix [0] [0] = -2.3 ----------------------------
	aload 10
	iconst_0
	aaload
	iconst_0
	ldc 2.3
	fneg
	fastore
; matrix [0] [1] = 1.7 ----------------------------
	aload 10
	iconst_0
	aaload
	iconst_1
	ldc 1.7
	fastore
; matrix [0] [2] = 5.5 ----------------------------
	aload 10
	iconst_0
	aaload
	iconst_2
	ldc 5.5
	fastore
; matrix [1] [0] = 3.4 ----------------------------
	aload 10
	iconst_1
	aaload
	iconst_0
	ldc 3.4
	fastore
; matrix [1] [1] = -1.7 ----------------------------
	aload 10
	iconst_1
	aaload
	iconst_1
	ldc 1.7
	fneg
	fastore
; matrix [1] [2] = -1.9 ----------------------------
	aload 10
	iconst_1
	aaload
	iconst_2
	ldc 1.9
	fneg
	fastore
; matrix [2] [0] = 0.4 ----------------------------
	aload 10
	iconst_2
	aaload
	iconst_0
	ldc 0.4
	fastore
; matrix [2] [1] = 1.3 ----------------------------
	aload 10
	iconst_2
	aaload
	iconst_1
	ldc 1.3
	fastore
; matrix [2] [2] = -1.6 ----------------------------
	aload 10
	iconst_2
	aaload
	iconst_2
	ldc 1.6
	fneg
	fastore
	aload 10
	iload 12
	invokestatic MyMinicLib/print_matrix([[FI)V
	ldc 0
	istore 13
	iload 12
	iconst_3
	if_icmpeq #0
	goto #1
#0:
	ldc 1
	istore 13
#1:
	iload 13
	ifgt STMT_1
	goto ELSE_1
	STMT_1:
; det = sarrus(matrixSIZE2) ----------------------------
	aload 10
	iload 12
	invokestatic MyMinicLib/sarrus([[FI)F
	fstore 9
	ldc "Il suo determinante e':  "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	fload 9
	invokestatic MinicLib/float2string(F)Ljava/lang/String;
	invokestatic MinicLib/println(Ljava/lang/String;)V
	goto EXIT_1
	ELSE_1:
	ldc "Non posso usare Sarrus per una matrice "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 12
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "*"
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 12
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "!!!"
	invokestatic MinicLib/println(Ljava/lang/String;)V
	ldc 0
	istore 13
	EXIT_1:
	nop
	return
	
.end method
