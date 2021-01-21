import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Everything extends JFrame {
	
	private static final int BOX_SIZE = SudokuSolver.BOX_SIZE;
	
	Board board;
	Buttons buttons;
	JMenuBar mb;
	JMenu menu1, menu2;
	JPanel middlePanel;
	Message message;
	boolean solved = false;
	
	public Everything() {
		super("AWESOME SUDOKU SOLVER");
		
		mb = new JMenuBar();
		menu1 = new JMenu("File");
		menu2 = new JMenu("Help");
		mb.add(menu1);
		mb.add(menu2);
		add(BorderLayout.NORTH, mb);
		
		middlePanel = new JPanel(new BorderLayout());
		board = new Board();
		message = new Message();
//		board.setSize(9*BOX_SIZE, 9*BOX_SIZE);
		message.setEditable(false);
		middlePanel.add(board, BorderLayout.CENTER);
		middlePanel.add(message, BorderLayout.SOUTH);
		add(BorderLayout.CENTER, middlePanel);
		
//		board.setFocusable(true); ???
		board.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				char c = e.getKeyChar();
				int k = e.getKeyCode();
				if(Character.isDigit(c)) message.setText(board.setBox(c-'0'));
				else if(k == KeyEvent.VK_BACK_SPACE) message.setText(board.setBox(0));
				else if(k == KeyEvent.VK_UP) board.adjustBoxSelected(0,-1);
				else if(k == KeyEvent.VK_DOWN) board.adjustBoxSelected(0,1);
				else if(k == KeyEvent.VK_LEFT) board.adjustBoxSelected(-1,0);
				else if(k == KeyEvent.VK_RIGHT) board.adjustBoxSelected(1,0); 
				board.repaint();
			}
		});
		
		board.addMouseListener(new MouseAdapter() {
			private int clickCounter = 0;
			public void mouseClicked(MouseEvent event) {
				if(solved) return;
				board.resetError();
				board.requestFocus();
				int x = event.getX(), y = event.getY();
				x /= BOX_SIZE; y /= BOX_SIZE;
				board.setBoxSelected(x-1, y-1);
				if(x>=1 && x<=9 && y>=1 && y<=9) {
					clickCounter = 0;
					message.setText("Welcome :)");
				}
				else {
					clickCounter++;
					if(clickCounter > 5) message.setText("Stop clicking out of the box!"); 
					else if(y<1) {
						if(x<1)		message.setText("not here");
						else if(x>9)message.setText(":D");
						else 		message.setText("D:");
					}else if(y>9) {
						if(x<1) 	message.setText("where you clicking");
						else if(x>9)message.setText(":)");
						else 		message.setText("^^");
					}else if(x<1) 	message.setText("(-(-(-.-)-)-)");
					else if(x>9) 	message.setText(":P");
				}
				repaint();
			}
		});
		
		buttons = new Buttons();
		add(BorderLayout.SOUTH, buttons);
		buttons.setFocusable(true);		// for key listener
		for(int i=0; i<10; i++) {
			buttons.buttons[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String c = e.getActionCommand();
					if(c == "<-") message.setText(board.setBox(0));
					else {
						message.setText(board.setBox(Integer.valueOf(c)));
					}
					repaint();
				}
			});
		}
		buttons.solveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				message.setText("can. i. solve. it...");
				if(solved = board.solve()) {
					message.setText("Woohoo");
					board.setBoxSelected(-1, -1);
					buttons.showReset();
					repaint();
				}else {
					message.setText("error! your puzzle cannot be solved :D");
				}
			}
		});
		buttons.backButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				solved = false;
				board.setUnsolved();
				buttons.showNormal();
				repaint();
			}
		});
		for(int i=0; i<2; i++) {
			buttons.resetButton[i].addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					solved = false;
					buttons.showNormal();
					message.setText("Welcome!");
					board.reset();
					repaint();
				}
			});
		}
	}
}
