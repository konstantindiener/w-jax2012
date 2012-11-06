package de.cofinpro.wjax2012.webstart;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.junit.Before;
import org.junit.Test;

import de.cofinpro.wjax2012.webstart.ExcelSheetTableModel;

public class ExcelSheetTableModelTestCase {
	
	private ExcelSheetTableModel excelSheetTableModel;

	@Before
	public void setUp() throws BiffException, IOException {
		InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(
				"de/cofinpro/wjax2012/webstart/tax-calculation.db.import.xls");
		
		Workbook workbook = Workbook.getWorkbook(resourceAsStream);
		Sheet sheet = workbook.getSheet(0);
		
		excelSheetTableModel = new ExcelSheetTableModel(sheet);
	}

	@Test
	public void testGetColumnCount() {
		assertEquals(6, excelSheetTableModel.getColumnCount());
	}
	
	@Test
	public void testGetRowCount() {
		assertEquals(606, excelSheetTableModel.getRowCount());
	}
	
	@Test
	public void testGetValueAt() {
		assertEquals("Kalmykia", excelSheetTableModel.getValueAt(23, 3));
	}
}
