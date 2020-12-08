package com.jobsity.bowling.formatter;

import com.jobsity.bowling.domain.Frame;
import static com.jobsity.bowling.util.BowlingConstantes.*;

public class PinfallStandardOutputFormatter {

	public static String print(Frame frame) {
		StringBuilder formatter = new StringBuilder();
		if(frame.getIndex() < 9) {
			if(frame.getSecondRoll().equalsIgnoreCase(STRIKE_MARK)) {
				formatter.append(TAB_SEPARATOR).append(STRIKE_MARK).append(TAB_SEPARATOR);
			} else {
				formatter.append(frame.getFirstRoll()).append(TAB_SEPARATOR).append(frame.getSecondRoll()).append(TAB_SEPARATOR);
			}
		} else {
			if(frame.getFirstRoll().equalsIgnoreCase(STRIKE_MARK) || frame.getSecondRoll().equals(SPARE_MARK)) {
				formatter.append(frame.getFirstRoll()).append(TAB_SEPARATOR).append(frame.getSecondRoll()).append(TAB_SEPARATOR).append(frame.getThirdRoll());
			} else {
				formatter.append(frame.getFirstRoll()).append(TAB_SEPARATOR).append(frame.getSecondRoll());
			}
			formatter.append(BREAK_LINE_MARK);
		}
		return formatter.toString();
	}
}
