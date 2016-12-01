package cn.hxxd.crawler;

import java.util.ArrayList;

public class Test {
	static String str = "李克强总理出席第九届全球健康促进大会开幕式并在上海自贸区考察 > 视频视频李克强在上海自贸区考察2016-11-23李克强在上海主持召开深化简政放权放管结合优化服务改革座谈会2016-11-22李克强会见世界卫生组织总干事等国际组织负责人2016-11-21李克强出席第九届全球健康促进大会开幕式并致辞2016-11-21";

	public static void main(String[] args) {
		ArrayList<String> list = new ArrayList<>();
		System.out.println(str.indexOf("2016"));
	}
}
