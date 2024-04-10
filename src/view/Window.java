package view;

import java.awt.HeadlessException;
import java.awt.Toolkit;

import javax.swing.JFrame;

import data.PatternCreator;

public class Window extends JFrame {

	public PatternCreator pc;
	
	public Window(String title,PatternCreator pc) throws HeadlessException {
		super(title);
		this.pc=pc;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1200,800);
		setPanel(panels.MAIN_PANEL);
	}

	public enum panels{
		MAIN_PANEL,SELECT_IMAGE,EXPORT_IMAGE
	}

	public void setPanel(panels panel) {
		switch(panel) {
		case MAIN_PANEL:
			this.setContentPane(new MainPanel(this));
			break;
		case SELECT_IMAGE:
			this.setContentPane(new SelectImage(this));
			break;
		case EXPORT_IMAGE:
			this.setContentPane(new ExportImage(this));
			break;
		}
		this.revalidate();
	}
}
