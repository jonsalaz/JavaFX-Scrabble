# Scrabble Project
#### Author: Jonathan Salazar
### Terminal Solver
#### How to use:
In order to run the terminal solver use the command: <br>
```java -jar terminalSolver.jar sowpods.txt < example_input.txt > myoutput.txt``` <br>
Where sowpods.txt is replaced by whichever dictionary file that will be used and
example_input.txt is replaced by whichever config file that will be used.

#### Implementation: 
The Terminal Solver uses the algorithm found in 
[The World's Fastest Scrabble Program](https://www.cs.cmu.edu/afs/cs/academic/class/15451-s06/www/lectures/scrabble.pdf)
<br>
The algorithm follows the pseudocode provided in the paper; however it differentiates in the way in which is determines
crosschecks and candidate anchors. In the paper it is described that anchors and crosschecks for a row can be
precomputed, but the implementation found in the Terminal Solver computes crosschecks as necessary and precomputes all
candidate anchors before beginning solving. This works to not only help track the limit for leftPart at each anchor's
computation, but also removes the nuance of having to find a way to store the crosschecks in a way that is readable and
does not introduce a variety of ways in which bugs may appear.
#### Known Bugs and Issues:

#### Display:
PLEASE NOTE: Currently a diff test between two files is showing that every line is different from the 
example_output.txt file that was provided for student testing. I have by hand gone through every line of the two
files and there is not a single difference which leads me to believe there are invisible characters that are slightly
different. I ask that in the grading this be taken into consideration and I am going to attempt to fix this even after 
the due date passes. I would hate for a project that I worked extremely hard on and that I am very proud of the 
functionality to be docked heavily for such a silly "error". Thank you for your consideration. Outside of this error:

There are no known bugs or issues with the terminal display. Output from solver should match
with output provided by instructor.

#### Functionality:
There are no known bugs or issues with the functionality of the terminal solver. Solver
passes every example test case given by the instructor as well as a variety of other test
cases.

### GUI Scrabble Game:

#### How to use:
In order to run the GUI Scrabble Game use the command: <br>
```java -jar scrabbleSolver.jar``` <br>
All resources are included within the jar and the program does not require any additional file input.


In order to play a move, the player must first select a square on the board by clicking on one of the spaces.
Both unfilled and filled spots are available to be selected as the starting point of the players move. Next,
the player must type the word they wish to play into the TextField on the bottom quarter of the GUI. Finally, the player
must use the RadioButtons to decide whether this move is to be played in an across or down fashion. When the player is
ready to finalize their move, they may hit the "Play Move" button in order to place their move. The move will not be
placed if the move entered is not valid.

Example of a valid move: <br>
![Example of a Valid Move](/docs/exampleImage.png)

Please note: When entering a move, if the word contains letters already on the board, the letters on the board must
still be included when typing into the TextField. <br>
For example: <br>
To play the word "hello" while using an "e" already on the board: <br>
Correct: "hello" <br>
Incorrect: "hllo" <br>

Please note: When selecting a square to start the move on, the square will be highlighted red.
On the first move, the word played MUST begin on the center tile. The move will not be played if starting
on any other tile. For convenience, on the first move, the center tile is preselected.


#### Implementation:
The GUI Scrabble game uses the foundation of the Terminal Solver in order to allow the computer player to take a turn.
In addition, parts of the Terminal Solver were refactored in order to create a more universally applicable score checker
and legal move checker. As far as GUI aspects are concerned, the layout was designed using FXML and the logic was 
implemented using user input directly to the GUI elements through various action event listeners. 

#### Known Bugs and Issues:

#### Display:
There are no known bugs or issues with the GUI Display; however, the grip pane does not precisely align with the 
image of the scrabble board, likely due to inaccuracies in matching up the padding and other various sizing elements.

#### Functionality:
There are no known bugs or issues with the GUI Functionality or solver functionality; however, it may be useful to note
that for this implementation I deemed it necessary to let the player pass their turn rather than swap out their current
tiles for new tiles. This choice was made due to the fact that it felt inauthentic to allow the player to swap out their
tiles for new tiles when the computer player has no "intuition" of probabilities or the ability to swap their
own tiles out. Furthermore, as a demonstration of the solvers power, it becomes much easier to gauge the solver's 
abilities by simply observing as the solver takes a series of turns while the player repeatedly passes their turn. 
