package com.jobsity.bowling.formatter;

import com.jobsity.bowling.domain.BowlingGamePlayerScore;
import com.jobsity.bowling.domain.BowlingScore;
import static com.jobsity.bowling.util.BowlingConstantes.*;

import java.util.Map.Entry;

public class BowlingScoreStandardOutputFormatter {
	public static void format(BowlingScore bowlingScore) {
		System.out.println();
		printFrame();
		bowlingScore.entrySet().stream().forEach(entry -> {
			printPlayerName(entry);
			printPinfallsTitle();
			BowlingGamePlayerScore bowlingGamePlayerScore = entry.getValue();
			if(bowlingGamePlayerScore.getStatusMessage() == null) {
				entry.getValue().getGameFrames().stream().forEach(PinfallStandardOutputFormatter::print);
			} else {
				printErrorInDataInput(bowlingGamePlayerScore);
			}
			printScoreTitle();
			if(bowlingGamePlayerScore.getStatusMessage() == null) {
				entry.getValue().getGameFrames().stream().forEach(ScoreStandardOutputFormatter::print);
			} else {
				printErrorInDataInput(bowlingGamePlayerScore);
			}
		});
	}

	public static String printScoreTitle() {
		String scoreTitle = "Score" + BLANK_MARK + BLANK_MARK;
		System.out.print(scoreTitle);
		return scoreTitle;
	}

	public static String printErrorInDataInput(BowlingGamePlayerScore bowlingGamePlayerScore) {
		String errorInDataInput = bowlingGamePlayerScore.getStatusMessage() + BREAK_LINE_MARK;
		System.out.print(errorInDataInput);
		return errorInDataInput;
	}

	public static String printPinfallsTitle() {
		String pinFallsTitle = "Pinfalls" + BLANK_MARK + BLANK_MARK;
		System.out.print(pinFallsTitle);
		return pinFallsTitle;
	}
	
	public static String printFrame() {
		StringBuilder frameTitle = new StringBuilder();
		frameTitle.append("Frame" + BLANK_MARK + BLANK_MARK);
		for(int frame=1; frame<=10; frame++) {
			frameTitle.append(frame).append((frame!=10) ? BLANK_MARK + BLANK_MARK : BREAK_LINE_MARK);
		}
		System.out.print(frameTitle.toString());
		return frameTitle.toString();
	}
	
	public static String printPlayerName(Entry<String, BowlingGamePlayerScore> entry) {
		String playerName = entry.getKey() + BREAK_LINE_MARK;
		System.out.print(playerName);
		return playerName;
	}
}
