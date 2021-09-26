package com.example.sudoku.data.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sudoku.data.model.Sudoku;

public interface SudokuRepository extends MongoRepository<Sudoku, String> {
	
}
