# Connect4
The classic game of Connect Four, enhanced with several modern modifications. This was built originally as a proof to my friend that Connect Four is a solved game.

## A Lesson in Economics
The life we know is short, and it has a definite ending. What does that mean? It means you should spend as much time as possible on the things you care about, and minimize the amount of time spent on everything else. Sometimes, this means you have to trim some things away. For example, I've realized that this project has a significant opportunity cost. Imagine how many YouTube videos I could watch if I didn't have Connect4 hanging over my head! At least fourteen, I'll tell you that.

That being the case, there are three obvious solutions. **Number one**: I work really hard, sacrifice a few days of my life, and finish every feature I originally had in mind. **Number two**: I strip my game down to a more core set of functionalities, and therefore spend a little less time on the repetitive or implementationally uninteresting components. And finally **solution number three**: I fake my death, move to Australia, and start a fresh new life as a kangaroo.

I decided against option number one because I liked option number two better, and I ruled out option number three because I don't think I would fool any kangaroos – they're too smart for me.

As a result, I've decided to include a few less features than I'd initially planned. Namely, the networked connection (who _really_ wants to play Connect4 over the Internet, where you can't rage-drop all the pieces from the board) and the Board modes are going to have to go. Since I already made the menus for these, I'll probably end up sticking a bunch of "inadequate funding" screens all over the place. Look forward to it.

Still, never fear: it's still more than a basic Connect4 application. We still have the three other special modes (Warfare, Four by Two, and Removal) and my Photoshop-made board and piece graphics. All is not lost...

## Download
This download probably does not have any viruses attached. It'll require you to have the Java Runtime Environment installed, though.

- Link: [Version 1.0.0 JAR file](../master/dist/v1.0.0/Connect4_v1.0.0.jar?raw=true)

## Game Modes
Connect4 is a Swing application that allows the user to enjoy several different game modes:

- **vs CPU**: uses a depth-limited [minimax](https://en.wikipedia.org/wiki/Minimax) algorithm to choose moves for the AI player
- **vs Human**: uses a depth-limited human to choose moves for the other player
- **Warfare**: allows four players to play at the same time, and form alliances as they will
- **Four by Two**: enables players to place two pieces at a time
- **Removal**: enables players to remove a piece as their turn, with up to 5 removals total

## Technical Challenges
Minimax, minimax, minimax. This should have been relatively straightforward, but somehow I botched it. On my first go, my AI either took forever to move or just wasn't very good. (More to follow, after I actually _make_ it good.)

## Visuals
![alt text](https://github.com/ohjay/Connect4/blob/master/demo_imgs/demo_img1.png "Gameplay screens")
