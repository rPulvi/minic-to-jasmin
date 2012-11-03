package m2j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import java_cup.runtime.Symbol;

import ast.Node;

import compiler.CodeGenerator;
import compiler.SemanticChecker;
import compiler.SymbolTableConstructor;

public class JasminCompiler {

	private String fileName;
	private String className = "";
	
	public JasminCompiler(String fileName)
	{
		this.fileName = fileName;
	}
		
	public void Compile()
	{
		Symbol result;
		Node root;
    	FileWriter fstream;
    	String jasminFile = getJasminFileName(fileName);
    	
    	try {
			// Istanzio lo scanner aprendo il file d'ingresso args[0]
			m2jLex scanner = new m2jLex(new FileReader(fileName));
	
			// Istanzio il parser
			minic2jasminParser parser = new minic2jasminParser(scanner);
	
			//Avvio il parser
			result = parser.parse();
			root = (Node)result.value;

			SymbolTableConstructor stc = new SymbolTableConstructor(className, parser.registerCounter);
			root.accept(stc);
			
			int totErrors = stc.getErrorCount() + parser.getErrors();
			
			if (totErrors == 0) //Because Symbol table could be corrupted			
			{
				SemanticChecker tsc = new SemanticChecker(stc.getSymbolTable());
				root.accept(tsc);
				
				totErrors += tsc.getErrorCount();
				
				if (totErrors == 0)
				{			
		            // Create output file: 
		            fstream = new FileWriter(jasminFile);
		            
		            CodeGenerator cg = new CodeGenerator(stc.getSymbolTable(), className);
		            root.accept(cg);
		            
		            fstream.write(cg.getOutput());
		            fstream.close();
		            
		            System.out.println("Compilation Succedeed. " + jasminFile + " file created correctly.");
				}
				else
					System.out.println("Compilation failed. Found " + totErrors + " error(s).");
			}
			else
				System.out.println("Compilation failed. Found " + totErrors + " error(s).");
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private String getJasminFileName(String filePath)
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
	
	public String getJasminFileName()
	{
		return getJasminFileName(this.fileName);
	}
}
