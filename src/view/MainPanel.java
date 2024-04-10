package view;

import java.awt.Color;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import view.Window.panels;

public class MainPanel extends JPanel {

	private Window w;
	public MainPanel(Window w) {
		this.w=w;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setBackground(Color.GRAY);
		JButton createButton=new JButton();
		createButton.setSize(w.getWidth()/3,w.getHeight()/5);
		createButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				w.setPanel(panels.SELECT_IMAGE);
			}
		});
		createButton.setLocation(w.getWidth()/2-createButton.getWidth()/2, w.getHeight()/2-createButton.getHeight()/2);
		createButton.setText("CREATE PATTERN");
		this.add(createButton);
		
	}

}
