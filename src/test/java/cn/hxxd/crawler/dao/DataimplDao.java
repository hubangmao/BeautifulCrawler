package cn.hxxd.crawler.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.hxxd.crawler.pojo.Info;
import cn.hxxd.crawler.utils.DBUtils;
import cn.hxxd.crawler.utils.I;
import cn.hxxd.crawler.utils.Utils;

/**
 * 数据操作层
 * 
 * @author hbm
 *
 */
public class DataimplDao implements IDataDao {
	private String tag = DataimplDao.class.getSimpleName();
	// 布尔值说明 只有网页标题包含 关键字 以下任何值为true 都符合查询条件
	/** URL 是否包含查询关键字 */
	private boolean b1;
	/** URL 是否包含查询条件 */
	private boolean b2;
	/** 网页标题 是否包含查询条件 */
	private boolean b3;

	@Override
	synchronized public Info select(String value1, String[] ifS, String title, String text, String url) {
		Info bean = Utils.getBean();

		String arr1 = null;
		for (String arr2 : ifS) {
			arr2 += arr2;
			arr1 = "," + arr2;
		}
		// 比较标题是否包含 关键字
		if (compareTitle(value1, ifS, title, url) != -1) {
			System.out.println("胡邦茂" + url + title + title.indexOf(value1) + text);
			// 标题包含关键字 符合度等级 AAA
			bean.setPrecision(I.rank.AAA);
		}

		// 关键字出现次数
		int len = text.split(value1).length - 1;
		bean.setNumber(String.valueOf(len));

		System.err.println("固然条件查看" + b1 + b2 + b3 + bean.getPrecision());
		if (b1 || b2 || b3 && bean.getPrecision() != null) {
			bean.setKeyword(value1 + " 条件=" + arr1);
			bean.setUrl(url);
			bean.setSelectDate(Utils.getSelTime());
			bean.setTitle(title);
			bean.setText(text);
			System.out.println(bean.toString());
			remove();
			return bean;
		}

		// 比对 内容是否包含 关键字 准确度等级 A
		if (compareText(value1, text) != -1) {
			String s = bean.getPrecision();
			if (s != null) {
				bean.setPrecision(I.rank.A + s);
			} else {
				bean.setPrecision(I.rank.A);
			}
		}

		// 比对 内容是否包含查询条件
		if (compareIf(text, ifS) == -1) {
			return null;
		}

		// 等级不为空 保存搜索关键字相关信息
		if (bean.getPrecision() != null && len >= 1) {
			bean.setKeyword(value1 + " 条件=" + arr1);
			bean.setUrl(url);
			bean.setSelectDate(Utils.getSelTime());
			bean.setTitle(title);
			bean.setText(text);
		} else {
			bean = null;
			// 递归 关键字过长 拆分在查
			if (value1.length() >= 8) {
				System.err.println(tag + " 关键字过长 " + value1 + "截取查询");
				value1 = value1.substring(0, 7);
				select(value1, ifS, title, text, url);
			}
		}

		remove();
		return bean;
	}

	/** 初始化条件 */
	private void remove() {
		b1 = b2 = b3 = false;
		System.out.println("初始化值=" + b1 + b2 + b3);
	}

	/**
	 * 比对 查询条件
	 * 
	 * @param text
	 *            网页内容
	 * @param ifS
	 *            条件数组
	 * @return -1 不符合条件
	 */
	private int compareIf(String text, String[] ifS) {
		int j = 0;
		for (int i = 0; i < ifS.length; i++) {
			j = text.indexOf(ifS[i]);
			if (j == -1) {
				return j;
			}
		}
		return j;
	}

	/**
	 * 比对网页内容
	 * 
	 * @param value1
	 *            关键字
	 * @param text
	 *            网页内容
	 * @return -1 不包含
	 */
	private int compareText(String value1, String text) {
		// 网页 内容是否包含 关键字
		return text.indexOf(value1);
	}

	/**
	 * 比对 网页标题
	 * 
	 * @param value1
	 *            关键字
	 * @param ifS
	 *            条件
	 * @param title
	 *            网页标题
	 * @param url
	 *            信息来源url
	 * @return
	 */
	private int compareTitle(String value1, String[] ifS, String title, String url) {
		int j = title.indexOf(value1);

		if (j != -1) {
			// URL 是否包含 关键字
			int j1 = url.indexOf(value1);
			if (j1 != -1) {
				b1 = true;
			} else {
				b1 = false;
			}

			for (int i = 0; i < ifS.length; i++) {
				// URL 是否包含查询条件 字符
				if (url.indexOf(ifS[i]) == -1) {
					b2 = false;
				} else {
					b2 = true;
				}
				// 标题 是否包含查询条件 字符
				if (title.indexOf(ifS[i]) == -1) {
					b3 = false;
				} else {
					b3 = true;
				}
			}
		}

		return j;
	}

	@Override
	synchronized public boolean selectMySQL(Info bean) {
		// 查询信息是否存在 true存在
		if (select(bean.getUrl())) {
			System.err.println(tag + "数据库存在该信息" + bean.getUrl() + "查询关键字" + bean.getKeyword());
			return false;
		}

		// 不存在则插入信息
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		try {
			String sql = "insert into " + I.tInfo.T_NAME + "(" + I.tInfo.KEYWORD + "," + I.tInfo.URL + "," + I.tInfo.NUM
					+ "," + I.tInfo.PRECISION + "," + I.tInfo.SELECT_DATA + "," + I.tInfo.TITLE + "," + I.tInfo.TEXT
					+ ") values(?,?,?,?,?,?,?)";
			System.out.println(sql);
			ps = conn.prepareStatement(sql);

			ps.setString(1, bean.getKeyword());
			ps.setString(2, bean.getUrl());
			ps.setString(3, bean.getNumber());
			ps.setString(4, bean.getPrecision());
			ps.setString(5, bean.getSelectDate());
			ps.setString(6, bean.getTitle());
			ps.setString(7, bean.getText());
			int count = ps.executeUpdate();
			return count > 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} finally {
			DBUtils.closeAll(conn, ps, null);
		}

	}

	/**
	 * 
	 * @param url
	 * @return true 表示该最新政策存在
	 */
	synchronized private boolean select(String url) {
		Connection conn = DBUtils.getConnection();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			String sql = "select * from " + I.tInfo.T_NAME + " where " + I.tInfo.URL + " =?";
			ps = conn.prepareStatement(sql);
			ps.setString(1, url);
			rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			DBUtils.closeAll(conn, ps, rs);
		}
	}

}
