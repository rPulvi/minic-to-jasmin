package m2j;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.text.LayeredHighlighter;


public class GUI extends JFrame implements ActionListener{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextArea myArea;
	JButton compileButton;

	private JSplitPane splitPane;
	private JFileChooser fileDialog;

	private JTextArea minicFile;
	private JTextArea jasminFile;		

	private JLabel minicLabel;
	private JLabel jvmLabel;
	
	private String fileName;
	
    public GUI(){
    	initComponent();
    	
    	JScrollPane scrollArea = new JScrollPane(myArea);
        //JPanel content = new JPanel();
    	JLayeredPane content = new JLayeredPane();	
    	content.add(minicLabel, new Integer(500));
    	content.add(jvmLabel);
    	content.setLayout(new BorderLayout());
        
    	content.add(compileButton, BorderLayout.PAGE_START);
    	content.add(splitPane, BorderLayout.CENTER);        
    	content.add(scrollArea, BorderLayout.PAGE_END);
        
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
    	
    	fileDialog = new JFileChooser();
    	
    	minicFile = new JTextArea();
    	minicFile.setColumns(53);
    	minicFile.setLineWrap(true);
    	minicFile.setRows(15);
    	minicFile.setWrapStyleWord(true);
    	minicFile.setEditable(false);
    	minicFile.setBackground(Color.GRAY);
    	minicFile.setFont(new Font("Courier", Font.PLAIN, 12));

    	minicFile.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                	
                	minicLabel.setVisible(false);
                	File f = null;
					
                	try {
						f = new File(new File(".").getCanonicalPath());

	                	fileDialog.setCurrentDirectory(f);
	                	fileDialog.addChoosableFileFilter(new MyFilter());
	                	
	                	int retVal = fileDialog.showOpenDialog(null);
	                	
	                	if (retVal == JFileChooser.APPROVE_OPTION) {
	                        File file = fileDialog.getSelectedFile();	                        	                        
	                        
	                        fileName = file.getPath();	                        	                        
	                        
	                        minicFile.setText(readFile(fileName));
							minicFile.setBackground(Color.WHITE);

	                    } else
	                    	minicLabel.setVisible(true);
                	}
					catch (IOException e1) {
						minicLabel.setVisible(true);
						e1.printStackTrace();
					}                        
                }
            }    		
		});
    	    	
    	compileButton = new JButton("Click me now!");
    	compileButton.addActionListener(this);    	
    	
    	jasminFile = new JTextArea();
    	jasminFile.setColumns(53);
    	jasminFile.setLineWrap(true);
    	jasminFile.setRows(15);
    	jasminFile.setWrapStyleWord(true);
    	jasminFile.setEditable(false);
    	jasminFile.setBackground(Color.GRAY); 
    	jasminFile.setFont(new Font("Courier", Font.PLAIN, 12));
    	jasminFile.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                	;//jvmLabel.setVisible(false);
                }
            }    		
		});    	
    	    	
    	splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,minicFile,jasminFile);
    	splitPane.setResizeWeight(0.5);
    	splitPane.setOneTouchExpandable(true);
    	splitPane.setContinuousLayout(true);
          
    	minicLabel = new JLabel(".c");
    	minicLabel.setFont(new Font("Verdana", Font.BOLD, 52));
    	minicLabel.setForeground(Color.WHITE);
    	minicLabel.setBounds(300, 300, 80, 80);
    	
    	jvmLabel = new JLabel(".j");
    	jvmLabel.setFont(new Font("Verdana", Font.BOLD, 52));
    	jvmLabel.setForeground(Color.WHITE);
    	jvmLabel.setBounds(950, 300, 80, 80);    	    	 
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

	//From: http://stackoverflow.com/questions/326390/how-to-create-a-java-string-from-the-contents-of-a-file
	private static String readFile(String path) throws IOException {
		  FileInputStream stream = new FileInputStream(new File(path));
		  try {
		    FileChannel fc = stream.getChannel();
		    MappedByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
		    /* Instead of using default, pass in a decoder. */
		    return Charset.defaultCharset().decode(bb).toString();
		  }
		  finally {
		    stream.close();
		  }
		}

	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		Object src = arg0.getSource();
		if(src == compileButton)
		{
			JasminCompiler jc = new JasminCompiler(fileName);
			jc.Compile();
			try {
                jasminFile.setText(readFile(jc.getJasminFileName()));
                jasminFile.setBackground(Color.WHITE);	
			}
			catch (IOException e1) {
				minicLabel.setVisible(true);
				e1.printStackTrace();
			}
		}		
	}
}

class MyFilter extends javax.swing.filechooser.FileFilter {
    public boolean accept(File file) {
        String filename = file.getName();
        return filename.endsWith(".c") || file.isDirectory();
    }
    public String getDescription() {
        return "*.c";
    }
}
