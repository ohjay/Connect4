package Connect4;

/**
 * A big board, made for four players. This is the board used in Warfare.
 * @author Owen Jow
 */
public class WarfareBoard extends Board {
    
    public WarfareBoard() {
        // Set positional coordinates
        boardWidth = 14; 
        boardHeight = 14;
        leftOffset = 46;
        topOffset = 37;
        squareWidth = 36;
        maxNumPieces = boardWidth * boardHeight;
        
        board = new Piece[boardHeight][boardWidth];
        currColor = "red";
        interactivePiece = new Piece(currColor, true, MouseData.x, MouseData.y);
        warfareMode = true;
    }
    
    /**
     * Returns whether or not the space at (x, y) is unoccupied.
     */
    public boolean squareUnoccupied(int x, int y) {
        return (board[x][y] == null);
    }
    
    /**
     * Adds the piece PIECE to square (x, y).
     */
    public void addToSquare(Piece piece, int r, int c) {
        // Add the piece to the board
        board[r][c] = piece;
        piece.finalRow = r;
        piece.col = c;
        piece.setXY(leftOffset + c * squareWidth + 1, topOffset + r * squareWidth + 1);
        numPieces++;
        
        if (makesFour(piece) || isBoardFull()) {
            Connect4.returnToMainMenu();
        } else {
            // Continue the game
            switchPlayers();
            interactivePiece = new Piece(currColor, true, MouseData.x - Piece.SMALL_WIDTH / 2, 
                    MouseData.y - Piece.SMALL_WIDTH / 2);
        }
    }
    
    @Override
    public void switchPlayers() {
        if (currColor.equals("red")) {
            currColor = "yellow";
        } else if (currColor.equals("yellow")) {
            currColor = "green";
        } else if (currColor.equals("green")) {
            currColor = "magenta";
        } else {
            currColor = "red";
        }
    }
}
