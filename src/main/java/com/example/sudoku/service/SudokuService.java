package com.example.sudoku.service;

import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.sudoku.data.model.Sudoku;
import com.example.sudoku.data.repository.SudokuRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SudokuService {
	
	private Random random = new Random();
	private final SudokuRepository sudokuRepository;
	
	public Sudoku getRandomSudoku() {
		List<Sudoku> allSudokus = sudokuRepository.findAll();
		return allSudokus.get(random.nextInt(allSudokus.size()));
	}

}
