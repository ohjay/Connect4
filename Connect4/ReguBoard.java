package Connect4;

/**
 * A regular connect four board; that is, one that is 7x6.
 * @author Owen Jow
 */
public class ReguBoard extends Board {
    boolean isPieceFalling, shouldHidePiece; // "piece" refers to the interactive piece
    
    /**
     * Default constructor for an empty 7x6 board.
     */
    public ReguBoard() {
        setPositionalValues();
        board = new Piece[boardHeight][boardWidth];
        currColor = "red";
        interactivePiece = new Piece(currColor, MouseData.x);
    }
    
    /**
     * Initializes the board with some specific configuration and current player.
     */
    public ReguBoard(Piece[][] board, int numPieces, String currColor) {
        setPositionalValues();
        this.board = board;
        this.numPieces = numPieces;
        this.currColor = currColor;
        interactivePiece = new Piece(currColor, MouseData.x - Piece.REG_WIDTH / 2);
    }
    
    /**
     * Sets positional values for a regular-sized board.
     */
    private void setPositionalValues() {
        boardWidth = 7; 
        boardHeight = 6;
        leftOffset = 87;
        topOffset = 80;
        squareWidth = 60; 
        pieceStartHeight = 10;
        maxNumPieces = 42;
    }
    
    /**
     * Returns the color of the player whose turn it isn't.
     * For example, if it were red's turn, this method would return "black".
     */
    public String otherPlayer() {
        return currColor.equals("red") ? "black" : "red";
    }
    
    /**
     * Describes whether or not a column is falling right now,
     * supposedly as a result of a piece being removed.
     */
    public boolean isColumnFalling() {
        return false;
    }
    
    /**
     * Returns the lowermost open row in the column COLUMN.
     * @param column an integer from 0-6
     */
    public int lowestOpenRow(int column) {
        int i = -1;
        while (i < boardHeight - 1 && board[i + 1][column] == null) i++;
        return i;
    }
    
    /**
     * Returns the column to which the given x-coordinate is nearest.
     */
    public static int getNearestCol(int x) {
        return chooseLesser(6, Math.round((x + Piece.REG_WIDTH / 2 - leftOffset) / squareWidth));
    }
    
    /**
     * Adds piece PIECE to column COLUMN.
     * Assumes that the column is not already full, and that there is no piece falling already.
     * @param piece the piece to be added
     * @param column the column to be added to
     */
    public void addToColumn(Piece piece, int column) {
        piece.finalRow = lowestOpenRow(column);
        piece.col = column;
        piece.setX(leftOffset + column * squareWidth + 2);
        numPieces++; // increment the total number of pieces
        isPieceFalling = true;
    }
    
    /**
     * Removes the piece at row ROW and column COL.
     * If there are any pieces on top of the piece to-be-removed, they will plummet.
     */
    public void removePiece(int row, int col) {
        /* Override if necessary. */
    }
    
    /**
     * Reassigns CURR_COLOR so that it's now the other player's turn.
     */
    @Override
    protected void switchPlayers() {
        currColor = (currColor.equals("red")) ? "black" : "red";
    }
    
    /**
     * Performs last-minute checks and ends the turn.
     * Should be called after the interactive piece is done falling.
     */
    protected void endTurn() {
        board[interactivePiece.finalRow][interactivePiece.col] = interactivePiece; // add to the board
        isPieceFalling = false;
        
        // Check if the piece makes four-in-a-row
        if (makesFour(interactivePiece)) {
            // If so, the game is over!
            System.out.println(this); // debugging output (state of the board @ end of game)
            Connect4.returnToMainMenu();
            return;
        }
        
        // Create a new interactive piece; this one's in its final position (i.e. it is immobile)
        if (!isBoardFull()) {
            switchPlayers();
            interactivePiece = new Piece(currColor, MouseData.x - Piece.REG_WIDTH / 2);
        } else {
            // Game's over guys, let's go home
            interactivePiece = null;
            Connect4.returnToMainMenu();
            return;
        }
    }
    
    /**
     * Causes the moving piece, if there is one, to fall.
     * Should ONLY do something if there is actually a piece falling, 
     * so it is assumed that the caller has already checked for this.
     * If the caller doesn't check, then this method may malfunction and throw an exception.
     */
    public void animateFallingPiece() {
        if (interactivePiece.inFinalPosition()) {
            endTurn();
        } else if (interactivePiece.getY() > topOffset + squareWidth) {
            interactivePiece.translate(0, 4);
        } else if (interactivePiece.getY() > topOffset) {
            interactivePiece.translate(0, 3);
        } else {
            interactivePiece.translate(0, 2);
        }
    }
    
    /**
     * String representation of this board.
     */
    @Override
    public String toString() {
        String stringRepr = "";
        boolean firstCol; // whether the current piece is in the first column
        
        for (Piece[] pArr : board) {
            stringRepr += "[";
            firstCol = true;
            for (Piece p : pArr) {
                if (firstCol) {
                    stringRepr += p;
                    firstCol = false;
                } else {
                    stringRepr += ", " + p;
                }
            }
            stringRepr += "]\n";
        }
        
        return stringRepr;
    }
}
