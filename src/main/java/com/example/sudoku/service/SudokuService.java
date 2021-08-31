package com.example.sudoku.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SudokuService {

	List<Integer> numberList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	List<Integer> orderedNumberList = Collections.unmodifiableList(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
	int counter = 0;
	
	public void fillGrid(int[][] grid) {

		int i=0,k=0;
		while (i < 81) {

			int row = Math.floorDiv(i, 9);
			int col = i % 9;
			boolean foundValue = false;
			

			if (grid[row][col] == 0) {
				if (k==0) {
					Collections.shuffle(numberList);
				}

				for (int j=k; j<numberList.size(); j++) {
					int value = numberList.get(j);
					if (!rowContainsValue(grid, row, value) && !columnContainsValue(grid, col, value) && !squareContainsValue(grid, row, col, value)) {
						grid[row][col] = value;
						i++;
						k=0;
						foundValue = true;
						break;
					}
					else {
						foundValue = false;
					}
				}
				
				if (!foundValue) {
					i--;
					k = numberList.indexOf(getValueAtPosition(grid, i))+1;
					resetValue(grid, i);
				}
			}
			else {
				i++;
			}
			
		}
		log.info("Found Sudoku");
	}
	
	@SneakyThrows
	public int[][] createSudokuRiddle(int[][] fullSudoku) {
		//1. Remove value randomly and backup
		//2. Check if other value can be put into field
		//3. If yes, try to solve Sudoku without stepping back
		Random random = new Random();
		int attempts = 2;
		while (attempts > 0) {
			Thread.sleep(500L);
			log.info("Riddle has {} zeroes", countOccurrencesOfValueInGrid(fullSudoku, 0));
			printArray(fullSudoku);
			int row = random.nextInt(9);
			int col = random.nextInt(9);
			int[][] clonedSudoku = cloneGrid(fullSudoku);
			if (clonedSudoku[row][col] != 0) {
				int backup = clonedSudoku[row][col];
				clonedSudoku[row][col] = 0;
				boolean foundValue = false;
				for (int value:orderedNumberList) {
					if (value!=backup && valueIsPossible(clonedSudoku, value, row, col)) {
						log.info("Value {} is possible at row {} and col {}", value, row, col);
						clonedSudoku[row][col] = value;
						if (hasSolution(clonedSudoku)) {
							//More than one solution was found
							log.info("Has more than one solution. Cannot set value at row {} and col {} to 0.", row, col);
							attempts--;
							foundValue=true;
							break;
						}
					}
				}
				if(!foundValue) {
					log.info("Solution is still unique. Setting value at row {} and col {} to 0.", row, col);
					fullSudoku[row][col] = 0;
				}
			}
		}
			
		return fullSudoku;
	}
	

	private List<Integer> findZeroIndexes(int[][] grid) {
		List<Integer> result = new ArrayList<>();
		for (int i=0;i<81;i++) {
			int row = Math.floorDiv(i, 9);
			int col = i % 9;
			
			if (grid[row][col] == 0) {
				result.add(i);
			}
		}
			
		return result;
	}
	
	private boolean hasSolution(int[][] grid) {
		int i=0;
		while (i<81) {
			int row = Math.floorDiv(i, 9);
			int col = i % 9;
			
			if (grid[row][col] == 0) {
				for (int value:orderedNumberList) {
					if (!rowContainsValue(grid, row, value) && !columnContainsValue(grid, col, value) && !squareContainsValue(grid, row, col, value)) {
						grid[row][col] = value;
						if (!squareContainsValue(grid, 0)) {
							return true;
						}
						else {
							i++;
							break;
						}
					}
				}
				if (grid[row][col] == 0) {
					return false;
				}
			}
			else {
				i++;
			}
		}
		return false;
	}

	private boolean valueIsPossible(int[][] grid, int value, int row, int col) {
		if (!rowContainsValue(grid, row, value) && !columnContainsValue(grid, col, value) && !squareContainsValue(grid, row, col, value)) {
			return true;
		}
		return false;
	}

	public boolean checkFullSudoku(int[][] grid) {
		if (grid.length!=9) {
			log.info("Number of columns is not equal to 9");
			return false;
		}
		for (int i=0;i<grid.length; i++) {
			if (grid[i].length !=9) {
				log.info("Row {} has length not equal to 9", i);
				return false;
			}
			for (int value:numberList) {
				if (!rowContainsValue(grid, i, value)) {
					log.info("Row {} does not contain value {}", i, value);
					return false;
				}
			}
			for (int value: numberList) {
				if (!columnContainsValue(grid, i, value)) {
					log.info("Column {} does not contain value {}", i, value);
				}
			}
			for (int j=0;j<9; j++) {
				int[][] square = getSquare(i, j, grid);
				for (int value: numberList) {
					if (!squareContainsValue(square, value)) {
						log.info("Square for row {} and column {} does not contain value {}", i, j, value);
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	private void resetValue(int[][] grid, int i) {
		int row = Math.floorDiv(i, 9);
		int col = i % 9;
		grid[row][col] = 0;
	}

	private int getValueAtPosition(int[][] grid, int i) {
		int row = Math.floorDiv(i, 9);
		int col = i % 9;
		return grid[row][col];
	}
	
	private boolean squareContainsValue(int[][] grid, int row, int col, int value) {
		int[][] square = getSquare(row, col, grid);
		return squareContainsValue(square, value);
	}
	
	private boolean squareContainsValue(int[][] square, int value) {
		for (int i=0; i<square.length; i++) {
			for (int j=0; j< square[i].length; j++) {
				if (square[i][j] == value) {
					return true;
				}
			}
		}
		return false;
	}
	
	private int countOccurrencesOfValueInGrid(int[][] grid, int value) {
		int counter = 0;
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j< grid[i].length; j++) {
				if (grid[i][j] == value) {
					counter++;
				}
			}
		}
		return counter;
	}


	private boolean rowContainsValue(int[][] grid, int row, int value) {
		for (int j : grid[row]) {
			if (j == value) {
				return true;
			}
		}
		return false;
	}

	private boolean columnContainsValue(int[][] grid, int col, int value) {
		for (int j = 0; j < grid.length; j++) {
			if (grid[j][col] == value) {
				return true;
			}
		}
		return false;
	}

	public int[][] initGrid() {
		int[][] grid = new int[9][9];

		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[i].length; j++) {
				grid[i][j] = 0;
			}
		}
		return grid;
	}

	public int[][] getSquare(int row, int col, int[][] grid) {
		if (row < 3 && col < 3)
			return get2DSubArray(grid, 0, 3, 0, 3);
		if (row < 3 && col < 6) 
			return get2DSubArray(grid, 0, 3, 3, 6);
		if (row < 3 && col < 9)
			return get2DSubArray(grid, 0, 3, 6, 9);
		if (row < 6 && col < 3)
			return get2DSubArray(grid, 3, 6, 0, 3);
		if (row < 6 && col < 6) 
			return get2DSubArray(grid, 3, 6, 3, 6);
		if (row < 6 && col < 9)
			return get2DSubArray(grid, 3, 6, 6, 9);
		if (row < 9 && col < 3)
			return get2DSubArray(grid, 6, 9, 0, 3);
		if (row < 9 && col < 6) 
			return get2DSubArray(grid, 6, 9, 3, 6);
		if (row < 9 && col < 9)
			return get2DSubArray(grid, 6, 9, 6, 9);
		return null;
	}

	public int[][] get2DSubArray(int[][] origArray, int fromRow, int toRow, int fromColumn, int toColumn) {
		int[][] subArray = new int[toRow - fromRow][];

		for (int i = fromRow; i < toRow; i++) {
			subArray[i - fromRow] = Arrays.copyOfRange(origArray[i], fromColumn, toColumn);
		}

		return subArray;
	}
	
	public int[][] cloneGrid(int[][] grid) {
		int[][] clonedGrid = new int[grid.length][grid.length];
		for (int i=0; i<grid.length; i++) {
			for (int j=0; j<grid[i].length; j++) {
				clonedGrid[i][j] = grid[i][j];
			}
		}
		return clonedGrid;
	}
	
	public void printArray(int[][] grid) {
		for (int i=0; i<grid.length; i++) {
			String s = "[";
			for (int j=0; j<grid[i].length; j++) {
				s += grid[i][j];
				if (j<grid[i].length-1) {
					s += ",";
				}
			}
			s += "]";
			log.info(s);
		}
	}

}
