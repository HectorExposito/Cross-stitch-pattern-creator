package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import javax.swing.ImageIcon;

import data.UserImage;
import view.Window.panels;

public class ExportImage extends JPanel {

	private Window w;
	private boolean exportingToPdf;
	
	public ExportImage(Window w) {
		this.w=w;
		exportingToPdf=false;
		initialize();
	}

	private void initialize() {
		this.setLayout(null);
		this.setBackground(Color.LIGHT_GRAY);
		
		setRightPanelComponents();
		setLeftPanelComponents();
		
		
		
	}

	private void setRightPanelComponents() {
		JPanel rightPanel=new JPanel();
		rightPanel.setSize(3*w.getWidth()/4,w.getHeight());
		rightPanel.setBackground(Color.GREEN);
		rightPanel.setLocation(w.getWidth()/4, 0);
		rightPanel.setLayout(null);
		
		JLabel patternImage=new JLabel();
		patternImage.setIcon(new ImageIcon(w.pc.getConvertedImage()));
		patternImage.setSize(rightPanel.getWidth(), rightPanel.getHeight());
		
		rightPanel.add(patternImage);
		
		JScrollPane scrollPane=new JScrollPane(patternImage);
		scrollPane.setSize(3*w.getWidth()/4,w.getHeight()-30);
		scrollPane.setLocation(w.getWidth()/4-13,-6);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  
		System.out.println(scrollPane.getVerticalScrollBar().getBounds().getWidth());
		//this.add(rightPanel);
		this.add(scrollPane);
		 
	}

	private void setLeftPanelComponents() {
		JPanel leftPanel=new JPanel();
		leftPanel.setSize(w.getWidth()/4,w.getHeight());
		leftPanel.setBackground(Color.CYAN);
		leftPanel.setLayout(null);
		
		JPanel widthTextPanel=new JPanel();
		JPanel heightTextPanel=new JPanel();
		JLabel widthText=new JLabel();
		JLabel heightText=new JLabel();
		
		widthTextPanel.setSize(leftPanel.getWidth(), leftPanel.getHeight()/15);
		heightTextPanel.setSize(leftPanel.getWidth(), leftPanel.getHeight()/15);
		
		widthText.setText("Width:");
		heightText.setText("Height:");
		
		widthText.setFont(new Font("Arial",Font.BOLD,16));
		heightText.setFont(new Font("Arial",Font.BOLD,16));
		
		widthTextPanel.setLocation(leftPanel.getWidth()/2-widthTextPanel.getWidth()/2,
				leftPanel.getWidth()/4-widthTextPanel.getHeight()/2);
		heightTextPanel.setLocation(leftPanel.getWidth()/2-heightTextPanel.getWidth()/2,
				3*leftPanel.getWidth()/4-heightTextPanel.getHeight()/2);
		
		NumberFormat format = NumberFormat.getInstance();
	    NumberFormatter formatter = new NumberFormatter(format);
	    formatter.setValueClass(Integer.class);
	    formatter.setMinimum(1);
	    formatter.setMaximum(300);
	    formatter.setAllowsInvalid(true);
		
	    JFormattedTextField  widthTextField=new JFormattedTextField(formatter);
	    JFormattedTextField  heightTextField=new JFormattedTextField(formatter);
		
		widthTextField.setSize(leftPanel.getWidth(), leftPanel.getHeight()/15);
		heightTextField.setSize(leftPanel.getWidth(), leftPanel.getHeight()/15);
		
		widthTextField.setLocation(leftPanel.getWidth()/2-widthTextField.getWidth()/2,
				widthTextPanel.getY()+widthTextField.getHeight());
		heightTextField.setLocation(leftPanel.getWidth()/2-heightTextPanel.getWidth()/2,
				heightTextPanel.getY()+heightTextField.getHeight());
		
		widthTextField.setFont(new Font("Arial",Font.PLAIN,16));
		heightTextField.setFont(new Font("Arial",Font.PLAIN,16));
		
		widthTextField.setText(w.pc.getImageToConvert().getImage().getWidth()+"");
		heightTextField.setText(w.pc.getImageToConvert().getImage().getHeight()+"");
		
		JPanel dimmensionsPanel=new JPanel();
		JLabel dimmensionsText=new JLabel();
		
		dimmensionsPanel.setSize(leftPanel.getWidth(), leftPanel.getHeight()/15);
		
		dimmensionsPanel.setLocation(leftPanel.getWidth()/2-dimmensionsPanel.getWidth()/2,
				heightTextField.getY()+dimmensionsPanel.getHeight());
		
		dimmensionsText.setFont(new Font("Arial",Font.PLAIN,16));
		
		dimmensionsText.setText("<html>Minimum dimmensions: 1x1<br/>Maximum dimmensions: 300x300</html>");
		
		JButton resizeButton=new JButton();
		JButton exportButton=new JButton();
		JButton newPatternButton=new JButton();
		
		resizeButton.setSize(leftPanel.getWidth()/2, leftPanel.getHeight()/15);
		exportButton.setSize(leftPanel.getWidth()/2, leftPanel.getHeight()/15);
		newPatternButton.setSize(leftPanel.getWidth()/2, leftPanel.getHeight()/15);
		
		resizeButton.setLocation(leftPanel.getWidth()/2-resizeButton.getWidth()/2,
				dimmensionsPanel.getY()+resizeButton.getHeight()*4);
		
		exportButton.setLocation(leftPanel.getWidth()/2-exportButton.getWidth()/2,
				resizeButton.getY()+exportButton.getHeight());
		
		newPatternButton.setLocation(leftPanel.getWidth()/2-newPatternButton.getWidth()/2,
				exportButton.getY()+newPatternButton.getHeight());
		
		resizeButton.setText("RESIZE IMAGE");
		exportButton.setText("EXPORT TO PDF");
		newPatternButton.setText("CREATE NEW PATTERN");
		
		resizeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(!exportingToPdf) {
					int width=Integer.parseInt(widthTextField.getText());
					int height=Integer.parseInt(heightTextField.getText());
					
					w.pc.resizeImage(width,height);
					w.setPanel(panels.EXPORT_IMAGE);
				}
			}
		});
		
		exportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(!exportingToPdf) {
					Runnable r=new Runnable() {
						@Override
						public void run() {
							exportingToPdf=true;
							w.pc.exportToPdf();
							exportingToPdf=false;
							exportButton.setText("EXPORT TO PDF");
						}
					};
					new Thread(r).start();
					exportButton.setText("EXPORTING");
				}
			}
		});
		
		newPatternButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				if(!exportingToPdf) {
					w.setPanel(panels.SELECT_IMAGE);
				}
			}
		});
		
		dimmensionsPanel.add(dimmensionsText);
		widthTextPanel.add(widthText);
		heightTextPanel.add(heightText);
		leftPanel.add(dimmensionsPanel);
		leftPanel.add(widthTextPanel);
		leftPanel.add(heightTextPanel);
		leftPanel.add(widthTextField);
		leftPanel.add(heightTextField);
		leftPanel.add(exportButton);
		leftPanel.add(resizeButton);
		leftPanel.add(newPatternButton);
		this.add(leftPanel);
		
	}

}
