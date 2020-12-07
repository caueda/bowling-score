package com.jobsity.bowling.formatter;

import com.jobsity.bowling.domain.Frame;

public class PinfallFormatter {
	public static void print(Frame frame) {
		String formater = null;
		if(frame.getIndex() < 9) {
			if(frame.getSecondRoll().equalsIgnoreCase("x")) {
				formater = " X ";
			} else {
				formater =  " " + frame.getFirstRoll() + " " + frame.getSecondRoll() + " ";
			}
		} else {
			if(frame.getFirstRoll().equalsIgnoreCase("X")) {
				formater =  " " + frame.getFirstRoll() + " " + frame.getSecondRoll() + " " + frame.getThirdRoll() + " ";
			} else if(frame.getSecondRoll().equalsIgnoreCase("/")) {
				formater =  " " + frame.getFirstRoll() + " " + frame.getSecondRoll() + " " + frame.getThirdRoll() + " ";
			} else {
				formater =  " " + frame.getFirstRoll() + " " + frame.getSecondRoll() + " ";
			}
		}
		System.out.print(formater);
	}
}
