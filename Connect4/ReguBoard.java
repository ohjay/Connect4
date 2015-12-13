package Connect4;

import java.awt.Graphics2D;

/**
 * A regular connect four board; that is, one that is 7x6.
 * @author Owen Jow
 */
public class ReguBoard {
    static final int BOARD_WIDTH = 7, BOARD_HEIGHT = 6, L_OFFSET = 87, TOP_OFFSET = 80,
            SQUARE_WIDTH = 60, PIECE_START_HEIGHT = 10;
    protected Piece[][] board; // row x col (Y-AXIS x X-AXIS)
    protected int numPieces;
    protected String currColor;
    Piece interactivePiece; // only 1 piece controlled at once
    boolean isPieceFalling, shouldHidePiece;
    
    /**
     * Default constructor for an empty 7x6 board.
     */
    public ReguBoard() {
        board = new Piece[BOARD_HEIGHT][BOARD_WIDTH];
        currColor = "red";
        interactivePiece = new Piece(currColor, MouseData.x);
    }
    
    /**
     * Initializes the board with some specific configuration and current player.
     */
    public ReguBoard(Piece[][] board, int numPieces, String currColor) {
        this.board = board;
        this.numPieces = numPieces;
        this.currColor = currColor;
        interactivePiece = new Piece(currColor, MouseData.x);
    }
    
    /**
     * Returns the board in its present condition.
     * @return the board represented as a multidimensional array
     */
    public Piece[][] getBoard() {
        return board;
    }
    
    /**
     * Returns a clone of the board in its current condition.
     * Modifications to the cloned board will not affect the original.
     */
    public Piece[][] cloneBoard() {
        Piece[][] clone = new Piece[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                clone[i][j] = board[i][j];
            }
        }
        
        return clone;
    }
    
    /**
     * Returns the color of the player that is next to play.
     */
    public String getCurrPlayer() {
        return currColor;
    }
    
    /**
     * Returns the number of pieces on the present board.
     */
    public int getNumPieces() {
        return numPieces;
    }
    
    /**
     * Returns the color of the player whose turn it isn't.
     * For example, if it were red's turn, this method would return "black".
     */
    public String otherPlayer() {
        return currColor.equals("red") ? "black" : "red";
    }
    
    /**
     * Returns true if the board is filled; that is, if it contains 42 pieces.
     * @return whether or not the board is full
     */
    public boolean isBoardFull() {
        return numPieces >= 42;
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
        for (; i < chooseLesser(piece.finalLevel + 4, BOARD_HEIGHT); i++) {
            if (sameTeam(piece.color, board[i][piece.col])) {
                count1++; // increment the vertical count
            } else count1 = 0;
            
            // Return true if there's four in a row
            if (count1 >= 4) return true;
        }
        
        // Check for horizontal fours
        i = chooseGreater(piece.col - 3, 0);  // Initialize the horizontal starting column
        for (count1 = 0; i < chooseLesser(piece.col + 4, BOARD_WIDTH); i++) {
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
     * Takes a set of coordinates (r, c) and determines if it's contained by the board.
     * @param r the row (i.e. the level on the board; think y-axis)
     * @param c the column (think x-axis)
     */
    protected static boolean onBoard(int r, int c) {
        return (r >= 0 && r < BOARD_HEIGHT) && (c >= 0 && c < BOARD_WIDTH);
    }
    
    /**
     * Returns true if piece PIECE is on the team specified by COLOR.
     */
    protected static boolean sameTeam(String color, Piece piece) {
        return piece != null && color.equals(piece.color);
    }
    
    /**
     * A helper function for choosing the greater of two integers.
     * Given two integers, it will return whichever one is greater in value.
     * If the integers are equal in value, it will return the first one.
     */
    protected static int chooseGreater(int a, int b) {
        return (b > a) ? b : a;
    }
    
    /**
     * A helper function for choosing the lesser / "lower" of two integers.
     * Given two integers, it will return whichever one is smaller in value.
     * If the integers are equal in value, it will return the first one.
     */
    protected static int chooseLesser(int a, int b) {
        return (a > b) ? b : a;
    }
    
    /**
     * Returns true if the column identified by COLUMN is full.
     * @param column the column in question
     * @return whether or not the column is full (i.e. contains 7 pieces)
     */
    public boolean isColumnFull(int column) {
        return (board[0][column] != null);
    }
    
    /**
     * Returns the column to which the given x-coordinate is nearest.
     */
    public static int getNearestCol(int x) {
        return chooseLesser(6, Math.round((x + Piece.WIDTH / 2 - L_OFFSET) / SQUARE_WIDTH));
    }
    
    /**
     * Returns the lowermost open row in the column COLUMN.
     * @param column an integer from 0-6
     */
    public int lowestOpenRow(int column) {
        int i = -1;
        while (i < BOARD_HEIGHT - 1 && board[i + 1][column] == null) i++;
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
        piece.setX(L_OFFSET + column * SQUARE_WIDTH + 2);
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
        } else if (interactivePiece.getY() > TOP_OFFSET + SQUARE_WIDTH) {
            interactivePiece.translate(0, 4);
        } else if (interactivePiece.getY() > TOP_OFFSET) {
            interactivePiece.translate(0, 3);
        } else {
            interactivePiece.translate(0, 2);
        }
    }
    
    /**
     * Draws the interactive piece. Pretty simple.
     */
    protected void drawInteractivePiece(Graphics2D g2) {
        g2.drawImage(interactivePiece.getImage(), interactivePiece.getX(), 
                interactivePiece.getY(), null); 
    }
    
    /**
     * Draws the board and all of its pieces behind it.
     * @param g2 a Graphics2D object to be used to drawing/painting/whatever you want to call it
     * @param bgId the background identifier (this should be an integer from 1-6)
     */
    public void draw(Graphics2D g2, int bgId) {
        // First, draw the background
        switch (bgId) {
            case 6:
                g2.drawImage(Images.GOLD_BACKGROUND_6, 0, 0, null);
                break;
            case 5:
                g2.drawImage(Images.GOLD_BACKGROUND_5, 0, 0, null);
                break;
            case 4:
                g2.drawImage(Images.GOLD_BACKGROUND_4, 0, 0, null);
                break;
            case 3:
                g2.drawImage(Images.GOLD_BACKGROUND_3, 0, 0, null);
                break;
            case 2:
                g2.drawImage(Images.GOLD_BACKGROUND_2, 0, 0, null);
                break;
            default:
                g2.drawImage(Images.GOLD_BACKGROUND_1, 0, 0, null);
                break;
        }
        
        // Draw all of the pieces
        for (Piece[] pieceArr : board) {
            for (Piece piece : pieceArr) {
                if (piece != null) {
                    g2.drawImage(piece.getImage(), piece.getX(), piece.getY(), null);
                }
            }
        }
        
        if (interactivePiece != null) { // don't forget about the interactive piece!
            drawInteractivePiece(g2);
        }
        
        // Then, draw the board over them so it appears as if the pieces are inside the board
        for (int i = 0; i < BOARD_WIDTH; i++) { // we have to draw 7 rows' worth of squares...
            for (int j = 0; j < BOARD_HEIGHT; j++) { // ...and 6 columns' worth of squares
                g2.drawImage(Images.SQUARE, L_OFFSET + i * SQUARE_WIDTH, 
                        TOP_OFFSET + j * SQUARE_WIDTH, null);
            }
        }
    }
}
