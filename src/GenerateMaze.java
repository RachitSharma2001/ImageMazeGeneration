import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.print.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class GenerateMaze extends JPanel{	
	static double start_maze_parameter = 0.05;
	static double wall_size_parameter = 0.0155;
	static int complexity = 0;
	static Maze current_maze;
	
	public JRadioButton[] createRadioButtons(String[] text){
		JRadioButton[] radio_arr = new JRadioButton[3];
		for(int i = 0; i < text.length; i++){
			radio_arr[i] = new JRadioButton(text[i]);
			final int c = i+1;
			radio_arr[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					complexity = c;
				}
			});
		}
		return radio_arr;
	}
	
	public JFrame initializeUserSelect(){
		JFrame user_select = new JFrame();
		user_select.setSize(700, 60);
		
		JTextArea text = new JTextArea("Select the complexity of maze");
		String[] radio_text = {"Small", "Medium", "Large"};
		JRadioButton[] radio_buttons = createRadioButtons(radio_text);
		Button create = new Button("Create");
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < radio_buttons.length; i++) radio_buttons[i].setSelected(false);
				user_select.setVisible(false);
				repaint();
			}
		});
		
		JPanel components = new JPanel();
		components.add(text);
		for(int i = 0; i < radio_buttons.length; i++) components.add(radio_buttons[i]);
		components.add(create);
		
		user_select.add(components);
		return user_select;
	}
	
	public GenerateMaze(int width, int height){
		//repaint();
		Button create_maze = new Button("Create");
		Button save_maze = new Button("Save Maze");
		
		JFrame user_select = initializeUserSelect();
		
		create_maze.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				user_select.setVisible(true);
			}
		});
		save_maze.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(current_maze == null){
					JOptionPane.showMessageDialog(null, "Must create a maze first!");
					return;
				}
				
				JFileChooser file_save = new JFileChooser();
				file_save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int return_value = file_save.showSaveDialog(null);
				
				if(return_value == JFileChooser.APPROVE_OPTION){
					File given_file = file_save.getSelectedFile();
					String file_name = given_file.getName();
					String extension = file_name.substring(file_name.length()-4);
					if(!extension.equals(".png") && !extension.equals(".jpg")){
						extension = "png";
					}else{
						extension = extension.substring(1);
					}
					
					System.out.println("filename, extension, path: " + file_name + ", " + extension + ", " + given_file.getAbsolutePath());
					
					boolean error = downloadImageGivenFile(current_maze.grid.grid, width, height, given_file, extension);
					if(error){
						JOptionPane.showMessageDialog(null, "An error occured. Make sure to give a file name that is not a directory");
					}
				}
			}
		});
		
		add(create_maze);
		add(save_maze);
	}
	
	public void drawMazeToGraphics(char[][] grid, Graphics givenGraphics, int width, int height){
		// Make a height_parameter = 1/11, then set wall_size to (1-height_parameter)*height/grid.length - 10
		int wall_size = (int) (wall_size_parameter*height);
		int x_start_pos = height/11;
		int y_start_pos = width/2 - wall_size*grid[0].length/2;
		
		for(int i = 0, x = x_start_pos; i < grid.length; i++, x+=wall_size){
			for(int j = 0, y = y_start_pos; j < grid[0].length; j++,y+=wall_size){
				if(grid[i][j] == '#'){
					givenGraphics.fillRect(y, x, wall_size, wall_size);
				}
			}
		}
	}
	
	public void paint(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		if(complexity == 1){
			current_maze = new Maze(10, 20);
			current_maze.generateMaze();
			current_maze.grid.print();

			drawMazeToGraphics(current_maze.grid.grid, g, getWidth(), getHeight());
		}else if(complexity == 2){
			current_maze = new Maze(20, 30);
			current_maze.generateMaze();
			current_maze.grid.print();

			drawMazeToGraphics(current_maze.grid.grid, g, getWidth(), getHeight());
		}else if(complexity == 3){
			current_maze = new Maze(30, 40);
			current_maze.generateMaze();
			current_maze.grid.print();

			drawMazeToGraphics(current_maze.grid.grid, g, getWidth(), getHeight());
		}
	}
	
	public boolean downloadImageGivenFile(char[][] mazeGrid, int width, int height, File save_to, String file_type){
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics mazeGraphics = (Graphics) image.createGraphics();
		mazeGraphics.setColor(Color.WHITE);
		mazeGraphics.fillRect(0, 0, width, height);
		mazeGraphics.setColor(Color.BLACK);
		
		drawMazeToGraphics(mazeGrid, mazeGraphics, width, height);
		
		try {
			ImageIO.write(image, file_type, save_to);
			return false;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return true;
		}
	}
	
	public void printEverything(Graphics g){
		PrinterJob print_job = PrinterJob.getPrinterJob();
		print_job.setPrintable(new Printer(g));
		try {
			print_job.print();
		} catch (PrinterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

class Printer implements Printable{
	Graphics passed_in_graphics;
	
	Printer(Graphics given_graphics){
		passed_in_graphics = given_graphics;
	}
	
	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex)
			throws PrinterException {
		
		if(pageIndex > 0){
			return NO_SUCH_PAGE;
	    }
		
		Graphics2D twod_graphics = (Graphics2D) graphics;
		twod_graphics.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		
		return PAGE_EXISTS;
	}
	
}

class Maze{
	private Walls walls;
	public Grid grid;
	private DisjointSet ds;
	
	Maze(int R, int C){
		walls = new Walls(R, C);
		grid = new Grid(R, C);
		ds = new DisjointSet(R, C);
	}
	
	void generateMaze(){
		while(!walls.noneLeft()){
			WallObj curr_wall = walls.giveRandomWall();
			int first_id = curr_wall.getFirstId();
			int second_id = curr_wall.getSecondId();
			if(!ds.sameSet(first_id, second_id)){
				ds.union(first_id, second_id);
				grid.knockDownWall(first_id, second_id);
			}
		}
	}
}

// Eventually I want to remove the array list and have just the priority queues
class Walls{
	private ArrayList<WallObj> walls_list;
	
	Walls(int R, int C){
		int id_count = 0;
		walls_list = new ArrayList<WallObj>();
		for(int row = 0; row < R; row++){
			for(int col = 0; col < C; col++){
				if(col != 0) walls_list.add(new WallObj(id_count-1, id_count));
				if(row != 0) walls_list.add(new WallObj(id_count-C, id_count));
				id_count++;
			}
		}
	}
	
	WallObj giveRandomWall(){
		int rand_ind = Random.giveRandInt(0, walls_list.size() - 1);
		WallObj deleted_wall = walls_list.get(rand_ind);
		walls_list.remove(rand_ind);
		return deleted_wall;
	}
	
	boolean noneLeft(){
		return walls_list.size() == 0;
	}
}

class WallObj{
	int first_id, second_id;
	
	WallObj(int f_id, int s_id){
		first_id = f_id;
		second_id = s_id;
	}
	
	int getFirstId(){
		return first_id;
	}
	
	int getSecondId(){
		return second_id;
	}
}

class Grid{
	char[][] grid;
	char SPACE = ' ', WALL = '#';
	int R, C;
	Grid(int given_R, int given_C){
		R = given_R;
		C = given_C;
		
		initializeGridArray(2*R+1, 2*C+1);
	}
	
	void initializeGridArray(int R, int C){
		grid = new char[R][C];
		for(int i = 0; i < R; i++){
			for(int j = 0; j < C; j++){
				if(isEven(i*j)){
					grid[i][j] = WALL;
				}else{
					grid[i][j] = SPACE;
				}
			}
		}
		grid[0][1] = SPACE;
		grid[R-1][C-2] = SPACE;
	}
	
	void knockDownWall(int first_id, int second_id){
		int x = getRow(first_id);
		int y = getCol(first_id);
		if(isRight(first_id, second_id)){
			grid[x][y+1] = SPACE;
		}else{
			grid[x+1][y] = SPACE;
		}
	}
	
	int getRow(int id){
		return 1 + 2*(id/C);
	}
	
	int getCol(int id){
		return 1 + 2*(id%C);
	}
	
	boolean isRight(int first_id, int second_id){
		return first_id + 1 == second_id;
	}
	
	boolean isEven(int num){ 
		return num % 2 == 0;
	}
	
	void print(){
		for(int i = 0; i < grid.length; i++){
			for(int j = 0; j < grid[0].length; j++){
				System.out.print(grid[i][j]);
			}
			System.out.println();
		}
	}
}

class DisjointSet{
	int[] disjoint_set;
	
	DisjointSet(int R, int C){
		disjoint_set = new int[R*C];
		for(int i = 0; i < disjoint_set.length; i++){
			disjoint_set[i] = -1;
		}
	}
	
	void union(int first, int second){
		int head_first = find(first);
		int head_second = find(second);
		int new_size = disjoint_set[head_first] + disjoint_set[head_second];
		
		if(isSmaller(head_first, head_second)){
			disjoint_set[head_first] = head_second;
			disjoint_set[head_second] = new_size;
		}else{
			disjoint_set[head_second] = head_first;
			disjoint_set[head_first] = new_size;
		}
	}
	
	int find(int id){
		while(disjoint_set[id] >= 0){
			id = disjoint_set[id];
		}
		
		return id;
	}
	
	boolean sameSet(int first_id, int second_id){
		return find(first_id) == find(second_id);
	}
	
	boolean isSmaller(int left_head, int right_head){
		return disjoint_set[left_head] > disjoint_set[right_head];
	}
}

class Random{
	static int giveRandInt(int low, int high){
		return low + (int) (Math.random() * (high - low));
	}
}