package com.jobsity.bowling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.jobsity.bowling.dao.BowlingGameInputReaderFile;
import com.jobsity.bowling.domain.BowlingScore;
import com.jobsity.bowling.formatter.BowlingScoreStandardOutputFormatter;
import com.jobsity.bowling.service.BowlingGameService;

@SpringBootApplication
public class BowlingApplication {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BowlingApplication.class, args);
		BowlingGameService bowlingGameService = (BowlingGameService) ctx.getBean("bowlingGameServiceImpl");
		System.out.println(execute(bowlingGameService, args[0]));
	}

	public static String execute(BowlingGameService bowlingGameService, String inputFilePath) {
		BowlingGameInputReaderFile bowlingGameInputReaderFile = new BowlingGameInputReaderFile(inputFilePath);
		BowlingScore bowlingScore = bowlingGameService.processScore(bowlingGameInputReaderFile);
		String output = BowlingScoreStandardOutputFormatter.format(bowlingScore);
		return output;
	}
}