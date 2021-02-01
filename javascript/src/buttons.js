class Buttons{
    constructor(board,x,y,w,h){
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;
        this.board = board;

        this.buttonWidth = 80;
        this.buttonHeight = 40;

        this.buttons = {
            "solve": {
                x:this.x+this.width/2-this.buttonWidth, 
                y:this.y+this.height/2,
                w:this.buttonWidth,
                h:this.buttonHeight,
                str: "solve"
            },
            "reset": {
                x:this.x+this.width/2, 
                y:this.y+this.height/2,
                w:this.buttonWidth,
                h:this.buttonHeight,
                str: "reset"
            },
            "back": {
                x:this.x+this.width/2-this.buttonWidth, 
                y:this.y+this.height/2,
                w:this.buttonWidth,
                h:this.buttonHeight,
                str: "back"
            },
        }
    }

    drawSolve(){
        ctx.clearRect(this.x, this.y, this.width, this.height);
        this.drawButton([this.buttons.solve, this.buttons.reset]);
    }

    drawSolved(){
        ctx.clearRect(this.x, this.y, this.width, this.height);
        this.drawButton([this.buttons.back, this.buttons.reset]);
    }

    drawButton(buttons){
        buttons.forEach(button =>{
            ctx.fillStyle = "rgba(255,255,255,0.2)";
            ctx.fillRect(button.x, button.y, button.w, button.h);
    
            ctx.strokeStyle = "white";
            ctx.strokeRect(button.x, button.y, button.w, button.h);
    
            ctx.font = "15px Arial";
            ctx.fillStyle = "white";
            ctx.textAlign = "center";
            ctx.fillText(button.str, button.x+button.w/2, button.y+0.6*button.h);
        })
    }

    click(x,y){
        if(this.inside(this.buttons.reset, x, y)) this.clickReset();
        else if(this.inside(this.buttons.solve, x, y)){
            if(!this.board.solved) this.clickSolve(); 
            else this.clickBack();
        }
    }
    
    clickSolve(){
        if(!this.board.solved){
            if(this.board.solve()){
                this.drawSolved();
            }
        }else{
            this.board.solved=false;
            this.drawSolve();
        }
    }

    clickReset(){
        board.reset();
        this.drawSolve();
    }

    clickBack(){
        this.board.solved = false;
    }

    inside(button,x,y){
        return x > button.x && x < button.x + button.w &&
            y > button.y && y < button.y + button.h;
    }
}