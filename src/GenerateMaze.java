import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Scrollbar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
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
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


public class GenerateMaze extends JPanel{	
	static double start_maze_parameter = 0.05;
	static double wall_size_parameter = 0.0155;
	static int complexity = 0;
	static Maze current_maze;
	int width, height;
	Scrollbar mheight_slider, mwidth_slider;
	
	public void remakeMaze(){
		int maze_h = mheight_slider.getValue();
		int maze_w = mwidth_slider.getValue();
		current_maze = new Maze(maze_h, maze_w);
		repaint();
	}
	
	public GenerateMaze(int w, int h){
		width = w;
		height = h;
		
		Button create_maze = new Button("Create New");
		Button save_maze = new Button("Save Maze");
		Button print_maze = new Button("Print Maze");
		mheight_slider = new Scrollbar(Scrollbar.HORIZONTAL);
		mwidth_slider = new Scrollbar(Scrollbar.HORIZONTAL);

		mheight_slider.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				remakeMaze();
			}
			
		});
		
		mwidth_slider.addAdjustmentListener(new AdjustmentListener(){

			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				remakeMaze();
			}
			
		});
		
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
					showPopupMessage("Must create a maze first!");
					return;
				}
				
				JFileChooser file_save = new JFileChooser();
				file_save.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int return_value = file_save.showSaveDialog(null);
				
				if(return_value == JFileChooser.APPROVE_OPTION){
					String result = downloadImage(current_maze.grid.grid, file_save.getSelectedFile());
					showPopupMessage(result);
				}
			}
		});
		
		print_maze.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				printEverything();
			}
		});
		
		add(create_maze);
		add(save_maze);
		add(print_maze);
		add(mheight_slider);
		add(mwidth_slider);
	}
	
	public JRadioButton[] createRadioButtons(String[] text){
		JRadioButton[] radio_arr = new JRadioButton[3];
		for(int i = 0; i < text.length; i++){
			radio_arr[i] = new JRadioButton(text[i]);
			final int c = i+1;
			radio_arr[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int j = 0; j < 3; j++) radio_arr[j].setSelected(false);
					radio_arr[c-1].setSelected(true);
					complexity = c;
					current_maze = new Maze(10*c, 10*c + 10);
					mheight_slider.setValue(10*c);
					mwidth_slider.setValue(10*c+10);
				}
			});
		}
		return radio_arr;
	}
	
	public JFrame initializeUserSelect(){
		JFrame user_select = new JFrame();
		user_select.setSize(700, 60);
		
		JTextArea text = new JTextArea("Select the complexity of maze");
		text.setEditable(false);
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
	
	public void showPopupMessage(String text){
		JOptionPane.showMessageDialog(null, text);
	}
	
	public void drawMazeToGraphics(char[][] grid, Graphics givenGraphics){
		if(grid == null) return;
		
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
	
	/*public void paint(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		int maze_height = 0, maze_width = 0;
		if(complexity == 1){
			maze_height = 10;
			maze_width = 20;
		}else if(complexity == 2){
			maze_height = 20;
			maze_width = 30;
		}else if(complexity == 3){
			maze_height = 30;
			maze_width = 40;
		}
		
		if(maze_height != 0){
			current_maze = new Maze(maze_height, maze_width);
			current_maze.generateMaze();
			drawMazeToGraphics(current_maze.grid.grid, g);
		}
	}*/
	
	public void paint(Graphics g){
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		
		if(current_maze != null){
			//current_maze = new Maze(maze_height, maze_width);
			current_maze.generateMaze();
			drawMazeToGraphics(current_maze.grid.grid, g);
		}
	}
	
	public Graphics giveGraphics(BufferedImage image){
		Graphics mazeGraphics = (Graphics) image.createGraphics();
		mazeGraphics.setColor(Color.WHITE);
		mazeGraphics.fillRect(0, 0, width, height);
		mazeGraphics.setColor(Color.BLACK);
		return mazeGraphics;
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
		return new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
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
	
	public void printEverything(){
		if(current_maze.grid.grid == null) return;
		Graphics maze = giveGraphics(new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB));
		drawMazeToGraphics(current_maze.grid.grid, maze);
		
		PrinterJob print_job = PrinterJob.getPrinterJob();
		print_job.setPrintable(new Printer(maze));
		try {
			print_job.print();
		} catch (PrinterException e) {
			showPopupMessage("An error occured. Make sure a printer is set up on your computer!");
		} catch (Exception e){
			showPopupMessage("An error occured");
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
