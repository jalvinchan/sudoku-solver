//in case i need it
import javax.swing.JTextArea;

public class Message extends JTextArea{

	public Message() {
		super("Welcome!");
	}
	
	public void showError() {
		
	}
	
	public void showDefault() {
		setText("Welcome :)");
	}
	
	public void selectBox() {
		setText("Select a BOX");
	}
}
