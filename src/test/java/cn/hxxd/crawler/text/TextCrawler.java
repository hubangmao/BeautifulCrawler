package cn.hxxd.crawler.text;

import java.util.ArrayList;
import java.util.regex.Pattern;

import cn.hxxd.crawler.dao.DataimplDao;
import cn.hxxd.crawler.dao.IDataDao;
import cn.hxxd.crawler.pojo.Info;
import cn.hxxd.crawler.utils.I;
import cn.hxxd.crawler.utils.Utils;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import edu.uci.ics.crawler4j.url.WebURL;

public class TextCrawler extends WebCrawler {
	private String tag = TextCrawler.class.getSimpleName();
	// 过滤无用URL
	private static final Pattern filters = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf"
			+ "|rm|smil|wmv|swf|wma|zip|rar|gz|bmp|gif|jpe?g|png|tiff?))$");
	/** 查询关键字 */
	private static String values;

	/** 条件关键字 */
	private static String[] ifs;

	/** 符合条件储存 */
	private ArrayList<Info> list;

	/** 查询条件结果实体类 */
	private static Info bean;

	/** 数据操作层 */
	private IDataDao dao = new DataimplDao();

	@Override
	public boolean shouldVisit(Page page, WebURL url) {
		String href = url.getURL().toLowerCase();
		// url 是否以 filters字符结尾
		if (filters.matcher(href).matches()) {
			return false;
		} else {
			return true;
		}
	}

	long l;

	@Override
	public void visit(Page page) {
		String url = page.getWebURL().getURL();
		System.err.println(tag + "  符合初选 " + url);

		// HtmlParseData 存放解析网页类容实体类
		if (page.getParseData() instanceof HtmlParseData) {
			l = System.currentTimeMillis();
			HtmlParseData data = (HtmlParseData) page.getParseData();
			String title = data.getTitle().replaceAll("\\s*", "").trim();
			String text = data.getText().toString().replaceAll("\\s*", "");
			// 完全剔除文本空格
			text = text.replaceAll("\\s*", "").trim();
			// 比对网页信息是否符合
			bean = dao.select(values, ifs, title, text, url);
			
			if (list == null) {
				list = new ArrayList<>();
			}
			if (bean != null) {
				list.add(bean);
			}
			// 将信息保存到数据库
			if (list != null && list.size() > 0) {
				for (int j = 0; j < list.size(); j++) {
					boolean b = dao.selectMySQL(list.get(j));
					if (b) {
						System.err.println(tag + " MySQL插入信息" + b);
						System.err.println(tag + " 可能符合 标题=" + title);
						System.err.println(tag + " 可能符合 内容=" + text);
						System.err.println(tag + " 筛选保存 耗时=" + (System.currentTimeMillis() - l));
					}
				}
			}
			// 保存完后清除上次信息
			if (bean != null) {
				bean.clear();
			}
			list.clear();
		}
	}

	public static void keyWord(String values, String[] ifs) {
		TextCrawler.values = values;
		TextCrawler.ifs = ifs;
	}
}
