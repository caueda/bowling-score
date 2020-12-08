package com.jobsity.bowling.dao;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
class BowlingGameInputReaderFileTest {

	private static final int MOCK_STRING_CONTAINS_5_ROWS = 5;
	private static final String TEST_CASES_INPUT01_TXT = "test_cases/input01.txt";
	private static List<String> MOCK_LIST_RETURNED_AFTER_READING_THE_INPUT_FILE = new ArrayList<>();
	
	@BeforeAll
	public void setUp() {
		MOCK_LIST_RETURNED_AFTER_READING_THE_INPUT_FILE.add("Jeff 10	John 3	John 7	Jeff 7	Jeff 3");
	}
	
	@Test
	void parseReturnedLinesBreakingByTabCharacter_fileContains4Tabs_ShouldCreateListSize5() {
		BowlingGameInputReaderFile reader = new BowlingGameInputReaderFile(TEST_CASES_INPUT01_TXT);
		assertThat("Number of rows separated by tabs from a line", 
				reader.parseReturnedLinesBreakingByTabCharacter(MOCK_LIST_RETURNED_AFTER_READING_THE_INPUT_FILE), 
				hasSize(MOCK_STRING_CONTAINS_5_ROWS));
	}
	
	@Test
	void parseReturnedLinesBreakingByTabCharacter_WhenListIsNull_ThenReturnEmptyList() {
		BowlingGameInputReaderFile reader = new BowlingGameInputReaderFile(TEST_CASES_INPUT01_TXT);
		assertThat("Number of rows separated by tabs from a line", 
				reader.parseReturnedLinesBreakingByTabCharacter(null), empty());
	}
}
