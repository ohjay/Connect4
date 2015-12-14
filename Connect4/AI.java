package Connect4;

/**
 * AI (artificial intelligence) methods for computer play.
 * There's only one AI, so we shouldn't need to create any instances of this class.
 * Instead, all of its contents will be made static.
 * @author Owen Jow
 */
public class AI {
    static final String COMPUTER_COLOR = "red";
    static final int INFINITY = 500000;
    static int computerMove; // the computer's next move, determined by the algorithm below
    
    //================================================================================
    // Minimax solution logic (with alpha-beta pruning)
    //================================================================================
    
    /**
     * Evaluates MAX_DEPTH levels of game state possibilities with the minimax algorithm 
     * and returns the computer's maximal heuristic game score. Will also set the COMPUTER_MOVE 
     * variable to the value of the optimal column in which the computer can play. This method 
     * will always assume that the computer plays as the color COMPUTER_COLOR,
     * both of which are given as instance variables of the SolvedPanel class.
     * 
     * @param board the board over which the algorithm is being run
     * @param depth the number of turns (/current recursive depth). Always begins at 0
     * @param prevPiece the piece that was previously played
     * @param alpha the maximum score that the computer is guaranteed to get
     * @param beta the minimum score that the player is guaranteed to get
     */
    public static int minimax(ReguBoard board, int depth, Piece prevPiece, int alpha, int beta) {
        if (prevPiece != null && board.makesFour(prevPiece)) {
            if (COMPUTER_COLOR.equals(prevPiece.color)) { return INFINITY; }
            else { return -INFINITY; }
        } else if (board.isBoardFull()) {
            return 0;
        } else if (depth == 0) {
            // Heuristic scoring
            return heuristicEval(board);
        }
        
        // The game isn't over, so we'll continue the recursion
        if (board.getCurrPlayer().equals(COMPUTER_COLOR)) {
            // Since it's the computer's turn, we want the MAX heuristic score
            int maxScore = Integer.MIN_VALUE; 
            for (int c = board.boardWidth - 1; c >= 0; c--) {
                if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                    // Create a piece for the computer to [theoretically] place
                    int lowRow = board.lowestOpenRow(c);
                    Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                    
                    // Simulate the new board configuration
                    Piece[][] newBoard = board.cloneBoard();
                    newBoard[lowRow][c] = p;
                    ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                    
                    // Check if the move yields the best score we've seen so far
                    int score = minimax(b, depth - 1, p, alpha, beta);
                    if (score > maxScore) {
                        maxScore = score;
                        computerMove = c;
                        
                        /* // Alpha-beta pruning (alpha cutoff)
                        alpha = Math.max(alpha, maxScore);
                        if (beta <= alpha) { break; } */
                    }
                }
            }
            
            return maxScore;
        } else {
            // It's the player's turn, who wants to MINIMIZE the computer's score
            int minScore = Integer.MAX_VALUE;
            for (int c = board.boardWidth - 1; c >= 0; c--) {
                if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                    // Create a piece for the computer to [theoretically] place
                    int lowRow = board.lowestOpenRow(c);
                    Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                
                    // Simulate the new board configuration
                    Piece[][] newBoard = board.cloneBoard();
                    newBoard[lowRow][c] = p;
                    ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                
                    int score = minimax(b, depth - 1, p, alpha, beta);
                    if (score < minScore) {
                        minScore = score;
                        computerMove = c;
                        
                        /* // Cut off fruitless subtrees (beta cutoff)
                        beta = Math.min(beta, minScore);
                        if (beta <= alpha) { break; } */
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
     * @return a score heuristic for the computer (positive is good, negative is bad)
     */
    public static int heuristicEval(ReguBoard board) {
        Piece[][] b = board.getBoard();
        return getHorizontalScore(b) + getVerticalScore(b) + getDiagonalScore(b);
    }
    
    /**
     * Returns the score that the computer receives for [potential] horizontal piece combinations.
     * Points are awarded for chains of two or three that have the potential to become four.
     * 
     * For such chains of two, 100 points will be awarded; for such chains of three, 1000 points
     * will be awarded (to Gryffindor). Finally, for chains of four, INFINITY points will be awarded
     * (because chains of four signify that the game has been won).
     * 
     * Equivalent points will also be deducted for chains belonging to the opposing color.
     * 
     * @param b an array representation of the Connect4 board to evaluate
     * @return a horizontal score heuristic
     */
    private static int getHorizontalScore(Piece[][] b) {
        int score = 0;
        Piece p; // the piece we're currently examining
        CountTracker tracker = new CountTracker();
            
        for (int r = ReguBoard.boardHeight - 1; r >= 0; r--) { // start from the bottom up
            for (int c = 0; c < ReguBoard.boardWidth; c++) { // start from the left rightward
                p = b[r][c]; // kind of like pv = nrt, except not really at all
                
                if (p == null) {
                    tracker.recentEmptyCount += 1;
                } else if (p.color.equals(tracker.color)) {
                    tracker.nrEmptyCount += tracker.recentEmptyCount;
                    tracker.recentEmptyCount = 0;
                    tracker.fullCount += 1;
                } else {
                    score += tracker.getScore();
                    tracker.reset(p.color, true, true);
                }
            }
            
            score += tracker.getScore();
            tracker.reset("", false, false);
        }
        
        return score;
    }
    
    /**
     * Returns the score that the computer receives for [potential] vertical piece combinations.
     * The hierarchy of points awarded is identical to that of the horizontal scoring method.
     * 
     * @param b an array representation of the Connect4 board to evaluate
     * @return a vertical score heuristic
     */
    private static int getVerticalScore(Piece[][] b) {
        int score = 0;
        Piece p;
        CountTracker tracker = new CountTracker();
        
        for (int c = 0; c < ReguBoard.boardWidth; c++) {
            for (int r = ReguBoard.boardHeight - 1; r >= 0; r--) { // traveling upward
                p = b[r][c];
                
                if (p == null) {
                    tracker.recentEmptyCount += 1;
                } else if (p.color.equals(tracker.color)) {
                    tracker.fullCount += 1;
                } else {
                    score += tracker.getScore();
                    tracker.reset(p.color, true, false);
                }
            }
            
            score += tracker.getScore();
            tracker.reset("", false, false);
        }
        
        return score;
    }
    
    /**
     * Returns the score that the computer receives for [potential] diagonal piece combinations.
     * The hierarchy of points awarded is similar to that of the horizontal scoring method.
     * The distinction is that this method only checks for diagonal threes and fours at the moment.
     * 
     * @param b an array representation of the Connect4 board to evaluate
     * @return a diagonal score heuristic
     */
    private static int getDiagonalScore(Piece[][] b) {
        int score = 0;
        
        for (int c = 0; c < 4; c++) {
            String origColor; // the color of the first piece
            
            // Traveling in a bottom-right direction
            for (int r = 3; r >= 0; r--) {
                if (b[r][c] == null) { break; } // because there can't be any pieces above this one
                origColor = b[r][c].color;
                if (b[r + 1][c + 1] != null && origColor.equals(b[r + 1][c + 1].color)
                        && b[r + 2][c + 2] != null && origColor.equals(b[r + 2][c + 2].color)) {
                    if ((r > 0 && c > 0 && b[r - 1][c - 1] == null) 
                            || (r < 3 && c < 4 && b[r + 3][c + 3] == null)) {
                        // We'll call it a three with potential
                        score += (origColor.equals(COMPUTER_COLOR)) ? 1000 : -1000;
                    }
                }
            }
            
            // Traveling in an up-right direction
            for (int r = 5; r >= 2; r--) {
                if (b[r][c] == null) { break; }
                origColor = b[r][c].color;
                if (b[r - 1][c + 1] != null && origColor.equals(b[r - 1][c + 1].color)
                        && b[r - 2][c + 2] != null && origColor.equals(b[r - 2][c + 2].color)) {
                    if ((r < 5 && c > 0 && b[r + 1][c - 1] == null) 
                            || (r > 2 && c < 4 && b[r - 3][c + 3] == null)) {
                        score += (origColor.equals(COMPUTER_COLOR)) ? 1000 : -1000;
                    }
                }
            }
        }
        
        return score;
    }
    
    /**
     * Used to keep track of connected runs during evaluation of game states.
     *
     * Includes a tally of empty spaces during a run, so that the tracker
     * can take disjoint sets of pieces into account.
     */
    private static class CountTracker {
        int fullCount; // number of spots in the streak that are full
        int nrEmptyCount; // number of spots in the streak that are empty and not recent
        int recentEmptyCount; // number of empty spots on the right/bottom of the current streak
        String color = ""; // color of the current streak
        
        /**
         * Returns the computer-oriented score contained within the tracker.
         */
        int getScore() {
            if (!color.isEmpty() && fullCount + nrEmptyCount + recentEmptyCount >= 4) {
                return (color.equals(COMPUTER_COLOR)) ? 
                        (int) (Math.pow(10, fullCount)) : (int) (-Math.pow(10, fullCount));
            } else {
                return 0;
            }
        }
    
        /**
         * Resets values in the tracker.
         * @param the color to set the new streak to
         * @param newStreak describes whether this is the beginning of a new streak
         * @param useRecent describes whether the recent empty squares should be taken into account
         */
        void reset(String color, boolean newStreak, boolean useRecent) {
            this.color = color;
            fullCount = (newStreak) ? 1 : 0;
            nrEmptyCount = (useRecent) ? recentEmptyCount : 0;
            recentEmptyCount = 0;
        }
    }
}
