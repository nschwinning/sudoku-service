package com.example.sudoku.job;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.sudoku.data.model.Riddle;
import com.example.sudoku.data.model.Sudoku;
import com.example.sudoku.data.repository.RiddleRepository;
import com.example.sudoku.data.repository.SudokuRepository;
import com.example.sudoku.service.SudokuGeneratorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@ConditionalOnProperty(prefix = "sudoku.config.scheduler", value = "enabled", havingValue = "true")
@RequiredArgsConstructor
@Component
@Slf4j
public class SudokuGeneratorJob {
	
	private final SudokuGeneratorService sudokuService;
	private final SudokuRepository sudokuRepository;
	private final RiddleRepository riddleRepository;
	
    @Scheduled(cron = "0 * * * * *")
    public void generateSudokuAndStoreInDatabase() {
    	int[][] grid = sudokuService.createFullSudoku();
    	
    	Sudoku sudoku = Sudoku.builder()
    			.grid(grid)
    			.build();
    	sudokuRepository.save(sudoku);
    	log.info("Successfully saved Sudoku to database");
    }
    
    @Scheduled(cron = "1 * * * * *")
    public void generateRiddlesAndStoreInDatabase() {
    	List<Sudoku> allSudokus = sudokuRepository.findAll();
    	List<Riddle> riddles = allSudokus.stream().map(sudoku -> createRiddle(sudoku)).collect(Collectors.toList());
    	riddleRepository.saveAll(riddles);
    	log.info("Successfully created {} new riddles", riddles.size());
    }

	private Riddle createRiddle(Sudoku sudoku) {
		int[][] grid = sudokuService.createSudokuRiddle(sudoku.getGrid(), 100);
		int numberOfZeroes = sudokuService.countOccurrencesOfValueInGrid(grid, 0);
		
		return Riddle.builder()
				.grid(grid)
				.numberOfZeroes(numberOfZeroes)
				.sudoku(sudoku)
				.build();
	}

}
