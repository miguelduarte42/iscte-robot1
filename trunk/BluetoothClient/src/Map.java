import java.io.Serializable;

public class Map implements Serializable{
	private static final int SIZE = 400;
	static int[][] grid = new int[SIZE][SIZE];
	private static Map INSTANCE; 
	static int lastX = 0;
	static int lastY = 0;
	
	private Map() {
		for(int i = 0; i < grid.length ; i++){
			for(int j = 0; j < grid[i].length ; j++){
				grid[i][j] = -1;
			}
		}
	}
	
	public static Map getInstance(){
		if(INSTANCE == null)
			INSTANCE = new Map();
		return INSTANCE;
	}
	
	public void markOccuppied(int x, int y){
		x+=SIZE/2;
		y+=SIZE/2;
		
		/*int diff = x - SIZE/2;		
		if(diff > 0) x = SIZE/2 + diff;
		else x = SIZE/2 - diff;*/
		
		if(!isOutOfBounds(x) && !isOutOfBounds(y))
			grid[y][x] = 1;

		lastY = y;
		lastX = x;
	}
	
	public void markEmpty(int x, int y){
		x+=SIZE/2;
		y+=SIZE/2;
		
		/*int diff = x - SIZE/2;		
		if(diff > 0) x = SIZE/2 + diff;
		else x = SIZE/2 - diff;*/
		
		if(!isOutOfBounds(x) && !isOutOfBounds(y)){
			if(grid[y][x] != 1)
				grid[y][x] = 0;
		}
		lastY = y;
		lastX = x;
	}
	
	public boolean isOutOfBounds(int p){
		if(p < 0 || p > grid.length)
			return true;
		return false;
	}

}
