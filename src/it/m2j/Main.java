package it.m2j;

import java.io.FileReader;

import javax.swing.JFrame;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
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
			parser.parse();
			
			System.out.println("VARS: " + parser.symbolType_table);
			System.out.println("FUNCS: " + parser.symbolTypeFunction_table);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
