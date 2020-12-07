package com.jobsity.bowling.service;

import com.jobsity.bowling.dao.BowlingGameInputReader;
import com.jobsity.bowling.domain.BowlingScore;

public interface BowlingGameService {
	BowlingScore processScore(BowlingGameInputReader bowlingGameInputReader);
}
