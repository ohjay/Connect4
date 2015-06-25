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
        return Math.round((x + Piece.WIDTH / 2 - L_OFFSET) / SQUARE_WIDTH);
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
            
            // Create a new interactive piece; this one's in its final position (i.e. it is immobile)
            if (!isBoardFull()) {
                currColor = (currColor.equals("red")) ? "yellow" : "red";
                interactivePiece = new Piece(currColor, MouseData.x);
            } else interactivePiece = null;
        } else if (interactivePiece.getY() > TOP_OFFSET + SQUARE_WIDTH) {
            interactivePiece.translate(0, 4);
        } else if (interactivePiece.getY() > TOP_OFFSET) {
            interactivePiece.translate(0, 3);
        } else {
            interactivePiece.translate(0, 1);
        }
    }
    
    /**
     * Draws the board and all of its pieces behind it.
     * @param g2 a Graphics2D object to be used to drawing/painting/whatever you want to call it
     */
    public void draw(Graphics2D g2) {
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
