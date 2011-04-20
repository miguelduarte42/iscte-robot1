import java.io.Serializable;

public class Map implements Serializable{
	
	static int[][] grid = new int[200][200];
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
		if(!isOutOfBounds(x) && !isOutOfBounds(y)){
			grid[x][y] = 1;
			lastY = y;
			lastX = x;
		}
	}
	
	public void markEmpty(int x, int y){
		if(!isOutOfBounds(x) && !isOutOfBounds(y)){
			if(grid[x][y] != 1)
				grid[x][y] = 0;
			lastY = y;
			lastX = x;
		}
	}
	
	public boolean isOutOfBounds(int p){
		if(p < 0 || p > grid.length)
			return true;
		return false;
	}

}
