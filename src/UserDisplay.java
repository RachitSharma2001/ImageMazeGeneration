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
	
	/* method to update graphics on screen; called whenever
	   repaint() is called */
	public void paint(Graphics g){
		// Set parent paint component
		super.paintComponent(g);
		
		// Avoid null pointer exception
		if(current_maze == null) return;
		
		// Draw the current maze to g(which displays maze on screen)
		My_Graphics.drawMazeToGraphics(current_maze.grid.grid, g);
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
		
		Printer printer = new Printer();
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
