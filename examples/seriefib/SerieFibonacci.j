	.class public SerieFibonacci
	.super java/lang/Object
	
	; standard initializer
	
	
	.method public <init>()V
	aload 0
	invokenonvirtual java/lang/Object/<init>()V
	return
	.end method
	
	
	.method public static main()V
	.limit locals 15
	.limit stack 15
	ldc 0
	istore 5
; int i ----------------------------
	iconst_0
	istore 4
; i = 15 ----------------------------
	bipush 15
	istore 4
	ldc "Calcolo della serie di Fibonacci per il numero "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 4
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "."
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "Risultato: "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 4
	invokestatic MyMinicLib/Fib(I)I
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "."
	invokestatic MinicLib/print(Ljava/lang/String;)V
	return
	
.end method
