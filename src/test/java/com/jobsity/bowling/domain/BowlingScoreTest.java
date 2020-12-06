package com.jobsity.bowling.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

import org.junit.jupiter.api.Test;

class BowlingScoreTest {

	private static final String NON_EXISTENT_KEY = "nonExistentKey";

	@Test
	void get_WhenThereIsNoPreExistentValue_ShouldCreateANewOneAndReturnIt() {
		BowlingScore newlyCreated = new BowlingScore();
		Object returnedValue = newlyCreated.get(NON_EXISTENT_KEY);
		assertThat("Returned value can't be null", returnedValue, is(notNullValue()));
		assertThat("Returned value must be a BowlingGamePlayerScore", returnedValue, is(instanceOf(BowlingGamePlayerScore.class)));
		assertThat("The key must always return the same object", returnedValue, equalTo(newlyCreated.get(NON_EXISTENT_KEY)));
	}
}
