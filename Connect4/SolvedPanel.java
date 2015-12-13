package Connect4;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;

/**
 * The panel in which a user plays Connect4 against a computer of HARD difficulty.
 * (This is another way of saying that the computer will play perfectly.)
 * @author Owen Jow
 */
public class SolvedPanel extends ReguC4Panel {
    private boolean listenerEnabled = true;
    
    public SolvedPanel() {
        mouseListener = new SolvedMouseListener();
        bgId = 4;
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (board.isPieceFalling) {
            board.animateFallingPiece();
        } else if (board.getCurrPlayer().equals(computerColor)) {
            listenerEnabled = false;
            // Run the minimax algorithm, which will update computerMove
            minimax(board, 0, null, Integer.MIN_VALUE, Integer.MAX_VALUE);
            board.addToColumn(board.interactivePiece, computerMove);
        } else {
            listenerEnabled = true;
        }
        
        repaint();
    }
    
    class SolvedMouseListener extends MouseAdapter {
        /**
         * Sends the interactive piece (if there is one) downward, as if it were dropped.
         */
        public void mouseClicked(MouseEvent evt) {
            if (listenerEnabled) {
                if (board.interactivePiece != null && !board.isPieceFalling) {
                    int nearestCol = ReguBoard.getNearestCol(MouseData.x);
                    if (!board.isColumnFull(nearestCol)) {
                        board.addToColumn(board.interactivePiece, nearestCol);
                    }
                }
            }
        }
        
        /**
         * Controls the interactive piece, and registers movement in the MouseData class.
         */
        public void mouseMoved(MouseEvent evt) {
            if (listenerEnabled) {
                if (evt.getX() <= Connect4.WINDOW_LEN - Piece.REG_WIDTH) {
                    MouseData.x = evt.getX();
                    if (!board.isPieceFalling) {
                        board.interactivePiece.setX(MouseData.x);
                    }
                }
            }
        }
    }
    
    //================================================================================
    // Minimax solution logic (with alpha-beta pruning)
    //================================================================================
    
    private String computerColor = "black", userColor = "red";
    private int computerMove; // the computer's next move, determined by the algorithm below
    private int maxDepth = 9; /* maxDepth should always be an odd number, so that heuristicEval 
                                * will only ever be called right after the computer has moved */
    
    /**
     * Evaluates all game possibilities with the minimax algorithm and returns the computer's 
     * maximal heuristic game score. Will also set the COMPUTER_MOVE variable to the value
     * of the optimal column in which the computer can play. This method will always assume
     * that the computer plays as the color COMPUTER_COLOR and that the user plays as USER_COLOR,
     * both of which are given as instance variables of the SolvedPanel class.
     * 
     * @param board the board over which the algorithm is being run
     * @param depth the number of turns (/current recursive depth). Always begins at 0
     * @param prevPiece the piece that was previously played
     * @param alpha the maximum score that the computer is guaranteed to get
     * @param beta the minimum score that the player is guaranteed to get
     */
    public int minimax(ReguBoard board, int depth, Piece prevPiece, int alpha, int beta) {
        // Heuristic scoring
        if ((prevPiece != null && board.makesFour(prevPiece)) || board.isBoardFull() || depth == maxDepth) {
            return (depth % 2 == 1) ? depth * heuristicEval(board, computerColor) 
                    + ((prevPiece != null && board.makesFour(prevPiece)) ? 500000 : 0)
                    : depth * heuristicEval(board, userColor)
                    + ((prevPiece != null && board.makesFour(prevPiece)) ? 500000 : 0);
        }
        
        // The game isn't over, so we'll continue the recursion
        if (board.getCurrPlayer().equals(computerColor)) {
            // Since it's the computer's turn, we want the MAX heuristic score
            int maxScore = Integer.MIN_VALUE; 
            for (int c = ReguBoard.boardWidth - 1; c >= 0; c--) {
                if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                    // Create a piece for the computer to [theoretically] place
                    int lowRow = board.lowestOpenRow(c);
                    Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                    
                    // Simulate the new board configuration
                    Piece[][] newBoard = board.cloneBoard();
                    newBoard[lowRow][c] = p;
                    ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                    
                    // Check if the move yields the best score we've seen so far
                    int score = minimax(b, depth + 1, p, alpha, beta);
                    if (score > maxScore) {
                        maxScore = score;
                        computerMove = c;
                        
                        // Alpha-beta pruning (alpha cutoff)
                        alpha = Math.max(alpha, maxScore);
                        if (beta <= alpha) { break; }
                    }
                }
            }
            
            return maxScore;
        } else {
            // It's the player's turn, whose score we want to MINimize
            int minScore = Integer.MAX_VALUE;
            for (int c = ReguBoard.boardWidth - 1; c >= 0; c--) {
                if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                    // Create a piece for the computer to [theoretically] place
                    int lowRow = board.lowestOpenRow(c);
                    Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                
                    // Simulate the new board configuration
                    Piece[][] newBoard = board.cloneBoard();
                    newBoard[lowRow][c] = p;
                    ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                
                    int score = minimax(b, depth + 1, p, alpha, beta);
                    if (score < minScore) {
                        minScore = score;
                        computerMove = c;
                        
                        // Cut off fruitless subtrees (beta cutoff)
                        beta = Math.min(beta, minScore);
                        if (beta <= alpha) { break; }
                    }
                }
            }
            
            return minScore;
        }
    }
    
    /**
     * Heuristically evalutes a Connect4 board and assigns to it a score 
     * representing the strength of the computer's position. [This is the 
     * value that will be returned.]
     *
     * In order to quantify positional strength, this method will take into account
     * the number of connected fours, threes, and twos for each side.
     * @param board the Connect4 board to evaluate
     * @param color the color whose perspective we want to assume
     * @return a score heuristic for COLOR (positive is good, negative is bad)
     */
    public int heuristicEval(ReguBoard board, String color) {
        Piece[][] b = board.getBoard();
        return getHorizontalScore(b, color) + getVerticalScore(b, color) + getDiagonalScore(b, color);
    }
    
    /**
     * Returns the score that COLOR receives for [potential] horizontal piece combinations.
     * Points are awarded for chains of two or three that have the potential to become four.
     * 
     * For such chains of two, 10 points will be awarded; for such chains of three, 100 points
     * will be awarded (to Gryffindor). Finally, for chains of four, 10000 points will be awarded
     * (because chains of four signify that the game has been won).
     * 
     * Equivalent points will also be deducted for chains belonging to the opposing color.
     * 
     * @param b an array representation of the Connect4 board to evaluate
     * @param color the color whose perspective we want to assume
     * @return a horizontal score heuristic
     */
    private int getHorizontalScore(Piece[][] b, String color) {
        int score = 0;
        String oneColor = "", twoColor = "", threeColor = ""; // the color of length 1/2/3 connected chains
        Piece p; // the piece we're currently examining
            
        for (int r = ReguBoard.boardHeight - 1; r >= 0; r--) { // start from the bottom up
            for (int c = 0; c < ReguBoard.boardWidth; c++) { // start from the left rightward
                p = b[r][c]; // kind of like pv = nrt, except not really at all
                
                if (p == null) {
                    if (!threeColor.isEmpty()) { 
                        // There were three pieces connected (with potential)
                        score += (threeColor.equals(color)) ? 1000 : -1000;
                        threeColor = "";
                    } else if (!twoColor.isEmpty()) {
                        // There were two pieces connected (maybe with potential)
                        score += (twoColor.equals(color)) ? 100 : -100;
                        twoColor = "";
                    }
                    
                    oneColor = "";
                } else if (p.color.equals(threeColor)) {
                    // This is basically catching the Snitch, so we may as well return now
                    return (threeColor.equals(color)) ? score + 100000 : score - 100000;
                } else if (p.color.equals(twoColor)) {
                    threeColor = twoColor;
                    twoColor = "";
                } else if (p.color.equals(oneColor)) {
                    twoColor = oneColor;
                    oneColor = "";
                } else {
                    // Chain broken by the opposing color!
                    oneColor = p.color;
                }
            }
            
            // Check for a far-right chain with potential
            if (!threeColor.isEmpty() && b[r][3] == null) {
                score += (threeColor.equals(color)) ? 1000 : -1000;
            } else if (!twoColor.isEmpty()) {
                Piece middlePiece = b[r][3];
                if (b[r][4] == null && !(middlePiece != null && !middlePiece.color.equals(color))) {
                    score += (twoColor.equals(color)) ? 100 : -100;
                } 
                
                twoColor = "";
            }
            
            oneColor = ""; threeColor = "";
        }
        
        return score;
    }
    
    /**
     * Returns the score that COLOR receives for [potential] vertical piece combinations.
     * The hierarchy of points awarded is identical to that of the horizontal scoring method.
     * 
     * @param b an array representation of the Connect4 board to evaluate
     * @param color the color whose perspective we want to assume
     * @return a vertical score heuristic
     */
    private int getVerticalScore(Piece[][] b, String color) {
        int score = 0;
        String oneColor = "", twoColor = "", threeColor = "";
        Piece p;
        
        for (int c = 0; c < ReguBoard.boardWidth; c++) {
            for (int r = ReguBoard.boardHeight - 1; r >= 0; r--) {
                p = b[r][c];
                
                if (p == null) {
                    if (!threeColor.isEmpty()) {
                        score += (threeColor.equals(color)) ? 1000 : -1000;
                        threeColor = "";
                    } else if (!twoColor.isEmpty()) {
                        score += (twoColor.equals(color)) ? 100 : -100;
                        twoColor = "";
                    }
                    
                    oneColor = "";
                    break; // obviously there won't be any pieces above an empty square
                } else if (p.color.equals(threeColor)) {
                    return (threeColor.equals(color)) ? score + 100000 : score - 100000;
                } else if (p.color.equals(twoColor)) {
                    threeColor = twoColor;
                    twoColor = "";
                } else if (p.color.equals(oneColor)) {
                    twoColor = oneColor;
                    oneColor = "";
                } else {
                    // Chain broken by the opposing color!
                    oneColor = p.color;
                }
            }
            
            oneColor = ""; twoColor = ""; threeColor = ""; // resetting these for the next iteration
        }
        
        return score;
    }
    
    /**
     * Returns the score that COLOR receives for [potential] diagonal piece combinations.
     * The hierarchy of points awarded is similar to that of the horizontal scoring method.
     * The distinction is that this method only checks for diagonal threes and fours at the moment.
     * 
     * @param b an array representation of the Connect4 board to evaluate
     * @param color the color whose perspective we want to assume
     * @return a diagonal score heuristic
     */
    private int getDiagonalScore(Piece[][] b, String color) {
        int score = 0;
        
        for (int c = 0; c < 4; c++) {
            String origColor; // the color of the first piece
            
            // Traveling in a bottom-right direction
            for (int r = 3; r >= 0; r--) {
                if (b[r][c] == null) { break; } // because there can't be any pieces above this one
                origColor = b[r][c].color;
                if (b[r + 1][c + 1] != null && origColor.equals(b[r + 1][c + 1].color)
                        && b[r + 2][c + 2] != null && origColor.equals(b[r + 2][c + 2].color)) {
                    if (r < 3 && c < 4 && b[r + 3][c + 3] != null && origColor.equals(b[r + 3][c + 3].color)) {
                        return (origColor.equals(color)) ? score + 100000 : score - 100000;
                    } else if ((r > 0 && c > 0 && b[r - 1][c - 1] == null) 
                            || (r < 3 && c < 4 && b[r + 3][c + 3] == null)) {
                        // We'll call it a three with potential
                        score += (origColor.equals(color)) ? 1000 : -1000;
                    }
                }
            }
            
            // Traveling in an up-right direction
            for (int r = 5; r >= 2; r--) {
                if (b[r][c] == null) { break; }
                origColor = b[r][c].color;
                if (b[r - 1][c + 1] != null && origColor.equals(b[r - 1][c + 1].color)
                        && b[r - 2][c + 2] != null && origColor.equals(b[r - 2][c + 2].color)) {
                    if (r > 2 && c < 4 && b[r - 3][c + 3] != null && origColor.equals(b[r - 3][c + 3].color)) {
                        return (origColor.equals(color)) ? score + 100000 : score - 100000;
                    } else if ((r < 5 && c > 0 && b[r + 1][c - 1] == null) 
                            || (r > 2 && c < 4 && b[r - 3][c + 3] == null)) {
                        score += (origColor.equals(color)) ? 1000 : -1000;
                    }
                }
            }
        }
        
        return score;
    }
}
