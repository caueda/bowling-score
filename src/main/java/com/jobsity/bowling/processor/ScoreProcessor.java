package com.jobsity.bowling.processor;

import java.util.List;

import com.jobsity.bowling.domain.BowlingScore;

public interface ScoreProcessor {
	BowlingScore process(List<String> listOfPlayersRolls);
}
