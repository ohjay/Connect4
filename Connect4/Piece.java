package Connect4;

import java.awt.Image;
import java.awt.Point;

/**
 * A connect four piece, colored either red, yellow, green, or magenta.
 * @author Owen Jow
 */
public class Piece {
    static final int REG_WIDTH = 55, SMALL_WIDTH = 33;
    static final double REG_RADIUS = ((double) REG_WIDTH) / 2;
    String color;
    private Point position;
    int col; // the piece's column
    int finalRow; // the final row that this piece will occupy within its column
    private boolean isSmall;
    
    /**
     * Constructs a piece; assumes that COLOR is either red, yellow, green, or magenta.
     * Assumes that the piece is not small.
     * @param color an identifier to be used to select the piece's color
     * @param x the x-coordinate at which the piece should reside
     */
    public Piece(String color, int x) {
        this.color = color;
        position = new Point(x, ReguBoard.pieceStartHeight);
    }
    
    /**
     * The same as the above, except that this constructor specifies a final level
     * and immediately sets the piece to reside at that level.
     */
    public Piece(String color, int x, int fLevel) {
        this.color = color;
        this.position = new Point(x, fLevel);
        this.col = x;
        this.finalRow = fLevel;
    }
    
    /**
     * Constructs an optionally small piece at position (x, y).
     */
    public Piece(String color, boolean isSmall, int x, int y) {
        this.color = color;
        this.position = new Point(x, y);
        this.isSmall = isSmall;
    }
    
    /**
     * Returns the piece's associated image, based on the nature of its color attribute.
     */
    public Image getImage() {
        if (!isSmall) {
            switch (color) {
                case "red":
                    return Images.RED_PIECE;
                default:
                    return Images.BLACK_PIECE;
            }
        } else {
            switch (color) {
                case "red":
                    return Images.SMALL_RED;
                case "yellow":
                    return Images.SMALL_YELLOW;
                case "green":
                    return Images.SMALL_GREEN;
                default:
                    return Images.SMALL_MAGENTA;
            }
        }
    }
    
    /**
     * Sets the piece's x-coordinate to the given value.
     */
    public void setX(int x) {
        position.move(x, getY());
    }
    
    /**
     * Sets the piece's x-coordinate and the piece's y-coordinate 
     * to X and Y, respectively.
     */
    public void setXY(int x, int y) {
        position.move(x, y);
    }
    
    /**
     * Returns the x-coordinate of this piece in its current position.
     * @return an x-coordinate as an int
     */
    public int getX() {
        return (int) position.getX();
    }
    
    /**
     * Returns the y-coordinate of this piece in its current position.
     * @return a y-coordinate as an int
     */
    public int getY() {
        return (int) position.getY();
    }
    
    /**
     * Translates this piece by length DX along the x-axis and by length DY along the y-axis.
     * @param dx the distance the piece should travel along the x-axis
     * @param dy the distance the piece should travel along the y-axis
     */
    public void translate(int dx, int dy) {
        position.translate(dx, dy);
    }
    
    /**
     * Describes whether or not the piece is in its final position (if it has fallen all the way).
     * @return a boolean indicating whether or not the piece has reached its final level
     */
    public boolean inFinalPosition() {
        return (getY() >= ReguBoard.topOffset + finalRow * ReguBoard.squareWidth);
    }
    
    /**
     * Returns the string representation of a piece.
     * Includes information about the piece's color and its coordinates within the board.
     */
    @Override
    public String toString() {
        return color + " piece @ (" + finalRow + ", " + col + ")";
    }
}
