package com.jobsity.bowling.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class BowlingGamePlayerScore {
	public static final int TOTAL_FRAMES = 10;
	
	private List<String> rolls = new LinkedList<>();
	private List<Frame> gameFrames = new ArrayList<>(TOTAL_FRAMES);
	
	public BowlingGamePlayerScore() {
		for(int frame=0; frame<TOTAL_FRAMES; frame++) {
			gameFrames.add(new Frame());
		}
	}
}
