package org.net.perorin.workingManager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Window {

	private boolean isStart = false;

	private JFrame frame;
	private JPanel time;
	private JLabel serifFrame;
	private Font timeFont;
	private JPanel timePanel;
	protected JTextArea serifArea;
	protected JTextArea workingData;
	protected JTextArea workingContent;
	protected JComboBox<String> category;

	/**
	 * Launch the application.
	 */
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

	private Window() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(SystemColor.inactiveCaptionBorder);
		frame.setResizable(false);
		frame.setBounds(100, 100, 310, 422);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setTitle("茜マネージャーさん");
		frame.setIconImage(new ImageIcon("./META-INF/icon/icon_nc92313.png").getImage());

		timeFont = new Font("MS UI Gothic", Font.PLAIN, 32);
		try {
			timeFont = Font.createFont(Font.TRUETYPE_FONT, new File("./META-INF/font/GN-KillGothic-U-KanaNB.ttf"));
			timeFont = timeFont.deriveFont(32f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		// UIをWindows風に
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException ex) {
			ex.printStackTrace();
		}

		workingContent = new JTextArea();
		workingContent.setFont(new Font("メイリオ", Font.PLAIN, 12));
		JScrollPane workingContentScroll = new JScrollPane(workingContent);
		workingContentScroll.setBounds(5, 175, 293, 75);
		frame.getContentPane().add(workingContentScroll);

		JButton workLap = new JButton("スタート");
		workLap.setBounds(5, 256, 160, 63);
		workLap.setFont(timeFont);
		workLap.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isStart) {
					workLap.setText("ラップ！");
					Logic.workStart(Window.this);
					Logic.setSerifWorkLap(Window.this);
					isStart = true;
				} else {
					if ("".equals(workingContent.getText()) || category.getSelectedIndex() == -1) {

					} else {
						Logic.workLap(Window.this);
						Logic.setSerifWorkLap(Window.this);
						workingContent.setText("");
						category.setSelectedIndex(-1);
					}
				}
			}
		});
		frame.getContentPane().add(workLap);

		time = new JPanel();
		time.setBounds(3, 122, 210, 50);
		time.setOpaque(false);
		frame.getContentPane().add(time);

		timeInit();

		timePanel = new JPanel();
		timePanel.setBounds(5, 132, 208, 45);
		timePanel.setBackground(SystemColor.inactiveCaptionBorder);
		frame.getContentPane().add(timePanel);

		category = new JComboBox<String>();
		category.setBounds(168, 256, 130, 28);
		for (String str : Logic.getJobDate()) {
			category.addItem(str);
		}
		category.setSelectedIndex(-1);
		frame.getContentPane().add(category);

		workingData = new JTextArea();
		workingData.setFont(new Font("メイリオ", Font.PLAIN, 12));
		workingData.setEditable(false);
		JScrollPane workingDataScroll = new JScrollPane(workingData);
		workingDataScroll.setBounds(5, 323, 293, 63);
		frame.getContentPane().add(workingDataScroll);

		JPanel image = new JPanel();
		image.setBackground(Color.WHITE);
		image.setBounds(12, 10, 144, 162);
		image.setLayout(new BorderLayout(0, 0));
		image.add(new JLabel(new ImageIcon("./META-INF/icon/nc92313.png")));
		frame.getContentPane().add(image);

		JPanel serifPanel = new JPanel();
		serifFrame = new JLabel();
		serifFrame.setBounds(0, 0, 140, 109);
		serifFrame.setVisible(false);
		serifPanel.setBounds(158, 10, 140, 109);
		serifPanel.setBackground(SystemColor.inactiveCaptionBorder);
		serifPanel.setLayout(null);
		serifPanel.add(serifFrame);
		frame.getContentPane().add(serifPanel);

		serifArea = new JTextArea();
		serifArea.setBounds(20, 10, 110, 90);
		serifArea.setEditable(false);
		serifArea.setBackground(new Color(242, 242, 242));
		serifArea.setVisible(false);
		serifArea.setFont(new Font("メイリオ", Font.PLAIN, 12));
		serifPanel.add(serifArea);

		mistery = new JPanel();
		mistery.setBackground(SystemColor.inactiveCaption);
		mistery.setBounds(215, 122, 83, 50);
		frame.getContentPane().add(mistery);
		mistery.setLayout(new BorderLayout(0, 0));

		lblM = new JLabel("謎空間");
		lblM.setFont(new Font("Meiryo UI", Font.PLAIN, 12));
		lblM.setHorizontalAlignment(SwingConstants.CENTER);
		mistery.add(lblM);

		Logic.setSerifGreeting(this);

		SerifUpdate serifUpdate = new SerifUpdate();
		serifUpdate.start();

		startTimer();
	}

	private JLabel timeLabel;
	private JPanel mistery;
	private JLabel lblM;

	private void timeInit() {
		time.setLayout(null);
		timeLabel = new JLabel("0");
		timeLabel.setBounds(10, 10, 210, 50);
		timeLabel.setFont(timeFont);
		time.add(timeLabel);
	}

	protected void setSerifFrame(String serifFramePath) {
		if ("".equals(serifFramePath)) {
			serifFrame.setVisible(false);
		} else {
			serifFrame.setIcon(new ImageIcon(serifFramePath));
			serifFrame.setVisible(true);
		}
	}

	private Calendar cal = null;

	private void startTimer() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		while (true) {
			cal = Calendar.getInstance();
			if (cal.getTimeInMillis() % 1000 == 0) {
				break;
			}
		}
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				timeLabel.setText(sdf.format(cal.getTime()));
				cal.add(Calendar.SECOND, 1);
			}
		}, 0L, 1000L);
	}

	class SerifUpdate extends Thread {
		@Override
		public void run() {
			super.run();
			Random r = new Random();
			int time;
			while (true) {
				time = r.nextInt(600) + 1200;
				try {
					sleep(time * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Logic.setSerifOther(Window.this);
			}
		}
	}

}
