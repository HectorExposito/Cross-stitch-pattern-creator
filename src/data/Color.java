package data;

import java.util.Objects;

public class Color {

	private int[] rgb;
	private String hex;
	
	public Color(int[] rgb,String hex) {
		this.rgb=rgb;
		this.hex=hex;
	}
	
	public int[] getRgb() {
		return rgb;
	}
	public String getHex() {
		return hex;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(hex);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(this.rgb[0]==((Color)obj).getRgb()[0]) {
			if(this.rgb[1]==((Color)obj).getRgb()[1]) {
				if(this.rgb[2]==((Color)obj).getRgb()[2]) {
					return true;
				}
			}
		}
		return false;
	}

	
}
