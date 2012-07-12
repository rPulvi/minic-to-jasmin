	.class public Test
	.super java/lang/Object
	
	; standard initializer
	
	
	.method public <init>()V
	aload 0
	invokenonvirtual java/lang/Object/<init>()V
	return
	.end method
	
	
	.method public static main()F
	.limit locals 3
	.limit stack 3
; int a ----------------------------
	iconst_0
	istore 0
; int b ----------------------------
	iconst_0
	istore 1
; float c ----------------------------
	fconst_0
	fstore 2
; a = 7 ----------------------------
	bipush 7
	istore 0
; b = 3 ----------------------------
	iconst_3
	istore 1
; c = a / b ----------------------------
	iload 0
	iload 1
	idiv
	i2f
	fstore 2
	fload 2
	freturn
	
.end method
