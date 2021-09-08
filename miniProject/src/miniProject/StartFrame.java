package miniProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToolBar;

public class StartFrame extends JFrame {
	private MyPanel panel;
	public StartFrame() {
		super("벽돌 깨기");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//프레임 윈도우 종료시 프로그램 종료
		setSize(750,800);
		Container c= getContentPane();
		panel = new MyPanel();
		panel.setBackground(Color.WHITE);
		c.add(panel,BorderLayout.CENTER);
		setVisible(true);
	}
	
	class MyPanel extends JPanel{
		private JLabel imgLabel;
		private JLabel imgLabel2;
		public MyPanel() {
			setLayout(null);
			ImageIcon icon = new ImageIcon("Start.png");
			ImageIcon icon2 = new ImageIcon("exit0.png");
			imgLabel = new JLabel(icon);// 이미지 레이블 생성
			imgLabel.setLocation(100, 300);
			imgLabel.setSize(200,200);
			add(imgLabel);
			
			imgLabel2 = new JLabel(icon2);
			imgLabel2.setLocation(420, 300);
			imgLabel2.setSize(200,200);
			add(imgLabel2);//panel에 add

			StartAdapter ml= new StartAdapter();
			imgLabel.addMouseListener(ml);
			
			StartAdapter2 ml2= new StartAdapter2();
			imgLabel2.addMouseListener(ml2);
		}

		class StartAdapter extends MouseAdapter{// 마우스 어뎁터
			public void mouseClicked(MouseEvent e) {// 마우스를 눌러졌을때
				if(e.getClickCount()==1) {
					new BlockGameFrame();
				}	
			}
			public void mousePressed(MouseEvent e) {//눌러졌을때
				ImageIcon icon = new ImageIcon("Start2.png");
				imgLabel.setIcon(icon);
			}
			public void mouseEntered(MouseEvent e) {//마우스가 올라갈때
				ImageIcon icon = new ImageIcon("Start1.png");
				imgLabel.setIcon(icon);
				
			}
			public void mouseExited(MouseEvent e) {
				ImageIcon icon = new ImageIcon("Start.png");
				imgLabel.setIcon(icon);
			}
		}
		
		class StartAdapter2 extends MouseAdapter{
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==1) {
					System.exit(0);
				}	
			}
			public void mousePressed(MouseEvent e) {
				ImageIcon icon = new ImageIcon("exit2.png");
				imgLabel2.setIcon(icon);
			}
			public void mouseEntered(MouseEvent e) {
				ImageIcon icon = new ImageIcon("exit1.png");
				imgLabel2.setIcon(icon);
				
			}
			public void mouseExited(MouseEvent e) {
				ImageIcon icon = new ImageIcon("exit0.png");
				imgLabel2.setIcon(icon);
			}
		}
	}

	public static void main(String[] args) {
		new StartFrame();
	}

}