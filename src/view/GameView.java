package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import Controller.GUIcontrol;
import engine.Game;

public class GameView extends JFrame implements ActionListener {
		private JFrame openingview; 
		private JFrame secondview;
		private JButton entery;
		private JTextArea enter;
		private String playername;
		private MapView  mapv;
		public String getPlayername() {
			return playername;
		}

		private GUIcontrol parent;
		private JLabel label2;
		private JButton rome;
		private JButton egypt;
		private JButton sparta;
	public GameView() {
		rome = null;
		openingview = new JFrame();
		openingview.setUndecorated(true);
		secondview = new JFrame();
		secondview.setUndecorated(true);
		secondview.setContentPane(new JLabel(new ImageIcon("intro55.jpg")));
		openingview.setContentPane(new JLabel(new ImageIcon("intro55.jpg")));
		openingview.setTitle("The Conqueror");
	
		openingview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		openingview.setExtendedState(JFrame.MAXIMIZED_BOTH);
		openingview.setLayout(null);
		
		JLabel label = new JLabel();
		label.setText("The Conqueror");
//		label.setBackground(Color.green);
		label.setBounds(425,70,450,150);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setPreferredSize(new Dimension(200,200));
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setVerticalAlignment(JLabel.CENTER);
		label.setFont(new Font(Font.SANS_SERIF,Font.BOLD,55));
		label.setOpaque(false);
		label.setPreferredSize(new Dimension(50,50));
		entery = new JButton("To Battle !!");
		
		JPanel body = new JPanel();
		body.setLayout(null);
		body.setBounds(300,400,600,100);
		body.setBackground(Color.cyan);
		body.setOpaque(true);
		JLabel name = new JLabel();
		name.setBounds(530, 370, 250, 25);
		name.setBorder(null);
//		name.setEditable(false);
		name.setHorizontalAlignment(JLabel.CENTER);
		name.setBackground(Color.decode("#155ABA"));
		name.setForeground(Color.WHITE);
		name.setOpaque(true);
		name.setText("Please Enter Your Name:");
		name.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
		enter = new JTextArea();
		enter.setBounds(460,405,380,20);
		entery.setBounds(530,440,250,50);
		entery.setFocusable(false);
		entery.setForeground(Color.WHITE);
		entery.setBackground(Color.decode("#155ABA"));
		
		entery.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
		    	entery.setText("");
		    	entery.setBackground(Color.white);
		    	entery.setIcon(new ImageIcon("rsz_swordimage.jpg"));;
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
		    	entery.setIcon(null);;
		    	entery.setText("To Battle !!");
				entery.setBackground(Color.decode("#155ABA"));
		    }
		});
		
		
		
		
		
		
		entery.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
		entery.addActionListener(this);
		body.add(entery);
		body.add(name);
		body.add(enter);
		
		
		
		
		
		openingview.add(entery);
		openingview.add(name);
		openingview.add(enter);
		panel.add(label,BorderLayout.SOUTH);
		openingview.add(label);

		
		secondview.setLayout(null);
		secondview.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		secondview.setTitle("The Conqueror");
		secondview.setExtendedState(JFrame.MAXIMIZED_BOTH);

		
		
		
		label2 = new JLabel();
		label2.setText("");
		label2.setFont(new Font(Font.SANS_SERIF,Font.BOLD,25));
		label2.setBounds(0, 150, 1280, 25);
		label2.setHorizontalAlignment(JLabel.CENTER);
		label2.setVerticalAlignment(JLabel.CENTER);
		
		
		JPanel panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,3,30,0));
		panel2.setBounds(50,325,1175,250);
		panel2.setOpaque(false);
		
		JLabel label3 = new JLabel();
		label3.setText("Cairo");
		label3.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		label3.setBounds(51, 570, 371, 75);
		label3.setForeground(Color.white);
		label3.setHorizontalAlignment(JLabel.CENTER);
		label3.setVerticalAlignment(JLabel.CENTER);
		
		JLabel label4 = new JLabel();
		label4.setText("Sparta");
		label4.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		label4.setForeground(Color.white);
		label4.setBounds(452, 570, 371, 75);
		label4.setHorizontalAlignment(JLabel.CENTER);
		label4.setVerticalAlignment(JLabel.CENTER);
		
		JLabel label5 = new JLabel();
		label5.setText("Rome");
		label5.setFont(new Font(Font.SANS_SERIF,Font.BOLD,20));
		label5.setBounds(853, 570, 371, 75);
		label5.setForeground(Color.white);
		label5.setHorizontalAlignment(JLabel.CENTER);
		label5.setVerticalAlignment(JLabel.CENTER);

		
		rome = new JButton();
		rome.setIcon(new ImageIcon("rsz_romeimage.jpg"));
		rome.addActionListener(this);

		
		
		egypt = new JButton();
		egypt.setIcon(new ImageIcon("rsz_egypp.jpg"));
		egypt.addActionListener(this);

		sparta = new JButton();
		sparta.setIcon(new ImageIcon("rsz_1sparta.jpg"));
		sparta.addActionListener(this);

		panel2.add(egypt);
		panel2.add(sparta);
		panel2.add(rome);
		secondview.add(panel2);
		secondview.add(label2);
		secondview.add(label3);
		secondview.add(label4);
		secondview.add(label5);
		openingview.setVisible(true);	
//		secondview.setVisible(true);
		
		
		
	}

	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==entery) {
				if(enter.getText().length()==0) {
					ImageIcon i =new ImageIcon("rsz_1rsz_archer.jpg");
				JOptionPane.showMessageDialog(null,"Please enter your name first","Name",
						JOptionPane.WARNING_MESSAGE);
				}
				else {
					playername=enter.getText();
					openingview.setVisible(false);
					label2.setText("Now Chose your starting city, "+ playername);
					secondview.setVisible(true);
				}	
		}
		if(e.getSource()==rome) {
			Game g=null;
			try {
				g = new Game(playername,"Rome");
			} catch (IOException e1) {
				
			}
//			this.setMapview(new MapView(g));
			secondview.setVisible(false);
			mapv = new MapView(g);
		

			
		}
		if(e.getSource()==egypt) {
			Game g=null;
			try {
				g = new Game(playername,"Cairo");
			} catch (IOException e1) {
				
			
			}
			mapv = new MapView(g);
			secondview.setVisible(false);
			
		}
		if(e.getSource()==sparta) {
			Game g=null;
			try {
				g = new Game(playername,"Sparta");
			} catch (IOException e1) {
				
			}
			mapv = new MapView(g);
//			this.setMapview(new MapView(g));
			secondview.setVisible(false);
			
		}
		
		
	}


	public MapView getMapv() {
		return mapv;
	}

}

