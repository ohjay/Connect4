package Connect4;

/**
 * A special board to be used in the Four by Two game mode.
 * Only switches turns after every two moves 
 * (with the exception of the first turn, in which the player
 * only gets one drop).
 * @author Owen Jow
 */
public class FourByTwoBoard extends ReguBoard {
    int counter;
    
    @Override
    protected void switchPlayers() {
        if (counter % 2 == 0) {
            // Switch the turn
            currColor = (currColor.equals("red")) ? "black" : "red";
        } // otherwise do nothing (the player gets a second move!)
        
        counter++;
    }
}
