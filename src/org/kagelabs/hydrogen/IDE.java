package org.kagelabs.hydrogen;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

public class IDE extends JFrame {	
		
		private JMenuBar menuBar;
		private JMenu file, project, help;
		private JMenuItem open, save, run, syntax, about;
		private FlowLayout layout;
		private JTextArea textarea, output;
		private JScrollPane textareaScroller, outputScroller;
		
	public IDE(){
		 super(); 
		 setBounds(100,100,300,460);
		 Dimension fourth = new Dimension(300,460);
		 setMaximumSize(fourth);
		 setMinimumSize(fourth);
		 this.setResizable(false);
		 setVisible(true);	
		 this.layout = new FlowLayout();
		 this.setLayout(layout);
		 
		 this.menuBar = new JMenuBar();
		 this.file = new JMenu("File");
		 this.project = new JMenu("Project");
		 this.help = new JMenu("Help");
		 menuBar.add(file);
		 menuBar.add(project); 
		 menuBar.add(help);
		 this.add(menuBar);
		 this.setJMenuBar(menuBar);
		 this.open = new JMenuItem("open");
		 this.save = new JMenuItem("save");
		 this.run = new JMenuItem("run");
		 run.addActionListener(new IDEActionListener(this));
		 this.syntax = new JMenuItem("syntax");
		 this.about = new JMenuItem("about");
		 this.file.add(open);
		 this.file.add(save);
		 this.project.add(run);
		 this.help.add(syntax);
		 this.help.add(about);
		 
		 
		 this.textarea = new JTextArea(22,24);
		 this.textarea.setText("import org.kagelabs.hydrogen.stl.io\nio.print \"hello world!\"");
		 this.textareaScroller = new JScrollPane(textarea);
		 this.textareaScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		 this.textareaScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 this.add(textareaScroller);
		 
		 this.output = new JTextArea(5,24);
		 this.output.setEditable(false);
		 this.outputScroller = new JScrollPane(output);
		 this.outputScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		 this.outputScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 this.add(outputScroller);
		 
		 

		 //JMenuItem menuItem = new JMenuItem(); 
		 
	}
	
	public static void createIDE(){
		
	}
	
	class IDEActionListener implements ActionListener {
		IDE parent;
		IDEActionListener(IDE parent) {
			this.parent = parent;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource() == this.parent.run) {
				String code = this.parent.textarea.getText();
				String[] splitcode = code.split("\n", -1);
				LineBundle lb = new LineBundle();
				for (String c : splitcode) {
					lb.add(c);
				}
				Interpreter hy =  new Interpreter();
				hy.initialize(lb);
				this.parent.output.setText("");
				PrintStream con = new PrintStream(new TextAreaOutputStream(this.parent.output));
				System.setOut(con); System.setErr(con);
				while (hy.canRunMore()) {
					hy.tick();
				}
				System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
				System.setErr(new PrintStream(new FileOutputStream(FileDescriptor.out)));
			}
		}
		
	}
}
