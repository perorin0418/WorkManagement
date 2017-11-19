package org.net.perorin.workingManager.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;

import org.net.perorin.workingManager.config.Constant;
import org.net.perorin.workingManager.logic.Logic;
import org.net.perorin.workingManager.table.WorkModel;
import org.net.perorin.workingManager.table.WorkTable;

public class Window {

	private Font btnFont;
	private boolean isStart = false;

	private JFrame frame;
	private JPanel image_SerifPnl;
	private JPanel imagePnl;
	private JPanel serifPnl;
	private JPanel timer_Button_Content_Category_Table_Pnl;
	private JPanel timer_Button_Pnl;
	private JPanel content_Category_Table_Pnl;
	private JPanel content_Category_Pnl;
	private JPanel table_Pnl;
	private JPanel content_Pnl;
	private JPanel category_Pnl;
	private TimerPanel timer;
	private SerifPanel serif;
	private JTextField contentText;
	private JComboBox<String> category;
	private WorkModel model;
	private WorkTable table;
	private JButton workBtn;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Window window = new Window();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Window() {
		initialize();
		init_Frame();
		init_Image_SerifPanel();
		init_Image_Panel();
		init_Serif_Panel();
		init_Timer_Button_Content_Category_Table_Panel();
		init_Timer_Button_Panel();
		init_Content_Category_Table_Panel();
		init_Content_Category_Panel();
		init_Content_Panel();
		init_Category_Panel();
		init_Table_Panel();
		init_Final();
	}

	private void initialize() {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		btnFont = new Font("MS UI Gothic", Font.PLAIN, 32);
		try {
			btnFont = Font.createFont(Font.TRUETYPE_FONT, new File(Constant.TIMER_FONT_PATH));
			btnFont = btnFont.deriveFont(32f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}
	}

	private void init_Frame() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		frame.setBounds(100, 100, 456, 540);
		frame.setIconImage(new ImageIcon(Constant.ICON_PATH).getImage());
		frame.setTitle("茜マネージャーさん");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Logic.workSave(model);
			}
		});
	}

	private void init_Image_SerifPanel() {
		image_SerifPnl = new JPanel();
		Border b = image_SerifPnl.getBorder();
		EmptyBorder e = new EmptyBorder(10, 10, 5, 10);
		image_SerifPnl.setBorder(new CompoundBorder(b, e));
		image_SerifPnl.setLayout(new BorderLayout(0, 0));
		image_SerifPnl.setBackground(SystemColor.inactiveCaptionBorder);
		frame.getContentPane().add(image_SerifPnl, BorderLayout.NORTH);
	}

	private void init_Image_Panel() {
		imagePnl = new JPanel();
		imagePnl.setBackground(SystemColor.inactiveCaptionBorder);
		imagePnl.setPreferredSize(new Dimension(125, 162));
		imagePnl.add(new JLabel(new ImageIcon(Constant.IMAGE_PATH)));
		image_SerifPnl.add(imagePnl, BorderLayout.WEST);
	}

	private void init_Serif_Panel() {
		serifPnl = new JPanel();
		image_SerifPnl.add(serifPnl, BorderLayout.CENTER);
		serifPnl.setLayout(new BorderLayout(0, 0));
		serif = new SerifPanel();
		serifPnl.add(serif, BorderLayout.CENTER);
	}

	private void init_Timer_Button_Content_Category_Table_Panel() {
		timer_Button_Content_Category_Table_Pnl = new JPanel();
		timer_Button_Content_Category_Table_Pnl.setBackground(SystemColor.inactiveCaptionBorder);
		timer_Button_Content_Category_Table_Pnl.setLayout(new BorderLayout(0, 0));
		frame.getContentPane().add(timer_Button_Content_Category_Table_Pnl, BorderLayout.CENTER);
	}

	private void init_Timer_Button_Panel() {
		timer_Button_Pnl = new JPanel();
		FlowLayout flowLayout = (FlowLayout) timer_Button_Pnl.getLayout();
		flowLayout.setVgap(0);
		timer_Button_Pnl.setBackground(SystemColor.inactiveCaptionBorder);
		timer_Button_Content_Category_Table_Pnl.add(timer_Button_Pnl, BorderLayout.NORTH);

		timer = new TimerPanel();
		timer_Button_Pnl.add(timer);

		workBtn = new JButton("スタート");
		workBtn.setPreferredSize(new Dimension(160, 55));
		workBtn.setFont(btnFont);
		workBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isStart) {
					isStart = true;
					workBtn.setText("ラップ");
					Logic.workStart(timer);
					serif.setText("\n作業開始や！\n一生懸命働くんやで！");
				} else {
					if (!"".equals(contentText.getText()) && category.getSelectedIndex() != -1) {
						Logic.workEnd(timer, contentText, category, model);
						serif.setText("作業内容を記録したで\nお疲れやでー\nほな、次の作業を始めてな");
					} else {
						serif.setText("\n作業内容を入力せんかい！\n　　　アホ！　ボケ！");
					}
				}
			}
		});
		timer_Button_Pnl.add(workBtn);
	}

	private void init_Content_Category_Table_Panel() {
		content_Category_Table_Pnl = new JPanel();
		content_Category_Table_Pnl.setLayout(new BorderLayout(0, 0));
		timer_Button_Content_Category_Table_Pnl.add(content_Category_Table_Pnl, BorderLayout.CENTER);
	}

	private void init_Content_Category_Panel() {
		content_Category_Pnl = new JPanel();
		content_Category_Pnl.setBackground(SystemColor.inactiveCaptionBorder);
		content_Category_Table_Pnl.add(content_Category_Pnl, BorderLayout.NORTH);
		content_Category_Pnl.setLayout(new BorderLayout(0, 0));
	}

	private void init_Content_Panel() {
		content_Pnl = new JPanel();
		Border b = content_Pnl.getBorder();
		EmptyBorder e = new EmptyBorder(2, 10, 2, 10);
		content_Pnl.setBorder(new CompoundBorder(b, e));
		content_Pnl.setBackground(SystemColor.inactiveCaptionBorder);
		content_Category_Pnl.add(content_Pnl, BorderLayout.NORTH);
		content_Pnl.setLayout(new BorderLayout(0, 0));

		contentText = new JTextField();
		content_Pnl.add(contentText, BorderLayout.CENTER);
		contentText.setColumns(10);
		contentText.setFont(new Font("メイリオ", Font.PLAIN, 14));
		contentText.setPreferredSize(new Dimension(0, 25));

	}

	private void init_Category_Panel() {
		category_Pnl = new JPanel();
		Border b = category_Pnl.getBorder();
		EmptyBorder e = new EmptyBorder(2, 10, 2, 10);
		category_Pnl.setBorder(new CompoundBorder(b, e));
		category_Pnl.setBackground(SystemColor.inactiveCaptionBorder);
		content_Category_Pnl.add(category_Pnl, BorderLayout.SOUTH);
		category_Pnl.setLayout(new BorderLayout(0, 0));

		category = new JComboBox<String>();
		category.setFont(new Font("メイリオ", Font.PLAIN, 14));
		category.setPreferredSize(new Dimension(0, 25));
		for (String str : Logic.getJobData()) {
			category.addItem(str);
		}
		category.setSelectedIndex(-1);
		category_Pnl.add(category, BorderLayout.CENTER);
	}

	private void init_Table_Panel() {
		table_Pnl = new JPanel();
		Border b = table_Pnl.getBorder();
		EmptyBorder e = new EmptyBorder(2, 10, 10, 10);
		table_Pnl.setBorder(new CompoundBorder(b, e));
		table_Pnl.setBackground(SystemColor.inactiveCaptionBorder);
		content_Category_Table_Pnl.add(table_Pnl, BorderLayout.CENTER);
		table_Pnl.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		table_Pnl.add(scrollPane, BorderLayout.CENTER);

		model = new WorkModel();
		table = new WorkTable(model);
		scrollPane.setViewportView(table);
	}

	private void init_Final() {
		SerifUpdate serifUpdate = new SerifUpdate();
		serifUpdate.start();
		serif.setText("\nおっす！おっす！\n今日も一日かんばるゾイ！");
		if (Logic.workLoad(model)) {
			isStart = true;
			workBtn.setText("ラップ");
			Logic.workReStart(timer, model.getValueAt(model.getRowCount() - 1, 1).toString());
			serif.setText("\n作業途中やな\n続きから始めるで！");
		}
		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle desktopBounds = env.getMaximumWindowBounds();
		int width = desktopBounds.width;
		int height = desktopBounds.height;
		frame.setBounds(width - frame.getWidth(), height - frame.getHeight(), frame.getWidth(), frame.getHeight());
	}

	class SerifUpdate extends Thread {
		@Override
		public void run() {
			super.run();
			Random r = new Random();
			int time;
			while (true) {
				time = r.nextInt(600);
				try {
					sleep(time * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				serif.setText(Logic.getRandomSerif());
			}
		}
	}

}
