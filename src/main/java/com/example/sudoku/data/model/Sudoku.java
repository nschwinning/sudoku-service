package com.example.sudoku.data.model;

import org.springframework.data.annotation.Id;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Sudoku {
	
	  @Id
	  private String id;
	  
	  private int[][] grid;

}
