package com.jobsity.bowling.dao;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.List;

import org.junit.jupiter.api.Test;

class BowlingGameInputReaderFileTestIT {

	private static final String TEST_CASES_NON_EXISTENT_FILE_TXT = "test_cases/nonExistentFile.txt";
	private static final String TEST_CASES_INPUT01_TXT = "test_cases/input01.txt";
	private static final int NUMBER_OF_LINES_OF_INPUT01 = 35;
	
	@Test
	void read_input01_shouldReturn35ListSize() throws IOException {
		BowlingGameInputReaderFile reader = new BowlingGameInputReaderFile(TEST_CASES_INPUT01_TXT);
		List<String> gameLinesFromFile = reader.read();
		assertThat("The size of returned list of rolls", gameLinesFromFile, hasSize(NUMBER_OF_LINES_OF_INPUT01));
		assertThat("The last line", gameLinesFromFile.get(NUMBER_OF_LINES_OF_INPUT01-1), equalTo("John 0"));
	}
	
	@Test
	void read_fileDoesNotExist_ThrowIllegalArgumentException() {
		BowlingGameInputReaderFile reader = new BowlingGameInputReaderFile(TEST_CASES_NON_EXISTENT_FILE_TXT);
		assertThrows(IllegalArgumentException.class, () -> reader.read());
	}
}
