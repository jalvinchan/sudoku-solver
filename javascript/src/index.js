let canvas = document.getElementById("screen");
let ctx = canvas.getContext("2d");

board = new Board(canvas.width);
buttons = new Buttons(board, 0, canvas.width, canvas.width, canvas.height-canvas.width);
new InputHandler(canvas, ctx, board, buttons);

board.draw(ctx);
buttons.drawSolve(ctx);