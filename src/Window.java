import javax.swing.JFrame;


public class Window extends JFrame{

	public Window(int width, int height) {
		UserDisplay display = new UserDisplay();
		
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(display);
		setVisible(true);
	}

}
