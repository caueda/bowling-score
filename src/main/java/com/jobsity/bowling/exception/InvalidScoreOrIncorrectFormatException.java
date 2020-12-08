package com.jobsity.bowling.exception;

public class InvalidScoreOrIncorrectFormatException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2671819573331283979L;
	public static final String INVALID_SCORE_VALUE_OR_INCORRECT_FORMAT = "Invalid score value or incorrect format.";

	public InvalidScoreOrIncorrectFormatException() {
		super(INVALID_SCORE_VALUE_OR_INCORRECT_FORMAT);
	}
}
