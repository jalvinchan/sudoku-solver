class Brain{
    constructor(){
        this.row = new Array(9);
        this.col = new Array(9);
        this.box = new Array(9);
    }
    
    solve(board, sol){
        this.row.fill(0), this.col.fill(0), this.box.fill(0);
        this.board = sol;
        for(let i=0; i<9; i++){
            for(let j=0; j<9; j++){
                sol[i][j] = board[i][j];
                if(!sol[i][j]) continue;
                let p = 1<<sol[i][j]-1
                this.row[i]+=p, this.col[j]+=p, this.box[(i/3>>0)*3+(j/3>>0)]+=p;
            }
        }
        return this.rb(0);
    }

    rb(n){
        if(n==81) return true;

        let i=n/9>>0, j=n%9, k=(i/3>>0)*3+(j/3>>0);
        if(this.board[i][j]) return this.rb(n+1);

        let pos = ((1<<9)-1) & ~(this.row[i] | this.col[j] | this.box[k]);
        let num = 1, p = 1;
        do{
            if((pos & p) != 0) {
				this.row[i]+=p; this.col[j]+=p; this.box[k]+=p;
				this.board[i][j] = num;
				if(this.rb(n+1)) return true;
				this.row[i]-=p; this.col[j]-=p; this.box[k]-=p;
			}
			p <<= 1;
        }while(++num<10);
		this.board[i][j]=0;
		return false;
    }
}