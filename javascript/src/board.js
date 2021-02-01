class Board{
    constructor(w){
        this.size = w;
        this.boxSize = w/9;
        this.boxSelectedX = -1;
        this.boxSelectedY = -1;
        this.solved = false;
        this.brain = new Brain();
        this.grid = Array(9);
        this.sol = Array(9);
        for(let i=0; i<9; i++) {
            this.grid[i] = Array(9).fill(0);
            this.sol[i] = Array(9).fill(0);
        }
        this.resetError();
    }

    draw(ctx){
        ctx.clearRect(0,0,this.size,this.size);

        // error
        if(this.repeatRow > -1){
            ctx.fillStyle = "rgb(65,65,65)"
            ctx.fillRect(0, this.boxSelectedY * this.boxSize, 9*this.boxSize, this.boxSize);
            ctx.fillStyle = "red";
            ctx.fillRect(this.repeatRow * this.boxSize, this.boxSelectedY * this.boxSize, this.boxSize, this.boxSize);
        }
        if(this.repeatCol >= 0) {
            ctx.fillStyle = "rgb(65,65,65)"
            ctx.fillRect(this.boxSelectedX * this.boxSize, 0, this.boxSize, 9*this.boxSize);
            ctx.fillStyle = "red";
            ctx.fillRect(this.boxSelectedX * this.boxSize, this.repeatCol * this.boxSize, this.boxSize, this.boxSize);
        }
        if(this.repeatBoxX >= 0 && this.repeatBoxY >= 0) {
            ctx.fillStyle = "rgb(65,65,65)"
            ctx.fillRect((this.repeatBoxX/3>>0)*3 * this.boxSize, (this.repeatBoxY/3>>0)*3 * this.boxSize, 3*this.boxSize, 3*this.boxSize);
            ctx.fillStyle = "red";
            ctx.fillRect(this.repeatBoxX * this.boxSize, this.repeatBoxY * this.boxSize, this.boxSize, this.boxSize);
        }

        // selected box
        ctx.fillStyle = "rgb(130,130,130)"
        if(!this.solved && !this.outside()) {
            ctx.fillRect(this.boxSelectedX * this.boxSize, this.boxSelectedY * this.boxSize, this.boxSize, this.boxSize);
        }

        ctx.font = "20px Arial"
        ctx.textAlign = "center"
        for(let i=0; i<9; i++){
            for(let j=0; j<9; j++){
                if(this.grid[j][i]){
                    ctx.fillStyle = "white"
                    ctx.fillText(this.grid[j][i], (i+0.5)*this.boxSize, (j+0.6)*this.boxSize);
                }
                else if(this.solved){
                    ctx.fillStyle = "orange"
                    ctx.fillText(this.sol[j][i], (i+0.5)*this.boxSize, (j+0.6)*this.boxSize);
                }
            }
        }

        // board
        ctx.strokeStyle = "white";
        for(let i=0; i<10; i++){
            ctx.beginPath();
            if(i%3) ctx.lineWidth = 0.5
            else ctx.lineWidth = 2
            ctx.moveTo(0, i*this.boxSize);
            ctx.lineTo(this.size, i*this.boxSize);
            ctx.moveTo(i*this.boxSize, 0);
            ctx.lineTo(i*this.boxSize, this.size);
            ctx.stroke();
        }
    }

    setSelected(x, y){
        this.resetError();
        this.boxSelectedX = x/this.boxSize>>0;
        this.boxSelectedY = y/this.boxSize>>0;
    }

    changeSelected(x){
        if(this.outside()) return;
        this.resetError();
        if(x==0) this.boxSelectedX = Math.max(this.boxSelectedX-1, 0);
        if(x==1) this.boxSelectedY = Math.max(this.boxSelectedY-1, 0);
        if(x==2) this.boxSelectedX = Math.min(this.boxSelectedX+1, 8);
        if(x==3) this.boxSelectedY = Math.min(this.boxSelectedY+1, 8);
    }

    setBox(n){
        if(this.outside() || this.solved) return;
        if(!this.checkError(n))
            this.grid[this.boxSelectedY][this.boxSelectedX] = n;
    }

    checkError(n){
        this.resetError();
        if(!n) return false;
        let message = ""
        for(let i=0; i<9; i++){
            if(i!=this.boxSelectedX && this.grid[this.boxSelectedY][i] == n){
                this.repeatRow = i;
                message += "row has " + n + " alr >:(\n";
            }
            if(i!=this.boxSelectedY && this.grid[i][this.boxSelectedX] == n){
                this.repeatCol = i;
                message += "col has " + n + " alr >:(\n";
            }
            let x = (this.boxSelectedX/3>>0)*3 + (i%3), y = (this.boxSelectedY/3>>0)*3 + (i/3<<0);
            if((x!=this.boxSelectedX || y!=this.boxSelectedY) && this.grid[y][x] == n) {
                this.repeatBoxX = x; this.repeatBoxY = y;
                message += "box has " + n + " alr >:(\n";
            }
        }
        if(this.repeatRow<0 && this.repeatCol<0 && this.repeatBoxX<0) return false;
        alert(message);
        return true;
    }

    resetError(){
        this.repeatRow = -1;
        this.repeatCol = -1;
        this.repeatBoxX = -1;
        this.repeatBoxY = -1;
    }

    outside(){
        return this.boxSelectedX<0 || this.boxSelectedY<0 ||
            this.boxSelectedX>8 || this.boxSelectedY>8;
    }

    solve(){
        if(this.brain.solve(this.grid,this.sol)){
            this.solved = true;
            return true;
        }
        return false;
    }

    reset(){
        this.solved = false;
        for(let i=0; i<9; i++) {
            this.grid[i] = Array(9).fill(0);
            this.sol[i] = Array(9).fill(0);
        }
    }
}