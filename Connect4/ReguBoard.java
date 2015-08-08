package Connect4;

import java.awt.Graphics2D;

/**
 * A regular connect four board; that is, one that is 7x7.
 * @author Owen Jow
 */
public class ReguBoard {
    static final int BOARD_LEN = 7, L_OFFSET = 87, TOP_OFFSET = 80, 
            SQUARE_WIDTH = 60, PIECE_START_HEIGHT = 10;
    private Piece[][] board = new Piece[BOARD_LEN][BOARD_LEN];
    private int numPieces;
    private String currColor = "red";
    Piece interactivePiece = new Piece(currColor, MouseData.x); // only 1 piece controlled at once
    boolean isPieceFalling;
    
    /**
     * Returns the board in its present condition.
     * @return the board represented as a multidimensional array
     */
    public Piece[][] getBoard() {
        return board;
    }
    
    /**
     * Returns true if the board is filled; that is, if it contains 49 pieces.
     * @return whether or not the board is full
     */
    public boolean isBoardFull() {
        return numPieces >= 49;
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
        for (; i < chooseLesser(piece.finalLevel + 4, BOARD_LEN); i++) {
            if (sameTeam(piece.color, board[i][piece.col])) {
                count1++; // increment the vertical count
            } else count1 = 0;
            
            // Return true if there's four in a row
            if (count1 >= 4) return true;
        }
        
        // Check for horizontal fours
        i = chooseGreater(piece.col - 3, 0);  // Initialize the horizontal starting column
        for (count1 = 0; i < chooseLesser(piece.col + 4, BOARD_LEN); i++) {
            if (sameTeam(piece.color, board[piece.finalLevel][i])) {
                count1++; // increment the horizontal count
            } else count1 = 0;
            
            if (count1 >= 4) return true;
        }
        
        // Check for diagonal fours
        int count2 = 0; // initializing an extra count variable
        for (i = -4, count1 = 0; i < 4; i++) {
            // Heading from the top-left to the bottom-right
            if (onBoard(piece.finalLevel + i) && onBoard(piece.col + i)) {
                if (sameTeam(piece.color, board[piece.finalLevel + i][piece.col + i])) count1++;
                else count1 = 0;
            }
            
            // Heading from the bottom-left to the top-right
            if (onBoard(piece.finalLevel + i) && onBoard(piece.col - i)) {
                if (sameTeam(piece.color, board[piece.finalLevel + i][piece.col - i])) count2++;
                else count2 = 0;
            }
            
            if (count1 >= 4 || count2 >= 4) return true;
        }
        
        return false;
    }
    
    /**
     * Takes a generic coordinate (could be x or y) and determines if it's contained by the board.
     */
    private static boolean onBoard(int coord) {
        return coord >= 0 && coord < BOARD_LEN;
    }
    
    /**
     * Returns true if piece PIECE is on the team specified by COLOR.
     */
    private static boolean sameTeam(String color, Piece piece) {
        return piece != null && color.equals(piece.color);
    }
    
    /**
     * A helper function for choosing the greater of two integers.
     * Given two integers, it will return whichever one is greater in value.
     * If the integers are equal in value, it will return the first one.
     */
    private static int chooseGreater(int a, int b) {
        return (b > a) ? b : a;
    }
    
    /**
     * A helper function for choosing the lesser / "lower" of two integers.
     * Given two integers, it will return whichever one is smaller in value.
     * If the integers are equal in value, it will return the first one.
     */
    private static int chooseLesser(int a, int b) {
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
     * Adds piece PIECE to column COLUMN.
     * Assumes that the column is not already full, and that there is no piece falling already.
     * @param piece the piece to be added
     * @param column the column to be added to
     */
    public void addToColumn(Piece piece, int column) {
        int i = -1;
        while (i < BOARD_LEN - 1 && board[i + 1][column] == null) i++;
        piece.finalLevel = i; // set the piece's final level as the lowermost open row
        piece.col = column;
        piece.setX(L_OFFSET + column * SQUARE_WIDTH + 2);
        numPieces++; // increment the total number of pieces
        isPieceFalling = true;
    }
    
    /**
     * Causes the moving piece, if there is one, to fall.
     * Should ONLY do something if there is actually a piece falling, 
     * so it is assumed that the caller has already checked for this.
     * If the caller doesn't check, then this method may malfunction and throw an exception.
     */
    public void animateFallingPiece() {
        if (interactivePiece.inFinalPosition()) {
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
                currColor = (currColor.equals("red")) ? "black" : "red";
                interactivePiece = new Piece(currColor, MouseData.x);
            } else {
                // Game's over guys, let's go home
                interactivePiece = null;
                Connect4.returnToMainMenu();
                return;
            }
        } else if (interactivePiece.getY() > TOP_OFFSET + SQUARE_WIDTH) {
            interactivePiece.translate(0, 4);
        } else if (interactivePiece.getY() > TOP_OFFSET) {
            interactivePiece.translate(0, 3);
        } else {
            interactivePiece.translate(0, 2);
        }
    }
    
    /**
     * Draws the board and all of its pieces behind it.
     * @param g2 a Graphics2D object to be used to drawing/painting/whatever you want to call it
     */
    public void draw(Graphics2D g2) {
        // First, draw the background
        g2.drawImage(Images.goldBackground6, 0, 0, null);
        
        // Draw all of the pieces
        for (Piece[] pieceArr : board) {
            for (Piece piece : pieceArr) {
                if (piece != null) {
                    g2.drawImage(piece.getImage(), piece.getX(), piece.getY(), null);
                }
            }
        }
        
        if (interactivePiece != null) {
            g2.drawImage(interactivePiece.getImage(), interactivePiece.getX(), 
                    interactivePiece.getY(), null); // don't forget about the interactive piece!
        }
        
        // Then, draw the board over them so it appears as if the pieces are inside the board
        for (int i = 0; i < BOARD_LEN; i++) { // we have to draw 7 rows' worth of squares...
            for (int j = 0; j < BOARD_LEN; j++) { // ...and 7 columns' worth of squares
                g2.drawImage(Images.square, L_OFFSET + i * SQUARE_WIDTH, 
                        TOP_OFFSET + j * SQUARE_WIDTH, null);
            }
        }
    }
}
