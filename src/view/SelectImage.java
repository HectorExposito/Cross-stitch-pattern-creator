package view;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import data.UserImage;
import view.Window.panels;

public class SelectImage extends JPanel {

	private Window w;
	JLabel selectedImage;
	boolean canCreateAPattern;
	
	public SelectImage(Window w) {
		this.w=w;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		
		selectedImage=new JLabel();
		selectedImage.setSize(w.getWidth()/4, w.getWidth()/4);
		selectedImage.setLocation(w.getWidth()/2-selectedImage.getWidth()/2, w.getHeight()/2-selectedImage.getHeight()/2);
		this.add(selectedImage);
		
		JButton openFile=new JButton();
		openFile.setSize(w.getWidth()/3,w.getHeight()/9);
		
		openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				UserImage image=w.pc.openFileSearch();
				changeLabelImage(image.getImage());
			}

			private void changeLabelImage(BufferedImage image) {
				BufferedImage resizedImage = new BufferedImage(selectedImage.getWidth(), selectedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
				Graphics2D g = resizedImage.createGraphics();
				g.drawImage(image, 0, 0, selectedImage.getWidth(), selectedImage.getHeight(), null);
				g.dispose();
				image=resizedImage;
				selectedImage.setIcon(new ImageIcon(image));
				
				canCreateAPattern=true;
			}
		});
		openFile.setLocation(w.getWidth()/2-openFile.getWidth()/2, w.getHeight()/8);
		openFile.setText("SELECT IMAGE");
		this.add(openFile);
		
		JButton createPattern=new JButton();
		createPattern.setSize(w.getWidth()/2,w.getHeight()/9);
		
		createPattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(canCreateAPattern) {
					w.pc.convertImage();
					w.setPanel(panels.EXPORT_IMAGE);
				}
			}
		});
		createPattern.setLocation(w.getWidth()/2-createPattern.getWidth()/2, w.getHeight()-w.getHeight()/6);
		createPattern.setText("CREATE PATTERN");
		this.add(createPattern);
	}

}
