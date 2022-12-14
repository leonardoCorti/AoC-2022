package aoc;

import java.util.List;

public class Day10 extends AocCore {

	public Day10() {
		super("10");
	}
	
	public static void main(String[] args) {
		new Day10();
	}

	@Override
	public void solve(List<String> input) {
		// the register starts with 1
		/*
		 * addx takes 2 cycles
		 * noop takes 1 cycle
		 * 
		 * while execution addx does not modify the register
		 * signal strength = X*cycle
		 * 
		 * consider signal strength every 40 cycles
		 */
		int register = 1;
		int cycle=0;
		int signalStrength;
		int pt1=0;
		
		StringBuilder crt = new StringBuilder();
		
		for(String command : input) {
			
			String instruction = command.split(" ")[0];
			
			switch (instruction) {
				case "addx" -> {
					cycle++;
					
					signalStrength = cycle*register;
					pt1 = pt1Update(cycle, signalStrength, pt1);
					crtUpdate(crt, cycle, register);
					
					cycle++;
					
					register = register + Integer.parseInt( command.split(" ")[1] );
					signalStrength = cycle*register;
					pt1 = pt1Update(cycle, signalStrength, pt1);
					crtUpdate(crt, cycle, register);
				}
				case "noop" ->{
					cycle++;
					signalStrength = cycle*register;
					pt1 = pt1Update(cycle, signalStrength, pt1);
					crtUpdate(crt, cycle, register);
				}
			}
			
		}
		
		System.out.println("pt1 :"+pt1);
		System.out.println("pt2 : \n"+crt.toString());
		
	}

	private void crtUpdate(StringBuilder crt, int cycle, int register) {
		
		crt.append(  ( Math.abs( cycle%40 - register )<2 ) ? "█" : " "  );
		
		if(cycle%40==0) crt.append("\n");
		
		
	}

	private int pt1Update(int cycle, int signalStrength, int pt1) {
		if(cycle==20 || (cycle-20)%40==0) {
			pt1 += signalStrength;
		}
		return pt1;
	}

}
