import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JRadioButton;


public class Launch {
	static int height = 500, width = 800;
	public static void main(String[] args){
		GenerateMaze maze_generation_class = new GenerateMaze(width, height);
		
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(maze_generation_class);
		frame.setSize(width, height + 23);
		frame.setVisible(true);
		
	}
	
	
}
