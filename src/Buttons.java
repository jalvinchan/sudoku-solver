import java.awt.*;
import javax.swing.*;

public class Buttons extends JPanel {
	
	JPanel normalPanel, resetPanel;
	JButton buttons[], solveButton, resetButton[], backButton;
	
	public Buttons() {
		
		setLayout(new CardLayout());
		normalPanel = new JPanel();
		resetPanel = new JPanel();
		add(normalPanel, "normalPanel");
		add(resetPanel, "resetPanel");
		showNormal();
		
		normalPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		
		buttons = new JButton[10];
		for(int i=1; i<10; i++) {
			c.gridx = (i-1)%5;
			c.gridy = (i-1)/5;
			normalPanel.add(buttons[i] = new JButton(Integer.toString(i)), c);
		}
		c.gridx = 4;
		normalPanel.add(buttons[0] = new JButton("<-"), c);
		
		solveButton = new JButton("SOLVE");
		c.gridx = 5;
		c.gridy = 0;
		c.gridheight = 2;
		c.insets = new Insets(1,2,1,0);
		normalPanel.add(solveButton, c);
		
		backButton = new JButton("Back");
		resetPanel.add(backButton);
		
		resetButton = new JButton[2];
		for(int i=0; i<2; i++) 
			resetButton[i] = new JButton("Reset");
		c.gridx = 6;
		normalPanel.add(resetButton[0], c);
		resetPanel.add(resetButton[1]);
	}
	public void showReset() {
		CardLayout c = (CardLayout) getLayout();
		c.show(this, "resetPanel");
	}
	public void showNormal() {
		CardLayout c = (CardLayout) getLayout();
		c.show(this, "normalPanel");
	}
}
