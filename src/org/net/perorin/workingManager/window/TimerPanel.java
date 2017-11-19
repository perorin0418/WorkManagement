package org.net.perorin.workingManager.window;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.SystemColor;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.net.perorin.workingManager.config.Constant;

public class TimerPanel extends JPanel {

	private Font timerFont;
	private JLabel timer;
	private Calendar cal;
	private Timer t;
	private Date[] timeRecord = { null, null };

	public TimerPanel() {
		super();
		this.setBackground(SystemColor.inactiveCaptionBorder);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(250, 55));
		timerFont = new Font("MS UI Gothic", Font.PLAIN, 32);
		try {
			timerFont = Font.createFont(Font.TRUETYPE_FONT, new File(Constant.TIMER_FONT_PATH));
			timerFont = timerFont.deriveFont(41f);
		} catch (FontFormatException | IOException e) {
			e.printStackTrace();
		}

		timer = new JLabel("99:99:99");
		timer.setBounds(0, 6, 250, 50);
		timer.setVerticalAlignment(SwingConstants.CENTER);
		timer.setFont(timerFont);
		this.add(timer);

		startClock();
	}

	private void startClock() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		cal = Calendar.getInstance();
		t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				timer.setText(sdf.format(cal.getTime()));
				cal.add(Calendar.SECOND, 1);
			}
		}, 0L, 1000L);
	}

	private void updateClock() {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		t.cancel();
		t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				timer.setText(sdf.format(cal.getTime()));
				cal.add(Calendar.SECOND, 1);
			}
		}, 0L, 1000L);
		cal = Calendar.getInstance();
		timer.setText(sdf.format(cal.getTime()));
	}

	public void startTimer() {
		updateClock();
		timeRecord[0] = Calendar.getInstance().getTime();
	}

	public void setStartTimer(Date startTime) {
		timeRecord[0] = startTime;
	}

	public void stopTimer() {
		updateClock();
		timeRecord[1] = Calendar.getInstance().getTime();
	}

	public Date[] getTimeRecord() {
		return timeRecord;
	}

}
