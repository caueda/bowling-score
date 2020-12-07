package com.jobsity.bowling.formatter;

import com.jobsity.bowling.domain.Frame;
import static com.jobsity.bowling.util.BowlingConstantes.*;

public class ScoreStandardOutputFormatter {
	public static String print(Frame frame) {
		StringBuilder formattedScore = new StringBuilder();
		formattedScore.append(frame.getScore().toString());
		formattedScore.append((frame.getIndex() < LAST_FRAME_PER_GAME_INDEX) ? BLANK_MARK + BLANK_MARK: BREAK_LINE_MARK);
		System.out.print(formattedScore.toString());
		return formattedScore.toString();
	}
}
