package com.jobsity.bowling.processor;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import com.jobsity.bowling.domain.BowlingScore;
import static com.jobsity.bowling.util.BowlingConstantes.*;

class ScoreProcessorImplTest {
	
	private static final String PLAYER_NAME_JEFF_OF_ROW_SAMPLE = "Jeff";
	private static final String ROW_SAMPLE = "Jeff 10";
	private static final List<String> LIST_OF_PLAYERS_AND_THEIR_ROLLS = Lists.newArrayList("Jeff 10",	"John 3",	"John 7",	"Jeff 7",	"Jeff 3",	"John 6",	"John 3");
	
	@Test
	void testGetInstanceOfBowlingScoreWithPlayersAndTheirRolls() {
		BowlingScore bowlingScore = new ScoreProcessorImpl(new ComputeScoreImpl()).getInstanceOfBowlingScoreWithPlayersAndTheirRolls(LIST_OF_PLAYERS_AND_THEIR_ROLLS);
		assertThat("Should contain elements in specific order",bowlingScore.get(PLAYER_NAME_JEFF_OF_ROW_SAMPLE).getRolls(), contains("10", "7", "3"));
	}

	@Test
	void testGetArrayWithPlayerNameAndknockedPins() {
		ScoreProcessorImpl scoreProcessor = new ScoreProcessorImpl(new ComputeScoreImpl());
		String[] arrayWithPlayerNameAndknockedPins = scoreProcessor.getArrayWithPlayerNameAndknockedPins(ROW_SAMPLE);
		assertThat("Must have two elements, the name and pins", arrayWithPlayerNameAndknockedPins, arrayWithSize(2));
	}

	@Test
	void testGetNameFromArrayWithPlayerNameAndknockedPins() {
		ScoreProcessorImpl scoreProcessor = new ScoreProcessorImpl(new ComputeScoreImpl());
		String[] arrayWithPlayerNameAndknockedPins = scoreProcessor.getArrayWithPlayerNameAndknockedPins(ROW_SAMPLE);
		assertThat("Player name", scoreProcessor.getNameFromArrayWithPlayerNameAndknockedPins(arrayWithPlayerNameAndknockedPins), equalTo(PLAYER_NAME_JEFF_OF_ROW_SAMPLE));
	}

	@Test
	void testGetKnockedPinsFromArrayWithPlayerNameAndknockedPins() {
		ScoreProcessorImpl scoreProcessor = new ScoreProcessorImpl(new ComputeScoreImpl());
		String[] arrayWithPlayerNameAndknockedPins = scoreProcessor.getArrayWithPlayerNameAndknockedPins(ROW_SAMPLE);
		assertThat("Pins knocked", scoreProcessor.getKnockedPinsFromArrayWithPlayerNameAndknockedPins(arrayWithPlayerNameAndknockedPins), equalTo(String.valueOf(STRIKE)));
	}

}
