	.class public Mcd
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
; int i ----------------------------
	iconst_0
	istore 7
; int j ----------------------------
	iconst_0
	istore 8
; i = 1152 ----------------------------
	sipush 1152
	istore 7
; j = 480 ----------------------------
	sipush 480
	istore 8
	ldc "Calcolo l'MCD fra "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 7
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc " e "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 8
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "."
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "Il risultato e': "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 7
	iload 8
	invokestatic MyMinicLib/mcd(II)I
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "Il risultato usando il metodo di Euclide e': "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	iload 7
	iload 8
	invokestatic MyMinicLib/mcd(II)I
	invokestatic MinicLib/int2string(I)Ljava/lang/String;
	invokestatic MinicLib/print(Ljava/lang/String;)V
	ldc "Il risultato usando il metodo di Euclide e': "
	invokestatic MinicLib/print(Ljava/lang/String;)V
	return
	
.end method
