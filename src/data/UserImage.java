package data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.imageio.ImageIO;

public class UserImage {

	private BufferedImage image;
	private List<Color> colors;
	private Set<Color> differentColors;
	private final int MAX_SIZE=300;
	
	public UserImage(File image) {
		try {
			this.image = ImageIO.read(image);
			colors=new LinkedList();
			differentColors=new HashSet();
			checkSize();
			saveColors();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkSize() {
		if(image.getWidth()>MAX_SIZE && image.getHeight()>MAX_SIZE) {
			resizeImage(MAX_SIZE,MAX_SIZE);
		}else if(image.getWidth()>MAX_SIZE) {
			resizeImage(MAX_SIZE,image.getHeight());
		}else if(image.getHeight()>MAX_SIZE) {
			resizeImage(image.getWidth(),MAX_SIZE);
		}
		
	}

	private void saveColors() {
		int[] rgb=new int[3];

		colors=new LinkedList();
		differentColors=new HashSet();
		
		for(int i=0;i<image.getWidth();i++) {
			for(int j=0;j<image.getHeight();j++) {
				int color=image.getRGB(i, j);
				rgb[0] = color & 0xff;
				rgb[1] = (color & 0xff00) >> 8;
				rgb[2] = (color & 0xff0000) >> 16;
				String hex = String.format("#%02x%02x%02x", rgb[0], rgb[1], rgb[2]);  
				
				Color c=new Color(rgb,hex);
				colors.add(c);
				differentColors.add(c);
			}
		}
	}

	public void showDimmensions() {
		System.out.println(image.getHeight()+" "+image.getWidth());
		image.getScaledInstance(0, 0, java.awt.Image.SCALE_SMOOTH);
	}

	public List<Color> getColors() {
		return colors;
	}
	
	public void resizeImage(int width, int height) {
		  BufferedImage resizedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		  Graphics2D g = resizedImage.createGraphics();
		  g.drawImage(image, 0, 0, width, height, null);
		  g.dispose();
		  image=resizedImage;
		  saveColors();
	}

	public BufferedImage getImage() {
		return image;
	}

	public Set<Color> getDifferentColors() {
		return differentColors;
	}
	
	public int[] getDimmensions() {
		int[] dimmensions=new int[2];
		dimmensions[0]=image.getWidth();
		dimmensions[1]=image.getHeight();
		return dimmensions;
	}
}
