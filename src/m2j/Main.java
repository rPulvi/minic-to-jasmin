package m2j;

import java.awt.Toolkit;
import javax.swing.JFrame;

public class Main {
		
	public static void main(String[] args) {
		    
		if(args.length == 0)
		{
			Toolkit tk = Toolkit.getDefaultToolkit();
			int xSize = ((int) tk.getScreenSize().getWidth());  
			int ySize = ((int) tk.getScreenSize().getHeight()); 
			JFrame win = new GUI();
			win.setSize(xSize, ySize);
			win.setVisible(true);
	        win.setLocationRelativeTo(null);
		}
		else
		{
			JasminCompiler jc = new JasminCompiler(args[0]);
			jc.Compile();
		}	        
	}	
}
