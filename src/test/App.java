package test;

import javax.swing.JFrame;

public class App {

	public static void main(String[] args) {
		JFrame frame=new JFrame("Stamp");
		frame.add(new StampView());
		frame.setSize(320, 480);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
