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

class ComputeScoreImplTest {
	
	private static final int ALL_PINS_KNOCKED = 10;
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

	@Test
	void testComputeScoreGameForPlayer() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		BowlingGamePlayerScore bowlingGamePlayerScore = new BowlingGamePlayerScore();
		bowlingGamePlayerScore.setRolls(ROLLS_SAMPLE);
		computeScoreImpl.computeScoreGameForPlayer(bowlingGamePlayerScore);
		assertThat(bowlingGamePlayerScore.getGameFrames().get(9).getScore(), equalTo(147));
	}
	
	@Test
	void testComputeScoreGameForPlayer_YouTubeTutorialInput_ShouldReturn170() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		BowlingGamePlayerScore bowlingGamePlayerScore = new BowlingGamePlayerScore();
		bowlingGamePlayerScore.setRolls(ROLLS_SAMPLE_YOUTUBE_TUTORIAL);
		computeScoreImpl.computeScoreGameForPlayer(bowlingGamePlayerScore);
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
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			computeScoreImpl.getKnockedPins("-1");
		});
		assertTrue(exception.getMessage()
				.contains(ComputeScoreImpl.NEGATIVE_VALUE_FOR_KNOCKED_PINS_ARE_NOT_VALID));
	}
	
	@Test
	void testGetKnockedPins_whenNoValidInputCharacter_shouldThrowIllegalArgumentException() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			computeScoreImpl.getKnockedPins("a");
		});
		assertTrue(exception.getMessage()
				.contains(ComputeScoreImpl.THE_ONLY_VALUES_ACCEPTED_FOR_KNOCKED_PINS_ARE_X_F_X_F_1_10));
	}
	
	@Test
	void testGetKnockedPins_whenValueInputGreaterThan10_shouldThrowIllegalArgumentException() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			computeScoreImpl.getKnockedPins("11");
		});
		assertTrue(exception.getMessage()
				.contains(ComputeScoreImpl.VALUES_GREATER_THAN_10_ARE_NOT_VALID));
	}
	
	@Test
	void isStrike_whenAllPinsWereKnocked_ThenReturnTrue() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertTrue(computeScoreImpl.isStrike(FIRST_ROLL, ALL_PINS_KNOCKED));
	}
	
	@Test
	void isStrike_whenNotAllPinsWereKnocked_ThenReturnFalse() {
		ComputeScoreImpl computeScoreImpl = new ComputeScoreImpl();
		assertFalse(computeScoreImpl.isStrike(FIRST_ROLL, ALL_PINS_KNOCKED-1));
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
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
			computeScoreImpl.getSumFirstWithSecondRoll(knockedPinsFirstRoll,  knockedPinsSecondRoll);
		});
		assertTrue(exception.getMessage()
				.contains(ComputeScoreImpl.THE_SUM_OF_THE_KNOCKED_PINS_IN_THE_FIRST_AND_SECOND_ROLL_CAN_T_BE_LARGER_THAN_10));
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
}
