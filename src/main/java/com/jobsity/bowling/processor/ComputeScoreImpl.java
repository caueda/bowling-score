package com.jobsity.bowling.processor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jobsity.bowling.domain.BowlingGamePlayerScore;
import com.jobsity.bowling.domain.BowlingScore;
import com.jobsity.bowling.domain.Frame;
import com.jobsity.bowling.exception.InvalidScoreOrIncorrectFormatException;

@Component
public class ComputeScoreImpl implements ComputeScore {
	
	private static final String BLANK_MARK = " ";
	private static final String SPARE_MARK = "/";
	private static final String STRIKE_MARK = "X";
	private static final String FOUL_MARK = "F";
	private static final int LAST_FRAME_PER_GAME_INDEX = 9;
	private static final int STRIKE = 10;
	
	@Override
	public void compute(BowlingScore bowlingScore) {
		bowlingScore.values().forEach(bowlingGamePlayerScore -> {
			try {
				computeScoreGameForPlayer(bowlingGamePlayerScore);
			} catch (InvalidScoreOrIncorrectFormatException e) {
				bowlingGamePlayerScore.setStatusMessage(e.getMessage());
			}
		});
	}

	public void computeScoreGameForPlayer(BowlingGamePlayerScore bowlingGamePlayerScore) {
		int countFrameGame = 0;
		int countRoll = 0;
		
		List<String> rolls = bowlingGamePlayerScore.getRolls();
		
		try {
			for(int indexCurrentRoll=0; indexCurrentRoll< rolls.size(); indexCurrentRoll++) {
				Integer knockedPins = getKnockedPins(rolls.get(indexCurrentRoll));
				Frame currentFrame = bowlingGamePlayerScore.getGameFrames().get(countFrameGame);
				if(isLastFrame(countFrameGame)) {
					if(knockedPins == STRIKE) {
						currentFrame.setFirstRoll(STRIKE_MARK);
						currentFrame.setScore(knockedPins + getSumNextTwoRolls(rolls, indexCurrentRoll));
						currentFrame.setSecondRoll(rolls.get(indexCurrentRoll + 1));
						currentFrame.setThirdRoll(rolls.get(indexCurrentRoll + 2));	
						ifThereAreMoreThanThreeRollsInTheLastFrameWithStrikeOrSpareThrowException(rolls, indexCurrentRoll);
					} else {
						currentFrame.setFirstRoll(knockedPins.toString());
						computeSecondRoll(rolls, indexCurrentRoll + 1, currentFrame);
						if(currentFrame.getSecondRoll() != null && currentFrame.getSecondRoll().equals(SPARE_MARK)) {
							currentFrame.setThirdRoll(getNextRoll(rolls, indexCurrentRoll + 1).toString());
							ifThereAreMoreThanThreeRollsInTheLastFrameWithStrikeOrSpareThrowException(rolls, indexCurrentRoll);
						}
					}
					currentFrame.setScore(currentFrame.getScore() + currentFrame.getScorePreviousFrame());
					break;
				} else if(isStrike(countRoll, knockedPins)) {
					currentFrame.setFirstRoll(BLANK_MARK);
					currentFrame.setSecondRoll(STRIKE_MARK);
					currentFrame.setScore(knockedPins + getSumNextTwoRolls(rolls, indexCurrentRoll));
					countRoll=0;
					countFrameGame++;
				} else if(knockedPins < STRIKE && isFirstRoll(countRoll)) {
					currentFrame.setFirstRoll(knockedPins.toString());
					countRoll++;
				} else if(isSecondRoll(countRoll) && countFrameGame != LAST_FRAME_PER_GAME_INDEX) {
					computeSecondRoll(rolls, indexCurrentRoll, currentFrame);
					countRoll=0;				
					countFrameGame++;
				} 
				currentFrame.setScore(currentFrame.getScore() + currentFrame.getScorePreviousFrame());
			}
			
			whenThereAreLessThanTenFramesThrowInvalidScoreOrIncorrectFormatException(bowlingGamePlayerScore.getGameFrames().get(LAST_FRAME_PER_GAME_INDEX));
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidScoreOrIncorrectFormatException();
		}
	}

	public void ifThereAreMoreThanThreeRollsInTheLastFrameWithStrikeOrSpareThrowException(List<String> rolls,
			int indexCurrentRoll) {
		if(indexCurrentRoll + 3 <= rolls.size()-1) {
			throw new InvalidScoreOrIncorrectFormatException();
		}
	}

	public void whenThereAreLessThanTenFramesThrowInvalidScoreOrIncorrectFormatException(Frame lastFrame) {
		if(lastFrame.getFirstRoll() == null && lastFrame.getSecondRoll() == null) {
			throw new InvalidScoreOrIncorrectFormatException();
		}
	}

	protected boolean isSecondRoll(int countRoll) {
		return countRoll == 1;
	}

	protected boolean isFirstRoll(int countRoll) {
		return countRoll == 0;
	}

	public boolean isLastFrame(int countFrameGame) {
		return countFrameGame == LAST_FRAME_PER_GAME_INDEX;
	}

	public void computeSecondRoll(List<String> rolls, int currentRollIndex, Frame currentFrame) {
		Integer firstRoll = Integer.valueOf(currentFrame.getFirstRoll());
		Integer knockedPins = getKnockedPins(rolls.get(currentRollIndex));
		Integer sumFirstSecondRoll = getSumFirstWithSecondRoll(knockedPins, firstRoll);
		if(sumFirstSecondRoll == STRIKE) {
			currentFrame.setSecondRoll(SPARE_MARK);
			currentFrame.setScore(sumFirstSecondRoll + getNextRoll(rolls, currentRollIndex));
		} else {
			currentFrame.setSecondRoll(knockedPins.toString());
			currentFrame.setScore(sumFirstSecondRoll);
		}
	}

	public int getSumFirstWithSecondRoll(Integer firstRoll, Integer knockedPins) {
		Integer sumResult = knockedPins + firstRoll;
		if(sumResult > 10) {
			throw new InvalidScoreOrIncorrectFormatException();
		}
		return sumResult;
	}

	public boolean isStrike(int countRoll, Integer knockedPins) {
		return knockedPins == STRIKE && isFirstRoll(countRoll);
	}
	
	public Integer getKnockedPins(String value) {
		if(value.equalsIgnoreCase(STRIKE_MARK)) {
			return 10;
		} else if(value.equalsIgnoreCase(FOUL_MARK)) {
			return 0;
		}
		try {
			Integer knockedPins = Integer.valueOf(value);
			if(knockedPins < 0 || knockedPins > 10) {
				throw new InvalidScoreOrIncorrectFormatException();
			} 
			return knockedPins;
		} catch(NumberFormatException e) {
			throw new InvalidScoreOrIncorrectFormatException();
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
