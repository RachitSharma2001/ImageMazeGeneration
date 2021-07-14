import javax.swing.JFrame;
import javax.swing.JTextArea;


public class UserSelect extends JFrame{
	public UserSelectPanel components_panel;
	public UserSelect(int width, int height) {
		components_panel = new UserSelectPanel(this);
		
		setSize(width, height);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(components_panel);
		setVisible(false);
	}
	
	public void display(){
		setVisible(true);
	}

}
