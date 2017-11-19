package org.net.perorin.workingManager.table;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class WorkModel extends DefaultTableModel {

	public static final String[] COLUMN_HEADER = { "開始時間", "終了時間", "作業内容", "作業分類", "作業コード", "作業時間" };
	private static final String[][] CLUMN_INIT = { { "", "", "" } };
	boolean[] columnEditables = new boolean[] { false, true, true };

	public WorkModel() {
		super(CLUMN_INIT, COLUMN_HEADER);
		this.setRowCount(0);
		this.addTableModelListener(new TableModelListener() {

			@Override
			public void tableChanged(TableModelEvent e) {
				if (e.getColumn() == 1 && e.getLastRow() < WorkModel.this.getRowCount() - 1) {
					String stopTime = WorkModel.this.getValueAt(e.getLastRow(), e.getColumn()).toString();
					WorkModel.this.setValueAt(stopTime, e.getLastRow() + 1, e.getColumn() - 1);
				}
			}
		});
	}

	public boolean isCellEditable(int row, int column) {
		return columnEditables[column];
	}

}
