package aoc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day14 extends AocCore {

	public Day14() {
		super("14");
	}
	public static void main(String[] args) {
		new Day14();
	}
	
	static final String ROCK = "#"; 
	static final String AIR = "."; 
	static final String SOURCE = "+";
	static final String SAND_LOCKED = "O";
	static final String SAND_FALLING = "~";
	
	/*
	 * sand tries to go down, then down left, then down right.
	 * 
	 *  pt1: how many units of sand come to rest?
	 */
	
	public record Point(int x, int y) {}
	
	@Override
	public void solve(List<String> input) {
		Map<Point,String> map = new HashMap<>();
		Point sandSource = new Point(500, 0);
		
		input.forEach(w -> buildWall(w,map));
//		System.out.println("all walls are raised");
		
		Point lowestPoint = null;
		int yLowest = map.keySet().stream().sorted( (p1,p2) -> p2.y-p1.y).findFirst().get().y;
		lowestPoint = new Point(0, yLowest);
		
		boolean isSandFalling = false;
		int unitsOfSand = 0;
		
		while(!isSandFalling) {
			unitsOfSand++;
			boolean isSettled = false;
			
			int x=sandSource.x;
			int y=sandSource.y;
			
			while(!isSettled) {
				
				String downLeft = map.get(new Point(x-1, y+1));
				String down = map.get(new Point(x, y+1));
				String downRight = map.get(new Point(x+1, y+1));
				
				if(down == null || !down.equals(ROCK) && !down.equals(SAND_LOCKED)) {
					// x = x;
					y = y+1;
					if(y>lowestPoint.y) {
						isSandFalling = true;
						break;
					}					
				} else if (downLeft == null || !downLeft.equals(ROCK) && !downLeft.equals(SAND_LOCKED)) {
					x = x-1;
					y = y+1;
				}else if (downRight == null || !downRight.equals(ROCK) && !downRight.equals(SAND_LOCKED)) {
					x = x+1;
					y = y+1;
				}else { //it must settle here
					isSettled = true;
					map.put(new Point(x,y), SAND_LOCKED);
				}
			}
		}
				
		System.out.println("pt1 : "+(unitsOfSand-1));
		
		int yFloor = lowestPoint.y+2; //infinite
		
		int xLowest = map.keySet().stream().sorted( (p1,p2) -> p1.x-p2.x).findFirst().get().x; //447
		int xHighest= map.keySet().stream().sorted( (p1,p2) -> p2.x-p1.x).findFirst().get().x; //515
//		System.out.println("x lowest : "+xLowest);
//		System.out.println("x highest : " +xHighest);
		
		
		//reset variables

		isSandFalling = false; // now this represents the source being blocked
		unitsOfSand = 0;
		Map<Point,String> map2 = new HashMap<>();
		input.forEach(w -> buildWall(w,map2));
		
		int offset = 1000;
		for(int i = xLowest-offset ; i<=xHighest+offset ; i++) {
			map2.put(new Point(i, yFloor), ROCK);
		}
		
		while(!isSandFalling) {
			unitsOfSand++;
			boolean isSettled = false;
			
			int x=sandSource.x;
			int y=sandSource.y;
						
			while(!isSettled) {
				
				String downLeft = map2.get(new Point(x-1, y+1));
				String down = map2.get(new Point(x, y+1));
				String downRight = map2.get(new Point(x+1, y+1));
				
				if(down == null || !down.equals(ROCK) && !down.equals(SAND_LOCKED)) {
					// x = x;
					y = y+1;
//					if(y>lowestPoint.y) {
//						isSandFalling = true;
//						break;
//					}					
				} else if (downLeft == null || !downLeft.equals(ROCK) && !downLeft.equals(SAND_LOCKED)) {
					x = x-1;
					y = y+1;
				}else if (downRight == null || !downRight.equals(ROCK) && !downRight.equals(SAND_LOCKED)) {
					x = x+1;
					y = y+1;
				}else { //it must settle here
					if(sandSource.x==x && sandSource.y==y) {
						isSandFalling = true;
						break;
					}
					isSettled = true;
					map2.put(new Point(x,y), SAND_LOCKED);
				}
			}
		}
		
		System.out.println("pt2 : "+(unitsOfSand));
		
		
		
		
	}
	
	private void buildWall(String w, Map<Point, String> map) {
		
		var listOfPoints = Stream
				.of(w.split(" -> "))
				.map(p -> StringToPoint(p))
				.collect(Collectors.toList());
		
		for(int i = 1; i<listOfPoints.size(); i++) {
			Point from = listOfPoints.get(i-1);
			Point to = listOfPoints.get(i);
			
			if(from.x == to.x) { //wall is horizontal
				int yMin = Math.min(from.y, to.y);
				int yMax = Math.max(from.y, to.y);
				
				for( int y = yMin; y <= yMax; y++) {
					map.put(new Point(from.x, y), ROCK);
				}
				
			}else { //wall is vertical
				int xMin = Math.min(from.x, to.x);
				int xMax = Math.max(from.x, to.x);
				
				for( int x = xMin; x <= xMax; x++) {
					map.put(new Point(x, from.y), ROCK);
				}
			}
			
		}
		
		
		
	}
	
	public Point StringToPoint(String s) {
		return new Point( 
				Integer.parseInt(s.strip().split(",")[0]) ,
				Integer.parseInt(s.strip().split(",")[1])
				);
	}

}
