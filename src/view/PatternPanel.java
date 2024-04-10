package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import data.PatternCreator;
import view.Window.panels;

public class PatternPanel extends JScrollPane {

	private Window w;
	private PatternCreator pc;
	private final int sizeOfSquare=20;
	
	public PatternPanel(PatternCreator pc,int x,Window w) {
		super(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.pc=pc;
		this.setSize(3*w.getWidth()/4,w.getHeight());
		this.setLocation(x, 0);
		this.setBackground(Color.GREEN);
		this.setLayout(null);
		this.w=w;
		//initialize();
		paintPanel();
	}

	
	private void initialize() {
		JScrollBar verticalScroll=new JScrollBar();
		JScrollBar horizontalScroll=new JScrollBar(JScrollBar.HORIZONTAL);
		
		verticalScroll.setSize(20, this.getHeight());
		horizontalScroll.setSize(this.getWidth(), 20);
		
		verticalScroll.setLocation(this.getWidth()+verticalScroll.getWidth(), 0);
		horizontalScroll.setLocation(0,0);
		
		this.add(verticalScroll);
		this.add(horizontalScroll);
	}


	public void paintPanel() {
		
	}

}
