## Sudoku Generator Service

Spring Boot Project that can generate Sudoku riddles. 

### How to start

SudokuService class contains a lot of useful methods around Sudokus. 
The two most important methods are:

- createFullSudoku: Creates a full Sudoku that fulfills all necessary conditions
- createSudokuRiddle: Can create a riddle based on a full Sudoku

Look at test **testFullSudokuRiddleGeneration** to see the whole process.

### Next

- Run Scheduler to generate full Sudoku grids and store them in MongoDB
- Run Scheduler to generate Sudoku riddles and store them in MongoDB with 
foreign key of full Sudoku and number of blank fields 
- Create HTTP endpoint to retrieve random Sudoku riddle 
- Improve Sudoku algorithm, e.g. generate symmetric riddles, stop only 
when a certain number of blank fields was retrieved