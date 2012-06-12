package it.m2j;

import java.awt.BorderLayout;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;


public class GUI extends JFrame{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextArea myArea = new JTextArea(30,70);

    public GUI(){
        myArea.setText("Compilation Result:\n\n");
        JScrollPane scrollArea = new JScrollPane(myArea);

        JPanel content = new JPanel();
        content.setLayout(new BorderLayout());
        content.add(scrollArea, BorderLayout.CENTER);

        this.setContentPane(content);
        this.setTitle("MiniC to Jasmin Compiler");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.pack();
        redirectSystemStreams();

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
