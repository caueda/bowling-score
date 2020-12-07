package com.jobsity.bowling.processor;

import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.jobsity.bowling.domain.BowlingGamePlayerScore;
import com.jobsity.bowling.domain.Frame;
import com.jobsity.bowling.exception.InvalidScoreOrIncorrectFormatException;
import com.jobsity.bowling.util.BowlingConstantes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Scope("prototype")
public class ComputeScoreImpl implements ComputeScore, BowlingConstantes {
	
	@Override
	public void compute(BowlingGamePlayerScore bowlingGamePlayerScore) {
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
					if(isFoulRoll(rolls, indexCurrentRoll, knockedPins)) {
						currentFrame.setFirstRoll(FOUL_MARK);
					} else {
						currentFrame.setFirstRoll(knockedPins.toString());
					}
					countRoll++;
				} else if(isSecondRoll(countRoll) && countFrameGame != LAST_FRAME_PER_GAME_INDEX) {
					computeSecondRoll(rolls, indexCurrentRoll, currentFrame);
					countRoll=0;				
					countFrameGame++;
				} 
				currentFrame.setScore(currentFrame.getScore() + currentFrame.getScorePreviousFrame());
			}
			
			whenThereAreLessThanTenFramesThrowInvalidScoreOrIncorrectFormatException(bowlingGamePlayerScore.getGameFrames().get(LAST_FRAME_PER_GAME_INDEX));
		} catch (Exception e) {
			log.error(e.getMessage());
			bowlingGamePlayerScore.setStatusMessage(InvalidScoreOrIncorrectFormatException.INVALID_SCORE_VALUE_OR_INCORRECT_FORMAT);
		}
	}

	public boolean isFoulRoll(List<String> rolls, int indexCurrentRoll, Integer knockedPins) {
		return knockedPins == 0 && rolls.get(indexCurrentRoll).equalsIgnoreCase(FOUL_MARK);
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
		Integer firstRoll = getKnockedPins(currentFrame.getFirstRoll());
		Integer knockedPins = getKnockedPins(rolls.get(currentRollIndex));
		Integer sumFirstSecondRoll = getSumFirstWithSecondRoll(knockedPins, firstRoll);
		if(sumFirstSecondRoll == STRIKE) {
			currentFrame.setSecondRoll(SPARE_MARK);
			currentFrame.setScore(sumFirstSecondRoll + getNextRoll(rolls, currentRollIndex));
		} else {
			if(isFoulRoll(rolls, currentRollIndex, knockedPins)) {
				currentFrame.setSecondRoll(FOUL_MARK);
			} else {
				currentFrame.setSecondRoll(knockedPins.toString());
			}
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
