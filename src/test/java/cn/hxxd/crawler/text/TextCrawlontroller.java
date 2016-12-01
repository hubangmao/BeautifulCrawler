package cn.hxxd.crawler.text;

import cn.hxxd.crawler.utils.I;
import cn.hxxd.crawler.utils.Utils;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class TextCrawlontroller {
	public static void main(String[] args) {
		Utils.delete(I.tInfo.T_NAME);
		try {
			getTextInfo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void getTextInfo() throws Exception {
		// 抓取初始化
		CrawlConfig config = new CrawlConfig();
		// 中间数据储存
		config.setCrawlStorageFolder("F:/dow/dow1");
		// 每隔一秒请求一次
		config.setPolitenessDelay(100);

		// 最大抓取深度 -1无线深度
		config.setMaxDepthOfCrawling(-1);

		// 抓取网页的最大数量 -1无限页
		config.setMaxPagesToFetch(1000);

		// 设置是否抓取 二进制文件 例如 图片 pdf文件 等 false不抓取
		config.setIncludeBinaryContentInCrawling(false);

		// 这个配置参数可以用来设置爬行是可恢复的
		config.setResumableCrawling(false);

		// 需要验证设置账号和密码
		// config.setProxyUsername("");
		// config.setProxyPassword("");

		// 实例化此爬网控制器。
		PageFetcher pageFetcher = new PageFetcher(config);
		RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
		RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
		CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

		// 某些网站无法爬取 设置为 true
		// controller.setCoerce(true);

		// 添加你要爬取的url种子
		// controller.addSeed("http://www.gov.cn/zhuanti/lkq201611kc/index.htm");
		controller.addSeed("http://www.soqi.cn/search.xhtml?keywords=%E5%9B%9B%E5%B7%9D&city=100000&search_type=1");

		// 增加查询关键字,条件
		TextCrawler.keyWord("成都", new String[] { "电话号码", "2016" });

		controller.start(TextCrawler.class, 1);

		// 爬取10分钟 后停止抓取
		/*
		 * Thread.sleep(1000 * 60 * 10);
		 * 
		 * controller.shutdown(); controller.waitUntilFinish();
		 * System.err.println("爬虫已停止");
		 */
	}
}
