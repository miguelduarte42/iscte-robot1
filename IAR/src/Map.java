public class Map{
	
	private Point[][] quadrants = new Point[4][10];
	private int arrLenght;
	private static Map INSTANCE; 
	
	public static Map getInstance(){
		if(INSTANCE == null)
			INSTANCE = new Map();
		return INSTANCE;
	}
	
	public void markOccuppied(int x, int y){
		Point p = new Point(x,y);
		
		int quadrant = checkQuadrant(x,y);
		
		if(isQuadrantFull(quadrant))
			extendQuadrant(quadrant);
		
		int freeSlot = firstFreeSlot(quadrant);
		
		quadrants[quadrant][freeSlot] = p;
	}
	
	private int firstFreeSlot(int quadrant){
		
		int slot = quadrants[quadrant].length -1;
		
		for(int i = 0 ; i < quadrants[quadrant].length ; i++){
			if(quadrants[quadrant][i] == null){
				slot = i;
				break;
			}
		}
		
		return slot;
		
	}
	
	private boolean isQuadrantFull(int quadrant){
		int arrLength = quadrants[quadrant].length;
		
		return quadrants[quadrant][arrLength] != null;
	}
	
	private void extendQuadrant(int quadrant){
		int arrLength = quadrants[quadrant].length;
		Point[] temp = new Point[arrLength*2];
		
		for(int i = 0 ; i < arrLenght ; i++)
			if(quadrants[quadrant][i] != null)
				temp[i] = quadrants[quadrant][i];
		
		quadrants[quadrant] = temp;
	}

	private int checkQuadrant(int x, int y){
		
		if(x < 0 && y > 0) return 0;
		if(x > 0 && y > 0) return 1;
		if(x < 0 && y < 0) return 2;
		
		return 3;
	}
}
