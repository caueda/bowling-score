package com.jobsity.bowling.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service
public class BowlingGameInputReaderFile implements BowlingGameInputReader {

	private String filePath;
	
	public BowlingGameInputReaderFile(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public List<String> read() {
		List<String> gameLines = null;
		try {
			gameLines = parseReturnedLinesBreakingByTabCharacter(getTextLinesFromFile());
			
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return gameLines;
	}
	
	public List<String> getTextLinesFromFile() throws IOException {
		return Files.readAllLines(Paths.get(this.filePath));
	}

	public List<String> parseReturnedLinesBreakingByTabCharacter(List<String> rowsSeparatedByTab) {
		if(rowsSeparatedByTab != null && !rowsSeparatedByTab.isEmpty()) {
			String rowSeparatedByTab = rowsSeparatedByTab.get(0);
			String[] splitedByTabArray = rowSeparatedByTab.split("\t");
			LinkedList<String> gameRows = new LinkedList<>();
			for(int i=0; i<splitedByTabArray.length; i++) {
				gameRows.add(splitedByTabArray[i]);
			}
			return gameRows;
		} 
		return new ArrayList<>(0);
	}
}
