package com.jobsity.bowling.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.jobsity.bowling.domain.BowlingScore;
import com.jobsity.bowling.processor.ScoreProcessor;

@Service
@Scope("prototype")
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
	public BowlingScore processScore(List<String> listOfPlayersRolls) {
		return this.scoreProcessor.process(listOfPlayersRolls);
	}
}
