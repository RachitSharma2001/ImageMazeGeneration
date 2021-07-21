import java.awt.Graphics;

public class My_Graphics {

	// Static method that draws the given grid to given graphics
	public static void drawMazeToGraphics(char[][] grid, Graphics givenGraphics){
		// Avoid possible null pointer exception
		if(grid == null) return;
		
		// Define parameters 
		int wall_size = 7;
		int x_start_pos = 45;
		int y_start_pos = 400 - wall_size*grid[0].length/2;
		
		// Go through grid, draw walls where they occur
		for(int i = 0, x = x_start_pos; i < grid.length; i++, x+=wall_size){
			for(int j = 0, y = y_start_pos; j < grid[0].length; j++,y+=wall_size){
				if(grid[i][j] == '#'){
					givenGraphics.fillRect(y, x, wall_size, wall_size);
				}
			}
		}
	}

}
