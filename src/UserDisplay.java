import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;


public class UserDisplay extends JPanel{
	private Button create_maze;
	private UserSelect user_select;
	private Maze current_maze;
	
	public void paint(Graphics g){
		super.paintComponent(g);
		
		if(current_maze == null) return;
		
		char[][] grid = current_maze.grid.grid;
		g.setColor(Color.BLACK);
		
		int wall_size = 7;
		int x_start_pos = 45;
		int y_start_pos = 400 - wall_size*grid[0].length/2;
		for(int i = 0, x = x_start_pos; i < grid.length; i++, x+=wall_size){
			for(int j = 0, y = y_start_pos; j < grid[0].length; j++,y+=wall_size){
				if(grid[i][j] == '#'){
					g.fillRect(y, x, wall_size, wall_size);
				}
			}
		}
		
	}
	
	private void createComponents(){
		create_maze = new Button("Create New");
		
		user_select = new UserSelect(700, 60);
		ComponentListener listener = new ComponentAdapter() {
		      public void componentHidden(ComponentEvent evt) {
		    	  int complexity = user_select.components_panel.findComplexity();
		    	  current_maze = new Maze(10+10*complexity, 20+10*complexity);
		    	  current_maze.generateMaze();
		    	  repaint();
		      }
		};
		user_select.addComponentListener(listener);
		
		create_maze.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				user_select.display();
			}
		});
		
		add(create_maze);
	}
	
	public UserDisplay() {
		createComponents();
	}
	
	

}
