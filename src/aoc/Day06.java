package aoc;

import java.util.ArrayList;
import java.util.List;

public class Day06 extends AocCore{

	public Day06() {
		super("6");
	}
	public static void main(String[] args) {
		new Day06();
	}
	@Override
	public void solve(List<String> input) {
		
		String line = input.get(0);
		
		List<Integer> positions = forDistinctCharacters(line,4);
		List<Integer> positions2 = forDistinctCharacters(line,14);
		
		int result1 = positions.get(0);
		int result2 = positions2.get(0);
		
		System.out.println("pt1 : "+result1);
		System.out.println("pt2 : "+result2);
	}

	private List<Integer> forDistinctCharacters(String line, int charCount) {
		
		List<Character> chs = new ArrayList<>();
		List<Integer> positions = new ArrayList<>();
		int howManyChar = charCount;
		
		for(int i=0; i<line.length(); i++) {

			chs.add(line.charAt(i));
			
			if(chs.size()>=howManyChar){
				if (chs.stream().distinct().count()==howManyChar)
					positions.add(i+1);
				
				chs.remove(0);
			}
		}
		return positions;
	}
}
