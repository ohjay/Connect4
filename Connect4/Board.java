package Connect4;

import java.awt.Graphics2D;

/**
 * The most general abstraction for a board.
 * Does basic board things.
 * @author Owen Jow
 */
abstract class Board {
    // Publically accessible values
    static int boardWidth, boardHeight, leftOffset, topOffset, squareWidth, 
            pieceStartHeight, maxNumPieces;
    
    protected Piece[][] board; // row x col (Y-AXIS x X-AXIS)
    protected int numPieces;
    protected String currColor;
    Piece interactivePiece; // only 1 piece controlled at once
    
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
        Piece[][] clone = new Piece[boardHeight][boardWidth];
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
     * Returns true if the board is filled; that is, if it contains 42 pieces.
     * @return whether or not the board is full
     */
    public boolean isBoardFull() {
        return numPieces >= maxNumPieces;
    }
    
    /**
     * Returns the number of pieces on the present board.
     */
    public int getNumPieces() {
        return numPieces;
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
     * Takes a set of coordinates (r, c) and determines if it's contained by the board.
     * @param r the row (i.e. the level on the board; think y-axis)
     * @param c the column (think x-axis)
     */
    protected static boolean onBoard(int r, int c) {
        return (r >= 0 && r < boardHeight) && (c >= 0 && c < boardWidth);
    }
    
    /**
     * Returns the column to which the given x-coordinate is nearest.
     */
    public static int getNearestCol(int x) {
        return chooseLesser(6, Math.round((x + Piece.WIDTH / 2 - leftOffset) / squareWidth));
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
        for (int i = 0; i < boardWidth; i++) { // we have to draw 7 rows' worth of squares...
            for (int j = 0; j < boardHeight; j++) { // ...and 6 columns' worth of squares
                g2.drawImage(Images.SQUARE, leftOffset + i * squareWidth, 
                        topOffset + j * squareWidth, null);
            }
        }
    }
    
    /**
     * Should determine whether PIECE creates a connected four with its neighbors.
     */
    abstract boolean makesFour(Piece piece);
}
