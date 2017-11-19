package org.net.perorin.workingManager.window;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.net.perorin.workingManager.config.Constant;

public class SerifPanel extends JPanel {

	private SerifCenterPanal serifCenterPnl;

	public SerifPanel() {
		super();
		this.setLayout(new BorderLayout(0, 0));

		JPanel serifLeftPnl = new JPanel();
		serifLeftPnl.setBackground(SystemColor.inactiveCaptionBorder);
		serifLeftPnl.setLayout(new BorderLayout(0, 0));
		serifLeftPnl.add(new JLabel(new ImageIcon(Constant.FRAME_LEFT_PATH)));
		this.add(serifLeftPnl, BorderLayout.WEST);

		serifCenterPnl = new SerifCenterPanal();
		serifCenterPnl.setBackground(SystemColor.inactiveCaptionBorder);
		this.add(serifCenterPnl, BorderLayout.CENTER);

		JPanel serifRightPnl = new JPanel();
		serifRightPnl.setBackground(SystemColor.inactiveCaptionBorder);
		serifRightPnl.setLayout(new BorderLayout(0, 0));
		serifRightPnl.add(new JLabel(new ImageIcon(Constant.FRAME_RIGHT_PATH)));
		this.add(serifRightPnl, BorderLayout.EAST);
	}

	public void setText(String text) {
		serifCenterPnl.setText(text);
	}

	public class SerifCenterPanal extends JPanel {

		private BufferedImage centerFrame;
		private JTextArea serifArea;

		public SerifCenterPanal() {
			super();
			try {
				centerFrame = ImageIO.read(new File(Constant.FRAME_CENTER_PATH));
			} catch (IOException e) {
				e.printStackTrace();
			}
			Border b = this.getBorder();
			EmptyBorder e = new EmptyBorder(18, 0, 20, 0);
			this.setBorder(new CompoundBorder(b, e));
			this.setLayout(new BorderLayout(0, 0));
			serifArea = new JTextArea();
			serifArea.setFont(new Font("メイリオ", Font.BOLD, 20));
			serifArea.setEditable(false);
			serifArea.setBackground(new Color(242, 242, 242));
			this.add(serifArea, BorderLayout.CENTER);
		}

		@Override
		public Dimension getPreferredSize() {
			return new Dimension(10, 162);
		}

		@Override
		protected void paintComponent(Graphics g) {
			serifArea.setPreferredSize(new Dimension(getWidth(), getHeight()));
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g.create();
			int tileWidth = centerFrame.getWidth();
			int tileHeight = centerFrame.getHeight();
			for (int y = 0; y < getHeight(); y += tileHeight) {
				for (int x = 0; x < getWidth(); x += tileWidth) {
					g2d.drawImage(centerFrame, x, y, this);
				}
			}
			g2d.dispose();
		}

		public void setText(String text) {
			serifArea.setText("");
			String strs[] = text.split("\\\\n");
			for (String str : strs) {
				serifArea.append(str);
				serifArea.append("\n");
			}
		}
	}

}
