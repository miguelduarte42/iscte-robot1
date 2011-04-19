
public class Repainter extends Thread{
	
	MapWindow m;
	
	public Repainter(MapWindow m) {
		this.m = m;
	}
	
	public void run() {
		while(true){
			m.repaintPainter();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
