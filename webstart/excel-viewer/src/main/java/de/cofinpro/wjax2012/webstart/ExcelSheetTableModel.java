package de.cofinpro.wjax2012.webstart;

import javax.swing.table.AbstractTableModel;

import jxl.Sheet;

@SuppressWarnings("serial")
public class ExcelSheetTableModel extends AbstractTableModel {

	private final Sheet sheet;

	public ExcelSheetTableModel(Sheet sheet) {
		this.sheet = sheet;
	}

	public int getColumnCount() {
		return this.sheet.getColumns();
	}

	public int getRowCount() {
		return this.sheet.getRows();
	}

	public Object getValueAt(int row, int column) {
		return this.sheet.getCell(column, row).getContents();
	}

}
