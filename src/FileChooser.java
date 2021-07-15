import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;


public class FileChooser {
	JFileChooser file_save;
	
	public FileChooser() {
		file_save = new JFileChooser();
	}
	
	public void drawMazeToGraphics(char[][] grid, Graphics givenGraphics){
		if(grid == null) return;

		int wall_size = 7;
		int x_start_pos = 45;
		int y_start_pos = 400 - wall_size*grid[0].length/2;
		for(int i = 0, x = x_start_pos; i < grid.length; i++, x+=wall_size){
			for(int j = 0, y = y_start_pos; j < grid[0].length; j++,y+=wall_size){
				if(grid[i][j] == '#'){
					givenGraphics.fillRect(y, x, wall_size, wall_size);
				}
			}
		}
	}
	
	public boolean isValidExtension(String extension){
		return extension.equals("png") || extension.equals("jpg");
	}
	
	public String getExtension(String file_name){
		if(file_name.indexOf('.') != -1){
			String extension = file_name.substring(file_name.length()-3);
			return (isValidExtension(extension) ? extension : null);
		}else{
			return "png";
		}
	}
	
	public BufferedImage createBufferedImage(){
		return new BufferedImage(800, 500, BufferedImage.TYPE_INT_RGB);
	}
	
	public Graphics giveGraphics(BufferedImage image){
		Graphics mazeGraphics = (Graphics) image.createGraphics();
		mazeGraphics.setColor(Color.WHITE);
		mazeGraphics.fillRect(0, 0, 800, 500);
		mazeGraphics.setColor(Color.BLACK);
		return mazeGraphics;
	}
	
	public String downloadImage(char[][] mazeGrid, File given_file){
		String file_name = given_file.getName();
		String extension = getExtension(file_name);
		if(extension == null) return "Please make sure your file names have .png or .jpg extensions";
		
		BufferedImage image = createBufferedImage();
		drawMazeToGraphics(mazeGrid, giveGraphics(image));
		
		try {
			ImageIO.write(image, extension, given_file);
			return "Successfully saved image to " + file_name;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return "An error occurred. Make sure your file name is different from any directories on your device";
		}
	}

	public void showUp(Maze current_maze){
		if(current_maze == null){
			showPopupMessage("Must create a maze first!");
			return;
		}
		
		file_save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int return_value = file_save.showSaveDialog(null);
		
		if(return_value == JFileChooser.APPROVE_OPTION){
			String result = downloadImage(current_maze.grid.grid, file_save.getSelectedFile());
			showPopupMessage(result);
		}
	}
	
	public void showPopupMessage(String text){
		JOptionPane.showMessageDialog(null, text);
	}
}