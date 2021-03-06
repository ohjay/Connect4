package Connect4;

import java.awt.Graphics2D;
import java.awt.Rectangle;

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
    protected boolean warfareMode;
    Piece interactivePiece; // only 1 piece controlled at once
    boolean gameOver, toMMHighlighted;
    
    // Overlay image positioning coordinates
    private static final int GAME_OVER_Y = 230, TO_MM_X = 140, TO_MM_Y = 500, WF_MM_Y = 530;
    static final Rectangle TO_MM_RECT = new Rectangle(TO_MM_X, TO_MM_Y, 320, 36);
    static final Rectangle WF_MM_RECT = new Rectangle(TO_MM_X, WF_MM_Y, 320, 36); // WF = Warfare
    
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
        for (int i = 0; i < boardHeight; i++) {
            for (int j = 0; j < boardWidth; j++) {
                clone[i][j] = board[i][j];
            }
        }
        
        return clone;
    }
    
    /**
     * Adds piece P to the internal board at row R and column C.
     * Meant to be used for simulation only.
     */
    public void addPiece(Piece p, int r, int c) {
        board[r][c] = p;
        numPieces++;
    }
    
    /**
     * Removes the piece at (R, C) from the internal board.
     * Meant to be used alongside updateBoardWithPiece as a game state simulator.
     */
    public void removePiece(int r, int c) {
        board[r][c] = null;
        numPieces--;
    }
    
    /**
     * Returns the color of the player that is next to play.
     */
    public String getCurrPlayer() {
        return currColor;
    }
    
    /**
     * Switches to the next player in the lineup.
     */
    abstract void switchPlayers();
    
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
     * Checks if piece PIECE makes a connected four with any other pieces on the board.
     * Presumably, PIECE will be the piece that was most recently played.
     * @param piece the piece to base checks around
     * @boolean true if there is a connected four on the board that involves the given piece
     */
    public boolean makesFour(Piece piece) {
        int count1 = 0;
        
        // Check for vertical fours
        int i = chooseGreater(piece.finalRow - 3, 0); // Initialize the vertical starting row
        for (; i < chooseLesser(piece.finalRow + 4, boardHeight); i++) {
            if (sameTeam(piece.color, board[i][piece.col])) {
                count1++; // increment the vertical count
            } else count1 = 0;
            
            // Return true if there's four in a row
            if (count1 >= 4) return true;
        }
        
        // Check for horizontal fours
        i = chooseGreater(piece.col - 3, 0);  // Initialize the horizontal starting column
        for (count1 = 0; i < chooseLesser(piece.col + 4, boardWidth); i++) {
            if (sameTeam(piece.color, board[piece.finalRow][i])) {
                count1++; // increment the horizontal count
            } else count1 = 0;
            
            if (count1 >= 4) return true;
        }
        
        // Check for diagonal fours
        int count2 = 0; // initializing an extra count variable
        for (i = -4, count1 = 0; i < 4; i++) {
            if (i == 0) { // this is the piece itself, so we know it's the same color
                count1++;
                count2++;
            } else {
                // Heading from the top-left to the bottom-right
                if (onBoard(piece.finalRow + i, piece.col + i)) {
                    if (sameTeam(piece.color, board[piece.finalRow + i][piece.col + i])) count1++;
                    else count1 = 0;
                }
            
                // Heading from the bottom-left to the top-right
                if (onBoard(piece.finalRow + i, piece.col - i)) {
                    if (sameTeam(piece.color, board[piece.finalRow + i][piece.col - i])) count2++;
                    else count2 = 0;
                }
            }
            
            if (count1 >= 4 || count2 >= 4) return true;
        }
        
        return false;
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
        // First, draw the background (if there is one)
        switch (bgId) {
            case 5:
                g2.drawImage(Images.GOLD_BACKGROUND_5, 0, 0, null);
                break;
            case 4:
                g2.drawImage(Images.GOLD_BACKGROUND_4, 0, 0, null);
                break;
            case 2:
                g2.drawImage(Images.GOLD_BACKGROUND_2, 0, 0, null);
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
        
        if (!warfareMode) {
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
            
            // "Game over" + "back to main menu" overlays
            if (gameOver) {
                g2.drawImage(Images.GAME_OVERLAY, 0, GAME_OVER_Y, null);
            } else if (toMMHighlighted) {
                g2.drawImage(Images.BACK_TO_MM2, TO_MM_X, TO_MM_Y, null);
            } else {
                g2.drawImage(Images.BACK_TO_MM1, TO_MM_X, TO_MM_Y, null);
            }
        } else {
            // In Warfare, we draw the interactive piece above the board
            for (int i = 0; i < boardWidth; i++) { 
                for (int j = 0; j < boardHeight; j++) {
                    g2.drawImage(Images.SMALL_SQUARE, leftOffset + i * squareWidth, 
                            topOffset + j * squareWidth, null);
                }
            }
            
            if (gameOver) {
                g2.drawImage(Images.GAME_OVERLAY, 0, GAME_OVER_Y, null);
            } else if (toMMHighlighted) {
                g2.drawImage(Images.BACK_TO_MM3, TO_MM_X, WF_MM_Y, null);
            } else {
                g2.drawImage(Images.BACK_TO_MM2, TO_MM_X, WF_MM_Y, null);
                if (interactivePiece != null) { drawInteractivePiece(g2); }
            }
        }
    }
}
