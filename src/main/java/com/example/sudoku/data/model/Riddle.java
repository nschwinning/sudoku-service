package com.example.sudoku.data.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Riddle {

	@Id
	private String id;

	private int[][] grid;

	private int numberOfZeroes;
	
	@DBRef
	private Sudoku sudoku;

}
