package de.cofinpro.wjax2012.webstart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.read.biff.BiffException;

public class Controller {
	
	private ExcelViewerMainFrame mainFrame;

	public void start() {
		mainFrame = new ExcelViewerMainFrame();
		mainFrame.setController(this);
		
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(400, 300);
		mainFrame.setVisible(true);
	}

	public void openFileRequested(File fileToOpen) {
		storeRecentlyOpenedPath(fileToOpen.getPath());
		
		try {
			WorkbookSettings ws = new WorkbookSettings();
			ws.setEncoding("ISO-8859-1");
			final Workbook workbook = Workbook.getWorkbook(fileToOpen, ws);
			
			SwingUtilities.invokeLater(new Runnable() {
				
				public void run() {
					mainFrame.setDisplayedWorkbook(workbook);
				}
			});
		} catch (BiffException e) {
			JOptionPane.showMessageDialog(mainFrame, "Unable to open the file.");
			e.printStackTrace();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(mainFrame, "Unable to open the file.");
			e.printStackTrace();
		}
	}
	
	public String readRecentlyOpenedPath() {
		File tempFile = getTempFile();
		if (tempFile.exists()) {
			FileInputStream tempFileInputStream = null;
			try {
				tempFileInputStream = new FileInputStream(tempFile);
				return new BufferedReader(new InputStreamReader(tempFileInputStream)).readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				if (tempFileInputStream != null) {
					try {
						tempFileInputStream.close();
					} catch (IOException e) {
					}
				}
			}
		}

		Properties properties = System.getProperties();
		return properties.getProperty("user.dir");
	}
	
	public void storeRecentlyOpenedPath(String recentlyOpenedPath) {
		File tempFile = getTempFile();
		
		FileOutputStream tempFileOutputStream = null;
		try {
			tempFileOutputStream = new FileOutputStream(tempFile);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(tempFileOutputStream);
			outputStreamWriter.write(recentlyOpenedPath);
			outputStreamWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (tempFileOutputStream != null) {
				try {
					tempFileOutputStream.close();
				} catch (IOException e) {
				}
			}
		}
	}

	private File getTempFile() {
		Properties properties = System.getProperties();
		String userHomePath = properties.getProperty("user.home");
		
		return new File(new File(userHomePath), "recentlyOpenedPath");
	}
}
