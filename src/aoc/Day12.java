package aoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Day12 extends AocCore {

	public Day12() {
		super("12");
	}
	public static void main(String[] args) {
		new Day12();
	}
	
	public class Position{
		@Override
		public String toString() {
			return "Position [numberOfSteps=" + numberOfSteps + ", coo=" + Arrays.toString(coo) + "]";
		}

		int numberOfSteps;
		int[] coo;
		public Position(int numberOfSteps, int[] coordinates) {
			super();
			this.numberOfSteps = numberOfSteps;
			this.coo = coordinates;
			
		}
		
		public List<Position> findPossiblePositions(int[][] map) {
			int myAltitude = map[coo[0]][coo[1]];
			List<Position> neighbors = new LinkedList<>();
			
			if((coo[0]+1<map.length) && map[coo[0]+1][coo[1]]<=myAltitude+1) { // right
				int[] tmp = {coo[0]+1,coo[1]};
				neighbors.add(new Position(this.numberOfSteps+1,tmp));
			}
			if((coo[0]-1>=0) && map[coo[0]-1][coo[1]]<=myAltitude+1) { // left
				int[] tmp = {coo[0]-1,coo[1]};
				neighbors.add(new Position(this.numberOfSteps+1,tmp));
			}
			if((coo[1]+1<map[0].length) && map[coo[0]][coo[1]+1]<=myAltitude+1) { // down
				int[] tmp = {coo[0],coo[1]+1};
				neighbors.add(new Position(this.numberOfSteps+1,tmp));
			}
			if((coo[1]-1>=0) && map[coo[0]][coo[1]-1]<=myAltitude+1) { // right
				int[] tmp = {coo[0],coo[1]-1};
				neighbors.add(new Position(this.numberOfSteps+1,tmp));
			}
			return neighbors;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + Arrays.hashCode(coo);
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
			Position other = (Position) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
			return Arrays.equals(coo, other.coo);
		}

		private Day12 getEnclosingInstance() {
			return Day12.this;
		}

		public List<Position> findPossiblePositions2(int[][] map) {
			int myAltitude = map[coo[0]][coo[1]];
			List<Position> neighbors = new LinkedList<>();
			
			if((coo[0]+1<map.length) && map[coo[0]+1][coo[1]]>=myAltitude-1) { // right
				int[] tmp = {coo[0]+1,coo[1]};
				neighbors.add(new Position(this.numberOfSteps+1,tmp));
			}
			if((coo[0]-1>=0) && map[coo[0]-1][coo[1]]>=myAltitude-1) { // left
				int[] tmp = {coo[0]-1,coo[1]};
				neighbors.add(new Position(this.numberOfSteps+1,tmp));
			}
			if((coo[1]+1<map[0].length) && map[coo[0]][coo[1]+1]>=myAltitude-1) { // down
				int[] tmp = {coo[0],coo[1]+1};
				neighbors.add(new Position(this.numberOfSteps+1,tmp));
			}
			if((coo[1]-1>=0) && map[coo[0]][coo[1]-1]>=myAltitude-1) { // right
				int[] tmp = {coo[0],coo[1]-1};
				neighbors.add(new Position(this.numberOfSteps+1,tmp));
			}
			return neighbors;
		}
		
	}

	@Override
	public void solve(List<String> input) {
		
		//building the matrix
		int[][] map = new int[input.get(0).length()][input.size()];
		int[] start = {0,0};
		int[] end= {0,0};
		
		initateVariables(input, map, start, end);
		System.out.print("\n");
		printMatrix(map);
		
		
		Position startPosition = new Position(0, start);
		Queue<Position> positionToAnalyze = new LinkedList<>();
		List<Position> positionAlreadyAnalyzed = new ArrayList<>();
		positionToAnalyze.add(startPosition);
		
		Position theChosen = null;
		while(true) {
			Position currentPos =  positionToAnalyze.poll();
			if(currentPos==null) break;
//			positionToAnalyze.add(currentPos);
			
			if(currentPos.coo[0]==end[0] && currentPos.coo[1]==end[1]) {
				theChosen = currentPos;
				break;
			}
			
//			System.out.println(currentPos.coo[0] + " " + currentPos.coo[1]);

			positionAlreadyAnalyzed.add(currentPos);
			List<Position> findPossiblePositions = currentPos.findPossiblePositions(map);
			positionToAnalyze.addAll( findPossiblePositions );
			positionToAnalyze.removeIf(e -> isItIn(e, positionAlreadyAnalyzed));
		}
		System.out.println("pt1: "+theChosen.numberOfSteps);

		// PT 2
		
		Position endP = new Position(0, end);
		positionToAnalyze.clear();
		positionAlreadyAnalyzed.clear();
		positionToAnalyze.add(endP);
		
		Position theChosen2 = null;
		while(true) {
			Position currentPos = positionToAnalyze.poll();
			if(currentPos==null) break;
			
			if(map[ currentPos.coo[0] ][ currentPos.coo[1] ] == 0) {
				theChosen2 = currentPos;
				break;
			}
			
			positionAlreadyAnalyzed.add(currentPos);
			List<Position> findPossiblePositions = currentPos.findPossiblePositions2(map);
			positionToAnalyze.addAll(findPossiblePositions);
			positionToAnalyze.removeIf(e -> isItIn(e, positionAlreadyAnalyzed));
			
			
		}
		
		
		System.out.println("pt2: "+theChosen2.numberOfSteps);
		
	}
	
	
	
	
	private boolean isItIn(Position e, List<Position> postionAlreadyAnalyzed) {
		for(Position pos : postionAlreadyAnalyzed) {
			if( e.coo[0]==pos.coo[0] && e.coo[1]==pos.coo[1]) return true;
		}
		return false;
	}
	private void printMatrix(int[][] map) {
		for (int j = 0; j<map[0].length ; j++) {
			for (int i = 0; i<map.length ; i++) {			
				System.out.format("%3d", map[i][j]);
			}
			System.out.print("\n");
		}
	}
	private void initateVariables(List<String> input, int[][] map, int[] start, int[] end) {
		for(int j = 0; j<map[0].length ; j++) {
			for( int i = 0; i<map.length ; i++) {
				char currentChar = input.get(j).charAt(i);
				
				System.out.print(currentChar);
				
				if(currentChar == 'S') {
					start[0] = i;
					start[1] = j;
					currentChar = 'a';
				}else if(currentChar == 'E') {
					end[0] = i;
					end[1] = j;
					currentChar = 'z';
				}
				map[i][j]= ((int) currentChar ) -97;
			}
			System.out.print("\n");
		}
		
		
		
		
	}

}
