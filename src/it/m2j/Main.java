package it.m2j;

import java.io.FileReader;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Istanzio lo scanner aprendo il file d'ingresso args[0]
			m2jLex scanner = new m2jLex(new FileReader(args[0]));
			
			// Istanzio il parser
			minic2jasminParser parser = new minic2jasminParser(scanner);
			
			//Avvio il parser
			parser.parse();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}

}
