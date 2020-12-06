package com.jobsity.bowling.domain;

import java.util.LinkedHashMap;

public class BowlingScore extends LinkedHashMap<String, BowlingGamePlayerScore>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3360028058036388482L;

	@Override
	public BowlingGamePlayerScore get(Object key) {
		if(!super.containsKey(key)) {
			super.put((String) key, new BowlingGamePlayerScore());
		}
		return super.get(key);
	}
}
