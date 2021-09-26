## Sudoku Generator Service

Spring Boot Project that can generate Sudoku riddles. 

### How to start

SudokuService class contains a lot of useful methods around Sudokus. 
The two most important methods are:

- createFullSudoku: Creates a full Sudoku that fulfills all necessary conditions
- createSudokuRiddle: Can create a riddle based on a full Sudoku

Look at test **testFullSudokuRiddleGeneration** to see the whole process.

### MongoDB

- Installation: https://docs.mongodb.com/manual/installation/
- https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#reference

### PDF Files

- https://www.tutorialspoint.com/pdfbox
- https://github.com/vandeseer/easytable How to render tables with pdfbox

### Next

- Improve Sudoku algorithm, e.g. generate symmetric riddles, stop only 
when a certain number of blank fields was retrieved