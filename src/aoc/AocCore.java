package aoc;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public abstract class AocCore {
	
	String day;
	String inputPath;
	List<String> input;
	File inputFile;
	
	public AocCore(String day) {
		
		inputPath = ".\\inputs\\day"+day+".txt";
		inputFile = new File(inputPath);
		try {
			input = Files.readAllLines(inputFile.toPath());
		} catch (IOException e) {  e.printStackTrace(); }
		
		
		solve(input);
		
	}

	public abstract void solve(List<String> input);

}
