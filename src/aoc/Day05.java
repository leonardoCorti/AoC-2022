package aoc;

import java.util.LinkedList;
import java.util.List;

public class Day05 extends AocCore {

	public Day05() {
		super("5");
	}

	public static void main(String[] args) {
		new Day05();
	}

	@Override
	public void solve(List<String> input) {

		String result1 = pt1(input);
		String result2 = pt2(input);
		
		System.out.println("pt1 "+result1);
		System.out.println("pt2 "+result2);
		

	}

	private String pt1(List<String> input) {
		int numberOfStacks = 9;
		List<List<Character>> stacks = new LinkedList<>();
		
		for (int i = 0; i < numberOfStacks; i++) {
			stacks.add(new LinkedList<>());
		};
				
		boolean areStackFinished=false;
		for (String line : input) {
			
			if(!areStackFinished  && !line.equals("")) { //create stack representation
				int indexOfChar=1;
				for (int i = 0; i < stacks.size(); i++) {
					
					char charAt = line.charAt(indexOfChar);
					
					if(' ' != charAt && Character.isAlphabetic(charAt)) {
						stacks.get(i).add(charAt);
					}
					indexOfChar+=4;
				}
				
				
				
			}else if(line.equals("")) {
				areStackFinished=true;				
			}else { //actually move the crates				
				crateMover9001(stacks, line);
			}
			
		}
		
		String result1 = "";
		for (List<Character> list : stacks) {
			result1 += list.get(0);
		}
		return result1;
	}
	private String pt2(List<String> input) {
		int numberOfStacks = 9;
		List<List<Character>> stacks = new LinkedList<>();
		
		for (int i = 0; i < numberOfStacks; i++) {
			stacks.add(new LinkedList<>());
		};
				
		boolean areStackFinished=false;
		for (String line : input) {
			
			if(!areStackFinished  && !line.equals("")) { //create stack representation
				int indexOfChar=1;
				for (int i = 0; i < stacks.size(); i++) {
					
					char charAt = line.charAt(indexOfChar);
					
					if(' ' != charAt && Character.isAlphabetic(charAt)) {
						stacks.get(i).add(charAt);
					}
					indexOfChar+=4;
				}
				
				
				
			}else if(line.equals("")) {
				areStackFinished=true;				
			}else { //actually move the crates				
				crateMover9000(stacks, line);
			}
			
		}
		
		String result1 = "";
		for (List<Character> list : stacks) {
			result1 += list.get(0);
		}
		return result1;
	}
	private void crateMover9000(List<List<Character>> stacks, String line) {
		int from;
		int to;
		int numberOfCrates;
		numberOfCrates = Integer.parseInt(
				line.split("from")[0].replace("move", "").strip()
				);
		from = Integer.parseInt(
				line.split("from")[1].split("to")[0].replace("move", "").strip()
				)-1;
		to = Integer.parseInt(
				line.split("to")[1].replace("move", "").strip()
				)-1;
		
		List<Character> movedCrates = new LinkedList<>();
		for (int i = 0; i < numberOfCrates; i++) {
			int last = stacks.get(from).size()-1;
			last =0;
			movedCrates.add(stacks.get(from).get(last));
			
			stacks.get(from).remove(last);
		}
		
		for (int i = 0; i < numberOfCrates; i++) {
		
			stacks.get(to).add(0,movedCrates.get(i));
		}
	}
	private void crateMover9001(List<List<Character>> stacks, String line) {
		int from;
		int to;
		int numberOfCrates;
		numberOfCrates = Integer.parseInt(
				line.split("from")[0].replace("move", "").strip()
				);
		from = Integer.parseInt(
				line.split("from")[1].split("to")[0].replace("move", "").strip()
				)-1;
		to = Integer.parseInt(
				line.split("to")[1].replace("move", "").strip()
				)-1;
		
		List<Character> movedCrates = new LinkedList<>();
		for (int i = 0; i < numberOfCrates; i++) {
			int last = 0;
			
			movedCrates.add(stacks.get(from).get(last));
			
			stacks.get(from).remove(last);
		}
		
		for (int i = numberOfCrates-1; i >=0 ; i--) {
			
			stacks.get(to).add(0,movedCrates.get(i));
		}
	}

}
