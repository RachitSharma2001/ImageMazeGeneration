import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import javax.swing.JOptionPane;

public class Printer implements Printable{
	
	public void showPopupMessage(String text){
		JOptionPane.showMessageDialog(null, text);
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
	
	public Graphics giveGraphics(BufferedImage image){
		Graphics mazeGraphics = (Graphics) image.createGraphics();
		mazeGraphics.setColor(Color.WHITE);
		mazeGraphics.fillRect(0, 0, 800, 500);
		mazeGraphics.setColor(Color.BLACK);
		return mazeGraphics;
	}
	
	public void printScreen(Maze current_maze){
		if(current_maze == null /*|| current_maze.grid.grid == null*/){
			showPopupMessage("You must create a maze first!");
			return;
		}
		Graphics maze = giveGraphics(new BufferedImage(800, 500, BufferedImage.TYPE_INT_RGB));
		My_Graphics.drawMazeToGraphics(current_maze.grid.grid, maze);
		
		PrinterJob print_job = PrinterJob.getPrinterJob();
		print_job.setPrintable(this);
		try {
			print_job.print();
		} catch (PrinterException e) {
			showPopupMessage("An error occured. Make sure a printer is set up on your computer!");
		} catch (Exception e){
			showPopupMessage("An error occured");
		}
	}
	
}
