import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;


public class MapWindow extends JFrame{

	private Map map = Map.getInstance();
	private MapPainter mp;

	public MapWindow() {
		mp = new MapPainter(map); 
		add(mp);
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		new Repainter(this).start();
	}
	public static void main(String[] args) {
		new MapWindow();
	}
	
	public void repaintPainter(){
		mp.repaint();
	}
}

class MapPainter extends Canvas {
	
	Map map;
	
	static int SIZE = 800;
	
	public MapPainter(Map map) {
		this.map = map;
		setSize(800,800);
	}

	public void paint(Graphics g) {
		g.setColor(Color.gray);
		
		int factor = (SIZE/map.grid.length);
		
		for(int i = 0 ; i < map.grid.length ; i++)
			g.drawLine(i*factor, 0, i*factor, SIZE);
		for(int i = 0 ; i < map.grid[0].length ; i++)
			g.drawLine(0, i*factor, SIZE, i*factor);
		
		for(int i = 0  ; i < map.grid.length ; i++){
			for(int j = 0  ; j < map.grid[i].length ; j++){
				if(map.grid[i][j] == 0)
					g.setColor(Color.WHITE);
				else if(map.grid[i][j] == 1)
					g.setColor(Color.RED);
				else g.setColor(Color.BLACK);
				
				//if(i==map.grid.length/2 && j==map.grid[0].length/2)
					//g.setColor(Color.white);
				
				g.fillRect(j*factor, i*factor, factor, factor);
				
				g.setColor(Color.BLACK);
				
			}
		}
		
		g.setColor(Color.GREEN);
		g.fillRect(map.lastY*factor, map.lastX*factor, factor, factor);
	}
}
