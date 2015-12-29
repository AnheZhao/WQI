package org.whois.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtil {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/test";
	public static final String USERNAME = "root";
	public static final String PWD = "root";
	boolean bInited = false;

	// ��������
	public void initJDBC() throws ClassNotFoundException {
		// ����MYSQL JDBC��������
		Class.forName(DRIVER);
		bInited = true;
	}

	public Connection getConnection() throws ClassNotFoundException,
			SQLException {
		if (!bInited) {
			initJDBC();
		}
		Connection conn = DriverManager.getConnection(URL, USERNAME, PWD);
		return conn;
	}

	// �ͷ���Դ
	public void closeAll(Connection conn, Statement state, ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
			if (state != null) {
				state.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

}
