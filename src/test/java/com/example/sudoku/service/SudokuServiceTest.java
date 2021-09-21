package com.example.sudoku.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SudokuServiceTest {
	
	@Autowired
	private SudokuService sudokuService;
	
	@Test
	public void testFullSudokuRiddleGeneration() {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		int[][] grid = sudokuService.createFullSudoku();
		
		log.info("Found full Sudoku:");
		sudokuService.printArray(grid);
		
		//Use higher number of attempts to retrieve more blank fields
		int[][] riddle = sudokuService.createSudokuRiddle(grid, 20);
		stopWatch.stop();
		log.info("Sudoku riddle with {} blank fields:", sudokuService.countOccurrencesOfValueInGrid(riddle, 0));

		sudokuService.printArray(riddle);

		log.info("Complete process took {} seconds", stopWatch.getTotalTimeSeconds());
		
	}
	

	@Disabled
	@Test
	public void testSubArray() {
		int[][] grid = initGridTest();
		int[][] sub = sudokuService.get2DSubArray(grid, 6, 9, 6, 9);
		sudokuService.printArray(sub);

	}
	
	@Disabled
	@Test
	public void testGetSquare() {
		int[][] grid = initGridTest();
		sudokuService.printArray(grid);
		int[][] square = sudokuService.getSquare(0, 8, grid);
		sudokuService.printArray(square);

	}
	
	@Disabled
	@Test
	public void testFillGrid() {
		int[][] grid = sudokuService.initGrid();
		sudokuService.fillGrid(grid);
		sudokuService.printArray(grid);
		assertThat(sudokuService.checkFullSudoku(grid)).isTrue();
	}
	
	@Disabled
	@Test
	public void testSudokuRiddle() {
		int[][] grid = initFullSudoku();

		assertThat(sudokuService.checkFullSudoku(grid)).isTrue();
		
		int[][] riddle = sudokuService.createSudokuRiddle(grid, 5);
		log.info("Sudoku riddle with {} blank fields:", sudokuService.countOccurrencesOfValueInGrid(riddle, 0));
		sudokuService.printArray(riddle);
	}
	
	@Disabled
	@Test
	public void testHasSolutionForGivenConfig() {
		int[][] grid = initSolvableSudoku2();
		
		assertThat(sudokuService.hasSolutionForFixedStartConfig(grid)).isTrue();
	}
	
	
	private int[][] initGridTest() {
		int[][] grid = new int[9][9];
		
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				int index = i*9 + j + 1;
				grid[i][j] = index;
			}
		}
		return grid;
	}
	
	private int[][] initGridRandom() {
		Random random = new Random();
		int[][] grid = new int[9][9];
		
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				int index = random.nextInt(8)+1;
				grid[i][j] = index;
			}
		}
		return grid;
	}
	
	
	private int[][] initFullSudoku() {
		int[][] grid = new int[9][9];

		int[] row1 = new int[] {8,3,2,1,9,5,6,4,7};
		int[] row2 = new int[] {6,7,1,3,8,4,5,9,2};
		int[] row3 = new int[] {4,9,5,2,7,6,8,1,3};
		int[] row4 = new int[] {3,1,9,8,6,7,4,2,5};
		int[] row5 = new int[] {7,4,8,5,2,1,3,6,9};
		int[] row6 = new int[] {5,2,6,4,3,9,1,7,8};
		int[] row7 = new int[] {1,6,3,9,5,2,7,8,4};
		int[] row8 = new int[] {2,8,4,7,1,3,9,5,6};
		int[] row9 = new int[] {9,5,7,6,4,8,2,3,1};
		
		grid[0] = row1;
		grid[1] = row2;
		grid[2] = row3;
		grid[3] = row4;
		grid[4] = row5;
		grid[5] = row6;
		grid[6] = row7;
		grid[7] = row8;
		grid[8] = row9;
		
		return grid;
		
	}
	
	
	private int[][] initSolvableSudoku() {
		int[][] grid = new int[9][9];

		int[] row1 = new int[] {0,6,0,0,0,0,0,4,0};
		int[] row2 = new int[] {7,0,0,0,0,0,0,0,6};
		int[] row3 = new int[] {0,0,0,2,6,1,0,0,0};
		int[] row4 = new int[] {0,0,1,0,4,0,8,0,0};
		int[] row5 = new int[] {0,0,7,9,0,8,1,0,0};
		int[] row6 = new int[] {0,0,9,0,7,0,5,0,0};
		int[] row7 = new int[] {0,0,0,5,2,3,0,0,0};
		int[] row8 = new int[] {4,0,0,0,0,0,0,0,7};
		int[] row9 = new int[] {0,8,0,0,0,0,0,3,0};
		
		grid[0] = row1;
		grid[1] = row2;
		grid[2] = row3;
		grid[3] = row4;
		grid[4] = row5;
		grid[5] = row6;
		grid[6] = row7;
		grid[7] = row8;
		grid[8] = row9;
		
		return grid;
		
	}
	
	private int[][] initSolvableSudoku2() {
		int[][] grid = new int[9][9];

		int[] row1 = new int[] {0,0,2,0,0,0,6,4,0};
		int[] row2 = new int[] {6,0,1,0,0,0,0,0,2};
		int[] row3 = new int[] {0,9,5,2,0,6,0,0,3};
		int[] row4 = new int[] {0,1,9,0,0,7,4,0,0};
		int[] row5 = new int[] {0,4,0,0,2,1,3,0,0};
		int[] row6 = new int[] {0,0,0,4,0,9,1,0,8};
		int[] row7 = new int[] {0,0,3,9,0,0,7,0,0};
		int[] row8 = new int[] {2,0,0,0,1,3,9,0,0};
		int[] row9 = new int[] {9,5,7,0,0,8,2,0,0};
		
		grid[0] = row1;
		grid[1] = row2;
		grid[2] = row3;
		grid[3] = row4;
		grid[4] = row5;
		grid[5] = row6;
		grid[6] = row7;
		grid[7] = row8;
		grid[8] = row9;
		
		return grid;
		
	}

	
}
