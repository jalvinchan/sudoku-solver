import javax.swing.*;

public class SudokuSolver {
	
	public static final int BOX_SIZE = 40;
	
	public static void main(String[] args) {
		
		Everything a = new Everything();
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setSize(11*BOX_SIZE + 15, 11*BOX_SIZE + 150);
		a.setResizable(false);
		a.setVisible(true);
	}
}
