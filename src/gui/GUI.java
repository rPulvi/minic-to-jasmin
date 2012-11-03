package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.TextListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.EventObject;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.BevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.text.BadLocationException;

import m2j.JasminCompiler;
import gui.GuiState;

public class GUI extends JFrame{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextArea resultArea;		

	private JSplitPane splitPane;
	private JFileChooser fileDialog;

	private JTextArea minicFile;
	private JTextArea jasminFile;
	private TextLineNumber miniCLineNumber;
	private TextLineNumber jasminLineNumber;
	private JScrollPane minicScrollPane;
	private JScrollPane jasminScrollPane;	
	private JScrollPane scrollArea;
	private JPanel statusPanel;
	private JPanel southPanel;
	
	private JPopupMenu popupMenu;
	private JMenuItem menuItem;
	
	private JLabel minicLabel;
	private JLabel jvmLabel;
	private JLabel clmnCount;
	private JLabel lblFileName;	
	
	private String filePath;
	private String fileName;
	private GuiState guiState;
	
	private GuiEventSource ev;
	
	private int tabCnt = 0; //patch
	
    public GUI(){
    	    
    	fileName = "";
    	filePath = "";
    	
    	initComponent();
    	
    	ev = new GuiEventSource();
    	ev.addEventListener(new GuiEventListener() {
			
			@Override
			public void handleGuiEvent(EventObject e) {
				guiState = (GuiState)e.getSource();
				setGui();
			}
		});
    	
    	ev.stateChanged(GuiState.INIT);
    	    	 
        //JPanel content = new JPanel();
    	JLayeredPane content = new JLayeredPane();	

    	content.add(minicLabel, new Integer(500));
    	content.add(jvmLabel);
    	
    	content.setLayout(new BorderLayout());        
    	content.add(splitPane, BorderLayout.CENTER);
    	    	
    	southPanel.add(scrollArea, BorderLayout.NORTH);
    	southPanel.add(statusPanel, BorderLayout.SOUTH);
    	
    	content.add(southPanel, BorderLayout.PAGE_END);
        
    	this.setContentPane(content);
        this.setTitle("MiniC to Jasmin Compiler");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();

        redirectSystemStreams();
    }

    private void initComponent()
    {
    	//Result Area
    	resultArea  = new JTextArea(10,70);
    	resultArea.setFont(new Font("Courier", Font.PLAIN, 16));
    	resultArea.setForeground(Color.BLUE);
    	InitResultArea(); 
    	//
    	southPanel = new JPanel();
    	southPanel.setLayout(new BorderLayout());
    	//
    	scrollArea = new JScrollPane(resultArea);
    	//        	
    	fileDialog = new JFileChooser();
    	//    	    	
    	
    	//MiniC Panel
    	minicFile = new JTextArea();
    	minicFile.setColumns(53);    	
    	minicFile.setLineWrap(true);
    	minicFile.setRows(15);
    	minicFile.setWrapStyleWord(true); 
    	minicFile.setTabSize(4);    	
    	minicFile.setBackground(Color.GRAY);
    	minicFile.setFont(new Font("Courier", Font.PLAIN, 14));

    	minicFile.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
                
    			if (e.getButton() == MouseEvent.BUTTON1)
    			{
    				if(guiState == GuiState.INIT)
    					showOpenDialog();
    			}
                
                if(e.getButton() == MouseEvent.BUTTON3)
                {                	
                	popupMenu.show(e.getComponent(),e.getX(),e.getY());
                }
            }     		
		});

    	minicFile.addCaretListener(new CaretListener() {
			
			@Override
			public void caretUpdate(CaretEvent e) {
				JTextArea editArea = (JTextArea)e.getSource();				 
				int c = 0;
				
				try {
					c = editArea.getCaretPosition() - editArea.getLineStartOffset(editArea.getLineOfOffset(editArea.getCaretPosition()));
					
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				try {
					if(editArea.getText(editArea.getCaretPosition()-1, 1).equals("\t"))
						tabCnt = 4;
					if(c==0)
						tabCnt = 0;
				} catch (BadLocationException e1) {
						
				}
				 
				clmnCount.setText("Column: " + (c + tabCnt));	
			}
		});
    	
    	miniCLineNumber = new TextLineNumber(minicFile);
    	minicScrollPane = new JScrollPane(minicFile);
    	minicScrollPane.setRowHeaderView(miniCLineNumber);

    	//
    	    	
    	//Jasmin Panel
    	jasminFile = new JTextArea();
    	jasminFile.setColumns(53);
    	jasminFile.setLineWrap(true);
    	jasminFile.setRows(15);
    	jasminFile.setWrapStyleWord(true);
    	jasminFile.setBackground(Color.GRAY); 
    	jasminFile.setFont(new Font("Courier", Font.PLAIN, 14));
    	jasminFile.addMouseListener(new MouseAdapter() {
    		public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                	;//jvmLabel.setVisible(false);
                }
            }    		
		});
    	
    	jasminLineNumber = new TextLineNumber(jasminFile);    	
    	jasminScrollPane = new JScrollPane(jasminFile);
    	jasminScrollPane.setRowHeaderView(jasminLineNumber);
    	//    	    	
    	
    	//Split Panel
    	splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,minicScrollPane,jasminScrollPane);
    	splitPane.setResizeWeight(0.5);
    	splitPane.setOneTouchExpandable(true);
    	splitPane.setContinuousLayout(true);
    	//
    	    	
    	//Labels
    	minicLabel = new JLabel(".c");
    	minicLabel.setFont(new Font("Verdana", Font.BOLD, 52));
    	minicLabel.setForeground(Color.WHITE);
    	minicLabel.setBounds(300, 300, 80, 80);
    	//-------
    	jvmLabel = new JLabel(".j");
    	jvmLabel.setFont(new Font("Verdana", Font.BOLD, 52));
    	jvmLabel.setForeground(Color.WHITE);
    	jvmLabel.setBounds(950, 300, 80, 80);
    	//-------
    	clmnCount = new JLabel("Column: " + minicFile.getCaretPosition());
    	clmnCount.setForeground(Color.BLACK);
    	//    	    	    	    	
    	statusPanel = new JPanel();
    	statusPanel.setBorder(new BevelBorder(BevelBorder.LOWERED));    	
    	statusPanel.setPreferredSize(new Dimension(southPanel.getWidth(), 20));
    	statusPanel.setLayout(new BorderLayout());
    	lblFileName = new JLabel("File name: " + fileName);
    	clmnCount.setHorizontalAlignment(SwingConstants.RIGHT);
    	statusPanel.add(clmnCount, BorderLayout.LINE_END);
    	statusPanel.add(lblFileName, BorderLayout.LINE_START);
    }
    
    private void updateTextArea(final String text) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            resultArea.append(text);
            }
        });
    }

    private void initPopupMenu()
    {
    	popupMenu = new JPopupMenu();
    	
    	if(guiState == GuiState.INIT)
    	{
    		menuItem = new JMenuItem("Open...");
	    	menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					showOpenDialog();
				}
			});
	
	    	popupMenu.add(menuItem);
    	}
    	
    	if(guiState == GuiState.FILE_OPEN || guiState == GuiState.FILE_SAVED || guiState == GuiState.COMPILED || guiState == GuiState.RUN)
    	{
    		menuItem = new JMenuItem("Open...");
	    	menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					showOpenDialog();
				}
			});
	
	    	popupMenu.add(menuItem);
	    	
	    	popupMenu.add(new JSeparator());
    		
    		menuItem = new JMenuItem("Edit");
	    	menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {				
					ev.stateChanged(GuiState.EDIT);
				}
			});
    		popupMenu.add(menuItem);
    		
    		menuItem = new JMenuItem("Compile");
	    	menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {									
					compileMiniC();					
				}
			});    		
    		popupMenu.add(menuItem);    	
    		
    		menuItem = new JMenuItem("Run test");
	    	menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {					
					compileMiniC();
					RunTest();
				}
			});    		    		
    		popupMenu.add(menuItem);
    	}
    	
    	if(guiState == GuiState.EDIT)
    	{
    		menuItem = new JMenuItem("Open...");
	    	menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int retVal = JOptionPane.showOptionDialog(null, "All changes in " + fileName + " will be discarded. Do you really want to continue?", "MiniC to Jasmin", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
					
					if (retVal == JOptionPane.YES_OPTION)
						showOpenDialog();
				}
			});
	
	    	popupMenu.add(menuItem);
    		
    		menuItem = new JMenuItem("Save");
    		menuItem.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int n;
					n = SaveFile();
					if (n != 0)
						JOptionPane.showMessageDialog(null, "Error saving " + fileName);
					else
						ev.stateChanged(GuiState.FILE_SAVED);
				}
			});    		
    		
    		popupMenu.add(menuItem);
    		    		
    		popupMenu.add(new JSeparator());
    		
    		menuItem = new JMenuItem("Compile");
	    	menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int retVal = JOptionPane.showOptionDialog(null, "Do you want to save " + fileName + " before compile?", "MiniC to Jasmin", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
					
					if (retVal == JOptionPane.YES_OPTION)
						SaveFile();
					else{
						try {
							minicFile.setText(readFile(filePath));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
					compileMiniC();
				}
			});    		
    		popupMenu.add(menuItem);    	
    		
    		menuItem = new JMenuItem("Run test");
	    	menuItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					int retVal = JOptionPane.showOptionDialog(null, "Do you want to save " + fileName + " before running?", "MiniC to Jasmin", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
					
					if (retVal == JOptionPane.YES_OPTION)
						SaveFile();
					else{
						try {
							minicFile.setText(readFile(filePath));
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}					
					compileMiniC();
					RunTest();
				}
			});
    		popupMenu.add(menuItem);
    	}
    }

    private void setGui()
    {
    	initPopupMenu();
		
		if(guiState == GuiState.INIT)
		{
	    	minicFile.setEditable(false);
	    	minicFile.setBackground(Color.GRAY);
			minicLabel.setVisible(true);
			
			jasminFile.setEditable(false);
			jasminFile.setBackground(Color.GRAY);
			jvmLabel.setVisible(true);
		}

		if(guiState == GuiState.FILE_OPEN)
		{
			minicLabel.setVisible(false);
			minicFile.setEditable(false);
			minicFile.setBackground(Color.WHITE);

			jasminFile.setText("");
			lblFileName.setText("File name: " + fileName);
			
			
			InitResultArea();
		}
		
		if(guiState == GuiState.FILE_SAVED)
		{
			minicFile.setEditable(false);			
		}
		
		if(guiState == GuiState.EDIT)
		{
			minicFile.setEditable(true);
		}
		
		if(guiState == GuiState.COMPILED || guiState == GuiState.RUN)
		{
			jvmLabel.setVisible(false);
			minicFile.setEditable(false);			
		}
    }
        
    private void showOpenDialog()
    {
    	minicLabel.setVisible(false);
    	File f = null;

    	try {
    		f = new File("../examples/");
        	fileDialog.setCurrentDirectory(f);			
        	fileDialog.addChoosableFileFilter(new MyFilter());	                	

        	int retVal = fileDialog.showOpenDialog(null);

        	if (retVal == JFileChooser.APPROVE_OPTION) {
                File file = fileDialog.getSelectedFile();	                        	                        

                minicFile.setText("");
                filePath = file.getPath();
                fileName = file.getName();
                minicFile.setText(readFile(filePath));

				ev.stateChanged(GuiState.FILE_OPEN);

            } else
            	minicLabel.setVisible(true);
    	}
		catch (IOException e) {
			minicLabel.setVisible(true);
			e.printStackTrace();
		} 
    }
    
    private void compileMiniC()
    {
    	InitResultArea();
		JasminCompiler jc = new JasminCompiler(filePath);
		jc.Compile();
		ev.stateChanged(GuiState.COMPILED);
		
		try {
            jasminFile.setText(readFile(jc.getJasminFileName()));
            jasminFile.setBackground(Color.WHITE);	
		}
		catch (IOException e1) {
			minicLabel.setVisible(true);
			//e1.printStackTrace();
		}
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
		
	private void redirectOutput(Process process) {
		
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;


		try {
			while ((line = br.readLine()) != null) {
				System.out.println(line);       
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Program terminated.");
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
	
	private void InitResultArea()
	{
		resultArea.setText("");
		resultArea.setText("Compilation Result:\n");
	}

	private int SaveFile()
	{
		int nRet = 0;
		
		try{						
			FileWriter outFile = new FileWriter(filePath);
			PrintWriter out = new PrintWriter(outFile);						

			out.print(minicFile.getText());
			out.close();
		}
		catch(IOException e)
		{
			nRet = 1;
		}
		
		return nRet;
	}	

	private void RunTest()
	{
		String[] commands = new String[2];
		commands[0] = "../gui.sh";
		commands[1] = filePath;
		
		ProcessBuilder pb = new ProcessBuilder(commands);
		Process process = null;
		try {
			process = pb.start();
			redirectOutput(process);
			ev.stateChanged(GuiState.RUN);
		} catch (IOException e1) {
			e1.printStackTrace();
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
