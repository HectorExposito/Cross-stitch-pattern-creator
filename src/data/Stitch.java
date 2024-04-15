package data;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class Stitch {
	private String dmcName;
	private String floss;
	private int[] RGB;
	private String hex;
	private char character;
	private BufferedImage symbol;
	private final int symbolSize=10;
	
	public Stitch(String fileLine,int line) {
		String stitchData[]=fileLine.split("\\t");
		RGB=new int[3];
		loadSymbol(line);
		saveData(stitchData);
	}

	private void loadSymbol(int line) {
		line=line+33;
		
		if(line>126) {
			line=line+34;
		}
		character=(char)line;
	}

	private void saveData(String[] stitchData) {
		for(int i=0;i<stitchData.length;i++) {
			switch(i) {
			case 0:
				floss=stitchData[i];
				break;
			case 1:
				dmcName=stitchData[i];
				break;
			case 2:
				RGB[0]=Integer.parseInt(stitchData[i]);
				break;
			case 3:
				RGB[1]=Integer.parseInt(stitchData[i]);
				break;
			case 4:
				RGB[2]=Integer.parseInt(stitchData[i]);
				break;
			case 5:
				hex=stitchData[i];
				break;
			}
		}
	}

	
	public int[] getRGB() {
		return RGB;
	}
	
	public char getSymbol() {
		return character;
	}
	
	public String getDmcName() {
		return dmcName;
	}
	
	public String getFloss() {
		return floss;
	}

	@Override
	public String toString() {
		return "Stitch [dmcName=" + dmcName + ", floss=" + floss + ", RGB=" + Arrays.toString(RGB) + ", hex=" + hex
				+ "]";
	}

	public String getHex() {
		// TODO Auto-generated method stub
		return hex;
	}
	
	
}
