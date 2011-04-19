import java.io.Serializable;

public class Map{
	
	static int[][] grid = new int[50][50];
	private static Map INSTANCE; 
	
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
		grid[x][y] = 1;
	}
	
	public void markEmpty(int x, int y){
		if(grid[x][y] != 1)
			grid[x][y] = 0;
	}

}
