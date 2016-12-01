package cn.hxxd.crawler.utils;

public interface I {

	/** 信息准确度等级 */
	public interface rank {
		/** AAA级最准确 网页标题包含关键字 */
		String AAA = "AAA";

		/** AA级次准确 网页内出现关键字多次 */
		String AA = "AA";

		/** A级包含关键字出现一次 */
		String A = "A";
	}

	/** 指定关键字查询信息储存表 */
	public interface tInfo {
		/** 表名称 */
		String T_NAME = "info";
		/** 表id */
		String ID = "id";
		/** 搜索关键字 */
		String KEYWORD = "keyword";
		/** 关键字包含的链接 */
		String URL = "url";
		/** 关键字出现次数 */
		String NUM = "number";
		/** 准确度等级 */
		String PRECISION = "pre";
		/** 查询时时间 */
		String SELECT_DATA = "select_date";
		/**网页标题 */
		String TITLE = "title";
		/** 文本内容 */
		String TEXT = "text";
	}

}
