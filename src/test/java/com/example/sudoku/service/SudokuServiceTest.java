package com.example.sudoku.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class SudokuServiceTest {
	
	@Autowired
	private SudokuService sudokuService;

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
	
	@Test
	public void testFillGrid() {
		int[][] grid = sudokuService.initGrid();
		sudokuService.fillGrid(grid);
		sudokuService.printArray(grid);
		assertThat(sudokuService.checkFullSudoku(grid)).isTrue();
	}
	
	@Test
	public void testSudokuRiddle() {
		int[][] grid = sudokuService.initGrid();
		sudokuService.fillGrid(grid);
		sudokuService.printArray(grid);
		int[][] riddle = sudokuService.createSudokuRiddle(grid);
		log.info("Sudoku riddle");
		sudokuService.printArray(riddle);
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

	
}
