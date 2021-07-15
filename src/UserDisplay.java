import java.awt.Button;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class UserDisplay extends JPanel{
	private Button create_maze;
	private Button save_maze;
	private Button print_maze;
	private Scrollbar height_slider, width_slider;
	private UserSelect user_select;
	private Maze current_maze;
	
	public void paint(Graphics g){
		super.paintComponent(g);
		
		if(current_maze == null || current_maze.grid.grid == null) return;
		
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
	
	private void adjustSliders(int new_height, int new_width){
		height_slider.setValue(new_height);
		width_slider.setValue(new_width);
	}
	
	private void createMazeComponent(){
		create_maze = new Button("Create New");
		
		user_select = new UserSelect(700, 60);
		ComponentListener listener = new ComponentAdapter() {
		      public void componentHidden(ComponentEvent evt) {
		    	  int complexity = user_select.components_panel.findComplexity();
		    	  int new_maze_height = 10+10*complexity;
		    	  int new_maze_width = 20+10*complexity;
		    	  current_maze = new Maze(new_maze_height, new_maze_width);
		    	  current_maze.generateMaze();
		    	  adjustSliders(new_maze_height, new_maze_width);
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
	
	private void saveMazeComponent(){
		save_maze = new Button("Download");
		
		FileChooser new_chooser = new FileChooser();
		save_maze.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				new_chooser.showUp(current_maze);
			}
		});
		
		add(save_maze);
	}
	
	private void printMazeComponent(){
		print_maze = new Button("Print");
		
		Printer2 printer = new Printer2();
		print_maze.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				printer.printScreen(current_maze);
			}
		});
		
		add(print_maze);
	}
	
	private void remakeMaze(){
		int maze_h = height_slider.getValue();
		int maze_w = width_slider.getValue();
		current_maze = new Maze(maze_h, maze_w);
		current_maze.generateMaze();
		repaint();
	}
	
	private void adjustMazeComponent(){
		height_slider = new Scrollbar(Scrollbar.HORIZONTAL);
		width_slider = new Scrollbar(Scrollbar.HORIZONTAL);
		
		height_slider.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				remakeMaze();
			}
			
		});
		
		width_slider.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				remakeMaze();
			}
			
		});
		
		add(height_slider);
		add(width_slider);
	}
	
	private void createComponents(){
		createMazeComponent();
		saveMazeComponent();
		printMazeComponent();
		adjustMazeComponent();
	}
	
	public UserDisplay() {
		createComponents();
	}
	
	

}
