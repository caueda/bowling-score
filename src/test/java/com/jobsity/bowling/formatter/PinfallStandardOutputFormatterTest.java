package com.jobsity.bowling.formatter;

import static com.jobsity.bowling.util.BowlingConstantes.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.jupiter.api.Test;

import com.jobsity.bowling.domain.Frame;

class PinfallStandardOutputFormatterTest {

	@Test
	void testPrint_WhenFrameIsNotLastAndIsStrike_ShoudReturn_Blank_X_Blank() {
		Frame firstFrame = Frame
				.builder()
				.index(0)
				.secondRoll(STRIKE_MARK)
				.build();
		String formattedScore = PinfallStandardOutputFormatter.print(firstFrame);
		assertThat("blank X blank", formattedScore, equalTo(TAB_SEPARATOR + STRIKE_MARK + TAB_SEPARATOR));
	}
	
	@Test
	void testPrint_WhenFrameIsNotLastAndIsNotStrike_ShoudReturn_7_Blank_2_Blank() {
		Frame firstFrame = Frame
				.builder()
				.index(0)
				.firstRoll("7")
				.secondRoll("2")
				.build();
		String formattedScore = PinfallStandardOutputFormatter.print(firstFrame);
		assertThat(formattedScore, equalTo("7" + TAB_SEPARATOR + "2" + TAB_SEPARATOR));
	}
	
	@Test
	void testPrint_WhenFrameIsLastAndIsStrike_ShoudReturn_10_Blank_10_Blank_10() {
		Frame firstFrame = Frame
				.builder()
				.index(9)
				.firstRoll(STRIKE_MARK)
				.secondRoll(STRIKE_MARK)
				.thirdRoll(STRIKE_MARK)
				.build();
		String formattedScore = PinfallStandardOutputFormatter.print(firstFrame);
		assertThat(formattedScore, equalTo(STRIKE_MARK + TAB_SEPARATOR + STRIKE_MARK + TAB_SEPARATOR + STRIKE_MARK + BREAK_LINE_MARK));
	}
	
	@Test
	void testPrint_WhenFrameIsLastAndIsSpare_ShoudReturn_9_Blank_SpareMark_Blank_10() {
		Frame firstFrame = Frame
				.builder()
				.index(9)
				.firstRoll("9")
				.secondRoll(SPARE_MARK)
				.thirdRoll(STRIKE_MARK)
				.build();
		String formattedScore = PinfallStandardOutputFormatter.print(firstFrame);
		assertThat(formattedScore, equalTo("9" + TAB_SEPARATOR + SPARE_MARK + TAB_SEPARATOR + STRIKE_MARK + BREAK_LINE_MARK));
	}
	
	@Test
	void testPrint_WhenFrameIsLastAndIsNotStrike_ShoudReturn_7_Blank_2() {
		Frame firstFrame = Frame
				.builder()
				.index(9)
				.firstRoll("7")
				.secondRoll("2")
				.build();
		String formattedScore = PinfallStandardOutputFormatter.print(firstFrame);
		assertThat(formattedScore, equalTo("7" + TAB_SEPARATOR + "2" + BREAK_LINE_MARK));
	}
}
