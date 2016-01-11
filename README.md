# Connect4 [v1.0.0]
The classic game of Connect Four, enhanced with several modern modifications. This was built originally as a proof to my friend that Connect Four is a solved game.

## Direct Download
This download _probably_ does not have any viruses attached. It will require you to have the [Java Runtime Environment](http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html) installed, though.

- Link: [v1.0.0 JAR file](../master/dist/v1.0.0/Connect4_v1.0.0.jar?raw=true)

## Implementation Details
#### Artificially Intelligent Gameplay
By far the most interesting thing I got to explore in this project was the implementation of a Connect Four AI. My computer player makes use of the standard minimax algorithm (with alpha-beta pruning to speed up execution). Conceptually, this means that for any board state, the computer can determine the optimal play by examining all future move possibilities. As it happens, "all future move possibilities" can be represented as a game tree. For example, a Tic-tac-toe tree would look something like this:

![alt text](https://github.com/ohjay/Connect4/blob/master/demo_imgs/alphabeta.jpg "Tic-tac-toe game tree")

My AI constructs the next N levels of the Connect4 game tree (via simulated board arrays), and for every leaf state evaluates the strength of the board from the computer's point of view. Assuming a depth-limited implementation, the easy cases are boards in which the computer wins or loses: these garner scores of INFINITY and -INFINITY, respectively. If nobody has won yet, it gets a little trickier. For these game states, my score assignment function takes into account the number of potential 4s for each side and the progress that the players have made toward each of those 4s. In other words, if a player already has two or three pieces going toward a potential four, they'll earn some points in my heuristic.

Once we have the strength of each of the leaves, we'll basically look at our tree as a decision tree and assume that both sides are playing optimally... which allows us to eliminate possible futures. If the leaf boards were the result of the computer's move, then obviously the computer would want to achieve the board with the greatest "strength." Hence, we would use the "max"-strength leaves (for each group of leaves, that is). Traveling one level up the tree, it would one turn before have been the human player's move, who we'd assume would want to MINIMIZE the computer's score. Thus, we'd take the "min"-strength states from there. The _next_ level up the tree would be the computer's move, who'd again choose the "max"-strength states... and so on/so forth until it'd eventually get to the computer's original move. At this point, the computer would naturally select the move with the _best score that had managed to propagate all the way up the tree_. 

And that's it! That's the "optimal" move.

The cycle of the computer wanting to "maximize" its score and the player wanting to "minimize" that score, of course, is the reasoning behind the name "minimax." Given the first move and the chance to look at the entire game tree every turn, the computer can [always win the game](http://www.informatik.uni-trier.de/~fernau/DSL0607/Masterthesis-Viergewinnt.pdf)! Unfortunately, getting this to work in an "at all" reasonable amount of time requires many more optimizations than I actually built in. (There are over 4 trillion board states to look at :!) This being the case, my computers are actually beatable and operate using limited tree depths.

All things considered, I'd say it still works rather well. It did manage to beat a lot of the plebeian humans I tested it on (including myself a couple of times). Consider giving it a run or two. ;)

#### Everything Else
The rest of Connect4 was just a bunch of standard game programming: switching between JPanels, running events on timers + mouse actions, and checking whether someone just connected four. If you have specific questions about this stuff, feel free to peruse my code or send me an anonymous email.

## Game Modes
Connect4 is a Swing application that lets the user enjoy several different game modes:

- **vs CPU**: uses a depth-limited [minimax](https://en.wikipedia.org/wiki/Minimax) algorithm to choose moves for the other player
- **vs Human**: uses a depth-limited human to choose moves for the other player
- **Warfare**: allows four players to play at the same time, and form alliances as they will
- **Four by Two**: enables players to place two pieces at a time
- **Removal**: enables players to remove one of their pieces as their turn

## Visuals
![alt text](https://github.com/ohjay/Connect4/blob/master/demo_imgs/demo_img1.png "Gameplay screens")

## References
- The Tic-tac-toe image was borrowed from Yosen Lin's [online explanation of game trees](https://www.ocf.berkeley.edu/~yosenl/extras/alphabeta/alphabeta.html).
- I found [this assignment paper](http://www.cs.cornell.edu/courses/CS2110/2014sp/assignments/a4/A4ConnectFour.pdf) from Cornell incredibly useful for understanding minimax. I probably should have just linked it instead of writing that "Implementation Details" section earlier.
