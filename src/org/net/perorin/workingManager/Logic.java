package org.net.perorin.workingManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import jp.sf.orangesignal.csv.Csv;
import jp.sf.orangesignal.csv.CsvConfig;
import jp.sf.orangesignal.csv.handlers.StringArrayListHandler;

public class Logic {

	private static ArrayList<File> list = null;

	private Logic() {
	}

	public static ArrayList<String> getJobDate() {
		ArrayList<String> ret = new ArrayList<>();
		try {
			List<String[]> list = Csv.load(new File("./META-INF/jobData/job.csv"), new CsvConfig(), new StringArrayListHandler());
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
			List<String[]> list = Csv.load(new File("./META-INF/jobData/job.csv"), new CsvConfig(), new StringArrayListHandler());
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

	private static void serifAreaReset(Window window) {
		window.serifArea.setText("");
		window.serifArea.setVisible(false);
	}

	public static void setSerifGreeting(Window window) {
		serifAreaReset(window);
		list = listFiles(new File("./META-INF/serif/greeting"));
		Random r = new Random();
		window.setSerifFrame(list.get(r.nextInt(list.size())).toString());
	}

	public static void setSerifWorkLap(Window window) {
		serifAreaReset(window);
		list = listFiles(new File("./META-INF/serif/workLap"));
		Random r = new Random();
		window.setSerifFrame(list.get(r.nextInt(list.size())).toString());
	}

	public static void setSerifOther(Window window) {
		try {
			window.serifArea.setText("");
			List<String[]> list = Csv.load(new File("./META-INF/serif/other/serif.csv"), new CsvConfig(), new StringArrayListHandler());
			Random r = new Random();
			String strs[] = list.get(r.nextInt(list.size()))[0].split("\\\\n");
			for (String str : strs) {
				window.serifArea.append(str);
				window.serifArea.append("\n");
			}
			window.serifArea.setVisible(true);
			window.setSerifFrame("./META-INF/serif/frame/nc83045.png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void workStart(Window window) {
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(sdf.format(cal.getTime()));
		sb.append(" ～ ");
		window.workingData.append(sb.toString());
		workWrite(sdf.format(cal.getTime()));
	}

	public static void workLap(Window window) {
		String comma = ",";
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		StringBuilder sb = new StringBuilder();
		sb.append(sdf.format(cal.getTime()));
		sb.append(" ： ");
		sb.append(window.workingContent.getText());
		sb.append("\n");
		sb.append(sdf.format(cal.getTime()));
		sb.append(" ～ ");
		window.workingData.append(sb.toString());

		sb = new StringBuilder();
		sb.append(comma);
		sb.append(sdf.format(cal.getTime()));
		sb.append(comma);
		sb.append(window.workingContent.getText());
		sb.append(comma);
		sb.append(getJobCode((String) window.category.getSelectedItem()));
		sb.append("\n");
		sb.append(sdf.format(cal.getTime()));
		workWrite(sb.toString());
	}

	public static void workWrite(String str) {
		String fileName = null;
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fileName = new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime());
			file = new File("./workingData/" + fileName + ".csv");
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file, true);
			bw = new BufferedWriter(fw);
			bw.write(str);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bw.close();
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static ArrayList<File> listFiles(File path) {
		ArrayList<File> list = new ArrayList<File>();

		File[] files = path.listFiles();
		for (File file : files) {
			if (!file.getName().equals("")) {
				list.add(file);
			}
		}
		return list;
	}

}
