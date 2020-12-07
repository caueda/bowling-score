package com.jobsity.bowling.formatter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.jobsity.bowling.domain.Frame;
import static com.jobsity.bowling.util.BowlingConstantes.*;

class ScoreStandardOutputFormatterTest {

	@Test
	void testPrint_WhenFrameIsNotLast_ShoudReturnScoreWithEndingBlank() {
		Frame firstFrame = Frame
				.builder()
				.index(0)
				.score(STRIKE)
				.build();
		String formattedScore = ScoreStandardOutputFormatter.print(firstFrame);
		assertThat("Must have a blank character at the end", formattedScore, equalTo(STRIKE + BLANK_MARK));
	}
	@Test
	void testPrint_WhenFrameIsLast_ShoudReturnScoreWithoutEndingBlank() {
		Frame lastFrame = Frame
				.builder()
				.index(9)
				.score(STRIKE)
				.build();
		String formattedScore = ScoreStandardOutputFormatter.print(lastFrame);
		assertThat("Must not have a blank character at the end", formattedScore, equalTo(String.valueOf(STRIKE)));
	}
}
