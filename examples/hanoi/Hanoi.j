	.class public Hanoi
	.super java/lang/Object
	
	; standard initializer
	
	
	.method public <init>()V
	aload 0
	invokenonvirtual java/lang/Object/<init>()V
	return
	.end method
	
	
	.method public static main()V
	.limit locals 21
	.limit stack 21
	ldc 0
	istore 7
; int NDISK ----------------------------
	iconst_0
	istore 6
; NDISK = 5 ----------------------------
	iconst_5
	istore 6
	ldc "Soluzione della Torre di Hanoi con "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 6
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc " dischi."
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 6
	iconst_1
	iconst_3
	invokestatic MyMinicLib/hanoi(III)V
	ldc " dischi."
	invokestatic MinicLib/println(Ljava/lang/String;)V
	return
	
.end method
