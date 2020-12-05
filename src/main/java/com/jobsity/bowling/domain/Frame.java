package com.jobsity.bowling.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Frame {
	private Integer score;
	private Integer firstRoll;
	private Integer secondRoll;
	private Integer thirdRoll;
	
	@Builder
	public Frame(Integer score, Integer firstRoll, Integer secondRoll, Integer thirdRoll) {
		super();
		this.score = score;
		this.firstRoll = firstRoll;
		this.secondRoll = secondRoll;
		this.thirdRoll = thirdRoll;
	}
}
