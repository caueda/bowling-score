package com.jobsity.bowling.service;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobsity.bowling.dao.BowlingGameInputReader;
import com.jobsity.bowling.domain.BowlingGamePlayerScore;
import com.jobsity.bowling.domain.BowlingScore;
import com.jobsity.bowling.domain.Frame;
import static com.jobsity.bowling.util.BowlingConstantes.*;

@SpringBootTest
class BowlingGameServiceImplTestIT {

	private static final String PLAYER_BOB = "Bob";

	@Mock
	BowlingGameInputReader bowlingGameInputReader;
	
	@Autowired
	BowlingGameService bowlingGameServiceImpl;
	
	List<String> mockInputListPerfectGameByBob = 
			Lists.newArrayList("Bob 10", "Bob 10","Bob 10","Bob 10","Bob 10","Bob 10","Bob 10","Bob 10","Bob 10","Bob 10","Bob 10","Bob 10");
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testProcessScore() {
		Mockito.when(bowlingGameInputReader.read()).thenReturn(mockInputListPerfectGameByBob);
		BowlingScore score = bowlingGameServiceImpl.processScore(bowlingGameInputReader);
		BowlingGamePlayerScore playerScore = score.get(PLAYER_BOB);
		assertThat(playerScore, is(not(nullValue())));
		Frame lastFrame = playerScore.getGameFrames().get(LAST_FRAME_PER_GAME_INDEX);
		assertThat(lastFrame.getScore(), equalTo(MAX_TOTAL_SCORE));
	}

}
