package com.jobsity.bowling.processor;

import com.jobsity.bowling.domain.BowlingGamePlayerScore;

public interface ComputeScore {
	void compute(final BowlingGamePlayerScore bowlingGamePlayerScore);
}
