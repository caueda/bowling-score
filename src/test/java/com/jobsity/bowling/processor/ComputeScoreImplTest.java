package com.jobsity.bowling.processor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import com.jobsity.bowling.domain.BowlingGamePlayerScore;
import com.jobsity.bowling.domain.Frame;
import com.jobsity.bowling.exception.InvalidScoreOrIncorrectFormatException;
import static com.jobsity.bowling.util.BowlingConstantes.*;

class ComputeScoreImplTest {
	
	private static final int FIRST_ROLL = 0;
	private static final List<String> ROLLS_SAMPLE =
			Lists.newArrayList(
						"10", 
						"3", "5",
						"8", "2", 
						"7", "1",
						"10",
						"4", "5",
						"10",
						"10",
						"9", "1",
						"2","5"
						);
	private static final List<String> ROLLS_SAMPLE_YOUTUBE_TUTORIAL =
			Lists.newArrayList(
						"8","2",  //1
						"7", "3", //2
						"3", "4", //3
						"10",     //4
						"2","8",  //5
						"10",     //6
						"10",     //7
						"8","F",  //8
						"10",     //9
						"8","2","9" //10
						);
	private static final List<String> ROLLS_SAMPLE_PERFECT_GAME =
			Lists.newArrayList(
						"10",  //1
						"10", //2
						"10", //3
						"10",     //4
						"10",  //5
						"10",     //6
						"10",     //7
						"10",  //8
						"10",     //9
						"10","10","10" //10
						);
	private static final List<String> ROLLS_SAMPLE_FOUL_GAME =
			Lists.newArrayList(
						"f", "f", //1
						"f", "f", //2
						"f", "f", //3
						"f", "f", //4
						"f", "f", //5
						"f", "f", //6
						"f", "f", //7
						"f", "f", //8
						"f", "f", //9
						"f", "f" //10
						);
	private static final List<String> ROLLS_SAMPLE_GIVES_MORE_THAN_TEN_FRAMES_WITH_LAST_STRIKE =
			Lists.newArrayList(
						"f", "f", //1
						"f", "f", //2
						"f", "f", //3
						"f", "f", //4
						"f", "f", //5
						"f", "f", //6
						"f", "f", //7
						"f", "f", //8
						"f", "f", //9
						"10", "f", "1", //10
						"10"    //11
						);
	
	private static final List<String> ROLLS_SAMPLE_GIVES_MORE_THAN_TEN_FRAMES_WITH_LAST_SPARE =
			Lists.newArrayList(
						"f", "f", //1
						"f", "f", //2
						"f", "f", //3
						"f", "f", //4
						"f", "f", //5
						"f", "f", //6
						"f", "f", //7
						"f", "f", //8
						"f", "f", //9
						"8", "2", "2", //10
						"10"    //11
						);
	
	private static final List<String> ROLLS_SAMPLE_INCOMPLETE_FRAMES_GAME =
			Lists.newArrayList(
						"f", "f", //1
						"f", "f", //2
						"f", "f", //3
						"f", "f", //4
						"f", "f", //5
						"f", "f", //6
						"f", "f", //7
						"f", "f", //8
						"f", "f" //9
						);

	@Test
	void testComputeScoreGameForPlayer_AllFoulsGameInput_ShouldReturn300() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		BowlingGamePlayerScore bowlingGamePlayerScore = new BowlingGamePlayerScore();
		bowlingGamePlayerScore.setRolls(ROLLS_SAMPLE_FOUL_GAME);
		computeScoreImpl.compute(bowlingGamePlayerScore);
		assertThat(bowlingGamePlayerScore.getGameFrames().get(9).getScore(), equalTo(0));
	}
	
	@Test
	void testComputeScoreGameForPlayer_PerfectGameInput_ShouldReturn300() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		BowlingGamePlayerScore bowlingGamePlayerScore = new BowlingGamePlayerScore();
		bowlingGamePlayerScore.setRolls(ROLLS_SAMPLE_PERFECT_GAME);
		computeScoreImpl.compute(bowlingGamePlayerScore);
		assertThat(bowlingGamePlayerScore.getGameFrames().get(9).getScore(), equalTo(300));
	}
	
	@Test
	void testComputeScoreGameForPlayer_YouTubeTutorialInput_ShouldReturn170() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		BowlingGamePlayerScore bowlingGamePlayerScore = new BowlingGamePlayerScore();
		bowlingGamePlayerScore.setRolls(ROLLS_SAMPLE_YOUTUBE_TUTORIAL);
		computeScoreImpl.compute(bowlingGamePlayerScore);
		assertThat(bowlingGamePlayerScore.getGameFrames().get(9).getScore(), equalTo(170));
	}

	@Test
	void testGetSumNextTwoRolls_WhenRollsSampleAndCurrentIndexEqualZERO_ThenReturn8() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		Integer sumNextTwoRolls = computeScoreImpl.getSumNextTwoRolls(ROLLS_SAMPLE, 0);
		assertThat(sumNextTwoRolls, equalTo(8));
	}
	
	@Test
	void testGetNextRoll_WhenRollsSampleAndCurrentIndexEqualFour_ThenReturn7() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		Integer nextRoll = computeScoreImpl.getNextRoll(ROLLS_SAMPLE, 4);
		assertThat(nextRoll, equalTo(7));
	}
	
	@Test
	void testGetKnockedPins_whenInputX_shouldReturn10() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertThat("X uppercase", computeScoreImpl.getKnockedPins("X"), equalTo(10));
		assertThat("x lowercase", computeScoreImpl.getKnockedPins("x"), equalTo(10));
	}
	
	@Test
	void testGetKnockedPins_whenInputF_shouldReturn0() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertThat("F uppercase", computeScoreImpl.getKnockedPins("F"), equalTo(0));
		assertThat("f lowercase", computeScoreImpl.getKnockedPins("f"), equalTo(0));
	}
	
	@Test
	void testGetKnockedPins_whenNegativeInput_shouldThrowIllegalArgumentException() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertThrows(InvalidScoreOrIncorrectFormatException.class, () -> {
			computeScoreImpl.getKnockedPins("-1");
		});
	}
	
	@Test
	void testGetKnockedPins_whenNoValidInputCharacter_shouldThrowIllegalArgumentException() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			computeScoreImpl.getKnockedPins("a");
		});
		assertTrue(exception.getMessage()
				.contains(InvalidScoreOrIncorrectFormatException.INVALID_SCORE_VALUE_OR_INCORRECT_FORMAT));
	}
	
	@Test
	void testGetKnockedPins_whenValueInputGreaterThan10_shouldThrowIllegalArgumentException() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			computeScoreImpl.getKnockedPins("11");
		});
		assertTrue(exception.getMessage()
				.contains(InvalidScoreOrIncorrectFormatException.INVALID_SCORE_VALUE_OR_INCORRECT_FORMAT));
	}
	
	@Test
	void isStrike_whenAllPinsWereKnocked_ThenReturnTrue() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertTrue(computeScoreImpl.isStrike(FIRST_ROLL, STRIKE));
	}
	
	@Test
	void isStrike_whenNotAllPinsWereKnocked_ThenReturnFalse() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertFalse(computeScoreImpl.isStrike(FIRST_ROLL, STRIKE-1));
	}
	
	@Test
	void getSumFirstWithSecondRoll_WhenFirstPoll3AndSecondPoll5_ThenReturn8() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		int knockedPinsFirstRoll = 3;
		int knockedPinsSecondRoll = 5;
		assertThat(computeScoreImpl.getSumFirstWithSecondRoll(knockedPinsFirstRoll,  knockedPinsSecondRoll), equalTo(8));
	}
	
	@Test
	void getSumFirstWithSecondRoll_WhenFirstPollAndSecondPollIsLargerThan10_ThrowIllegalArgumentException() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		int knockedPinsFirstRoll = 6;
		int knockedPinsSecondRoll = 5;
		assertThrows(InvalidScoreOrIncorrectFormatException.class, () -> {
			computeScoreImpl.getSumFirstWithSecondRoll(knockedPinsFirstRoll,  knockedPinsSecondRoll);
		});
	}
	
	@Test
	void testComputeSecondRoll() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		int currentRollIndex = 4;
		Frame currentFrame = Frame.builder()
				.firstRoll("8")
				.build();
		computeScoreImpl.computeSecondRoll(ROLLS_SAMPLE, currentRollIndex, currentFrame);
		assertThat(currentFrame.getScore(), equalTo(17));
	}
	
	@Test
	void isStrike_WhenAllPinsWereKnockedInFirstRoll_ThenReturnTrue() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertTrue(computeScoreImpl.isStrike(FIRST_ROLL, STRIKE));
	}
	
	@Test
	void isStrike_WhenAllPinsWereNotKnockedInFirstRoll_ThenReturnFalse() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertFalse(computeScoreImpl.isStrike(FIRST_ROLL, STRIKE-1));
	}
	
	@Test
	void testIfThereAreMoreThanThreeRollsInTheLastFrameWithStrikeOrSpareThrowException_whehStrike() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertThrows(InvalidScoreOrIncorrectFormatException.class, () -> {
			computeScoreImpl.ifThereAreMoreThanThreeRollsInTheLastFrameWithStrikeOrSpareThrowException(ROLLS_SAMPLE_GIVES_MORE_THAN_TEN_FRAMES_WITH_LAST_STRIKE, 18);
		});
	}
	
	@Test
	void testIfThereAreMoreThanThreeRollsInTheLastFrameWithStrikeOrSpareThrowException_whehSpare() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertThrows(InvalidScoreOrIncorrectFormatException.class, () -> {
			computeScoreImpl.ifThereAreMoreThanThreeRollsInTheLastFrameWithStrikeOrSpareThrowException(ROLLS_SAMPLE_GIVES_MORE_THAN_TEN_FRAMES_WITH_LAST_SPARE, 18);
		});
	}
	
	@Test
	void testWhenThereAreLessThanTenFramesThrowInvalidScoreOrIncorrectFormatException() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		BowlingGamePlayerScore bowlingGamePlayerScore = new BowlingGamePlayerScore();
		bowlingGamePlayerScore.setRolls(ROLLS_SAMPLE_INCOMPLETE_FRAMES_GAME);
		computeScoreImpl.compute(bowlingGamePlayerScore);
		assertThat(bowlingGamePlayerScore.getStatusMessage(), equalTo(InvalidScoreOrIncorrectFormatException.INVALID_SCORE_VALUE_OR_INCORRECT_FORMAT));
	}
	
}
