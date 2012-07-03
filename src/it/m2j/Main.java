package it.m2j;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;

import javax.swing.JFrame;

import ast.Node;

import compiler.CodeGenerator;
import compiler.SemanticChecker;
import compiler.SymbolTableConstructor;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
        String outfile;
        String namepart;
    	FileWriter fstream;
        String className;
        
		try {
			Symbol result;
			Node root;
	        /*
			JFrame win = new GUI();
	        win.setVisible(true);
	        win.setLocationRelativeTo(null);
	        */		

			// Istanzio lo scanner aprendo il file d'ingresso args[0]
			m2jLex scanner = new m2jLex(new FileReader(args[0]));

			// Istanzio il parser
			minic2jasminParser parser = new minic2jasminParser(scanner);

			//Avvio il parser
			result = parser.parse();
			root = (Node)result.value;
			
			SymbolTableConstructor stc = new SymbolTableConstructor(null);
			root.accept(stc);
			
			SemanticChecker tsc = new SemanticChecker(stc.getSymbolTable(), null);
			root.accept(tsc);		
            
            // Create output file name:
            File f = new File(args[0]);
            
            namepart = f.getName();
            int fileLenght = namepart.length();
            
        	//create jasmin file
            if ( namepart.charAt(fileLenght - 2) == '.' && namepart.charAt(fileLenght - 1) == 'c') {
               className = new String(namepart.substring(0, fileLenght - 2));
               outfile = new String(namepart.substring(0, fileLenght - 1));
               outfile = outfile.concat("j");
            } else {
               className = new String(namepart);
               outfile = new String(namepart);
               outfile = outfile.concat(".j");
            }
            
            // Create output file: 
            fstream = new FileWriter(outfile);
            
            CodeGenerator cg = new CodeGenerator(stc.getSymbolTable(), null, className);
            root.accept(cg);
            
            fstream.write(cg.getOutput());
            fstream.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
