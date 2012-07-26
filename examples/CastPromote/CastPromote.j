	.class public CastPromote
	.super java/lang/Object
	
	; standard initializer
	
	
	.method public <init>()V
	aload 0
	invokenonvirtual java/lang/Object/<init>()V
	return
	.end method
	
	
	.method public static main()V
	.limit locals 27
	.limit stack 27
	ldc 0
	istore 9
; float result ----------------------------
	fconst_0
	fstore 4
; array = new float[2] [2] ----------------------------
	iconst_2
	iconst_2
	multianewarray [[F 2
	astore 3
; array [0] [0] = 21 ----------------------------
	aload 3
	iconst_0
	aaload
	iconst_0
	bipush 21
	i2f
	fastore
; array [0] [1] = 3 ----------------------------
	aload 3
	iconst_0
	aaload
	iconst_1
	iconst_3
	i2f
	fastore
; array [1] [0] = 11 ----------------------------
	aload 3
	iconst_1
	aaload
	iconst_0
	bipush 11
	i2f
	fastore
; array [1] [1] = 10 ----------------------------
	aload 3
	iconst_1
	aaload
	iconst_1
	bipush 10
	i2f
	fastore
; result = (array [0] [0] + array [0] [1] + array [1] [0]) / array [1] [1] ----------------------------
	aload 3
	iconst_0
	aaload
	iconst_0
	faload
	aload 3
	iconst_0
	aaload
	iconst_1
	faload
	fadd
	aload 3
	iconst_1
	aaload
	iconst_0
	faload
	fadd
	aload 3
	iconst_1
	aaload
	iconst_1
	faload
	fdiv
	fstore 4
	fload 4
	invokestatic MinicLib/float2string(F)Ljava/lang/String;
	invokestatic MinicLib/println(Ljava/lang/String;)V
; int intero ----------------------------
	iconst_0
	istore 5
; intero = 3 ----------------------------
	iconst_3
	istore 5
; float espressione_complessa ----------------------------
	fconst_0
	fstore 6
; espressione_complessa = 12.2 + (9.3 * 11.2) + 4 - 12 + 22.3 + (34 / 10.2) ----------------------------
	ldc 9.3
	ldc 11.2
	fmul
	ldc 12.2
	fadd
	iconst_4
	i2f
	fadd
	bipush 12
	i2f
	fsub
	ldc 22.3
	fadd
	bipush 34
	i2f
	ldc 10.2
	fdiv
	fadd
	fstore 6
; int cast_val ----------------------------
	iconst_0
	istore 7
; cast_val = (int)espressione_complessa ----------------------------
	fload 6
	f2i
	istore 7
; float float_promote ----------------------------
	fconst_0
	fstore 8
; float_promote = 15 + intero + espressione_complessa ----------------------------
	bipush 15
	iload 5
	iadd
	i2f
	fload 6
	fadd
	fstore 8
	iload 5
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/println(Ljava/lang/String;)V
	fload 6
	invokestatic MinicLib/float2string(F)Ljava/lang/String;
	invokestatic MinicLib/println(Ljava/lang/String;)V
	iload 7
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/println(Ljava/lang/String;)V
	fload 8
	invokestatic MinicLib/float2string(F)Ljava/lang/String;
	invokestatic MinicLib/println(Ljava/lang/String;)V
	return
	
.end method
