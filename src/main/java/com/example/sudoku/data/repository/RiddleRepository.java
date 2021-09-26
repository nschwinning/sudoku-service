package com.example.sudoku.data.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.sudoku.data.model.Riddle;

public interface RiddleRepository  extends MongoRepository<Riddle, String> {
	
	List<Riddle> findAllByNumberOfZeroes(int numberOfZeroes);

}
