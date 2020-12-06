package com.jobsity.bowling.processor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jobsity.bowling.domain.BowlingGamePlayerScore;
import com.jobsity.bowling.domain.BowlingScore;
import com.jobsity.bowling.domain.Frame;

@Component
public class ComputeScoreImpl implements ComputeScore {
	
	public static final String THE_SUM_OF_THE_KNOCKED_PINS_IN_THE_FIRST_AND_SECOND_ROLL_CAN_T_BE_LARGER_THAN_10 = "The Sum of the knocked pins in the first and second roll can't be larger than 10.";
	public static final String VALUES_GREATER_THAN_10_ARE_NOT_VALID = "Values greater than 10 are not valid.";
	public static final String THE_ONLY_VALUES_ACCEPTED_FOR_KNOCKED_PINS_ARE_X_F_X_F_1_10 = "The only values accepted for knocked pins are x, f, X, F , [1..10]";
	public static final String NEGATIVE_VALUE_FOR_KNOCKED_PINS_ARE_NOT_VALID = "Negative value for Knocked pins are not valid.";
	private static final int LAST_FRAME_PER_GAME_INDEX = 9;
	private static final int STRIKE = 10;
	
	@Override
	public void compute(BowlingScore bowlingScore) {
		bowlingScore.values().forEach(bowlingGamePlayerScore -> {
			computeScoreGameForPlayer(bowlingGamePlayerScore);
		});
		
		System.out.println(bowlingScore);
	}

	public void computeScoreGameForPlayer(BowlingGamePlayerScore bowlingGamePlayerScore) {
		int countFrameGamePrevious = 0;
		int countFrameGame = 0;
		int countRoll = 0;
		List<String> rolls = bowlingGamePlayerScore.getRolls();
		for(int i=0; i< rolls.size(); i++) {
			Integer knockedPins = getKnockedPins(rolls.get(i));
			Frame currentFrame = bowlingGamePlayerScore.getGameFrames().get(countFrameGame);
			if(countFrameGame == LAST_FRAME_PER_GAME_INDEX) {
				if(knockedPins == STRIKE) {
					currentFrame.setFirstRoll("X");
					currentFrame.setScore(knockedPins + getSumNextTwoRolls(rolls, i));
					
					currentFrame.setSecondRoll(rolls.get(i+1).toString());
					currentFrame.setThirdRoll(rolls.get(i+2).toString());					
				} else {
					currentFrame.setFirstRoll(knockedPins.toString());
					computeSecondRoll(rolls, i + 1, currentFrame);
					if(currentFrame.getSecondRoll() != null && currentFrame.getSecondRoll().equals("/")) {
						currentFrame.setThirdRoll(getNextRoll(rolls, i + 1).toString());
					}
					countRoll=0;

				}
				
				Integer previousScoreFrame = bowlingGamePlayerScore.getGameFrames().get(countFrameGamePrevious-1).getScore();
				currentFrame.setScore(currentFrame.getScore() + previousScoreFrame);
				
				break;
			} else if(isStrike(countRoll, knockedPins)) {
				currentFrame.setFirstRoll(" ");
				currentFrame.setSecondRoll("X");
				currentFrame.setScore(knockedPins + getSumNextTwoRolls(rolls, i));
				countRoll=0;
				countFrameGame++;
			} else if(knockedPins < STRIKE && countRoll == 0) {
				currentFrame.setFirstRoll(knockedPins.toString());
				countRoll++;
			} else if(countRoll == 1 && countFrameGame != LAST_FRAME_PER_GAME_INDEX) {
				computeSecondRoll(rolls, i, currentFrame);
				countRoll=0;				
				countFrameGame++;
			} 
			if(countFrameGame != countFrameGamePrevious && countFrameGamePrevious <= LAST_FRAME_PER_GAME_INDEX) {
				if(countFrameGamePrevious != 0) {
					Integer previousScoreFrame = bowlingGamePlayerScore.getGameFrames().get(countFrameGamePrevious-1).getScore();
					currentFrame.setScore(currentFrame.getScore() + previousScoreFrame);
				}
				countFrameGamePrevious = countFrameGame;
			}
		}
	}

	public void computeSecondRoll(List<String> rolls, int currentRollIndex, Frame currentFrame) {
		Integer firstRoll = Integer.valueOf(currentFrame.getFirstRoll());
		Integer knockedPins = getKnockedPins(rolls.get(currentRollIndex));
		Integer sumFirstSecondRoll = getSumFirstWithSecondRoll(knockedPins, firstRoll);
		if(sumFirstSecondRoll == STRIKE) {
			currentFrame.setSecondRoll("/");
			currentFrame.setScore(sumFirstSecondRoll + getNextRoll(rolls, currentRollIndex));
		} else {
			currentFrame.setSecondRoll(knockedPins.toString());
			currentFrame.setScore(sumFirstSecondRoll);
		}
	}

	public int getSumFirstWithSecondRoll(Integer firstRoll, Integer knockedPins) {
		Integer sumResult = knockedPins + firstRoll;
		if(sumResult > 10) {
			throw new IllegalArgumentException(THE_SUM_OF_THE_KNOCKED_PINS_IN_THE_FIRST_AND_SECOND_ROLL_CAN_T_BE_LARGER_THAN_10);
		}
		return sumResult;
	}

	public boolean isStrike(int countRoll, Integer knockedPins) {
		return knockedPins == STRIKE && countRoll == 0;
	}
	
	public Integer getKnockedPins(String value) {
		if(value.equalsIgnoreCase("x")) {
			return 10;
		} else if(value.equalsIgnoreCase("f")) {
			return 0;
		}
		try {
			Integer knockedPins = Integer.valueOf(value);
			if(knockedPins < 0) {
				throw new Exception(NEGATIVE_VALUE_FOR_KNOCKED_PINS_ARE_NOT_VALID);
			} else if(knockedPins > 10) {
				throw new Exception(VALUES_GREATER_THAN_10_ARE_NOT_VALID);
			}
			return knockedPins;
		} catch(NumberFormatException e) {
			throw new IllegalArgumentException(THE_ONLY_VALUES_ACCEPTED_FOR_KNOCKED_PINS_ARE_X_F_X_F_1_10);
		} catch(Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	public Integer getSumNextTwoRolls(List<String> rolls, int currentRollIndex) {
		Integer sumNextTwoRolls = 0;
		for(int i=currentRollIndex + 1; i <= (currentRollIndex+ 2) && i < rolls.size(); i++) {
			sumNextTwoRolls += getKnockedPins(rolls.get(i));
		}
		return sumNextTwoRolls;
	}
	
	public Integer getNextRoll(List<String> rolls, int currentRollIndex) {
		Integer nextRoll = 0;
		int nextRollIndex = currentRollIndex + 1;
		if(nextRollIndex < rolls.size()) {
			nextRoll = getKnockedPins(rolls.get(nextRollIndex));
		}
		return nextRoll;
	}
}
