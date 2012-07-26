	.class public Quicksort
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
; int SIZE ----------------------------
	iconst_0
	istore 12
; SIZE = 10 ----------------------------
	bipush 10
	istore 12
; vect = new int[SIZE] ----------------------------
	iload 12
	newarray int
	astore 11
; vect [0] = 3 ----------------------------
	aload 11
	iconst_0
	iconst_3
	iastore
; vect [5] = 9 ----------------------------
	aload 11
	iconst_5
	bipush 9
	iastore
; vect [1] = 5 ----------------------------
	aload 11
	iconst_1
	iconst_5
	iastore
; vect [6] = 3 ----------------------------
	aload 11
	bipush 6
	iconst_3
	iastore
; vect [2] = -7 ----------------------------
	aload 11
	iconst_2
	bipush 7
	ineg
	iastore
; vect [7] = -4 ----------------------------
	aload 11
	bipush 7
	iconst_4
	ineg
	iastore
; vect [3] = 1 ----------------------------
	aload 11
	iconst_3
	iconst_1
	iastore
; vect [8] = 8 ----------------------------
	aload 11
	bipush 8
	bipush 8
	iastore
; vect [4] = 0 ----------------------------
	aload 11
	iconst_4
	iconst_0
	iastore
; vect [9] = 6 ----------------------------
	aload 11
	bipush 9
	bipush 6
	iastore
	ldc "Il vettore iniziale e':"
	invokestatic MinicLib/print(Ljava/lang/String;)V
	aload 11
	iload 12
	invokestatic MyMinicLib/print_vector([II)V
	iload 12
	iconst_1
	isub
	aload 11
	iconst_0
	invokestatic MyMinicLib/quick(I[II)V
	ldc "Il vettore ordinato tramite Quicksort e':"
	invokestatic MinicLib/print(Ljava/lang/String;)V
	aload 11
	iload 12
	invokestatic MyMinicLib/print_vector([II)V
	return
	
.end method
