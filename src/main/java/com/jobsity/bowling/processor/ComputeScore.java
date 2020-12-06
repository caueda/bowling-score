package com.jobsity.bowling.processor;

import com.jobsity.bowling.domain.BowlingScore;

public interface ComputeScore {
	void compute(final BowlingScore bowlingScore);
}
