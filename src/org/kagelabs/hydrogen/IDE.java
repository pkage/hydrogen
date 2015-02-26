package org.kagelabs.hydrogen;

import java.awt.FlowLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JTextArea;

public class IDE extends JFrame implements IDEActionListener {	
		
		private JMenuBar menuBar;
		private JMenu file, project, help;
		private JMenuItem open, save, run, syntax, about;
		private FlowLayout layout;
		private JTextArea textarea, output;
		
	public IDE(){
		 super(); 
		 setBounds(100,100,300,500);
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
		 this.syntax = new JMenuItem("syntax");
		 this.about = new JMenuItem("about");
		 this.file.add(open);
		 this.file.add(save);
		 this.project.add(run);
		 this.help.add(syntax);
		 this.help.add(about);
		 this.textarea = new JTextArea(22, 24);
		 
		 this.add(textarea);
		 this.output = new JTextArea(5, 24);
		 this.output.setEditable(false);
		 this.add(output);
		 
		 //JMenuItem menuItem = new JMenuItem(); 
		 
	}
	
	public static void createIDE(){
		
	}
	
	public static void main(String args[]){
		IDE ide = new IDE();
		ide.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ide.setVisible(true);
		
		
	}
}
