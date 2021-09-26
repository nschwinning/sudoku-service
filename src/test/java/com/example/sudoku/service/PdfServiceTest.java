package com.example.sudoku.service;

import static com.example.sudoku.constants.ApplicationConstants.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PdfServiceTest {
	
	@Autowired
	private PdfService pdfService;
	
	@Autowired
	private SudokuGeneratorService sudokuService;
	
	@Test
	public void testGeneratePdfBlueColored() throws Exception {
		int[][] grid = sudokuService.createFullSudoku();
		int[][] riddle = sudokuService.createSudokuRiddle(grid, 20);

		
		pdfService.createPdf(riddle, PADDING, BLUE_LIGHT_1, BLUE_LIGHT_2, Color.WHITE, BLUE_DARK);
		
	}
	
	@Test
	public void testGeneratePdfClassicalColors() throws Exception {
		int[][] grid = sudokuService.createFullSudoku();
		int[][] riddle = sudokuService.createSudokuRiddle(grid, 20);

		
		pdfService.createPdf(riddle, PADDING, Color.WHITE, Color.WHITE, Color.GRAY, Color.BLACK);
		
	}

}
