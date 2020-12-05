package com.jobsity.bowling.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Scope("prototype")
@Service
public class BowlingGameReaderFile implements BowlingGameReader {

	private String filePath;
	
	public BowlingGameReaderFile(String filePath) {
		this.filePath = filePath;
	}
	
	@Override
	public List<String> read() {
		List<String> gameLines = new LinkedList<>();
		try {
			gameLines = Files.readAllLines(Paths.get(this.filePath));
			
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
		return gameLines;
	}

}
