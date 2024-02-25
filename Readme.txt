
# CS611-Assignment 2
## Name of Assignment
---------------------------------------------------------------------------
Name: Michelle Sun
Email: mlxs@bu.edu
Student ID: U14824452

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

Java files for specific games:
SliderGame.java: This class provides the Sliding Window game's structure and logic.
Generate.java: This class provides the Sliding Window game's puzzle generation.
SliderBoard.java: This class provides the Sliding Window game's board structure: board elements, enforce board bounds, etc.
BoxGame.java: This class provides the Dots and Boxes game's structure and logic.
BoxBoard.java: This class provides the box board structure: board elements, enforce board bounds, etc.
BoxIO.java: This class extends the I/O class for any game, but adds specific behaviour for the Dots and Boxes game.

## Notes (I used this kind of like a diary)
---------------------------------------------------------------------------

2/14a: Starting off, I'm thinking of ways to abstract out my previous code so that the Box game can exist.
2/14b: Given that the stuff in SliderBoard don't exist on every board (generating puzzle), I made a new, more general Board.java
2/16a: So the plan is to have a Game.java that holds everything a general game should logically have. 
2/16b: Remembering that the user shoud be able to play any game, I'm thinking of adding a "GameManager" init any given game.
2/16c: I'm having a hard time getting a good design for this program. Having troubles connecting Board to Game.
2/16d: I've decided, implementing a design pattern right now is too much for my puny brain, I will stick to abstract classes and \
confined methods.
2/17a: I think the last couple days I was overwhelmed with the amount of thing I needed to do so I just dived into a deep and confusing \
rabbit hole.
2/17b: I started by trying to correct the formatting for the abstract class Board to be able to create both SliderBoard and BoxBoard.
2/18a: I'm started looking at the Game.java that extends to SliderGame and BoxGame, I realized that I need to make a Team.java and \
change the previous Player.java. I also added methods to IO to prompt for team info.
2/18b: Okay, I've got everything loosely wired together now. I have debugged most of the SliderGame and will continue to make sure it is \
tip-top shape. After that, I will be debugging BoxGame. I don't think I will have the energy to abstract this more right now, but I can see a \
Factory class for this code. 
2/19a: Finished debugging SliderGame, also I forgot to add a way to quit from the game in the middle of the game, so I will add that now.
2/19b: I learned how to use one line if-statements! Pretty cool. I am still trying to debug BoxGame, I think I need to rest the brain for a \
bit before I can continue. Hopefully tomorrow will be the last day. (Update: That day was not the last day :,D)
2/20a: Realized there was a problem with my isValidMove method, it only works on East-West facing tiles.
2/20b: I learned that the Teams shouldn't be just two teams, but multiple. I can cap it at how many colors there are, so in my case, 5.
2/20c: Yes.. Things are working correctly. Finally, I'm almost done finally. 
2/20d: I will like to say, my SliderGame doesn't implement color, because color is for inidicating Teams right now. \
Totally not becuase it isn't of the highest priority right now.
2/20e: I learned that I could just have multiple ppls in a team, so I'll do that instead. Just kinda have the forloop go through the players.
2/20f: Please, I want to stop looking at this code soon, I'm so sleepy ;-; I should finish the readme and code checking by end of tomorrow. 
2/21a: Okay I think most things are done. I will check through the execution and then add some headers.
2/21b: I last minute decided I should have a Factory.java since it really doesn't make sense to have the GameManager make the board games. \
Especially since the GameManager's sole job is to communicate with users about what game they want to play.
2/21c: Really last minute stuff, I realized that although my program was working, it seems to display the exact opposite board length to what \
I wanted. For example, choosing width 2 and height 5 would give me a board that was 2 tall 5 wide. I've made the corrections to have it work now.\  
At some point while fixing this, I felt like "oh maybe I should just say in notes that when I say width I mean the opposite" but I mean.. I \
think I've fixed the program so everything should work completely fine. I'm just a bit scared since I found this error extremely last minute. \
I hope for the best.

## Citaions
---------------------------------------------------------------------------
https://datawookie.dev/blog/2019/04/sliding-puzzle-solvable/ was used to understand how to generate the Puzzle.

https://www.digitalocean.com/community/tutorials/shuffle-array-java was used to understand array shuffle in java. 
\Used Collections java library.

https://www.digitalocean.com/community/tutorials/java-list and https://docs.oracle.com/javase/8/docs/api/java/util/List.html \
was used to understand list in java better.

https://www.codeproject.com/Articles/5329247/How-to-Change-Text-Color-in-a-Linux-Terminal used for color.

My brain, used for getting each piece's counterpart piece. (My brain is perpetually fried.)

Tabnine was used for ease of coding. (Repeating Getter and Setters methods, Autofill comments, etc.)

## How to compile and run
---------------------------------------------------------------------------

1. Navigate to the directory "HW2" after unzipping the files
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
[>] 3. Quit
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
[>] Here are the valid colors: [RED, GREEN, BLUE, PURPLE]
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
[>] Select a game:
[>] 1. Slider Game
[>] 2. Box Game
[>] 3. Quit
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
[>] Here are the valid colors: [RED, GREEN, PURPLE]
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
[>] Select a game:
[>] 1. Slider Game
[>] 2. Box Game
[>] 3. Quit
[>] Enter your choice: 
Input:
3
Output:
[>] Thanks for playing! Goodbye!
