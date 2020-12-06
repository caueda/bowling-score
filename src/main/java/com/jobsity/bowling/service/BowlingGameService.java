package com.jobsity.bowling.service;

import java.util.List;

import com.jobsity.bowling.domain.BowlingScore;

public interface BowlingGameService {
	BowlingScore processScore(List<String> listOfPlayersRolls);
}
