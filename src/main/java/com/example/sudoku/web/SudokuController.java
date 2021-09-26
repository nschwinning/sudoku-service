package com.example.sudoku.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sudoku.data.model.Sudoku;
import com.example.sudoku.service.SudokuService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class SudokuController {
	
	private final SudokuService sudokuService;

	@GetMapping("/api/sudoku/random")
	public ResponseEntity<Sudoku> getRandomSudoku() {
		return ResponseEntity.ok(sudokuService.getRandomSudoku());
	}
	
}
