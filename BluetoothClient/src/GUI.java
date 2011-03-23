import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class GUI extends JFrame{
	
	private Connection c;
	
	public GUI(Connection c) {
		this.c = c;
		initiate();
	}
	
	private void initiate(){
		
		setLayout(new BorderLayout());
		
		JPanel mainPanel = new JPanel();
		mainPanel.setSize(200, 400);
		mainPanel.setLayout(new GridLayout(3,3));
		
		JButton[] buttons = new JButton[9];
		
		MyActionListener myAction = new MyActionListener(c);
		
		for(int i = 0 ; i < buttons.length ; i++){
			buttons[i] = new JButton();
			buttons[i].addActionListener(myAction);
			mainPanel.add(buttons[i]);
			if(i%2==0)
				buttons[i].setVisible(false);
		}
		buttons[1].setText("FRONT");
		buttons[3].setText("LEFT");
		buttons[4].setText("STAY"); buttons[4].setVisible(true);
		buttons[5].setText("RIGHT");
		buttons[7].setText("BACK");
		
		
		JPanel connectPanel = new JPanel();
		JButton connect = new JButton("CONNECT");
		connectPanel.add(connect);
		connect.addActionListener(myAction);
		
		add(mainPanel, BorderLayout.NORTH);
		add(connectPanel, BorderLayout.SOUTH);
		
		pack();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

}

class MyActionListener implements ActionListener{
	
	private Connection c;
	
	public MyActionListener(Connection c) {
		this.c = c;
	}

	public void actionPerformed(ActionEvent arg0) {
		JButton b = (JButton)arg0.getSource();
		
		if(b.getText().equals("FRONT")){
			c.send(2);
		}else if(b.getText().equals("BACK")){
			c.send(8);
		}else if(b.getText().equals("STAY")){
			c.send(5);
		}else if(b.getText().equals("LEFT")){
			c.send(4);
		}else if(b.getText().equals("RIGHT")){
			c.send(6);
		}else if(b.getText().equals("CONNECT")){
			c.connect();
		}
		
	}
	
}
