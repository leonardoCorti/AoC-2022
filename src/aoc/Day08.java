package aoc;

import java.util.List;

public class Day08 extends AocCore {

	public Day08() {
		super("8");
	}

	public static void main(String[] args) {
		new Day08();
	}

	@Override
	public void solve(List<String> input) {
//		 0 min -> 9 max
//		 a tree is visible only when all the trees between him and the edge
//		 are <= tall
//		 pt1 : how many trees are visible?
		
		int visibleTrees = 0;
		int maxScore=0;
		for (int y = 0; y < input.size(); y++) { // y coordinate
			for (int x = 0; x < input.get(0).length(); x++) { // x coordinate

				if (isVisible(x, y)) { //pt1
					visibleTrees++;
				}
				
				//pt2
				int scenicScoreCurrentTree = scenicScore(x,y);
				if (scenicScoreCurrentTree>maxScore) maxScore=scenicScoreCurrentTree;

			}
		}
		
		System.out.println("pt1 : "+visibleTrees);
		System.out.println("pt2 : "+maxScore);
	}

	private int scenicScore(int x, int y) {
		if(atLimit(x, y)) return 0;
		int focusTree = getNumberAt(x, y);
		
		//left
		int left = 0;
		for (int i = x - 1; i >= 0; i--) {
			left++;
			if (getNumberAt(i, y) >= focusTree) {
				break;
			}
		}
		// right
		int right = 0;
		for(int i = x+1; i<input.get(0).length(); i++) {
			right++;
			if(getNumberAt(i, y)>=focusTree) {
				break;
			}
		}
		
		// up
		int up = 0;
		for(int i = y-1; i>=0; i--) {
			up++;
			if(getNumberAt(x, i)>=focusTree) {
				break;
			}
		}
		// down
		int down = 0;
		for(int i = y+1; i<input.size(); i++) {
			down++;
			if(getNumberAt(x, i)>=focusTree) {
				break;
			}
		}
		
		//finished
		return left*right*down*up;
	}

	private boolean isVisible(int x, int y) {
		if(atLimit(x, y)) return true;
		return up(x, y) || down(x, y) || right(x, y) || left(x, y);
	}

	private boolean left(int x, int y) {
		int focusTree = getNumberAt(x, y);
		if (atLimit(x, y)) return true;
		
		for(int i = x-1; i>=0; i--) {
			if(getNumberAt(i, y)>=focusTree) return false;
		}
		return true;
	}

	private boolean right(int x, int y) {
		int focusTree = getNumberAt(x, y);
		if (atLimit(x, y)) return true;
		
		for(int i = x+1; i<input.get(0).length(); i++) {
			if(getNumberAt(i, y)>=focusTree) return false;
		}
		
		return true;
	}

	private boolean down(int x, int y) {
		int focusTree = getNumberAt(x, y);
		if (atLimit(x, y)) return true;
		
		for(int i = y+1; i<input.size(); i++) {
			if(getNumberAt(x, i)>=focusTree) return false;
		}
		
		return true;
	}

	private boolean up(int x, int y) {
		int focusTree = getNumberAt(x, y);
		if (atLimit(x, y)) return true;
		
		for(int i = y-1; i>=0; i--) {
			if(getNumberAt(x, i)>=focusTree) return false;
		}
		
		return true;
	}

	private int getNumberAt(int x, int y) {
		return Integer.parseInt(input.get(y).subSequence(x, x + 1).toString());
	}

	private boolean atLimit(int x, int y) {
		return x == 0 || y == 0 || x == input.get(0).length()-1 || y == input.size()-1;
	}
}
