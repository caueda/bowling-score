package com.jobsity.bowling.domain;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

class FrameTest {

	private static final int ALL_PINS_KNOCKED = 10;

	@Test
	void testGetSumFirstSecondRolls_whenOccurStrike() {
		Frame frame = Frame.builder()
				.secondRoll("X")
				.build();
		assertThat(frame.getSumFirstSecondRolls(), equalTo(ALL_PINS_KNOCKED));
	}
	
	@Test
	void testGetSumFirstSecondRolls_whenOccurSpare() {
		Frame frame = Frame.builder()
				.firstRoll("5")
				.secondRoll("5")
				.build();
		assertThat(frame.getSumFirstSecondRolls(), equalTo(ALL_PINS_KNOCKED));
	}
	
	@Test
	void testGetSumFirstSecondRolls_when9PinsWereKnocked() {
		Frame frame = Frame.builder()
				.firstRoll("5")
				.secondRoll("4")
				.build();
		assertThat(frame.getSumFirstSecondRolls(), equalTo(9));
	}
	
	@Test
	void testGetSumFirstSecondRolls_whenSecondRollWereFoul() {
		Frame frame = Frame.builder()
				.firstRoll("5")
				.secondRoll("F")
				.build();
		assertThat(frame.getSumFirstSecondRolls(), equalTo(5));
	}
	
	@Test
	void testGetSumFirstSecondRolls_whenTwoRollsWereFoul() {
		Frame frame = Frame.builder()
				.firstRoll("F")
				.secondRoll("F")
				.build();
		assertThat(frame.getSumFirstSecondRolls(), equalTo(0));
	}
}
