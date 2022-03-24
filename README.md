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
There are no known bugs or issues with the terminal display. Output from solver should match
with output provided by instructor.

#### Functionality:
There are no known bugs or issues with the functionality of the terminal solver. Solver
passes every example test case given by the instructor as well as a variety of other test
cases.

### GUI Scrabble Game:

