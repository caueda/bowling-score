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
	public Frame(Integer score, String firstRoll, String secondRoll, String thirdRoll) {
		super();
		this.score = score;
		this.firstRoll = firstRoll;
		this.secondRoll = secondRoll;
		this.thirdRoll = thirdRoll;
	}
	
	public Integer getSumFirstSecondRolls() {
		Integer first = 0;
		Integer second = 0;
		if(secondRoll.equalsIgnoreCase("x")) {
			return 10;
		}
		if(!secondRoll.equalsIgnoreCase("f")) {
			second = Integer.valueOf(secondRoll);
		}
		if(!firstRoll.equalsIgnoreCase("f")) {
			first = Integer.valueOf(firstRoll);
		}
		return first + second;
	}
	
	public Integer getScorePreviousFrame() {
		if(getPrevious() == null) {
			return 0;
		} 
		return getPrevious().getScore();
	}
}
