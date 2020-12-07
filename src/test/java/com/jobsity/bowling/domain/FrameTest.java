package com.jobsity.bowling.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import static com.jobsity.bowling.util.BowlingConstantes.*;

class FrameTest {
	
	@Test
	void testGetScorePreviousFrame_WhenThereIsNoPrevious_ShouldReturnZero() {
		Frame frame = Frame.builder()
				.index(0)
				.build();
		assertThat(frame.getScorePreviousFrame(), equalTo(0));
	}
	
	@Test
	void testGetScorePreviousFrame_WhenThereIsPrevious10_ShouldReturn10() {
		Frame previousFrame = Frame
				.builder()
				.index(0)
				.score(MAX_TOTAL_SCORE)
				.build();
		Frame frame = Frame.builder()
				.index(1)
				.score(9)
				.previous(previousFrame)
				.build();
		assertThat(frame.getScorePreviousFrame(), equalTo(MAX_TOTAL_SCORE));
	}
}
