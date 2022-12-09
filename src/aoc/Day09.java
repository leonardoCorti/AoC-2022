package aoc;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Day09 extends AocCore{

	public Day09(){
		super("9");
	}
	
	public static void main(String[] args) {
		new Day09();
	}
	
	public class position{
		public int x;
		public int y;
		public position(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		public position(int[] S) {
			this.x=S[0];
			this.y=S[1];
		}
		
		@Override
		public String toString() {
			return "x:"+x+"  y:"+y;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Objects.hash(x, y);
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			position other = (position) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return x == other.x && y == other.y;
		}
		private Day09 getEnclosingInstance() {
			return Day09.this;
		}
		
		
	}
	
	@Override
	public void solve(List<String> input) {
		//H and T stars at s
		//s in at bottom left of the simulation space
		int[] H = {0,0}; //x,y
		int[] S = {0,0};
		
		long countVisitedPt1 = simulationOneKnot(input, H, S);
		long countVisitedpt2 = simulationNineKnot(input);
		
		System.out.println("pt1 : "+countVisitedPt1);
		System.out.println("pt2 : "+countVisitedpt2);
		
		
	}
	
	
// ******* PART 2 ***********
	private long simulationNineKnot(List<String> input) {
		
		int numberOfKnots = 10;
		
		Set<position> positions = new HashSet<>();
		
		List<Knot> knots = new LinkedList<>();
		for(int i=0; i<numberOfKnots ; i++) {
			knots.add(new Knot(0, 0));
		}
		Knot head = knots.get(0);
		Knot tail = knots.get(numberOfKnots-1);
		
		for(String command : input) {
			
			String direction = command.split(" ")[0];
			int steps = Integer.parseInt( command.split(" ")[1].strip() );
			
			for (int i = 0; i < steps; i++) {
				
				switch (direction) {
					case "U" -> head.setY(head.y+1);
					case "D" -> head.setY(head.y-1);
					case "R" -> head.setX(head.x+1);
					case "L" -> head.setX(head.x-1);
				}
				
				for(int j=1; j < knots.size(); j++) {
					Knot currentKnot = knots.get(j);
					Knot previusKnot = knots.get(j-1);
					currentKnot.setPosition( nextPosition2(currentKnot,previusKnot) );
				}
				
				positions.add(tail.getPosition());
				
			}
			
			
			
			
		}
		
		
		
		positions.stream().forEach(System.out::println);
		
		long count = positions.stream().distinct().count();
		return count;
	}	
	

	private position nextPosition2(Knot S, Knot H) {
		int[] bufferS= {S.getX(),S.getY()};
		int[] bufferH= {H.getX(),H.getY()};
		int[] bufferResult=nextPosition(bufferS, bufferH);
		
		return new position(bufferResult);
	}
		
	public class Knot{
		int x;
		int y;
		
		
		public position getPosition() {
			return new position(x, y);
		}
		
		public void setPosition(position pos) {
			this.x=pos.x;
			this.y=pos.y;
		}
		
		public Knot(int x, int y) {
			super();
			this.x = x;
			this.y = y;
		}
		
		protected int getX() {
			return x;
		}
		protected void setX(int x) {
			this.x = x;
		}
		protected int getY() {
			return y;
		}
		protected void setY(int y) {
			this.y = y;
		}
		
	}
	

// ******* PART 1 ***********
	private long simulationOneKnot(List<String> input, int[] H, int[] S) {
		Set<position> visited = new HashSet<>();
		for(String command : input) {
			
			String direction = command.split(" ")[0].strip();
			int steps = Integer.parseInt(
					command.split(" ")[1].strip());
			
			for (int i = 0; i < steps; i++) {
				switch (direction) {
					case "R" -> H[0] +=1;
					case "L" -> H[0] -=1;
					case "U" -> H[1] +=1;
					case "D" -> H[1] -=1;
				}
				S = nextPosition(S, H);
				visited.add(new position(S));
			}
			
		}
		long countVisited = visited.stream().distinct().count();
		return countVisited;
	}
	
	
	
	private int[] nextPosition(int[] S, int[] H) {
		
		//if S is close to H both vertically and horizontally it doesn't move
		int distanceX = Math.abs(S[0]-H[0]);
		int distanceY = Math.abs(S[1]-H[1]);
		if( distanceX<=1 && distanceY<=1  )
			return S;
		//otherwise, if H and S are on the same column or row move S in the correct direction
		//if they are not in the same row or column do diagonal movement
		boolean diagional =
				( distanceX>1 && S[1]!=H[1] ) ||
				( distanceY>1 && S[0]!=H[0] );
		if(diagional) {
			horizontalMovement(S, H);
			verticalMovement(S, H);			
			return S;
		}
		else if( distanceY >1 ) {
			verticalMovement(S, H);
		}else if(distanceX >1 ) {
			horizontalMovement(S, H);
		}
		
		return S;
		
	}

	private int verticalMovement(int[] S, int[] H) {
		return S[1] = H[1]>S[1] ? S[1]+1 : S[1]-1;
	}

	private int horizontalMovement(int[] S, int[] H) {
		return S[0] = H[0]>S[0] ? S[0]+1 : S[0]-1;
	}
	

}
