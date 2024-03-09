
# CS611-Assignment 3
## Name of Assignment
---------------------------------------------------------------------------
Name: Tony Cen, Michelle Sun
Email: tcen17@bu.edu, mlxs@bu.edu
Student ID: U30361395, U14824452

## Files
---------------------------------------------------------------------------

Main.java: This class is used to initalize the GameManager.
GameManager.java: The hub for users to indicate which game to play.
Factory.java: This class is used to initalize different games.
IO.java: This class provides the IO class for any game, simple input validation done here.
Game.java: This abstract class holds the logic to play any general game.
Board.java: This abstract class holds the logic board for any general game.
Tile.java: This class provides the tile structure, what the board is made of.
Piece.java: This class provides the piece structure, holds the values of piece(s).
Team.java: This class provides the team structure, holds the values of team(s).
    - Team stats and a List of player objects
Player.java: This class provides a place to house player information: player name.
Color.java: This class provides all possible the colors for any game.
Constants.java: This class provides all global constants for any game.

Java files for specific games:
SliderGame.java: This class provides the Sliding Window game's structure and logic.
Generate.java: This class provides the Sliding Window game's puzzle generation.
SliderBoard.java: This class provides the Sliding Window game's board structure: board elements, enforce board bounds, etc.
BoxGame.java: This class provides the Dots and Boxes game's structure and logic.
BoxBoard.java: This class provides the box board structure: board elements, enforce board bounds, etc.
QuoridorGame.java: This class provides the Quoridor game's structure and logic
QuoridorBoard.java: This class provides the the quoridor board structure: board elements, placeWall, movePiece, etc.
    - This extends from BoxBoard
QuoridorTiles.java: This class implements Tile class and provides the specific tiles for Quoridor board.

## Notes (No diary this time, collaboration notes instead)
---------------------------------------------------------------------------
NOTE!!!: the ANSI color of yellow used for walls is very similar to white in the terminal, so please squint HARD!

2/22 - Tony and I have our first, and only meeting for the project. We breakdown (very loosely) what methods should be used and how \
the logic should flow between each class. We decided to use my implementatino as the base program we build off of.
2/25 - I forgor to add my UML so Tony can understand my code's logic without staring at it for a millenium.
2/26 - I start setting up for Quoridor: Made a Constatns, Quoridor Board and Game, as well as Quoridor Tiles as Quoridor needs more \
specific methods then regular Tiles.
2/27a - Looking at Tony's implementation, I find that having a more generic IO class with basic queries to integer, string, or boolean \
would be better for the overall games the IO will go into.
2/27b - (Lies) I completed isWin and Player input methods for the Quoridor game. I did not, I just gave pseudocode. 
(I did change this later.)
3/1a - Expanded my "more generic IO" changes to all other games in file. 
3/1b to 3/6 - Tony pushed his work for the project, that is, updating the Player-Team to be more specific, as well as the move checking \
logic for the Quoridor game. This also means implementing the BFS part of the game.
3/8 - Tony and I went through the code and started debugging the game. I connected Tony's methods and other creations to my base program \
and tried to help with debugging. Though, ultimately, Tony did most of the debugging because I cannot read c,:
3/9 - I am now finishing up the documentation for the whole program and giving the program a wholistic run through to make sure no bugs. \
It's time to submit!

## Citaions
---------------------------------------------------------------------------
https://datawookie.dev/blog/2019/04/sliding-puzzle-solvable/ was used to understand how to generate the Puzzle.

https://www.digitalocean.com/community/tutorials/shuffle-array-java was used to understand array shuffle in java. 
\Used Collections java library.

https://www.digitalocean.com/community/tutorials/java-list and https://docs.oracle.com/javase/8/docs/api/java/util/List.html \
was used to understand list in java better.

https://www.codeproject.com/Articles/5329247/How-to-Change-Text-Color-in-a-Linux-Terminal used for color.

https://www.themindcafe.sg/wp-content/uploads/2018/04/Quoridor.pdf used to understand the rules of Quoridor,
and aid in implementing the movePawn() and placeWall() methods

My brain, used for getting each piece's counterpart piece. (My brain is perpetually fried.)

Combined brains, but mostly Tony's brain. Used for remodeling the IO system and piece/wall validation for Quoridor Game.

Note from Tony: the template for BFS came from the work I did in CS440

Tabnine was used for ease of coding. (Repeating Getter and Setters methods, Autofill comments, etc.)

## How to compile and run
---------------------------------------------------------------------------

1. Navigate to the directory "HW3" after unzipping the files
    1a. If there is no such directory, then just make sure you are outside of src.
2. Run the following instructions:
javac -d bin src/Main.java
cd bin
java src.Main

## Input/Output Example
---------------------------------------------------------------------------
(Please note, this readme does not show the colors (I don't know why), but in terminal there is color.)

Output:
[>] Welcome!
[>] Select a game:
[>] 1. Slider Game
[>] 2. Box Game
[>] 3. Quoridor Game
[>] 4. Quit
[>] Enter your choice: 
Input:
0
Output:
[>] Invalid input. 
[>] Enter your choice: 
Input:
1
Output:
[>] Time to make some team(s)!
[>] Come up team 1!
[>] Enter your team name: 
Input:
Funky
Output:
[>] Enter your team color: 
Input:
cyan
Output:
[>] Invalid color. Please try again.
[>] Here are the valid colors: [RED, BLUE, PURPLE]
[>] Enter your team color: 
Input:
blue
Output:
[>] Let's get some team member names!
[>] Come forward team player 1.
[>] Enter your name:  
Input:
Bob
Output:
[>] Welcome to the Sliding Window Game! Let's begin.
[>] Enter the board width (max 9, min 2): 
Input:
2
Output:
[>] Enter the board height (max 9, min 2): 
Input:
2
Output:
3	1	
2	0	
[>] Enter the piece would you like to move or enter -1 to quit: 
Input:
3
Output:
[>] Invalid move. Please try again.
3	1	
2	0	
[>] Enter the piece would you like to move or enter -1 to quit: 
Input:
2
Output:
3	1	
0	2	
[>] Enter the piece would you like to move or enter -1 to quit: 
Input:
3
Output:
0	1	
3	2	
[>] Enter the piece would you like to move or enter -1 to quit: 
Input:
1
Output:
1	0	
3	2	
[>] Enter the piece would you like to move or enter -1 to quit: 
Input:
2
Output:
1	2	
3	0	
[>] Congratulations, Funk! You solved the puzzle!
[>] Do you want to quit or restart? (q/r): 
Input:
r
Output:
[>] Do you want change board size? (y/n):
Input:
y
Output:
[>] Enter the board width (max 9, min 2): 
Input:
10
Output:
[>] Invalid input. 
[>] Enter the board width (max 9, min 2): 
Input:
5
Output:
[>] Enter the board height (max 9, min 2): 
Input:
9
Output:
9	44	36	11	41	
1	15	26	25	33	
39	31	28	32	14	
43	7	6	30	8	
40	29	27	12	34	
5	20	2	13	19	
21	22	35	23	42	
24	3	16	10	4	
38	37	18	17	0	
[>] Enter the piece would you like to move or enter -1 to quit: 
Input:
-1
Output:
[>] Quitting game...
[>] We have a quitter :D
[>] Do you want to quit or restart? (q/r): 
Input:
q
Output:
[>] Thanks for playing Sliding Window Game! Here were the stats for this game session: 
[>] Wins: 1
[>] Total Moves: 4
[>] Total Games: 1
[>] Players: 
Bob  
[>] Welcome!
[>] Select a game:
[>] 1. Slider Game
[>] 2. Box Game
[>] 3. Quoridor Game
[>] 4. Quit
[>] Enter your choice: 
Input: 
2
Output:
[>] How many team members would you like to have? You can have at most 5.
[>] Enter the number of team members: 
Input: 
3
Output:
[>] Time to make some team(s)!
[>] Come up team 1!
[>] Enter your team name: 
Input:
Funky
Output: 
[>] Enter your team color: 
Input:
blue
Output:
[>] Let's get some team member names!
[>] Come forward team player 1.
[>] Enter your name: 
Input:
Cinnamon
Output:
[>] Come forward team player 2.
[>] Enter your name:
Input:
Toast
Output:
[>] Come forward team player 3.
[>] Enter your name: 
Input:
Crunch
Output:
[>] Come up team 2!
[>] Enter your team name: 
Input:
Baby
Output:
[>] Enter your team color:
Input:
blue
Output:
[>] Invalid color. Please try again.
[>] Here are the valid colors: [RED, PURPLE]
[>] Enter your team color: 
Input:
red 
Output:
[>] Let's get some team member names!
[>] Come forward team player 1.
[>] Enter your name:
Input:
Candy
Output:
[>] Come forward team player 2.
[>] Enter your name:
Input:
Tammy
Output:
[>] Come forward team player 3.
[>] Enter your name:
Input:
Cabby
Output:
[>] Welcome to the Dots and Boxes Game!
[>] Enter the board width (max 9, min 2): 
Input:
2
Output:
[>] Enter the board height (max 9, min 2): 
Input:
2
Output:
[>] Lets start with team Funk!
[>] Now it's Cinnamon's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit:
Input:
3
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
0
Output:
[>] Now team Baby!
[>] Now it's Candy's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
5
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input: 
0
Output:
[>] Invalid move. Please try again.
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
1
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
2
Output:
[>] Invalid move. Please try again.
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
0
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
1
Output:
[>] Now team Funky!
[>] Now it's Toast's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
0
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
2
Output:
[>] Now team Baby!
[>] Now it's Tammy's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
0
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
3
Output:
[>] Now team Funky!
[>] Now it's Crunch's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
0
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
0
Output:
[>] A box was completed successfully!  
[>] As a reward, it's still Crunch's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
1
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
0
Output:
[>] Now team Baby!
[>] Now it's Cabby's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
1
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
1
Output:
[>] A box was completed successfully!  
[>] As a reward, it's still Cabby's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
3
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
1
Output:
[>] Now team Funky!
[>] Now it's Cinnamon's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
3
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
2
Output:
[>] Now team Baby!
[>] Now it's Candy's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
2
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
3
Output:
[>] Now team Funky!
[>] Now it's Toast's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
2
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
2
Output:
[>] Now team Baby!
[>] Now it's Tammy's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
3
Output:
[>] Enter the piece you want (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
3
Output:
[>] A box was completed successfully! 
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Game Over! Baby wins!
[>] Do you want to quit or restart? (q/r): 
Input:
r
Output:
[>] Do you want change board size? (y/n): 
Input:
n
Output:
[>] Lets start with team Baby!
[>] Now it's Tammy's turn!
o-----o-----o
|     |     |
o-----o-----o
|     |     |
o-----o-----o
[>] Enter the tile you want (Starting with tile 0) or enter -1 to quit: 
Input:
-1
Output:
[>] Quitting game...
[>] Did one of the teams rage quit? WHOOPS XD
[>] Do you want to quit or restart? (q/r): 
Input:
q
Output:
[>] Thanks for playing Dots and Boxes!
[>] Here is for team Funky:
[>] Wins: 0
[>] Total Moves: 6
[>] Total Games: 1
[>] Players: 
Cinnamon  Toast  Crunch  
[>] Here is for team Baby:
[>] Wins: 1
[>] Total Moves: 6
[>] Total Games: 1
[>] Players: 
Candy  Tammy  Cabby  
[>] Welcome!
[>] Select a game:
[>] 1. Slider Game
[>] 2. Box Game
[>] 3. Quoridor Game
[>] 4. Quit
[>] Enter your choice: 
Input:
3
Output:
[>] How many team members would you like to have? You can have at most 5.
Input:
1
Output:
[>] Time to make some team(s)!
[>] Come up team 1!
[>] Enter your team name: 
Input:
1
Output:
[>] What is your team color? 
Input:
red
Output:
[>] Let's get some team member names!
[>] Come forward team player 1.
[>] What is your name? 
Input:
1
Output:
[>] Come up team 2!
[>] Enter your team name: 
Input:
2
Output:
[>] What is your team color? 
Input:
blue
Output:
[>] Let's get some team member names!
[>] Come forward team player 1.
[>] What is your name? 
Input:
2
Output:
[>] Welcome to the Quoridor Game!
[>] Enter the board width (max 9, min 2): 
Input:
2
Output:
[>] Enter the board height (max 9, min 2): 
Input:
2
Output:
[>] Enter on which column your player piece starts: (0, 1)
Input:
1
Output:
[>] Enter on which column your player piece starts: (0, 1)
Input:
0
Output:
[>] Lets start with team 1!
[>] Now it's RED Team Player 1's turn!
o-----o-----o
| 0   | 1*  |
o-----o-----o
| 2   | 3   |
o-----o-----o
[>] Enter your choice of move, team piece or wall: (p/w): 
Input:
p
Output:
[>] Enter which tile you want to move your team piece: 
Input:
3
Output:
o-----o-----o
| 0   | 1   |
o-----o-----o
| 2   | 3   |
o-----o-----o
[>] Game Over! 1 wins!
[>] Do you want to restart or quit?(r/q): 
Input:
r
Output:
[>] Do you want change board size?(y/n): 
Input:
y
Output:
[>] Enter on which column your player piece starts: (0, 1)
Input:
0
Output:
[>] Enter on which column your player piece starts: (0, 1)
Input:
1
Output:
[>] Lets start with team 1!
[>] Now it's RED Team Player 1's turn!
o-----o-----o
| 0*  | 1   |
o-----o-----o
| 2   | 3   |
o-----o-----o
[>] Enter your choice of move, team piece or wall: (p/w): 
Input:
w
Output:
[>] Please enter your choice of tile: 
Input:
2
Output:
[>] Enter the direction you want your wall to face (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
0
Output:
[>] Enter the direction you want your wall to extend (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
1
Output:
[>] Invalid move. Please try again.
o-----o-----o
| 0*  | 1   |
o-----o-----o
| 2   | 3   |
o-----o-----o
[>] Enter your choice of move, team piece or wall: (p/w): 
Input:
w
Output:
[>] Please enter your choice of tile: 
Input:
2
Output:
[>] Enter the direction you want your wall to face (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
3
Output:
[>] Enter the direction you want your wall to extend (north=0, east=1, south=2, west=3) or enter -1 to quit: 
Input:
0
Output:
[>] Now team 2!
[>] Now it's 2's turn!
o-----o-----o
| 0   | 1   |
o-----o-----o
| 2   | 3*  |
o-----o-----o
[>] Enter your choice of move, team piece or wall: (p/w): 
Input:
p
Output:
[>] Enter which tile you want to move your team piece: 
Input:
-1
Output:
[>] Did one of the teams rage quit? WHOOPS XD
[>] Do you want to restart or quit?(r/q): 
Input:
q
Output:
[>] Thanks for playing Quoridor!
[>] Here is for team 1:
[>] Wins: 0
[>] Total Moves: 1
[>] Total Games: 0
[>] Players: 
1  
[>] Here is for team 2:
[>] Wins: 1
[>] Total Moves: 1
[>] Total Games: 0
[>] Players: 
2  
[>] Select a game:
[>] 1. Slider Game
[>] 2. Box Game
[>] 3. Quoridor Game
[>] 4. Quit
[>] Enter your choice: 
Input:
4
Output:
[>] Thanks for playing! Goodbye!
