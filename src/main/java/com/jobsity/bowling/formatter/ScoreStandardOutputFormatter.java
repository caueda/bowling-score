package com.jobsity.bowling.formatter;

import com.jobsity.bowling.domain.Frame;
import static com.jobsity.bowling.util.BowlingConstantes.*;

public class ScoreStandardOutputFormatter {
	public static String print(Frame frame) {
		StringBuilder formattedScore = new StringBuilder();
		formattedScore.append(frame.getScore().toString());
		formattedScore.append((frame.getIndex() < LAST_FRAME_PER_GAME_INDEX) ? TAB_SEPARATOR + TAB_SEPARATOR: BREAK_LINE_MARK);
		return formattedScore.toString();
	}
}
