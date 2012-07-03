package it.m2j;

import java.io.FileReader;
import java.io.PrintWriter;
import java_cup.runtime.Symbol;

import javax.swing.JFrame;

import ast.Node;

import compiler.SemanticChecker;
import compiler.SymbolTableConstructor;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
