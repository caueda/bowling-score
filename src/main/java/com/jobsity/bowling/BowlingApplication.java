package com.jobsity.bowling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.jobsity.bowling.dao.BowlingGameInputReaderFile;
import com.jobsity.bowling.domain.BowlingScore;
import com.jobsity.bowling.formatter.PinfallFormatter;
import com.jobsity.bowling.formatter.ScoreFormatter;
import com.jobsity.bowling.service.BowlingGameService;

@SpringBootApplication
public class BowlingApplication {
	
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(BowlingApplication.class, args);
		BowlingGameService bowlingGameService = (BowlingGameService) ctx.getBean("bowlingGameServiceImpl");
		BowlingGameInputReaderFile bowlingGameInputReaderFile = new BowlingGameInputReaderFile(args[0]);
		BowlingScore bowlingScore = bowlingGameService.processScore(bowlingGameInputReaderFile);
		
		System.out.println();
		System.out.print("Frame");
		for(int frame=1; frame<=10; frame++) {
			System.out.print("  " + frame);
		}
		System.out.println();
		bowlingScore.entrySet().stream().forEach(entry -> {
			System.out.println(entry.getKey());
			System.out.print("Pinfalls");
			entry.getValue().getGameFrames().stream().forEach(PinfallFormatter::print);
			System.out.println();
			System.out.print("Score ");
			entry.getValue().getGameFrames().stream().forEach(ScoreFormatter::print);
		});
	}

}
