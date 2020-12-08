package com.jobsity.bowling.formatter;

import com.jobsity.bowling.domain.BowlingGamePlayerScore;
import com.jobsity.bowling.domain.BowlingScore;
import static com.jobsity.bowling.util.BowlingConstantes.*;

import java.util.Map.Entry;

public class BowlingScoreStandardOutputFormatter {
	public static String format(BowlingScore bowlingScore) {
		StringBuilder output = new StringBuilder();
		output.append(printFrame());
		bowlingScore.entrySet().stream().forEach(entry -> {
			output.append(printPlayerName(entry));
			output.append(printPinfallsTitle());
			BowlingGamePlayerScore bowlingGamePlayerScore = entry.getValue();
			if(bowlingGamePlayerScore.getStatusMessage() == null) {
				entry.getValue().getGameFrames().stream().forEach(frame -> {
					output.append(PinfallStandardOutputFormatter.print(frame));
				});
			} else {
				output.append(printErrorInDataInput(bowlingGamePlayerScore));
			}
			output.append(printScoreTitle());
			if(bowlingGamePlayerScore.getStatusMessage() == null) {
				entry.getValue().getGameFrames().stream().forEach(frame -> {
					output.append(ScoreStandardOutputFormatter.print(frame));
				});
			} else {
				output.append(printErrorInDataInput(bowlingGamePlayerScore));
			}
		});
		return output.toString();
	}

	public static String printScoreTitle() {
		String scoreTitle = "Score" + TAB_SEPARATOR + TAB_SEPARATOR;
		return scoreTitle;
	}

	public static String printErrorInDataInput(BowlingGamePlayerScore bowlingGamePlayerScore) {
		String errorInDataInput = bowlingGamePlayerScore.getStatusMessage() + BREAK_LINE_MARK;
		return errorInDataInput;
	}

	public static String printPinfallsTitle() {
		String pinFallsTitle = "Pinfalls" + TAB_SEPARATOR;
		return pinFallsTitle;
	}
	
	public static String printFrame() {
		StringBuilder frameTitle = new StringBuilder();
		frameTitle.append("Frame" + TAB_SEPARATOR + TAB_SEPARATOR);
		for(int frame=1; frame<=10; frame++) {
			frameTitle.append(frame).append((frame!=10) ? TAB_SEPARATOR + TAB_SEPARATOR : BREAK_LINE_MARK);
		}
		return frameTitle.toString();
	}
	
	public static String printPlayerName(Entry<String, BowlingGamePlayerScore> entry) {
		String playerName = entry.getKey() + BREAK_LINE_MARK;
		return playerName;
	}
}
