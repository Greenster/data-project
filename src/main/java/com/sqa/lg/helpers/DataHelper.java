/**
 *   File Name: DataHelper.java<br>
 *
 *   Green, Lorne<br>
 *   Java <br>
 *   <br>
 *   Created: Nov 5, 2016
 *
 */

package com.sqa.lg.helpers;

import java.io.*;
import java.sql.*;
import java.util.*;

import org.apache.log4j.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;

/**
 * DataHelper //ADDD (description of class)
 * <p>
 * //ADDD (description of core fields)
 * <p>
 * //ADDD (description of core methods)
 *
 * @author Green, Lorne
 * @version 1.0.0
 * @since 1.0
 *
 */
public class DataHelper {
	public static Object[][] sampleData = {
			{ "1", "Lorne G", "3630 William Avenue, Miami, FL 33133", "41", "QA Engineer" },
			{ "2", "Janet Hill", "465 NE 26th Street Miami, FL 33136", "23", "Lawyer" },
			{ "3", "Sam Privit", "2215 E 38th Ave, Denver, CO 80205 ", "40", "Farmer" },
			{ "4", "Lottie Downey", "3401 NE 38th Ave, Aventura, FL 33180", "71", "Principal" },
			{ "5", "Roy Allen", "4555 Las Vegas Blvd N, Las Vegas | NV 89115-0558", "10", "Musician" } };

	private static Logger log = Logger.getLogger(DataHelper.class);

	public static void displayData(Object[][] data) {

		for (int i = 0; i < data.length; i++) {
			String dataLine = "";
			for (int j = 0; j < data[i].length; j++) {
				dataLine += data[i][j];
				dataLine += "  \t";
			}
			getLog().info(dataLine.trim());
		}
	}

	public static void displayData(Object[][] data, Logger logger) {

	}

	public static Object[][] evalDatabaseTable(String driverClassString, String databaseStringUrl, String username,
			String password, String tableName) throws SQLException, ClassNotFoundException {
		int columnCount;
		Object[][] data;
		// Step 1
		Class.forName(driverClassString);
		// Step 2
		Connection dbconn = DriverManager.getConnection(databaseStringUrl, username, password);
		// Step 3
		Statement stmt = dbconn.createStatement();
		// Step 4
		ResultSet rs = stmt.executeQuery("select * from " + tableName);

		columnCount = rs.getMetaData().getColumnCount();
		List<Object[]> dataCollection = new ArrayList<>();
		while (rs.next()) {
			Object[] rowData = new Object[columnCount];

			for (int i = 0; i < rowData.length; i++) {
				rowData[i] = rs.getString(i + 1);
			}
			dataCollection.add(rowData);

		}
		data = new Object[dataCollection.size()][];
		for (int i = 0; i < data.length; i++) {
			data[i] = dataCollection.get(i);

		}
		// step 5
		rs.close();
		stmt.close();
		dbconn.close();
		return data;

	}

	public static Object[][] getExcelFileData(String fileLocation, String fileName, Boolean hasLabels)
			throws InvalidExcelExtensionException {
		Object[][] resultsObject;
		String[] fileNameParts = fileName.split("[.]");
		String extension = fileNameParts[fileNameParts.length - 1];
		ArrayList<Object> results = null;
		if (extension.equalsIgnoreCase("xlxs")) {
			results = getNewExcelFileResults(fileLocation, fileName, hasLabels);
		} else if (extension.equalsIgnoreCase("xls")) {
			results = getOldExcelFileResults(fileLocation, fileName, hasLabels);
		} else {
			throw new InvalidExcelExtensionException();

		}
		resultsObject = new Object[results.size()][];
		results.toArray(resultsObject);
		return resultsObject;

	}

	/**
	 * @return the log
	 */
	public static Logger getLog() {
		return log;
	}

	/**
	 * @param fileLocation
	 * @param fileName
	 * @param hasLabels
	 * @return
	 */
	private static ArrayList<Object> getNewExcelFileResults(String fileLocation, String fileName, Boolean hasLabels) {
		// TODO Auto-generated method stub

		ArrayList<Object> results = new ArrayList<Object>();
		try {
			String fullFilePath = fileLocation + fileName;
			InputStream newExcelFormatFile = new FileInputStream(new File(fullFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(newExcelFormatFile);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			if (hasLabels) {
				rowIterator.next();
			}
			while (rowIterator.hasNext()) {
				ArrayList<Object> rowData = new ArrayList<Object>();
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + "\t\t\t");
						rowData.add(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t\t\t");
						rowData.add(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t\t\t");
						rowData.add(cell.getStringCellValue());
						break;
					}
				}
				Object[] rowDataObject = new Object[rowData.size()];
				rowData.toArray(rowDataObject);
				results.add(rowDataObject);
				System.out.println("");
			}
			newExcelFormatFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;
	}

	/**
	 * @param fileLocation
	 * @param fileName
	 * @param hasLabels
	 * @return
	 */
	private static ArrayList<Object> getOldExcelFileResults(String fileLocation, String fileName, Boolean hasLabels) {
		// TODO Auto-generated method stub

		ArrayList<Object> results = new ArrayList<Object>();
		try {
			String fullFilePath = fileLocation + fileName;
			InputStream newExcelFormatFile = new FileInputStream(new File(fullFilePath));
			XSSFWorkbook workbook = new XSSFWorkbook(newExcelFormatFile);
			XSSFSheet sheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = sheet.iterator();
			if (hasLabels) {
				rowIterator.next();
			}
			while (rowIterator.hasNext()) {
				ArrayList<Object> rowData = new ArrayList<Object>();
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.cellIterator();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					switch (cell.getCellType()) {
					case Cell.CELL_TYPE_BOOLEAN:
						System.out.print(cell.getBooleanCellValue() + "\t\t\t");
						rowData.add(cell.getBooleanCellValue());
						break;
					case Cell.CELL_TYPE_NUMERIC:
						System.out.print(cell.getNumericCellValue() + "\t\t\t");
						rowData.add(cell.getNumericCellValue());
						break;
					case Cell.CELL_TYPE_STRING:
						System.out.print(cell.getStringCellValue() + "\t\t\t");
						rowData.add(cell.getStringCellValue());
						break;
					}
				}
				Object[] rowDataObject = new Object[rowData.size()];
				rowData.toArray(rowDataObject);
				results.add(rowDataObject);
				System.out.println("");
			}
			newExcelFormatFile.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return results;

	}

}
