package Connect4;

import java.awt.Image;
import java.awt.Point;

/**
 * A connect four piece, colored either red, yellow, green, or magenta.
 * @author Owen Jow
 */
public class Piece {
    static final int WIDTH = 55;
    String color;
    private Point position;
    int col; // the piece's column
    int finalLevel; // the final row that this piece will occupy within its column
    
    /**
     * Constructs a piece; assumes that COLOR is either red, yellow, green, or magenta.
     * @param color an identifier to be used to select the piece's color
     * @param x the x-coordinate at which the piece should reside
     */
    public Piece(String color, int x) {
        this.color = color;
        position = new Point(x, ReguBoard.PIECE_START_HEIGHT);
    }
    
    /**
     * The same as the above, except that this constructor specifies a final level
     * and immediately sets the piece to reside at that level.
     */
    public Piece(String color, int x, int fLevel) {
        this.color = color;
        this.position = new Point(x, fLevel);
        this.col = x;
        this.finalLevel = fLevel;
    }
    
    /**
     * Returns the piece's associated image, based on the nature of its color attribute.
     */
    public Image getImage() {
        switch (color) {
            case "red":
                return Images.RED_PIECE;
            case "yellow":
                return Images.YELLOW_PIECE;
            case "purple":
                return Images.PURPLE_PIECE;
            case "green":
                return Images.GREEN_PIECE;
            case "black":
                return Images.BLACK_PIECE;
            case "cyan":
                return Images.CYAN_PIECE;
            case "gray":
                return Images.GRAY_PIECE;
            case "lightGreen":
                return Images.LIGHT_GREEN_PIECE;
            default:
                return Images.MAGENTA_PIECE;
        }
    }
    
    /**
     * Sets the piece's x-coordinate to the given value.
     */
    public void setX(int x) {
        position.move(x, getY());
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
        return (getY() >= ReguBoard.TOP_OFFSET + finalLevel * ReguBoard.SQUARE_WIDTH);
    }
}
