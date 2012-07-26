	.class public Fibonacci
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
; int LIMIT ----------------------------
	iconst_0
	istore 6
; LIMIT = 1000000 ----------------------------
	ldc 1000000
	istore 6
	ldc "MyMinicLib/fiboRec"
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "La serie di Fibonacci (limite = "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 6
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc ") e':"
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "1"
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iconst_0
	iconst_1
	iload 6
	invokestatic MyMinicLib/fiboRec(III)V
	ldc "1"
	invokestatic MinicLib/print(Ljava/lang/String;)V
	return
	
.end method
