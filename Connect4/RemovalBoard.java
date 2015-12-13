package Connect4;

import java.awt.Graphics2D;

/**
 * A board in which pieces can be removed from play 
 * (the pieces on top will cascade down, and we'll have to check for fours again).
 * 
 * If doing so causes a connected four for both players, the winner will
 * be the player who initiated the fall.
 *
 * @author Owen Jow
 */
public class RemovalBoard extends ReguBoard {
    private int fallingCol = -1, lowerFallingRow, upperFallingRow;
    
    @Override
    public boolean isColumnFalling() {
        return (fallingCol > -1);
    }
    
    @Override
    protected void drawInteractivePiece(Graphics2D g2) {
        if (!shouldHidePiece) {
            g2.drawImage(interactivePiece.getImage(), interactivePiece.getX(), 
                    interactivePiece.getY(), null);
        }
    }
    
    @Override
    public void removePiece(int row, int col) {
        // Remove the piece
        board[row][col] = null;
        numPieces--;
        
        if (row - 1 >= 0 && board[row - 1][col] != null) {
            // There were pieces above this piece. They will have to fall.
            fallingCol = col;
            upperFallingRow = row + 1;
            lowerFallingRow = row;
            
            // Reassign array positions
            for (int r = row - 1; r >= 0; r--) { // go up the column
                Piece piece = board[r][col];
                if (piece == null) { break; }
                board[r + 1][col] = piece; // shift every piece down one spot
                board[r][col] = null;
                piece.finalRow = piece.finalRow + 1;
                upperFallingRow -= 1;
            }
        } else {
            switchPlayers();
            interactivePiece = new Piece(currColor, MouseData.x);
        }
    }
    
    @Override
    public void animateFallingPiece() {
        if (fallingCol > -1) {
            // See if the column is done falling
            if (board[lowerFallingRow][fallingCol].inFinalPosition()) {
                // It is! Now see if it connects any fours
                int statusCode = 0; // 0 = nobody wins, 1 = curr player wins, 2 = other guy wins
                for (int r = upperFallingRow; r <= lowerFallingRow; r++) {
                    Piece piece = board[r][fallingCol];
                    if (makesFour(piece)) {
                        if (piece.color.equals(currColor)) {
                            statusCode = 1;
                            break;
                        } else {
                            statusCode = 2;
                            // Don't break; there's still a chance that the curr player made a 4
                        }
                    }
                }
                
                if (statusCode == 0) {
                    // Move on to the other player's turn
                    fallingCol = -1;
                    switchPlayers();
                    interactivePiece = new Piece(currColor, MouseData.x);
                } else {
                    // Game over! Somebody won
                    interactivePiece = null;
                    Connect4.returnToMainMenu();
                    return;
                }
            } else {
                // Keep going, guys
                for (int r = upperFallingRow; r <= lowerFallingRow; r++) {
                    board[r][fallingCol].translate(0, 3);
                }
            }
        } else if (interactivePiece.inFinalPosition()) {
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
