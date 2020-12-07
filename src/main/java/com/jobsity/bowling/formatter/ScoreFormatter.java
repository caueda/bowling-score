package com.jobsity.bowling.formatter;

import com.jobsity.bowling.domain.Frame;

public class ScoreFormatter {
	public static void print(Frame frame) {
		String formater = frame.getScore() + " ";
		System.out.print(formater);
	}
}
