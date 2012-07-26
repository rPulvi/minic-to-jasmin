	.class public Heapsort
	.super java/lang/Object
	
	; standard initializer
	
	
	.method public <init>()V
	aload 0
	invokenonvirtual java/lang/Object/<init>()V
	return
	.end method
	
	
	.method public static main()V
	.limit locals 51
	.limit stack 51
	ldc 0
	istore 17
; int size ----------------------------
	iconst_0
	istore 15
; size = 10 ----------------------------
	bipush 10
	istore 15
; vect = new int[size] ----------------------------
	iload 15
	newarray int
	astore 16
; vect [0] = 95 ----------------------------
	aload 16
	iconst_0
	bipush 95
	iastore
; vect [1] = 1 ----------------------------
	aload 16
	iconst_1
	iconst_1
	iastore
; vect [2] = -2 ----------------------------
	aload 16
	iconst_2
	iconst_2
	ineg
	iastore
; vect [3] = -1 ----------------------------
	aload 16
	iconst_3
	iconst_1
	ineg
	iastore
; vect [4] = 1 ----------------------------
	aload 16
	iconst_4
	iconst_1
	iastore
; vect [5] = 6 ----------------------------
	aload 16
	iconst_5
	bipush 6
	iastore
; vect [6] = 12 ----------------------------
	aload 16
	bipush 6
	bipush 12
	iastore
; vect [7] = -44 ----------------------------
	aload 16
	bipush 7
	bipush 44
	ineg
	iastore
; vect [8] = 7 ----------------------------
	aload 16
	bipush 8
	bipush 7
	iastore
; vect [9] = 0 ----------------------------
	aload 16
	bipush 9
	iconst_0
	iastore
	ldc "Il vettore iniziale e':"
	invokestatic MinicLib/println(Ljava/lang/String;)V
	aload 16
	iload 15
	invokestatic MyMinicLib/print_vector([II)V
	iload 15
	iconst_1
	isub
	aload 16
	invokestatic MyMinicLib/heapsort(I[I)V
	ldc "Il vettore ordinato tramite Heapsort e':"
	invokestatic MinicLib/println(Ljava/lang/String;)V
	aload 16
	iload 15
	invokestatic MyMinicLib/print_vector([II)V
	return
	
.end method
