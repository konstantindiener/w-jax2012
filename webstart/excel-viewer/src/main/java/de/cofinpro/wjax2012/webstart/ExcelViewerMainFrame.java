package de.cofinpro.wjax2012.webstart;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

import jxl.Sheet;
import jxl.Workbook;

@SuppressWarnings("serial")
public class ExcelViewerMainFrame extends JFrame {
	
	private JTabbedPane worksheetsPane;
	
	private JButton openFileButton;

	private Controller controller;

	public ExcelViewerMainFrame() throws HeadlessException {
		super("Excel Viewer");
		
		initUi();
	}
	
	private void initUi() {
		setLayout(new BorderLayout());
		
		this.worksheetsPane = new JTabbedPane();
		this.add(worksheetsPane, BorderLayout.CENTER);
		
		JPanel buttonPanel = new JPanel();
		this.add(buttonPanel, BorderLayout.NORTH);
		
		this.openFileButton = new JButton("Open file ...");
		this.openFileButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent actionEvent) {
				openFileRequested();
			}
		});
		buttonPanel.add(openFileButton);
	}
	
	protected void openFileRequested() {
		JFileChooser openFileChooser = new JFileChooser();
		openFileChooser.setSelectedFile(new File(this.controller.readRecentlyOpenedPath()));
		
		if (openFileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			this.controller.openFileRequested(openFileChooser.getSelectedFile());
		}
	}

	public void setDisplayedWorkbook(Workbook workbook) {
		this.worksheetsPane.removeAll();
		
		Sheet[] sheets = workbook.getSheets();
		for (Sheet sheet : sheets) {
			JPanel sheetPanel = new JPanel(new BorderLayout());
			
			JTable sheetTable = new JTable();
			sheetTable.setModel(new ExcelSheetTableModel(sheet));
			sheetPanel.add(new JScrollPane(sheetTable), BorderLayout.CENTER);
			
			this.worksheetsPane.add(sheetPanel, sheet.getName());
		}
	}

	public Controller getController() {
		return controller;
	}

	public void setController(Controller controller) {
		this.controller = controller;
	}
}
