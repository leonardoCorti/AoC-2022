package aoc;

import java.util.HashMap;
import java.util.List;

public class Day02 extends AocCore {
	

	public Day02() {
		super("2");
	}
	
	public static void main(String[] args) {
		new Day02();
	}

	@Override
	public void solve(List<String> input) {
		/*
		 * A rock
		 * B paper
		 * C scissors
		 * 
		 * X rock 1 point
		 * Y paper 2 point
		 * Z scissors 3 point
		 * 
		 * 1 win over 3
		 * 3 win over 2
		 * 2 win over 1
		 * 
		 * win = 6
		 * lost = 0
		 * draw = 3
		 */
		
		int sum =input.stream()
				.map(t->findValue(t))
				.mapToInt(Integer::intValue)
				.sum();
		int sum2 =input.stream()
				.map(t->Solution(t))				
				.map(t->findValue(t))
				.mapToInt(Integer::intValue)
				.sum();
		
		
		System.out.println("pt1: "+sum);
		System.out.println("pt2: "+sum2);
		
				
	}
	
	private String Solution(String t) {
		var signes = t.split(" ");
		/*
		 * X lose
		 * Y draw
		 * Z win
		 */
		HashMap<String, String> lose= new HashMap<>();
		lose.put("A", "Z");
		lose.put("B", "X");
		lose.put("C", "Y");
		
		HashMap<String, String> win= new HashMap<>();
		win.put("A", "Y");
		win.put("B", "Z");
		win.put("C", "X");
		HashMap<String, String> eq= new HashMap<>();
		eq.put("A", "X");
		eq.put("B", "Y");
		eq.put("C", "Z");
		String newGame = "";
		switch (signes[1]) {
			case "X":
				newGame = signes[0] + " " + lose.get(signes[0]);
				break;
			case "Y":
				newGame = signes[0] + " " + eq.get(signes[0]);
				break;
			case "Z":
				newGame = signes[0] + " " + win.get(signes[0]);
	
				break;
			default:
				break;
		}
		
//		System.out.println(t);
//		System.out.println(newGame);
//		System.out.println("\n");
		return newGame;
	}

	private int findValue(String game) {

		String[] elves= {"A","B","C"};
		String[] me= {"X","Y","Z"};
		
		var signes=game.split(" ");
		int result=0;
		int elfValue = 0;
		int meValue = 0;
		
		for (int i = 0; i < me.length; i++) {
			if (signes[1].equals(me[i])) {
				result +=i+1;
				meValue=i;
			}
			
		}
		
		for (int i = 0; i < elves.length; i++) {
			if( signes[0].equals(elves[i])) {
				elfValue=i;
			}
		}
		
		if(meValue==elfValue) {
			result +=3;
		}
		else if((meValue==0&&elfValue==2)||(meValue==2 && elfValue==1) ||meValue>elfValue && meValue!=2 || (meValue==1 && elfValue==3)) {
			result+=6;
		}
		
		
//		System.out.println(signes[0]+elfValue+signes[1]+meValue+"  "+result);		
		return result;
		
	}

}
