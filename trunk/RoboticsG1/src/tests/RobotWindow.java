/*package tests;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;



public class RobotWindow {
	JFrame  frame;
	JButton startButton;
	boolean startRobot = false;
	
	*//** A simple class that records a pair of integer coordinates. *//*
	private static class Point {

		final double x;
		final double y;

		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}

	LinkedList<Point> robotTrajectory = new LinkedList<Point>();

	public RobotWindow() {
		frame = new JFrame("Robot window");
		frame.getContentPane().add(new RobotPainter(), BorderLayout.CENTER);

		startButton = new JButton("Start");
		frame.getContentPane().add(startButton, BorderLayout.SOUTH);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				synchronized(RobotWindow.this) {
					startRobot = true;
					RobotWindow.this.notify();
				}
			}
		});
		
		frame.setSize(500, 550);
		frame.setVisible(true);
	}

	protected class RobotPainter extends JLabel {

		public void paint(Graphics g) {
			double height = (double) getHeight();
			double width  = (double) getWidth();

			if (width < 10 || height < 10)
				return;
			
			double maxX  = 0.1;
			double minX = -0.1;
			double maxY  = 0.1;
			double minY = -0.1;
			
			synchronized(robotTrajectory) {
				for (Point p : robotTrajectory) {
					if (p.x < minX)
						minX = p.x;
					if (p.x > maxX)
						maxX = p.x;
					if (p.y < minY)
						minY = p.y;
					if (p.y > maxY)
						maxY = p.y;
				}
	
				double horizontalRatio = (maxX - minX) / (width - 10);
				double verticalRatio   = (maxY - minY) / (height - 10);

				double ratio = 0;

				if (horizontalRatio < verticalRatio)
					ratio = verticalRatio;
				else 
					ratio = horizontalRatio;

				double previousX = 0;
				double previousY = 0;

				for (Point p : robotTrajectory) {
					double x1 = ((previousX - (minX + maxX) / 2) / ratio) + width  / 2.0;
					double y1 = ((previousY - (minY + maxY) / 2) / ratio) + height / 2.0;
					
					double x2 = ((p.x - (minX + maxX) / 2) / ratio) + width  / 2.0;
					double y2 = ((p.y - (minY + maxY) / 2) / ratio) + height / 2.0;
					
					System.out.println("x1 " + x1 + ", y1: " + y1);
					
					g.drawLine((int) x1, (int) y1, (int) x2, (int) y2);
		
					previousX = p.x;
					previousY = p.y;
				}		
			}	
		}
	}

	protected void execute()  {
		synchronized(this) {
			while (!startRobot) {
				try {
					wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		DifferentialDriveKinematics ddk = new DifferentialDriveKinematics(1, 1, 3);
		Random r = new Random();
		for (int i = 0; i < 10000; i++) {
			
			ddk.nextStep(360.0,180.0);
			Point p  = new Point(ddk.getX(), ddk.getY());
			robotTrajectory.add(p);
			frame.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		RobotWindow rw = new RobotWindow();
		rw.execute();
	}
}*/