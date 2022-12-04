package aoc;

import java.util.List;

public class Day04 extends AocCore {

	public Day04() {
		super("4");
	}
	public static void main(String[] args) {
		new Day04();
	}

	@Override
	public void solve(List<String> input) {
		
		int OverlapGroupPt1=0;
		int OverlapGroupPt2=0;
		for(String groups : input) {
			if (doesOverlapComplete(groups)) OverlapGroupPt1++;
			if (doesOverlapPartial(groups)) OverlapGroupPt2++;
			
		}
		System.out.println("pt1 "+OverlapGroupPt1);
		System.out.println("pt2 "+OverlapGroupPt2);
		
	}
	
	private Boolean doesOverlapPartial(String groups) {
		String[] splitted = groups.split(",");
		String[] firstElfRange = splitted[0].split("-");
		String[] secondElfRange = splitted[1].split("-");
		
		int[] firstElf = { Integer.parseInt(firstElfRange[0]),
				Integer.parseInt(firstElfRange[1]) };
		int[] secondElf = { Integer.parseInt(secondElfRange[0]),
				Integer.parseInt(secondElfRange[1]) };
		
		if(firstElf[1]<secondElf[0] || firstElf[0]>secondElf[1]) return false;
		
		return true;
	}
	
	private Boolean doesOverlapComplete(String groups) {
		String[] splitted = groups.split(",");
		String[] firstElfRange = splitted[0].split("-");
		String[] secondElfRange = splitted[1].split("-");
		
		int[] firstElf = { Integer.parseInt(firstElfRange[0]),
				Integer.parseInt(firstElfRange[1]) };
		int[] secondElf = { Integer.parseInt(secondElfRange[0]),
				Integer.parseInt(secondElfRange[1]) };
		
		if(firstElf[0]>=secondElf[0] && firstElf[1]<=secondElf[1]) {
			return true;
		} else if (firstElf[0]<=secondElf[0] && firstElf[1]>=secondElf[1]) {
			return true;								
		}
		return false;
	}

}
