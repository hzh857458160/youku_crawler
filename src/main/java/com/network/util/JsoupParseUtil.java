package com.network.util;

import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 
 * Created by HanrAx
 *
 */
public class JsoupParseUtil {

	private final static Logger logger = LoggerFactory.getLogger(JsoupParseUtil.class);
	/**
	 * 一个功能强大的方法，可以通过网页获取并解析下面几个东西
	 * @param url url为获取该属性对应得网址，而key值为表中任意 ֵ["like","dislike","author","videoId"]
	 * @return
	 * @throws IOException
	 */
	public static String parseValueFromUrl(String url, String key) throws IOException{
		
		if(key == null|| "".equals(key)){
			System.out.println("the key is null");
			return null;
		}
		if(url == null|| "".equals(url)){
			System.out.println("the url is null");
			return null;
		}
		
		String value = "";
		int begin_index;
		int end_index;
		Document doc = Jsoup.connect(url)
				.userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36")
				.ignoreContentType(true)
				.get();
		String docs = doc.toString();

		
		if(!(docs.contains("videoId")||docs.contains("up")||docs.contains("down")||docs.contains("channelname"))){
			logger.info("docs: \n"+docs);
			System.out.println("web don't contain this value: "+key);
			return null;
		}
		
		if(key.equals("videoId")){
			begin_index	=	docs.indexOf("videoId:\"");
			end_index	=	docs.indexOf("\", videoId2:");
			value = docs.substring(begin_index+9, end_index);
		}
		else if(key.equals("like")){
			begin_index	=	docs.indexOf("\"up\"");
			end_index	=	docs.indexOf("\"down\"");
			value = docs.substring(begin_index+6, end_index-2);
		}
		else if(key.equals("dislike")){
			begin_index	=	docs.indexOf("\"down\"");
			end_index	=	docs.indexOf("\"},");
			value = docs.substring(begin_index+8, end_index);
			
		}
		else if(key.equals("author")){
			begin_index	=	docs.indexOf("channelname");
			end_index	=	docs.indexOf("channelurl");
			value = UnicodeUtil.unicodeToChinese(docs.substring(begin_index+14, end_index-3));
		}else{
			System.out.println("key out of range");
		}
		return value;

		
	}
}
