package com.example.sudoku.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@ConditionalOnProperty(prefix = "sudoku.config.scheduler", value = "enabled", havingValue = "true")
@EnableScheduling
@Configuration
public class SchedulingConfiguration {

}
