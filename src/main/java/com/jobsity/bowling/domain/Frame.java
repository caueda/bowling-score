package com.jobsity.bowling.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Frame {
	private Integer index;
	private Integer score = 0;
	private String firstRoll;
	private String secondRoll;
	private String thirdRoll;
	private Frame previous;
	
	@Builder
	public Frame(Integer score, String firstRoll, String secondRoll, String thirdRoll, Integer index, Frame previous) {
		super();
		this.score = score;
		this.firstRoll = firstRoll;
		this.secondRoll = secondRoll;
		this.thirdRoll = thirdRoll;
		this.index = index;
		this.previous = previous;
	}
	
	public Integer getScorePreviousFrame() {
		if(getPrevious() == null) {
			return 0;
		} 
		return getPrevious().getScore();
	}
}
