package com.jobsity.bowling.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

class BowlingGameReaderFileTest {

	private static final int NUMBER_OF_LINES_OF_INPUT01 = 35;

	@Test
	void read_input01_shouldReturn35ListSize() {
		BowlingGameReader reader = new BowlingGameReaderFile("test_cases/input01.txt");
		List<String> gameLinesFromFile = reader.read();
		assertThat("The size of returned list of rolls", gameLinesFromFile.size(), equalTo(NUMBER_OF_LINES_OF_INPUT01));
		assertThat("The last line", gameLinesFromFile.get(NUMBER_OF_LINES_OF_INPUT01-1), equalTo("John 0"));
	}
}
