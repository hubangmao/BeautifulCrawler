package cn.hxxd.crawler.pojo;

public class Info {
	/** 表id */
	int id;
	/** 搜索关键字 */
	String keyword;
	/** 关键字包含的链接 */
	String url;
	/** 关键字出现次数 */
	String number;
	/** 准确度等级 */
	String precision;
	/** 查询时时间 */
	String select_date;
	/** 网页标题 */
	String title;
	/** 文本内容 */
	String text;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/** 搜索关键字 */
	public String getKeyword() {
		return keyword;
	}

	/** 搜索关键字 */
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	/** 关键字所在网页 */
	public String getUrl() {
		return url;
	}

	/** 关键字所在网页 */
	public void setUrl(String url) {
		this.url = url;
	}

	/** 关键字出现次数 */
	public String getNumber() {
		return number;
	}

	/** 关键字出现次数 */
	public void setNumber(String number) {
		this.number = number;
	}

	/** 准确度等级 */
	public String getPrecision() {
		return precision;
	}

	/** 准确度等级 */
	public void setPrecision(String precision) {
		this.precision = precision;
	}

	/** 查询到信息是时间 */
	public String getSelectDate() {
		return select_date;
	}

	/** 查询到信息是时间 */
	public void setSelectDate(String select_date) {
		this.select_date = select_date;
	}

	/** 网页标题 */
	public String getTitle() {
		return title;
	}

	/** 网页标题 */
	public void setTitle(String title) {
		this.title = title;
	}

	/** 文本内容 */
	public String getText() {
		return text;
	}

	/** 文本内容 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return "Info [id=" + id + ", keyword=" + keyword + ", url=" + url + ", number=" + number + ", precision="
				+ precision + ", select_date=" + select_date + ", title=" + title + ", text=" + text + "]";
	}

	public void clear() {
		/** 表id */
		this.id = 0;
		/** 搜索关键字 */
		this.keyword = null;
		/** 关键字包含的链接 */
		this.url = null;
		/** 关键字出现次数 */
		this.number = null;
		/** 准确度等级 */
		this.precision = null;
		/** 查询时时间 */
		this.select_date = null;
		/** 文本内容 */
		this.text = null;
		/** 网页标题 */
		this.title = null;
	}

}
