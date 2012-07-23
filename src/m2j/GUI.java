package m2j;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.LayeredHighlighter;


public class GUI extends JFrame{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextArea myArea;
	JButton compileButton = new JButton("Click me now!");
	
	private JSplitPane splitPane;

	
	private JTextArea minicFile;
	private JTextArea jasminFile;
	private JLabel divisor;
	
	
	private JLabel minicLabel;
	private JLabel jvmLabel;
	
    public GUI(){
    	initComponent();
    	
    	JScrollPane scrollArea = new JScrollPane(myArea);
        //JPanel content = new JPanel();
    	JLayeredPane content = new JLayeredPane();	
    	content.add(minicLabel);
    	content.setLayout(new BorderLayout());
        
        content.add(scrollArea, BorderLayout.PAGE_END);        
        content.add(compileButton, BorderLayout.PAGE_START);
        content.add(splitPane, BorderLayout.CENTER);

        
        
        this.setContentPane(content);
        this.setTitle("MiniC to Jasmin Compiler");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        redirectSystemStreams();
    }

    private void initComponent()
    {
    	myArea  = new JTextArea(10,70);
    	myArea.setText("Compilation Result:\n");    	    	
    	
    	minicFile = new JTextArea();
    	minicFile.setColumns(53);
    	minicFile.setLineWrap(true);
    	minicFile.setRows(15);
    	minicFile.setWrapStyleWord(true);
    	minicFile.setEditable(false);
    	minicFile.setBackground(Color.GRAY);
    	
    	jasminFile = new JTextArea();
    	jasminFile.setColumns(53);
    	jasminFile.setLineWrap(true);
    	jasminFile.setRows(15);
    	jasminFile.setWrapStyleWord(true);
    	jasminFile.setEditable(false);
    	jasminFile.setBackground(Color.GRAY);
    	
    	divisor = new JLabel();
    	divisor.setBackground(Color.black);
    	divisor.setText("  ");
    	
    	splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,minicFile,jasminFile);
		 splitPane.setResizeWeight(0.5);
		 splitPane.setOneTouchExpandable(true);
		 splitPane.setContinuousLayout(true);
          
    	minicLabel = new JLabel(".c");
    	minicLabel.setFont(new Font("Verdana", Font.BOLD, 36));
    	minicLabel.setForeground(Color.WHITE);
    	minicLabel.setBounds(300, 300, 40, 40);
    	
    	jvmLabel = new JLabel(".j");
    }
    
    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            myArea.append(text);
            }
        });
    }

	private void redirectSystemStreams() {
	  OutputStream out = new OutputStream() {
	    @Override
	    public void write(int b) throws IOException {
	      updateTextArea(String.valueOf((char) b));
	    }
	
	    @Override
	    public void write(byte[] b, int off, int len) throws IOException {
	      updateTextArea(new String(b, off, len));
	    }
	
	    @Override
	    public void write(byte[] b) throws IOException {
	      write(b, 0, b.length);
	    }
	  };
	
	  System.setOut(new PrintStream(out, true));
	  System.setErr(new PrintStream(out, true));
	}
}
