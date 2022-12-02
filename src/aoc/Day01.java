package aoc;

import java.util.ArrayList;
import java.util.List;

public class Day01 extends AocCore {

	public Day01() {
		super("1");
	}
	
	public static void main(String[] args) {
		new Day01();
	}

	@Override
	public void solve(List<String> input) {
		int cal = 0;
		List<Integer> calories = new ArrayList<>();
		calories.add(0);
		for(String s : input) {
			if(!s.equals("")) {
				cal += Integer.parseInt(s);
			} else {
				calories.set(calories.size()-1, cal);
				cal = 0;
				calories.add(0);
			}
		}
		
		Integer result1 = calories.stream()
									.sorted((a,b) -> b-a)
									.findFirst().get();
		int result2 = calories.stream()
								.sorted((a,b)-> b-a)
								.limit(3).reduce(Integer::sum).get();
		
		System.out.println("pt1: "+result1);
		System.out.println("pt1: "+result2);

	}

}
