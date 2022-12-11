package aoc;

import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedList;
import java.util.List;

public class Day11 extends AocCore {

	public Day11() {
		super("11");
	}
	
	public static void main(String[] args) {
		new Day11();
	}
	
	public class monkey{
		List<Long> items;
		boolean isOperationSum; //if not, it is multiplication
		boolean isOperationNumberOld;
		Long operationNumber;
		int test; //divisible by
		int ifTrue;
		int ifFalse;
		int itemAnalyzed;
		
		public monkey(List<Long> items, boolean isOperationSum, int test, int ifTrue, int ifFalse, Long operationNumber) {
			super();
			this.items = items;
			this.isOperationSum = isOperationSum;
			this.test = test;
			this.ifTrue = ifTrue;
			this.ifFalse = ifFalse;
			this.operationNumber = operationNumber;
			itemAnalyzed = 0;
		}
		
		public monkey(String data) {
			/*
			 * 0: nothing
			 * 1: items
			 * 2: isoperationsum and operationNumber
			 * 3: test
			 * 4: iftrue
			 * 5: iffalse
			 */
			
			String[] dataSplitted = data.split("\r\n");
			
			this.items = new LinkedList<>();
			var tmp = dataSplitted[1].split("Starting items:");
			var itemsNumbers = tmp[1].strip().split(",");
			
			for(String item : itemsNumbers) {
				this.items.add( Long.parseLong( item.strip() ));
			}
			
			
			
			this.isOperationSum = dataSplitted[2].contains("+");
			tmp = dataSplitted[2].split(" ");
			String operationNumberString = tmp[tmp.length-1].strip();
			this.isOperationNumberOld = operationNumberString.equals("old");
			this.operationNumber = isOperationNumberOld ? 0 : Long.parseLong( operationNumberString ) ;
			
			tmp = dataSplitted[3].split(" ");
			this.test = Integer.parseInt( tmp[tmp.length-1] );
			
			tmp = dataSplitted[4].split(" ");
			this.ifTrue = Integer.parseInt( tmp[tmp.length-1] );
			
			tmp = dataSplitted[5].split(" ");
			this.ifFalse = Integer.parseInt( tmp[tmp.length-1] );
			
			
			
		}

		@Override
		public String toString() {
			return "monkey [items=" + items + ", isOperationSum=" + isOperationSum + ", isOperationNumberOld="
					+ isOperationNumberOld + ", operationNumber=" + operationNumber + ", test=" + test + ", ifTrue="
					+ ifTrue + ", ifFalse=" + ifFalse + ", itemAnalyzed=" + itemAnalyzed + "]";
		}

		
		public Long[] analyze(Long item) {
			
			itemAnalyzed++;
			
			long newValue=0;
			long monkeyToThrow=0;
			// I assume the monkeys can't throw an item at themselves.
//			items.remove(
//					items.indexOf(item)
//					);
			
			if (isOperationNumberOld)
				operationNumber = item;

			newValue = isOperationSum ? item + operationNumber : item * operationNumber;			
			
			newValue = newValue/3; //uncomment for part 1
			
			monkeyToThrow = (newValue%test==0) ?  ifTrue : ifFalse;
			
			Long[] result = {monkeyToThrow , newValue };
			return result;
		}

		public void addItem(Long i) {
			this.items.add(i);			
		}

		public void remove(Long item) {
			this.items.remove( items.indexOf(item) );
			
		}

		
		
		
	}

	@Override
	public void solve(List<String> input) {
		
		/*
		 * 20 rounds
		 * find the 2 most active monkeys
		 * multiply how many times they inspected items
		 */
		
		try {
			
			String[] inputString = Files.readString(inputFile.toPath()).split("\r\n\r\n");
			
			List<monkey> sillies = new LinkedList<>();
			
			for(String data : inputString)
				sillies.add( new monkey(data));
			sillies.forEach(System.out::println);
			
			int numberOfRounds = 20;	// part 1
//			int numberOfRounds = 10000;	// part 2

			for (int i = 0; i < numberOfRounds; i++) {
				for(monkey aMonkey : sillies) {
					var items = aMonkey.items;
					for(Long item : items) {
						Long[] response =  aMonkey.analyze(item);
//						if(response[1]==10) System.out.println("PROBLEM "+ aMonkey.toString()+" item:"+item);
						sillies.get(response[0].intValue()).addItem(response[1]);
					}
					aMonkey.items.clear();
				}
				
				System.out.println("round " +i+ " finished");
				sillies.forEach(System.out::println);
				
			}
			
			int pt1 = sillies.stream()
					.map(m -> m.itemAnalyzed)
					.mapToInt(Integer::valueOf)
					.map(i -> -i)
					.sorted()
					.map(i -> -i)					
					.limit(2)
					.reduce((e1,e2)->e1*e2).getAsInt();
			
			System.out.println("pt1 : "+pt1);
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

}
