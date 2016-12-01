package cn.hxxd.crawler.dao;

import cn.hxxd.crawler.pojo.Info;

/**
 * 数据操作 接口层
 * 
 * @author hbm
 *
 */
public interface IDataDao {
	/**
	 * 
	 * @param value1
	 *            查询关键字
	 * @param ifs
	 *            查询条件
	 * @param title
	 *            网页标题
	 * @param text
	 *            网页内容
	 * @param url
	 *            网页链接
	 * 
	 * @return 信息实体类
	 */
	Info select(String value1, String[] ifs, String title, String text, String url);

	/**
	 * 
	 * @param bean
	 *            包含搜索关键字的 网页信息
	 */
	boolean selectMySQL(Info bean);

}
