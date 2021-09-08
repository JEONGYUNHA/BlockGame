package miniProject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class BlockGameFrame extends JFrame {
	Clip clip;
	BlockGameFrame() {
		super("Mini game");
		XMLReader xml = new XMLReader("block.xml");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Node blockGameNode = xml.getBlockGameElement();
		Node sizeNode = XMLReader.getNode(blockGameNode, XMLReader.E_SIZE);
		String w = XMLReader.getAttr(sizeNode, "w");
		String h = XMLReader.getAttr(sizeNode, "h");
		setSize(Integer.parseInt(w), Integer.parseInt(h));

		//setContentPane(new GamePanel(xml.getGamePanelElement()));
		bgmSnd();
		Container c = getContentPane();
		c.add(makeToolBar(), BorderLayout.NORTH);
		c.add(new GamePanel(xml.getGamePanelElement()));
		
		setResizable(false);
		setVisible(true);
	}
	private void bgmSnd() {
		File bgm;
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;
		bgm = new File("kakaofriends.wav");

		try {
			// AudioSystem을 통해 음악을 받아와서 InputStream에 넣고
			stream = AudioSystem.getAudioInputStream(bgm);

			// clip에 아규먼트로 넣을 info를 추출
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			// 음악을 만들기위한 clip생성
			clip = (Clip) AudioSystem.getLine(info);
			// clip을 통해 음악파일을 불러옴
			clip.open(stream);

			// clip실행
			clip.start();
		} catch (Exception e) {

		}

	}

	private JToolBar makeToolBar() {
		JToolBar tBar = new JToolBar();
		
		JButton viewRankBtn = new JButton("등수보기");
		tBar.add(viewRankBtn);
		viewRankBtn.setToolTipText("등수를 보여줍니다.");
		tBar.addSeparator();
		
		JButton stopGameBtn = new JButton("게임 중지");
		tBar.add(stopGameBtn);
		stopGameBtn.setToolTipText("게임을 일시 정지합니다..");
		tBar.addSeparator();
		
		JButton newGameBtn = new JButton("새로시작");
		tBar.add(newGameBtn);
		newGameBtn.setToolTipText("새 게임을 시작합니다.");
		tBar.addSeparator();
		
		JButton exitGameBtn = new JButton("게임 나가기");
		tBar.add(exitGameBtn);
		exitGameBtn.setToolTipText("게임을 마칩니다.");
		tBar.addSeparator();
		
		JButton volumeUpBtn = new JButton("볼륨 up");
		tBar.add(volumeUpBtn);
		tBar.addSeparator();
		
		JButton volumeDownBtn = new JButton("볼륨 down");
		tBar.add(volumeDownBtn);
		tBar.addSeparator();
		
		
/*		JButton openBtn = new JButton("열기");
		openBtn.addActionListener(new ActionListener() {
			private JFileChooser chooser = new JFileChooser();

			public void actionPerformed(ActionEvent e) {
				int ret = chooser.showOpenDialog(GameEditor.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					String filePath = chooser.getSelectedFile().getPath();
					openFile(filePath); // XML 파일 읽기
				}
			}
		});
		

		JButton saveBtn = new JButton("저장");
		saveBtn.addActionListener(new ActionListener() {
			private JFileChooser chooser = new JFileChooser();

			public void actionPerformed(ActionEvent e) {
				int ret = chooser.showSaveDialog(GameEditor.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					String filePath = chooser.getSelectedFile().getPath();
					String xmlString = leftPanel.toXMLString(); // XML 문자열 생성
					saveFile(filePath, xmlString); // XML 문자열을 파일에 저장
				}
			}
		});

		JButton blockBtn = new JButton("블록");
		blockBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				leftPanel.peekBlock();
			}
		});
		tBar.add(blockBtn);

		JButton backBtn = new JButton("배경");
		backBtn.addActionListener(new ActionListener() {
			private JFileChooser chooser = new JFileChooser();

			public void actionPerformed(ActionEvent e) {
				int ret = chooser.showOpenDialog(GameEditor.this);
				if (ret == JFileChooser.APPROVE_OPTION) {
					String filePath = chooser.getSelectedFile().getPath();
					leftPanel.setBackgroundImage(filePath);
				}
			}
		});
		tBar.add(backBtn);*/
		


		return tBar;
	}

	class Block extends JLabel {
		Image img;

		public Block(int x, int y, int w, int h, ImageIcon icon) {
			this.setBounds(x, y, w, h);
			img = icon.getImage();
		}

		public void paintComponent(Graphics g) {
			g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	class GamePanel extends JPanel {
		ImageIcon bgImg;

		public GamePanel(Node gamePanelNode) {
			setLayout(null);
			Node bgNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BG);
			bgImg = new ImageIcon(bgNode.getTextContent());

			// read <Fish><Obj>s from the XML parse tree, make Food objects, and add them to
			// the FishBowl panel.
			Node blockNode = XMLReader.getNode(gamePanelNode, XMLReader.E_BLOCK);
			NodeList nodeList = blockNode.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				if (node.getNodeType() != Node.ELEMENT_NODE)
					continue;
				// found!!, <Obj> tag
				if (node.getNodeName().equals(XMLReader.E_OBJ)) {
					int x = Integer.parseInt(XMLReader.getAttr(node, "x"));
					int y = Integer.parseInt(XMLReader.getAttr(node, "y"));
					int w = Integer.parseInt(XMLReader.getAttr(node, "w"));
					int h = Integer.parseInt(XMLReader.getAttr(node, "h"));

					ImageIcon icon = new ImageIcon(XMLReader.getAttr(node, "img"));
					Block block = new Block(x, y, w, h, icon);
					add(block);
				}
			}
		}

		public void paintComponent(Graphics g) {
			g.drawImage(bgImg.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

}