package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day13 extends AocCore {

	public Day13() {
		super("13");
	}
	public static void main(String[] args) {
		new Day13();
	}
	
	public enum customType{
		INT,
		ARRAY;
	}
	public abstract class customArray{
		customType type;
		int num;
		List<customArray> array;
	}
	public class customNumber extends customArray{
		customNumber(String in){
			type=customType.INT;
			num = Integer.parseInt(in);
		}
		public customNumber(int in) {
			type=customType.INT;
			num = in;
		}

		@Override
		public String toString() {
			return "" + num + "";
		}
	}
	
	public class array extends customArray{
		public array() {
			type = customType.ARRAY;
			array = new LinkedList<>();
		}

		@Override
		public String toString() {
			return "" + array + "";
		}
		
		
	}

	public int match2(customArray first, customArray second) {
		// 1= first<second
		// 2 = second>first
		// 0 = first==second
		
		if(first.type==customType.ARRAY && second.type==customType.ARRAY) {
			
			for(int i = 0; i<Math.min(first.array.size(), second.array.size()); i++) {
				
				int theMatch = match2(first.array.get(i),second.array.get(i));
				if(theMatch==1) {
					return 1;
				}else if (theMatch==2) {
					return 2;
				}else if ( theMatch ==3) {
					//do nothing, continue
				}
			}
			//all contents tested are equals
			if(first.array.size()<second.array.size()) {
				return 1; //if this happen true
			} else if (first.array.size()>second.array.size()) {
				return 2; // if this happen false
			} else {
				return 0;
			}
			
			
		} else if ( first.type==customType.INT && second.type == customType.INT) {
			if (first.num == second.num) return 0;
			if (first.num > second.num)  return 2;
			if (first.num < second.num)  return 1;
		}else {
			if (first.type==customType.INT) {
				array temp = new array();
				temp.array.add(new customNumber(first.num));
				return match2(temp, second);
			}else { //second is int
				array temp = new array();
				temp.array.add(new customNumber(second.num));
				return match2(first, temp);
				
			}
		}
		return 0;
	}
	public boolean compare(String first, String second) {
		System.out.println("comparing "+first.strip()+" vs "+second.strip());
		boolean match;
		match = (match2(buildCustomArray(first.strip()),buildCustomArray(second.strip()))==1) ? true : false;
		System.out.println(match);
		return match;		
	}
	public int compare2(String first, String second){ //layer of compatibility because I used random values for match2
		int result = match2(buildCustomArray(first.strip()),buildCustomArray(second.strip()));
		if(result==1) return -1;
		if(result==2) return 1;
		if(result==0) return 0;
		return 0;
	}
	
	private customArray buildCustomArray(String data) {
		
		
		Stack<array> stackOfArray = new Stack<>();
		array currArr = null;
		StringBuilder bob = new StringBuilder();
		
		for (int i = 0; i < data.length(); i++) {
			
			if(data.charAt(i)=='[') {
				if(i==0) {
					currArr= new array();
					stackOfArray.add(currArr);
				}else {
					//create array and put it in stackofArray
					stackOfArray.add(currArr);
					array newArray = new array();
					currArr.array.add(newArray);
					currArr = newArray;
					
				}
					
				
				
			} else if( data.charAt(i)==',' ) {
				if( bob != null && !bob.isEmpty()) {
					customNumber newNum = new customNumber( bob.toString() );
					currArr.array.add(newNum);
					bob = new StringBuilder();
					
				}
			} else if( isNumeric(data.substring(i,i+1))) {
				//create number and put it in current array
				
				bob.append(data.substring(i, i+1));
				
//				customNumber newNum = new customNumber(data.substring(i, i+1));
//				currArr.array.add(newNum);
			} else if ( data.charAt(i)==']') {
				//close current array, take something from the stack idk
				if(!bob.isEmpty()) {
					customNumber newNum = new customNumber( bob.toString() );
					currArr.array.add(newNum);
					bob = new StringBuilder();					
				}
				currArr = stackOfArray.pop();
			} else {
				throw new InputMismatchException(data+"at index " +i+" that is "+ data.charAt(i) );
			}
			
			
		}
		
		return currArr;
	}
	
	public boolean isNumeric(String string) {
		if(string == null || string.equals(""))return false;
		
		try {
			Integer.parseInt(string);
			return true;
		}catch(NumberFormatException e) {
			//do nothing
		}
		return false;
	}
	
	@Override
	public void solve(List<String> input) {
		
		
		try {
			int pt1 = 0;
			int index=0;
			
			String[] pairs = Files.readString(inputFile.toPath()).split("\r\n\r\n");
			
			for(String pair : pairs) {
				index++;
				String first = pair.split("\n")[0];
				String second = pair.split("\n")[1];
				if ( compare(first, second) ) pt1+=index;
			}
			
			var pt2First = Stream.of(
					Files.readString(inputFile.toPath())
					.split("\r\n")
					)
			.filter(e-> !e.equals(""))
			.collect(Collectors.toList());
			pt2First.add("[[2]]");
			pt2First.add("[[6]]");
			
			var pt2ListSorted = pt2First.stream()
			.sorted(this::compare2)
			.collect(Collectors.toList());
			
			int pt2 = (pt2ListSorted.indexOf("[[2]]")+1)*(pt2ListSorted.indexOf("[[6]]")+1);
			
			System.out.println("pt1: "+ pt1);
			System.out.println("pt2: "+ pt2);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
