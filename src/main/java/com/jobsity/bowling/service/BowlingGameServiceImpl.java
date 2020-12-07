package com.jobsity.bowling.service;

import java.io.Serializable;

import org.springframework.stereotype.Service;

import com.jobsity.bowling.dao.BowlingGameInputReader;
import com.jobsity.bowling.domain.BowlingScore;
import com.jobsity.bowling.processor.ScoreProcessor;

@Service(value = "bowlingGameServiceImpl")
public class BowlingGameServiceImpl implements Serializable, BowlingGameService{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5479581869811928943L;
	
	private ScoreProcessor scoreProcessor;

	public BowlingGameServiceImpl(ScoreProcessor scoreProcessor) {
		this.scoreProcessor = scoreProcessor;
	}

	@Override
	public BowlingScore processScore(BowlingGameInputReader bowlingGameInputReader) {
		return this.scoreProcessor.process(bowlingGameInputReader.read());
	}
}
