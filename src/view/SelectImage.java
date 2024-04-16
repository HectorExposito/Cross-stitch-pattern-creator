package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import data.UserImage;
import view.Window.panels;

public class SelectImage extends JPanel{

	private Window w;
	JLabel selectedImage;
	JLabel progressionText;
	boolean canCreateAPattern;
	boolean creatingAPattern;
	
	public SelectImage(Window w) {
		this.w=w;
		canCreateAPattern=false;
		creatingAPattern=false;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setBackground(Color.BLUE);
		
		selectedImage=new JLabel();
		selectedImage.setSize(w.getWidth()/4, w.getWidth()/4);
		selectedImage.setLocation(w.getWidth()/2-selectedImage.getWidth()/2, w.getHeight()/2-selectedImage.getHeight()/2);
		this.add(selectedImage);
		
		selectedImage.setSize(w.getWidth()/4, w.getWidth()/4);
		selectedImage.setLocation(w.getWidth()/2-selectedImage.getWidth()/2, w.getHeight()/2-selectedImage.getHeight()/2);
		
		progressionText=new JLabel();
		progressionText.setSize(w.getWidth()/2,w.getHeight()/9);
		progressionText.setLocation(w.getWidth()/2-progressionText.getWidth()/8, w.getHeight()-w.getHeight()/4);
		progressionText.setFont(new Font("Arial",Font.BOLD,20));
		progressionText.setForeground(Color.WHITE);
		this.add(progressionText);
		
		JButton openFile=new JButton();
		openFile.setSize(w.getWidth()/3,w.getHeight()/9);
		
		openFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(!creatingAPattern) {
					UserImage image=w.pc.openFileSearch();
					changeLabelImage(image.getImage());
				}
				
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
		
		Runnable r=new Runnable() {
			@Override
			public void run() {
				canCreateAPattern=false;
				creatingAPattern=true;
				w.pc.convertImage();
				float progress=0;
				do {
					progress=w.pc.getProgress();
					progress=Math.round(progress*100)/100;
					progressionText.setText("PROGRESS: "+(int)progress+"%");
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}while(progress<100);
				w.setPanel(panels.EXPORT_IMAGE);
			}
			
		};
		createPattern.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(canCreateAPattern && !creatingAPattern) {
					new Thread(r).start();
				}
			}
		});
		
		createPattern.setLocation(w.getWidth()/2-createPattern.getWidth()/2, w.getHeight()-w.getHeight()/6);
		createPattern.setText("CREATE PATTERN");
		this.add(createPattern);
	}


}
