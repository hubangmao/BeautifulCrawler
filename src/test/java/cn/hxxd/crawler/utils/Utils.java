package cn.hxxd.crawler.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.drew.metadata.Tag;

import cn.hxxd.crawler.pojo.Info;

public class Utils {
	private static Info bean;
	private static String tag = Utils.class.getSimpleName();

	public static Document getDocument(String url, String err) {
		try {
			return Jsoup.connect(url).get();
		} catch (IOException e) {
			System.out.println(err);
			System.out.println("网址有误:" + e.getMessage());
		}
		return null;
	}

	/** 拿到此时时间 */
	public static String getSelTime() {
		Calendar c2 = Calendar.getInstance();
		Date date = c2.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}

	public static Info getBean() {
		if (bean == null) {
			bean = new Info();
		}
		return bean;
	}

	public static boolean delete(String tabName) {

		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "delete from " + tabName;
			ps = conn.prepareStatement(sql);
			int countUser = ps.executeUpdate();
			if (countUser > 0) {
				System.out.println(tag + " 清除上次搜索记录成功 总数=" + countUser);
			}
			return countUser > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtils.closeAll(conn, ps, null);
		}
		return false;
	}
}
