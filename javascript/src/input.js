class InputHandler{
    constructor(canvas, ctx, board, button){
        canvas.addEventListener("mousedown", e=>{
            board.setSelected(e.offsetX, e.offsetY);
            buttons.click(e.offsetX, e.offsetY);
            board.draw(ctx);
        });

        document.addEventListener("keydown", e=>{
            let k = e.keyCode;
            if(k >= 37 && k <= 40) board.changeSelected(k-37);
            else if(k >= 48 && k <= 57) board.setBox(k-48);
            else if(k >= 96 && k <= 105) board.setBox(k-96);
            else if(k == 13) buttons.clickSolve();
            else if(k == 8 && board.solved) buttons.clickBack();
            else if(k == 8 && !board.solved) board.setBox(0);
            board.draw(ctx);
        });
    }
}