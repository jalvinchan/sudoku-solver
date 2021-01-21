//import java.util.*;

// I solve Sudoku puzzles all day every day;
// feed me puzzle and I will enlighten u
public class Brain {
	
	int[][] board;
	int[] row,col,box;
	
	// this method solves directly on the board.
	public boolean solve(int[][] board){
		return solve(board, board);
	}
	
	// board contains the puzzle, returns solution in sol and leave board untouched
	public boolean solve(int[][] board, int[][] sol){
		row = new int[9];
		col = new int[9];
		box = new int[9];
		this.board = sol;
		for(int i=0; i<9; i++) {
			for(int j=0; j<9; j++) {
				sol[i][j] = board[i][j];
				if(sol[i][j] == 0) continue;
				int p = 1 << sol[i][j]-1;
				row[i]+=p; col[j]+=p; box[i/3*3 + j/3]+=p;
			}
		}
		return rb(0);
	}
	
	private boolean rb(int n) {
		if(n==81) return true;
		
		int i=n/9, j=n%9, k=i/3*3 + j/3;
		if(board[i][j] != 0) return rb(n+1);

		int pos = ((1<<9)-1) & ~(row[i] | col[j] | box[k]);
		int num = 1,p=1;
		while(num<10) {
			if((pos & p) != 0) {
				row[i]+=p; col[j]+=p; box[k]+=p;
				board[i][j] = num;
				if(rb(n+1)) return true;
				row[i]-=p; col[j]-=p; box[k]-=p;
			}
			num++; p <<= 1;
		}
		board[i][j]=0;
		return false;
	}
}