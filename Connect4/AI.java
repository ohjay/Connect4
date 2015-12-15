package Connect4;

/**
 * AI (artificial intelligence) methods for computer play.
 * There's only one AI, so we shouldn't need to create any instances of this class.
 * Instead, all of its contents will be made static.
 * @author Owen Jow
 */
public class AI {
    static final String COMPUTER_COLOR = "red";
    static final int INFINITY = 15625; // 15625 = 5^6
    static final int[] SELECTION_ORDER = new int[] {3, 4, 2, 5, 1, 6, 0}; // column choices
    
    /**
     * Returns the best move for the computer, given a board state BOARD and a maximum depth DEPTH.
     * Assumes that nobody has won the game yet.
     * Also assumes that it is the computer's turn.
     *
     * This method is the AI's interface with the rest of the world.
     */
    public static int getBestComputerMove(ReguBoard board, int depth) {
        int computerMove = 0; // the best computer move
        int maxScore = Integer.MIN_VALUE; 
        for (int c : SELECTION_ORDER) {
            if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                // Create a piece for the computer to [theoretically] place
                int lowRow = board.lowestOpenRow(c);
                Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                
                // Simulate the new board configuration
                Piece[][] newBoard = board.cloneBoard();
                newBoard[lowRow][c] = p;
                ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                
                // Check if the move yields the best score we've seen so far
                int score = minimax(b, depth - 1, p, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
                System.out.println("[Column " + c + "] The computer is considering " + score); // debugging output
                if (score > maxScore) {
                    maxScore = score;
                    computerMove = c;
                    
                    // INFINITY (+ depth * 5) means the computer's won!
                    if (score == INFINITY + depth * 5) {
                        break;
                    }
                }
            }
        }
        
        System.out.println("\n==========\n"); // formatting for debugging output
        return computerMove;
    }
    
    //================================================================================
    // Minimax solution logic (with alpha-beta pruning)
    //================================================================================
    
    /**
     * Evaluates MAX_DEPTH levels of game state possibilities with the minimax algorithm 
     * and returns the computer's maximal heuristic game score. This method 
     * will always assume that the computer plays as the color COMPUTER_COLOR.
     * 
     * @param board the board over which the algorithm is being run
     * @param depth the number of turns (/current recursive depth). Always begins at 0
     * @param prevPiece the piece that was previously played
     * @param alpha the maximum score that the computer is guaranteed to get
     * @param beta the minimum score that the player is guaranteed to get
     * @param isComputer a boolean specifying whether it's the computer's turn or not
     */
    private static int minimax(ReguBoard board, int depth, Piece prevPiece, int alpha, 
            int beta, boolean isComputer) {
        if (board.makesFour(prevPiece)) {
            if (COMPUTER_COLOR.equals(prevPiece.color)) { return INFINITY + depth * 5; }
            else { return -INFINITY - depth * 5; }
        } else if (board.isBoardFull()) {
            return 0;
        } else if (depth == 0) {
            // Heuristic scoring
            return heuristicEval(board);
        }
        
        // The game isn't over, so we'll continue the recursion
        if (isComputer) {
            // Since it's the computer's turn, we want the MAX heuristic score
            for (int c : SELECTION_ORDER) {
                if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                    // Create a piece for the computer to [theoretically] place
                    int lowRow = board.lowestOpenRow(c);
                    Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                    
                    // Simulate the new board configuration
                    Piece[][] newBoard = board.cloneBoard();
                    newBoard[lowRow][c] = p;
                    ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                    
                    // Check if the move yields the best score we've seen so far
                    alpha = Math.max(alpha, minimax(b, depth - 1, p, alpha, beta, false));
                    if (beta <= alpha) { break; }
                }
            }
            
            return alpha;
        } else {
            // It's the player's turn, who wants to MINIMIZE the computer's score
            for (int c : SELECTION_ORDER) {
                if (!board.isColumnFull(c)) { // if a column's not full, then it's a possible move
                    // Create a piece for the computer to [theoretically] place
                    int lowRow = board.lowestOpenRow(c);
                    Piece p = new Piece(board.getCurrPlayer(), c, lowRow);
                
                    // Simulate the new board configuration
                    Piece[][] newBoard = board.cloneBoard();
                    newBoard[lowRow][c] = p;
                    ReguBoard b = new ReguBoard(newBoard, board.getNumPieces() + 1, board.otherPlayer());
                    
                    // Cut off fruitless subtrees (beta cutoff)
                    beta = Math.min(beta, minimax(b, depth - 1, p, alpha, beta, true));
                    if (beta <= alpha) { break; }
                }
            }
            
            return beta;
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
    private static int heuristicEval(ReguBoard board) {
        Piece[][] b = board.getBoard();
        return getHorizontalScore(b) + getVerticalScore(b) + getDiagonalScore(b);
    }
    
    /**
     * Returns the score that the computer receives for [potential] horizontal piece combinations.
     * Points are awarded for chains of two or three that have the potential to become four.
     * 
     * For such chains of two, 25 points will be awarded; for such chains of three, 125 points
     * will be awarded (to Gryffindor). No chains of four should exist.
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
     * The hierarchy of points awarded is identical to that of the horizontal scoring method.
     * 
     * @param b an array representation of the Connect4 board to evaluate
     * @return a diagonal score heuristic
     */
    private static int getDiagonalScore(Piece[][] b) {
        int score = 0;
        Piece p;
        CountTracker tracker = new CountTracker();
        
        // Traveling in a bottom-right direction
        for (int sc = 0; sc <= 3; sc++) {
            for (int sr = 0; sr <= 2; sr++) {
                // We have our starting point at (sr, sc).
                // Now iterate until we hit the bottom
                
                for (int r = sr, c = sc; r < 6 && c < 7; r++, c++) { // 6 & 7 are board dimensions
                    p = b[r][c];
                    
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
        }
        
        // Traveling in an up-right direction
        for (int sc = 0; sc <= 3; sc++) {
            for (int sr = 3; sr <= 5; sr++) {
                // Again, we have our starting point at (sr, sc).
                // This time, we'll iterate till we hit the top
                
                for (int r = sr, c = sc; r >= 0 && c < 7; r--, c++) {
                    p = b[r][c];
                    
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
        }
        
        return score;
    }
    
    /**
     * A tool to keep track of connected runs during game state evaluation.
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
                fullCount = Math.min(fullCount, 4); // 5 unconnected isn't actually any better
                return (color.equals(COMPUTER_COLOR)) ? 
                        (int) (Math.pow(5, fullCount)) : (int) (-Math.pow(5, fullCount));
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
