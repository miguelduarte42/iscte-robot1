import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class MapWindow extends JFrame{

	private Map map = Map.getInstance();
	private MapPainter mp;

	public MapWindow() {
		mp = new MapPainter(map); 
		/*JScrollPane scroll = new JScrollPane();
		scroll.add(mp);*/
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
		setSize(SIZE,SIZE);
		
	}

	public void paint(Graphics g) {
		
		int factor = (SIZE/map.grid.length);
		
		boolean skip = false;
		
		g.setColor(Color.black);
		g.fillRect(0, 0, SIZE, SIZE);
		
		for(int i = 0  ; i < map.grid.length ; i++){
			for(int j = 0  ; j < map.grid[i].length ; j++){
				if(map.grid[i][j] == 0)
					g.setColor(Color.WHITE);
				else if(map.grid[i][j] == 1)
					g.setColor(Color.RED);
				else skip = true;;
				
				//if(i==map.grid.length/2 && j==map.grid[0].length/2)
					//g.setColor(Color.white);
				
				if(!skip){
					g.fillRect(j*factor, i*factor, factor, factor);
				}
				
				skip = false;
				
			}
		}
		
		g.setColor(Color.GREEN);
		g.fillRect(map.lastX*factor, map.lastY*factor, factor, factor);
		
		g.setColor(Color.gray);
		
		for(int i = 0 ; i < map.grid.length ; i+=30)
			g.drawLine(i*factor, 0, i*factor, SIZE);
		for(int i = 0 ; i < map.grid[0].length ; i+=30)
			g.drawLine(0, i*factor, SIZE, i*factor);
	}
}
