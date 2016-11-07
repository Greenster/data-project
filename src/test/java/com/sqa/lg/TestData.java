package com.sqa.lg;

import java.sql.*;

import org.apache.log4j.*;
import org.testng.annotations.*;

import com.sqa.lg.helpers.*;

public class TestData {
	private Logger log = Logger.getLogger(TestData.class);

	@Test(enabled = false)
	public void f() throws ClassNotFoundException, SQLException {
		// Step 1
		Class.forName("com.mysql.jdbc.Driver");
		// Step 2
		Connection dbconn = DriverManager.getConnection("jdbc:mysql://localhost:8889/sqadb", "root", "root");
		// Step 3
		Statement stmt = dbconn.createStatement();
		// Step 4
		ResultSet rs = stmt.executeQuery("select * from person");

		while (rs.next()) {
			String id = rs.getString(1);
			String name = rs.getString(2);
			String address = rs.getString(3);
			String age = rs.getString(4);
			String job = rs.getString(5);
			getLog().info("id= " + id + " name= " + name + " address= " + address + " age= " + age + " job= " + job);

		}
		// step 5
		rs.close();
		stmt.close();
		dbconn.close();
	}

	/**
	 * @return the log
	 */
	public Logger getLog() {
		return this.log;
	}

	@Test
	public void testDatabase() throws ClassNotFoundException, SQLException {

		Object[][] data = DataHelper.evalDatabaseTable("com.mysql.jdbc.Driver", "jdbc:mysql://localhost:8889/sqadb",
				"root", "root", "person");

		DataHelper.displayData(data);
	}

	@Test
	public void testExcelFile() throws InvalidExcelExtensionException {
		Object[][] data = DataHelper.getExcelFileData("", "data.xls", false);
		DataHelper.displayData(data);
	}
}
