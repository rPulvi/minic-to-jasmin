#!/bin/bash

JASMIN_PATH=/home/alenico84/javaTools/jasmin-2.4/jasmin.jar		# <----- SET this to the correct Jasmin Path
DIR=`dirname "$1"`
NAME=`basename "$1"`

NAME_JASMIN=${NAME/%c/j}	#Replace the last occurence of the letter c (change the file extension from .c to .j)
NAME_CLASS=${NAME/%c/class}
PATH_DEST=$DIR"/"$NAME_JASMIN

cd bin
java m2j.Main ../$1

mv $NAME_JASMIN ../$DIR		#Move the generated file .j from bin directory to the correct directory in examples


java -jar $JASMIN_PATH  ../$PATH_DEST	#Creation of the .class file from .j file
mv $NAME_CLASS ../$DIR  #Move the generated file .class from bin directory to the correct directory in examples

cd ../$DIR				# go to the directory where are placed the .j and .class files generated 

javac TestJasmin.java 	#Compile
java TestJasmin			#Run
