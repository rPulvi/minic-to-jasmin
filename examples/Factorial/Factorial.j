	.class public Factorial
	.super java/lang/Object
	
	; standard initializer
	
	
	.method public <init>()V
	aload 0
	invokenonvirtual java/lang/Object/<init>()V
	return
	.end method
	
	
	.method public static factorial(I)I
	.limit locals 6
	.limit stack 6
	ldc 0
	istore 2
; int res ----------------------------
	iconst_0
	istore 1
	ldc 0
	istore 2
	iload 0
	iconst_0
	if_icmpeq #0
	goto #1
#0:
	ldc 1
	istore 2
#1:
	iload 2
	ifgt STMT_1
	goto ELSE_1
	STMT_1:
	iconst_1
	ireturn
	goto EXIT_1
	ELSE_1:
; res = n * factorial(n - 1) ----------------------------
	iload 0
	iconst_1
	isub
	invokestatic Factorial/factorial(I)I
	iload 0
	imul
	istore 1
	ldc 0
	istore 2
	EXIT_1:
	nop
	iload 1
	ireturn
	
.end method
