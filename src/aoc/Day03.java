package aoc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day03 extends AocCore {

	public Day03() {
		super("3");
	}
	
	public static void main(String[] args) {
		new Day03();
	}

	@Override
	public void solve(List<String> input) {
		
		var result1=input.stream().map(
				t->
				priority(
						letterDuplicate(
								t.subSequence(0, t.length()/2),
								t.subSequence(t.length()/2, t.length()
										)
								)
						)
				).collect(Collectors.toList()).stream().mapToInt(Integer::intValue).sum();
		;
		int result2=0;
		for (int i = 0; i < input.size(); i+=3) {
			result2 += priority(
						commonChar(
								input.get(i),
								input.get(i+1),
								input.get(i+2)
								)
						);
		}

		System.out.println("pt1: "+result1);
		System.out.println("pt2: "+result2);

	}
	
	private char commonChar(String string, String string2, String string3) {
	
		var st1 = string.toCharArray();
		var st2 = string2.toCharArray();
		var st3 = string3.toCharArray();		
		
		List<Character> chars = new ArrayList<>();
		List<Character> chars2 = new ArrayList<>();
		List<Character> chars3 = new ArrayList<>();
		for(Character c : st1) {
			chars.add(c);
		}
		for(Character c : st2) {
			if(chars.contains(c)) {
				chars2.add(c);
			}
		}
		for(Character c : st3) {
			if(chars2.contains(c)) {
				chars3.add(c);
			}
		}
		
		return chars3.get(0);
		
	}

	public char letterDuplicate(CharSequence string1, CharSequence string2) {
		
		int len=string1.length();
		for (int i = 0; i < len; i++) {
			for (int j = 0; j < len; j++) {
				if( string1.charAt(i)==string2.charAt(j)) return string1.charAt(i);
			}
		}
		return 0; 
	}
	
	public int priority(char chara) {
		return Character.isLowerCase(chara) ? chara-96 : chara-38;
	}

}
