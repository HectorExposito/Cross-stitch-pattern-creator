package data;

import view.Window;

public class Main {

	public static void main(String[] args) {
		
		//String imagen="C:\\Users\\hecto\\Desktop\\doctor.jpg";
		PatternCreator pc=new PatternCreator();
		Window w=new Window("Pattern generator",pc);
		w.setVisible(true);
		
		//pc.openFileSearch();
	}

}
