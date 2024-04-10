package data;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PatternCreator {
	private final String COLORS_FILE="res\\Colors.txt";
	private List<Stitch> stitches;
	private UserImage imageToConvert;
	private BufferedImage convertedImage;
	private HashMap<data.Color,Stitch> stitchesToUse;
	private Set<Stitch> stitchesUsed;
	private final int MAX_WIDTH_FOR_PDF=440;
	private final int MAX_HEIGHT_FOR_PDF=640;
	public PatternCreator() {
		stitches=new LinkedList();
		readColorsFile();
		
		//showColors();
	}

	private void readColorsFile() {
		Scanner sc=null;
		int line=0;
		try {
			sc = new Scanner(new File(COLORS_FILE));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		while(sc.hasNextLine()) {
			stitches.add(new Stitch(sc.nextLine(),line));
			line++;
		}
	}
	
	public UserImage openFileSearch() {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JPG & PNG Images", "jpg","png");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(null);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	imageToConvert=new UserImage(chooser.getSelectedFile());
	    }
	    return imageToConvert;
	}
	
	public void convertImage() {
		List<data.Color> colors=imageToConvert.getColors();
		Set<data.Color> differentColors=imageToConvert.getDifferentColors();
		stitchesUsed=new HashSet<Stitch>();
		System.out.println("DIFERENTES: "+differentColors.size());
		stitchesToUse=new HashMap();
		for(data.Color c: differentColors) {
			Stitch s=getStitchForTheColor(c);
			System.out.println(s+"->"+c.getRgb()[0]+","+c.getRgb()[1]+","+c.getRgb()[2]);
			stitchesToUse.put(c, s);
			stitchesUsed.add(s);
		}
		drawPattern();
		
		//stitchesToUse=new LinkedList<Stitch>();
		try {
			  ImageIO.write(convertedImage, "png", new File("res\\imagen.png"));
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
	}
	
	private void drawPattern() {
		int color=0;
		int squareSize=20;
		int marginSize=40;
		convertedImage=new BufferedImage(marginSize*2+20*imageToConvert.getDimmensions()[0],marginSize*2+20*imageToConvert.getDimmensions()[1],BufferedImage.TYPE_INT_RGB);
		Graphics g=convertedImage.getGraphics();
		Font symbols=new Font("Arial",Font.BOLD,15);
		Font guides=new Font("Arial",Font.BOLD,10);
		g.setColor(Color.gray);
		g.drawRect(0, 0, convertedImage.getWidth(), convertedImage.getHeight());
		//Draw the pattern
		g.setColor(Color.red);
		for(int i=0;i<imageToConvert.getDimmensions()[0];i++) {
			g.setFont(guides);
			g.drawString((i+1)+"", squareSize*i+marginSize, marginSize);
			for(int j=0;j<imageToConvert.getDimmensions()[1];j++) {
				if(i==0) {
					g.setFont(guides);
					g.drawString(j+1+"", marginSize/2,squareSize*(j+1)+marginSize);
				}
				Stitch s=stitchesToUse.get(imageToConvert.getColors().get(color));
				g.setColor(new Color(s.getRGB()[0],
						s.getRGB()[1],
						s.getRGB()[2]));
				g.fillRect(squareSize*i+marginSize, squareSize*j+marginSize, squareSize, squareSize);
				if(s.getRGB()[0]+s.getRGB()[1]+s.getRGB()[2]>=382) {
					
					g.setColor(Color.BLACK);
				}else {
					g.setColor(Color.WHITE);
				}
				g.setFont(symbols);
				g.drawString(s.getSymbol()+"", squareSize*i+marginSize+7, squareSize*(j+1)+marginSize-3);
				//System.out.println(color+" / "+imageToConvert.getColors().size()+" -> "+s.toString());
				color++;
				g.setColor(Color.red);
				g.drawLine(0, 20*j+marginSize, imageToConvert.getDimmensions()[0]*20+marginSize, 20*j+marginSize);
			}
			g.drawLine(20*i+marginSize, 0, 20*i+marginSize, imageToConvert.getDimmensions()[1]*20+marginSize);
		}
		
		g.dispose();
		//Draw the grid
		
	}

	private Stitch getStitchForTheColor(data.Color c) {
		Stitch stitch=null;
		double closestColor=Double.MAX_VALUE;
		double distance=0;
		for(Stitch s:stitches) {
			int[] rgb=s.getRGB();
			int[] colorRgb=new int[3];
			colorRgb[0]=Color.decode(c.getHex()).getRed();
			colorRgb[1]=Color.decode(c.getHex()).getGreen();
			colorRgb[2]=Color.decode(c.getHex()).getBlue();
			
			distance=getDistance(rgb,colorRgb);
			/*distance=Math.sqrt(Math.pow(colorRgb[0]-rgb[0], 2)+
					Math.pow(colorRgb[1]-rgb[1], 2)+
					Math.pow(colorRgb[2]-rgb[2], 2));*/
			if(distance<closestColor) {
				closestColor=distance;
				stitch=s;
				//System.out.println(c.getHex()+"->"+stitch+" number: "+number);
			}
		}
		return stitch;
	}
	
	private double getDistance(int[] color1,int[] color2) {
	    int deltaR = color1[0] - color2[0];
	    int deltaG = color1[1] - color2[1];
	    int deltaB = color1[2] - color2[2];
	    float distance = (deltaR * deltaR) * 0.2989F
                + (deltaG * deltaG) * 0.5870F
                + (deltaB * deltaB) * 0.1140F;
	    return distance;
	}
	
	private void showColors() {
		for(Stitch s: stitches){
			System.out.println(s);
		}
	}

	public UserImage getImageToConvert() {
		return imageToConvert;
	}

	public BufferedImage getConvertedImage() {
		return convertedImage;
	}

	public void resizeImage(int width, int height) {
		// TODO Auto-generated method stub
		imageToConvert.resizeImage(width, height);
		try {
			  ImageIO.write(convertedImage, "png", new File("res\\imagenReescalada.png"));
		  } catch (IOException e) {
			  // TODO Auto-generated catch block
			  e.printStackTrace();
		  }
		convertImage();
	}

	public void exportToPdf() {
		String file=SelectFolder();
		if(file.length()==0) {
			JOptionPane.showMessageDialog(null, "The file name cannot be empty");
		}else if(file!=null){
			file+=".pdf";
			DivideConvertedImage();
			PdfManager pdf=new PdfManager(convertedImage,file);
		}
	}

	private String SelectFolder() {
		JFileChooser chooser = new JFileChooser(); 
	    //chooser.setCurrentDirectory(new java.io.File("."));
	    chooser.setDialogTitle("Choose the directory");
	    chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
	    //
	    // disable the "All files" option.
	    //
	    chooser.setAcceptAllFileFilterUsed(false);
	    //    
	    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) { 
		      System.out.println("getCurrentDirectory(): " 
		         +  chooser.getCurrentDirectory());
		      System.out.println("getSelectedFile() : " 
		         +  chooser.getSelectedFile());
	    	return chooser.getSelectedFile().getPath();
	    }
	    else {
	      return null;
	    }
		
	}

	private void DivideConvertedImage() {
		RemoveFilesFromDirectory();
		int numberOfImage=0;
		
		if(convertedImage.getWidth()<MAX_WIDTH_FOR_PDF && convertedImage.getHeight()<MAX_HEIGHT_FOR_PDF) {
			try {
				ImageIO.write(convertedImage,"png", new File("res\\pdfImages\\"+numberOfImage+".png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(convertedImage.getWidth()<MAX_WIDTH_FOR_PDF){
			for(int i=0;i<convertedImage.getHeight()/MAX_HEIGHT_FOR_PDF;i++) {
			   try {
					ImageIO.write(convertedImage.getSubimage(convertedImage.getWidth(), MAX_HEIGHT_FOR_PDF*i,
							convertedImage.getWidth(), MAX_HEIGHT_FOR_PDF),
							"png", new File("res\\pdfImages\\"+numberOfImage+".png"));
					numberOfImage++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else if(convertedImage.getHeight()<MAX_HEIGHT_FOR_PDF) {
			for(int i=0;i<convertedImage.getWidth()/MAX_WIDTH_FOR_PDF;i++) {
				try {
					ImageIO.write(convertedImage.getSubimage(MAX_WIDTH_FOR_PDF*i, convertedImage.getHeight(),
							MAX_WIDTH_FOR_PDF, convertedImage.getHeight()),
							"png", new File("res\\pdfImages\\"+numberOfImage+".png"));
					numberOfImage++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}else {
			for(int i=0;i<convertedImage.getWidth()/MAX_WIDTH_FOR_PDF;i++) {
				for(int j=0;j<convertedImage.getHeight()/MAX_HEIGHT_FOR_PDF;j++) {
					try {
						ImageIO.write(convertedImage.getSubimage(MAX_WIDTH_FOR_PDF*i, MAX_HEIGHT_FOR_PDF*j,
								MAX_WIDTH_FOR_PDF, MAX_HEIGHT_FOR_PDF),
								"png", new File("res\\pdfImages\\"+numberOfImage+".png"));
						numberOfImage++;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		
	}

	private void RemoveFilesFromDirectory() {
		System.out.println("Eliminar ficheros");
		for(File file:new File("res\\pdfImages").listFiles()) {
			if(!file.isDirectory()) {
				file.delete();
			}
		}
		
	}
	
	
}
