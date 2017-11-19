package org.net.perorin.workingManager.table;

import java.awt.Font;

import javax.swing.JTable;

public class WorkTable extends JTable {

	public WorkTable(WorkModel model) {
		super(model);
		this.setAutoResizeMode(AUTO_RESIZE_OFF);
		this.getColumn(WorkModel.COLUMN_HEADER[0]).setPreferredWidth(80);
		this.getColumn(WorkModel.COLUMN_HEADER[1]).setPreferredWidth(80);
		this.getColumn(WorkModel.COLUMN_HEADER[2]).setPreferredWidth(240);
		this.removeColumn(this.getColumn(WorkModel.COLUMN_HEADER[3]));
		this.removeColumn(this.getColumn(WorkModel.COLUMN_HEADER[4]));
		this.removeColumn(this.getColumn(WorkModel.COLUMN_HEADER[5]));
		this.setFont(new Font("メイリオ", Font.PLAIN, 14));
		this.setRowHeight(20);
	}

}
