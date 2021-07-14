import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;


public class UserSelectPanel extends JPanel{

	private JTextArea text;
	private JRadioButton[] radio_buttons;
	private Button create;

	private void addText(){
		text = new JTextArea("Select the complexity of maze");
		text.setEditable(false);
		add(text);
	}
	
	public void addRadioButtons(){
		String text[] = {"Small", "Medium", "Large"};
		radio_buttons = new JRadioButton[3];
		for(int i = 0; i < text.length; i++){
			radio_buttons[i] = new JRadioButton(text[i]);
			final int c = i+1;
			radio_buttons[i].addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e) {
					for(int j = 0; j < 3; j++) radio_buttons[j].setSelected(false);
					radio_buttons[c-1].setSelected(true);
				}
			});
			add(radio_buttons[i]);
		}
	}
	
	public int findComplexity(){
		for(int i = 0; i <= 2; i++) if(radio_buttons[i].isSelected()) return i;
		return -1;
	}
	
	public void addCreateButton(JFrame window){
		create = new Button("Create");
		create.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				window.setVisible(false);
			}
		});
		add(create);
	}
	
	public UserSelectPanel(JFrame window) {
		addText();
		addRadioButtons();
		addCreateButton(window);
	}

}
