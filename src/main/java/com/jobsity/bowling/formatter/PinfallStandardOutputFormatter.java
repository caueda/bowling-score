package com.jobsity.bowling.formatter;

import com.jobsity.bowling.domain.Frame;
import static com.jobsity.bowling.util.BowlingConstantes.*;

public class PinfallStandardOutputFormatter {

	public static String print(Frame frame) {
		StringBuilder formatter = new StringBuilder();
		if(frame.getIndex() < 9) {
			if(frame.getSecondRoll().equalsIgnoreCase(STRIKE_MARK)) {
				formatter.append(BLANK_MARK).append(STRIKE_MARK).append(BLANK_MARK);
			} else {
				formatter.append(frame.getFirstRoll()).append(BLANK_MARK).append(frame.getSecondRoll()).append(BLANK_MARK);
			}
		} else {
			if(frame.getFirstRoll().equalsIgnoreCase(STRIKE_MARK) || frame.getSecondRoll().equals(SPARE_MARK)) {
				formatter.append(frame.getFirstRoll()).append(BLANK_MARK).append(frame.getSecondRoll()).append(BLANK_MARK).append(frame.getThirdRoll());
			} else {
				formatter.append(frame.getFirstRoll()).append(BLANK_MARK).append(frame.getSecondRoll());
			}
			formatter.append(BREAK_LINE_MARK);
		}
		return formatter.toString();
	}
}
