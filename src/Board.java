import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.lang.Math;

public class Board extends JPanel{
	
	private final static int BOX_SIZE = SudokuSolver.BOX_SIZE;
	private final static Font baseFont = new Font("Sherif", Font.BOLD, BOX_SIZE/2);
	private final static Font solFont = new Font("Sherif", Font.PLAIN, BOX_SIZE/2);
	private final static BasicStroke thinLine = new BasicStroke(2);
	private final static BasicStroke thickLine = new BasicStroke(4);
	private int boxSelectedX = -1, boxSelectedY = -1;			// range 0-8
	private int repeatRow, repeatCol, repeatBoxX, repeatBoxY;
	private int[][] grid, sol;
	private boolean solved = false, error = false;
	private Brain brain;
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setBackground(Color.WHITE);
		
		if(error) {
			if(repeatRow >= 0)  {
				g.setColor(Color.GRAY);
				g.fillRect(BOX_SIZE, (boxSelectedY + 1) * BOX_SIZE, 9*BOX_SIZE, BOX_SIZE);
				g.setColor(Color.CYAN);
				g.fillRect((repeatRow + 1) * BOX_SIZE, (boxSelectedY + 1) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
			if(repeatCol >= 0) {
				g.setColor(Color.GRAY);
				g.fillRect((boxSelectedX + 1) * BOX_SIZE, BOX_SIZE, BOX_SIZE, 9*BOX_SIZE);
				g.setColor(Color.CYAN);
				g.fillRect((boxSelectedX + 1) * BOX_SIZE, (repeatCol + 1) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
			if(repeatBoxX >= 0 && repeatBoxY >= 0) {
				g.setColor(Color.GRAY);
				g.fillRect((repeatBoxX/3*3+1) * BOX_SIZE, (repeatBoxY/3*3+1) * BOX_SIZE, 3*BOX_SIZE, 3*BOX_SIZE);
				g.setColor(Color.CYAN);
				g.fillRect((repeatBoxX+1) * BOX_SIZE, (repeatBoxY+1) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
			}
		}
		
		// draw selected gray box
		if(boxSelectedX >= 0 && boxSelectedX < 9 && boxSelectedY >= 0 && boxSelectedY < 9) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect((boxSelectedX + 1) * BOX_SIZE, (boxSelectedY + 1) * BOX_SIZE, BOX_SIZE, BOX_SIZE);
		}
		
		// draw board
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(Color.BLACK);
		for(int i=1; i<=10; i++) {
			if((i-1)%3 == 0) g2.setStroke(thickLine);
			else g2.setStroke(thinLine);
			g2.drawLine(i*BOX_SIZE, BOX_SIZE, i*BOX_SIZE, 10*BOX_SIZE);
			g2.drawLine(BOX_SIZE, i*BOX_SIZE, 10*BOX_SIZE, i*BOX_SIZE);
		}
		
		//draw number
		g.setColor(Color.RED);
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				if(grid[i][j] > 0 && grid[i][j] <= 9) {
					g.setColor(Color.RED);
					g.setFont(baseFont);
					g.drawString(Integer.toString(grid[i][j]), (int)((i+1.3)*BOX_SIZE), (int)((j+1.8)*BOX_SIZE));
				}
				else if(solved) {
					g.setColor(Color.BLACK);
					g.setFont(solFont);
					g.drawString(Integer.toString(sol[i][j]), (int)((i+1.3)*BOX_SIZE), (int)((j+1.8)*BOX_SIZE));
				}
			}
		}
	}
	
	public Board(){
		grid = new int[9][9];
		brain = new Brain();
		resetError();
	}
	
	public String setBox(int num) {
		if(boxSelectedX<0 || boxSelectedX>8 || boxSelectedY<0 || boxSelectedY>8) return "Select a box";
		else {
			resetError();
			if(num == 0) {
				String s = String.format("%d removed from (%d, %d)...", grid[boxSelectedX][boxSelectedY], boxSelectedX, boxSelectedY);
				grid[boxSelectedX][boxSelectedY] = num;
				return s;
			}
			else {
				for(int i=0; i<9; i++) {
					if(i!=boxSelectedX && grid[i][boxSelectedY] == num) repeatRow = i;
					if(i!=boxSelectedY && grid[boxSelectedX][i] == num) repeatCol = i;
					int x =boxSelectedX/3*3 + (i%3), y = boxSelectedY/3*3 + i/3;
					if((x!= boxSelectedX || y!=boxSelectedY) && grid[x][y] == num) {
						repeatBoxX = x; repeatBoxY = y;
					}
				}
				String s = "";
				if(repeatRow!=-1) s+=String.format("error! row has %d alr >:(\n", num);
				if(repeatCol!=-1) s+=String.format("error! col has %d alr >:(\n", num);
				if(repeatBoxX!=-1) s+=String.format("error! box has %d alr >:(\n", num);
				if(!s.equals("")) {
					error = true;
					return s.substring(0, s.length()-1);
				}
				grid[boxSelectedX][boxSelectedY] = num;
				return String.format("%d inserted at (%d, %d)...", num, boxSelectedY+1, boxSelectedX+1);
			}
		}
	}
	
	public boolean solve() {
		resetError();
		sol = new int[9][9];
		return solved = brain.solve(grid, sol);
	}
	
	public void reset() {
		grid = new int[9][9];
		solved = false;
		boxSelectedX = -1; boxSelectedY = -1;
		resetError();
	}
	
	public void resetError() {
		repeatRow = repeatCol = repeatBoxX = repeatBoxY = -1;
		error = false;
	}
	
	public void setUnsolved() {
		solved = false;
	}
	
	public void setBoxSelected(int x, int y) {	//range 0-8
		boxSelectedX = x;
		boxSelectedY = y;
	}
	
	public void adjustBoxSelected(int dx, int dy) {
		boxSelectedX = Math.max(0, Math.min(8,boxSelectedX+dx));
		boxSelectedY = Math.max(0, Math.min(8,boxSelectedY+dy));
	}
}
