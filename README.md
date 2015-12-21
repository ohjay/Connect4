# Connect4
The classic game of Connect Four, enhanced with several modern modifications. This was built originally as a proof to my friend that Connect Four is a solved game.

## A Lesson in Economics
The life we know is short, and it has a definite ending. What does that mean? It means you should spend as much time as possible on the things you care about, and minimize the amount of time spent on everything else. Sometimes, this means you have to trim some things away. For example, I've realized that this project has a significant opportunity cost. Imagine how many YouTube videos I could watch if I didn't have Connect4 hanging over my head! At least fourteen, I'll tell you that.

That being the case, there are three obvious solutions to my problem:

- **Number one**: I work really hard, sacrifice a few days of my life, and get this thing done (right down to every last feature!).
- **Number two**: I strip my game down to a more core set of functionalities, and thereby spend a little less time on repetitive or implementationally uninteresting components. 
- And finally **solution number three**: I fake my death, move to Australia, and start a fresh new life as a kangaroo.

I decided against option number one because I liked option number two better, and I ruled out option number three because I don't think I would fool any kangaroos; they're too smart for me.

So yes – I'll be including a few less features than I'd initially planned. In particular, the networked two-player mode and the Board modes kinda got dropped from the lineup. Since I already made the menus for these, I'll probably end up sticking a bunch of "inadequate funding" screens all over the place. Look forward to it.

Never fear, though: this is still more than your basic Connect4 application. After all, we've got three other special modes (Warfare, Four by Two, and Removal), plus my Photoshop-made board + piece graphics. All is not lost...

## Download
This download _probably_ does not have any viruses attached. It will require you to have the Java Runtime Environment installed, though.

- Link: [v1.0.0 JAR file](../master/dist/v1.0.0/Connect4_v1.0.0.jar?raw=true)

## Implementation Details
#### Artificially Intelligent Gameplay
By far the most interesting thing I got to explore in this project was the implementation of a Connect Four AI. My computer player makes use of the standard minimax algorithm (with alpha-beta pruning to speed up execution). Conceptually, this means that for any board state, the computer can examine all future move possibilities and determine the optimal play. It turns out "all future move possibilities" can be represented as a game tree. For example, a Tic-tac-toe tree would look something like this:
![alt text](https://github.com/ohjay/Connect4/blob/master/demo_imgs/alphabeta.jpg "Tic-tac-toe game tree")

My AI constructs the next N levels of the Connect4 game tree (via simulated board arrays), and for every leaf state evaluates the strength of the board from the computer's point of view. Assuming a depth-limited implementation, the easy cases are boards in which the computer wins or loses: these garner scores of INFINITY and -INFINITY, respectively. If nobody has won, it gets a little trickier. For these game states, my heuristic evaluation function takes into account the number of potential 4s for each side and the progress each player has made toward that 4. In other words, if a player already has two or three pieces going toward a potential four, they'll earn some points in my heuristic.

Once we have the strength of each of the leaves, we'll basically look at our tree as a decision tree and assume that both sides are playing optimally. If the leaf boards were the result of the computer's move, then obviously the computer would want to achieve the board with the greatest "strength." Hence, we would use the "max"-strength leaves (for each group of leaves). Traveling one level up the tree, it would before have been the human player's move, who we'd assume would want to MINIMIZE the computer's score. Thus, we'd take the "min"-strength states from there. The next move up the tree would be the computer's move, who'd again choose the "max"-strength states... and so on/so forth until it'd get to the computer's original move, in which the computer naturally would select the move with the best score that had managed to propagate all the way up the tree. 

The cycle of the computer wanting to "maximize" its score and the player wanting to "minimize" that score, of course, is the reason for the name "minimax." Given the first move and the chance to look at the entire game tree every time, the computer can [always win the game](http://www.informatik.uni-trier.de/~fernau/DSL0607/Masterthesis-Viergewinnt.pdf)! Unfortunately, getting this to work in an at-all reasonable amount of time would require a lot more optimizations than I actually performed. (There are over 4 trillion board states to look at :!) This being the case, my computers are actually beatable and operate using limited game tree depths. In order for this to work, I use the heuristic eval function I mentioned above in order to assign scores to leaf boards. 

All in all, I'd say it still works rather well. It did manage to beat a lot of the plebeian humans I tested it on (including myself a couple of times!).

#### Everything Else
The rest of Connect4 was just a bunch of standard game programming: switching between JPanels, running events on timers + mouse actions, and checking whether someone just connected four. If you have specific questions about this stuff, feel free to peruse my code or send me an anonymous email.

## Game Modes
Connect4 is a Swing application that allows the user to enjoy several different game modes:

- **vs CPU**: uses a depth-limited [minimax](https://en.wikipedia.org/wiki/Minimax) algorithm to choose moves for the AI player
- **vs Human**: uses a depth-limited human to choose moves for the other player
- **Warfare**: allows four players to play at the same time, and form alliances as they will
- **Four by Two**: enables players to place two pieces at a time
- **Removal**: enables players to remove one of their pieces as their turn

## Visuals
![alt text](https://github.com/ohjay/Connect4/blob/master/demo_imgs/demo_img1.png "Gameplay screens")
