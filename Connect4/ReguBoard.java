package Connect4;

/**
 * A regular connect four board; that is, one that is 7x6.
 * @author Owen Jow
 */
public class ReguBoard extends Board {
    boolean isPieceFalling, shouldHidePiece;
    
    /**
     * Default constructor for an empty 7x6 board.
     */
    public ReguBoard() {
        // Positional values
        boardWidth = 7; 
        boardHeight = 6;
        leftOffset = 87;
        topOffset = 80;
        squareWidth = 60; 
        pieceStartHeight = 10;
        maxNumPieces = 42;
        
        board = new Piece[boardHeight][boardWidth];
        currColor = "red";
        interactivePiece = new Piece(currColor, MouseData.x);
    }
    
    /**
     * Initializes the board with some specific configuration and current player.
     */
    public ReguBoard(Piece[][] board, int numPieces, String currColor) {
        // Assign positional values
        boardWidth = 7; 
        boardHeight = 6;
        leftOffset = 87;
        topOffset = 80;
        squareWidth = 60; 
        pieceStartHeight = 10;
        maxNumPieces = 42;
        
        this.board = board;
        this.numPieces = numPieces;
        this.currColor = currColor;
        interactivePiece = new Piece(currColor, MouseData.x);
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
     * Checks if piece PIECE makes a connected four with any other pieces on the board.
     * Presumably, PIECE will be the piece that was most recently played.
     * @param piece the piece to base checks around
     * @boolean true if there is a connected four on the board that involves the given piece
     */
    public boolean makesFour(Piece piece) {
        int count1 = 0;
        
        // Check for vertical fours
        int i = chooseGreater(piece.finalLevel - 3, 0); // Initialize the vertical starting row
        for (; i < chooseLesser(piece.finalLevel + 4, boardHeight); i++) {
            if (sameTeam(piece.color, board[i][piece.col])) {
                count1++; // increment the vertical count
            } else count1 = 0;
            
            // Return true if there's four in a row
            if (count1 >= 4) return true;
        }
        
        // Check for horizontal fours
        i = chooseGreater(piece.col - 3, 0);  // Initialize the horizontal starting column
        for (count1 = 0; i < chooseLesser(piece.col + 4, boardWidth); i++) {
            if (sameTeam(piece.color, board[piece.finalLevel][i])) {
                count1++; // increment the horizontal count
            } else count1 = 0;
            
            if (count1 >= 4) return true;
        }
        
        // Check for diagonal fours
        int count2 = 0; // initializing an extra count variable
        for (i = -4, count1 = 0; i < 4; i++) {
            // Heading from the top-left to the bottom-right
            if (onBoard(piece.finalLevel + i, piece.col + i)) {
                if (sameTeam(piece.color, board[piece.finalLevel + i][piece.col + i])) count1++;
                else count1 = 0;
            }
            
            // Heading from the bottom-left to the top-right
            if (onBoard(piece.finalLevel + i, piece.col - i)) {
                if (sameTeam(piece.color, board[piece.finalLevel + i][piece.col - i])) count2++;
                else count2 = 0;
            }
            
            if (count1 >= 4 || count2 >= 4) return true;
        }
        
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
     * Adds piece PIECE to column COLUMN.
     * Assumes that the column is not already full, and that there is no piece falling already.
     * @param piece the piece to be added
     * @param column the column to be added to
     */
    public void addToColumn(Piece piece, int column) {
        piece.finalLevel = lowestOpenRow(column);
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
    protected void switchPlayers() {
        currColor = (currColor.equals("red")) ? "black" : "red";
    }
    
    /**
     * Performs last-minute checks and ends the turn.
     * Should be called after the interactive piece is done falling.
     */
    protected void endTurn() {
        board[interactivePiece.finalLevel][interactivePiece.col] = interactivePiece; // add to the board
        isPieceFalling = false;
        
        // Check if the piece makes four-in-a-row
        if (makesFour(interactivePiece)) {
            // If so, the game is over!
            Connect4.returnToMainMenu();
            return;
        }
        
        // Create a new interactive piece; this one's in its final position (i.e. it is immobile)
        if (!isBoardFull()) {
            switchPlayers();
            interactivePiece = new Piece(currColor, MouseData.x);
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
}
