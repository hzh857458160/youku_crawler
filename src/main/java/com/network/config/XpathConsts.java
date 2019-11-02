package com.network.config;

public class XpathConsts {

    //视频搜索url匹配值
    public static final String REGEX_SEARCH_URL = "https://so\\.youku\\.com/search_video/q_\\w+";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36";

    //获取当前页码
    public static final String CURRENT_PAGE_XPATH = "//*[@id='bpmodule-main']/div[3]/ul/li[@class='current']/text()";
    //获取查询关键字
    public static final String SEARCH_KEY_XPATH = "//*[@id=\"headq\"]/@value";
    //查询当前页面所有的视频
    public static final String CUR_PAGE_ALL_VIDEO_XPATH = "//*[@id=\"bpmodule-main\"]/div[2]/div";
}
