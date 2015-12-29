package org.whois.dao.basic;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BaseDao {
	public static final String DRIVER = "com.mysql.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/wqi";
	public static final String USERNAME = "root";
	public static final String PWD = "mysql";

	// 获得连接
	public Connection getConn() throws ClassNotFoundException, SQLException {
		Class.forName(DRIVER);
		Connection conn = DriverManager.getConnection(URL, USERNAME, PWD);
		return conn;
	}

	// 释放资源
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

	// 执行CUD指令
	public int executeSQL(String sql, Object[] args) {
		int rs = 0;
		Connection conn = null;
		PreparedStatement state = null;
		try {
			conn = getConn();
			state = conn.prepareStatement(sql);
			if (args != null && args.length > 0) {
				for (int i = 0; i < args.length; i++) {
					state.setObject(i + 1, args[i]);
				}

			}
			rs = state.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeAll(conn, state, null);
		}
		return rs;
	}
}
