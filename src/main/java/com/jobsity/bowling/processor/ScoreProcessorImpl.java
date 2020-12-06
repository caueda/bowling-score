package com.jobsity.bowling.processor;

import java.util.List;

import org.springframework.stereotype.Component;

import com.jobsity.bowling.domain.BowlingScore;

@Component
public class ScoreProcessorImpl implements ScoreProcessor {

	private ComputeScore computeScore;
	
	public ScoreProcessorImpl(ComputeScore computeScore) {
		this.computeScore = computeScore;
	}
	
	@Override
	public BowlingScore process(List<String> listOfPlayersAndRolls) {
		BowlingScore bowlingScore = getInstanceOfBowlingScoreWithPlayersAndTheirRolls(listOfPlayersAndRolls);
		computeScore.compute(bowlingScore);
		return bowlingScore;
	}
	
	public BowlingScore getInstanceOfBowlingScoreWithPlayersAndTheirRolls(List<String> listOfPlayersAndRolls) {
		BowlingScore bowlingScore = new BowlingScore();
		listOfPlayersAndRolls.stream().forEach(playerRoll -> {
			String[] playerNameAndknockedPins = getArrayWithPlayerNameAndknockedPins(playerRoll);
			String playerName = getNameFromArrayWithPlayerNameAndknockedPins(playerNameAndknockedPins);
			String knockedPins = getKnockedPinsFromArrayWithPlayerNameAndknockedPins(playerNameAndknockedPins);
			bowlingScore.get(playerName).getRolls().add(knockedPins);
		});
		return bowlingScore;
	}
	
	public String[] getArrayWithPlayerNameAndknockedPins(String playerRoll) {
		String[] playerNameAndknockedPins = playerRoll.split(" ");
		return playerNameAndknockedPins;
	}
	
	public String getNameFromArrayWithPlayerNameAndknockedPins(String[] playerNameAndknockedPins) {
		return playerNameAndknockedPins[0];
	}
	
	public String getKnockedPinsFromArrayWithPlayerNameAndknockedPins(String[] playerNameAndknockedPins) {
		return playerNameAndknockedPins[1];
	}
}
