package org.net.perorin.workingManager.logic;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import org.net.perorin.workingManager.config.Constant;
import org.net.perorin.workingManager.table.WorkModel;
import org.net.perorin.workingManager.window.TimerPanel;

import jp.sf.orangesignal.csv.Csv;
import jp.sf.orangesignal.csv.CsvConfig;
import jp.sf.orangesignal.csv.handlers.StringArrayListHandler;

public class Logic {

	private Logic() {
	}

	public static void workStart(TimerPanel timer) {
		timer.startTimer();
	}

	public static void workReStart(TimerPanel timer, String time) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
			timer.setStartTimer(sdf.parse(time));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public static void workEnd(TimerPanel timer, JTextField content, JComboBox<String> category, WorkModel model) {
		timer.stopTimer();
		Date[] timeRecord = timer.getTimeRecord();
		long diff = timeRecord[1].getTime() - timeRecord[0].getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Object[] rowData = {
				sdf.format(timeRecord[0]),
				sdf.format(timeRecord[1]),
				content.getText(),
				category.getSelectedItem().toString(),
				getJobCode(category.getSelectedItem().toString()),
				String.format("%02d", diff / (1000 * 60 * 60)) + ":" + String.format("%02d", diff / (1000 * 60)) + ":" + String.format("%02d", diff / 1000)
		};
		model.addRow(rowData);
		timer.startTimer();
		content.setText("");
		category.setSelectedIndex(-1);
	}

	public static void workSave(WorkModel model) {
		if (!(model.getRowCount() > 0)) {
			return;
		}
		List<String[]> list = new ArrayList<>();
		try {
			for (int row = 0; row < model.getRowCount(); row++) {
				List<String> record = new ArrayList<>();
				for (int col = 0; col < model.getColumnCount(); col++) {
					record.add(model.getValueAt(row, col).toString());
				}
				record.add(getJobCode(record.get(2)));
				list.add((String[]) record.toArray(new String[0]));
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Calendar cal = Calendar.getInstance();
			File file = new File(Constant.WORKING_DATA_PATH + sdf.format(cal.getTime()) + ".csv");
			Csv.save(list, file, new CsvConfig(), new StringArrayListHandler());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean workLoad(WorkModel model) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		File file = new File(Constant.WORKING_DATA_PATH + sdf.format(cal.getTime()) + ".csv");
		if (file.exists()) {
			try {
				List<String[]> list = Csv.load(file, new CsvConfig(), new StringArrayListHandler());
				for (int i = 0; i < list.size(); i++) {
					if (!"".equals(list.get(i)[0])) {
						Object[] obj = { list.get(i)[0], list.get(i)[1], list.get(i)[2], list.get(i)[3], list.get(i)[4], list.get(i)[5] };
						model.addRow(obj);
					}
				}
				return true;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static ArrayList<String> getJobData() {
		ArrayList<String> ret = new ArrayList<>();
		try {
			List<String[]> list = Csv.load(new File(Constant.JOBDATA_PATH), new CsvConfig(), new StringArrayListHandler());
			for (String[] strs : list) {
				ret.add(strs[0]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public static String getJobCode(String jobName) {
		try {
			List<String[]> list = Csv.load(new File(Constant.JOBDATA_PATH), new CsvConfig(), new StringArrayListHandler());
			for (String[] strs : list) {
				if (strs[0].equals(jobName)) {
					return strs[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String getRandomSerif() {
		try {
			List<String[]> list = Csv.load(new File(Constant.SERIF_PATH), new CsvConfig(), new StringArrayListHandler());
			Random r = new Random();
			return list.get(r.nextInt(list.size()))[0];
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

}
