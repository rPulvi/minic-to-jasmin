package m2j;

import java.awt.Toolkit;
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

	private static String className = "";
	
	public static void main(String[] args) {
		        		
    	FileWriter fstream;
    	String jasminFile = getJasminFileName(args[0]);
    	
		try {
			Symbol result;
			Node root;
	        
			/*
			Toolkit tk = Toolkit.getDefaultToolkit();
			int xSize = ((int) tk.getScreenSize().getWidth());  
			int ySize = ((int) tk.getScreenSize().getHeight()); 
			JFrame win = new GUI();
	        win.setVisible(true);
	        win.setLocationRelativeTo(null);

	        win.setSize(xSize, ySize);
			*/
			
			// Istanzio lo scanner aprendo il file d'ingresso args[0]
			m2jLex scanner = new m2jLex(new FileReader(args[0]));

			// Istanzio il parser
			minic2jasminParser parser = new minic2jasminParser(scanner);

			//Avvio il parser
			result = parser.parse();
			root = (Node)result.value;
			
			SymbolTableConstructor stc = new SymbolTableConstructor(className, parser.registerCounter);
			root.accept(stc);
			
			if (stc.getErrorCount() == 0)
			{
				SemanticChecker tsc = new SemanticChecker(stc.getSymbolTable());
				root.accept(tsc);
				
				if (tsc.getErrorCount() == 0)
				{			
		            // Create output file: 
		            fstream = new FileWriter(jasminFile);
		            
		            CodeGenerator cg = new CodeGenerator(stc.getSymbolTable(), className);
		            root.accept(cg);
		            
		            fstream.write(cg.getOutput());
		            fstream.close();
				}
				else
					System.out.println("Compilation failed. Found " + tsc.getErrorCount() + " error(s).");
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}	
	
	private static String getJasminFileName(String filePath)
	{
		String namepart;
		
		String outfile;
        File f = new File(filePath);           
        
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
        
		return outfile;
	}

}
