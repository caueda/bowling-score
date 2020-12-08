package com.jobsity.bowling;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jobsity.bowling.service.BowlingGameService;

@SpringBootTest
class BowlingApplicationTestsIT {
	
	private static final String TEST_CASES_INPUT01_TXT = "test_cases/input01.txt";
	
	private static final String TEST_CASES_INPUT02_TXT = "test_cases/input02.txt";
	
	private static final String TEST_CASES_INPUT03_TXT = "test_cases/input03.txt";
	
	private static final String TEST_CASES_INPUT04_TXT = "test_cases/input04.txt";
	
	private static final String OUTPUT_TEST_CASES_INPUT01_TXT = 
			"Frame  1  2  3  4  5  6  7  8  9  10\n" + 
			"Jeff\n" + 
			"Pinfalls   X 7 / 9 0  X 0 8 8 / F 6  X  X X 8 1\n" + 
			"Score  20  39  48  66  74  84  90  120  148  167\n" + 
			"John\n" + 
			"Pinfalls  3 / 6 3  X 8 1  X  X 9 0 7 / 4 4 X 9 0\n" + 
			"Score  16  25  44  53  82  101  110  124  132  151\n";
	
	private static final String OUTPUT_TEST_CASES_INPUT02_TXT = 
			"Frame  1  2  3  4  5  6  7  8  9  10\n" + 
			"Jeff\n" + 
			"Pinfalls   X  X  X  X  X  X  X  X  X X 10 10\n" + 
			"Score  30  60  90  120  150  180  210  240  270  300\n" + 
			"Bob\n" + 
			"Pinfalls  F F F 0 F F F F F F F F F F F F F F F F\n" + 
			"Score  0  0  0  0  0  0  0  0  0  0\n";
	
	private static final String SUBSTR_OUTPUT_TEST_CASES_INPUT03_JEFFS_INCORRECT_DATA =
			"Jeff\n" + 
			"Pinfalls  Invalid score value or incorrect format.\n" + 
			"Score  Invalid score value or incorrect format.\n";
	
	private static final String OUTPUT_TEST_CASES_INPUT04_TXT =
			"Frame  1  2  3  4  5  6  7  8  9  10\n" + 
					"Jeff\n" + 
					"Pinfalls  Invalid score value or incorrect format.\n" + 
					"Score  Invalid score value or incorrect format.\n" + 
					"Lewis\n" + 
					"Pinfalls  5 2 3 5 5 0 4 1 9 / 5 1 7 1  X 5 2 X 10 2\n" + 
					"Score  7  15  20  25  40  46  54  71  78  100\n" + 
					"Bob\n" + 
					"Pinfalls  F F F 0 F F F F F F F F F F F F F F F F\n" + 
					"Score  0  0  0  0  0  0  0  0  0  0\n";

	@Autowired
	BowlingGameService bowlingGameService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void GivenInput01_ThenShowOutputForJhonAndJeff() {
		String output = BowlingApplication.execute(bowlingGameService, TEST_CASES_INPUT01_TXT);
		assertThat(output, equalTo(OUTPUT_TEST_CASES_INPUT01_TXT));
	}
	
	@Test
	void GivenInput02ExtremeCases_ThenShowOutputForJhonAndBob() {
		String output = BowlingApplication.execute(bowlingGameService, TEST_CASES_INPUT02_TXT);
		assertThat(output, equalTo(OUTPUT_TEST_CASES_INPUT02_TXT));
	}
	
	@Test
	void GivenInput03WithJeffsIncorrect11PinsKnocked_ThenOutputJeffsDataAsInvalid() {
		String output = BowlingApplication.execute(bowlingGameService, TEST_CASES_INPUT03_TXT);
		assertThat(output, containsString(SUBSTR_OUTPUT_TEST_CASES_INPUT03_JEFFS_INCORRECT_DATA));
	}
	
	@Test
	void GivenInput04_ThenShowOutputForJhonAndJeffAndLewis() {
		String output = BowlingApplication.execute(bowlingGameService, TEST_CASES_INPUT04_TXT);
		assertThat(output, equalTo(OUTPUT_TEST_CASES_INPUT04_TXT));
	}
}
