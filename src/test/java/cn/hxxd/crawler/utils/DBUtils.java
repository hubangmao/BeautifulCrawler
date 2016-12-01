package cn.hxxd.crawler.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

//链接数据库
public class DBUtils {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName(PropertiesUtils.getValue("jdbcDriver", "jdbc.properties"));
			conn = DriverManager.getConnection(PropertiesUtils.getValue("jdbcUrl", "jdbc.properties"));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.print("链接异常");
		}
		return conn;
	}

	public static void closeAll(Connection conn, PreparedStatement ps, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (ps != null) {
			try {
				ps.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
