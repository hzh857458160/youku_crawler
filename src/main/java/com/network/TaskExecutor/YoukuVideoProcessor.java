package com.network.TaskExecutor;

import com.network.controller.MainController;
import com.network.model.YoukuInfo;
import com.network.util.JsoupParseUtil;
import com.network.util.PlayTimeUtil;
import com.network.util.ReleaseTimeUtil;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by HanrAx on 2017/12/14 0014.
 */

//TODO 尚未更新最新的搜酷逻辑
public class YoukuVideoProcessor implements PageProcessor {

    private final String REGEX_PAGE_URL = "https://so\\.youku\\.com/search_video/q_\\w+";
    private final String REGEX_SEARCH_URL = "https://so\\.youku\\.com/search_video/q_\\w+";
    private final String REGEX_VIDEO_PLAY_URL = "https://v\\.youku\\.com/v_show/\\w+";
    private final String REGEX_VIDEO_REDIRECT_URL = "https://cps\\.youku\\.com/redirect.html?\\w+";

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100).setTimeOut(10 * 1000).setCharset("UTF-8")
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");

    static List<YoukuInfo> youkuInfoList = new ArrayList<>();
    private YoukuInfo youkuInfo = new YoukuInfo();
    private String like_url = "https://v.youku.com/action/getVideoPlayInfo?beta&timestamp=1510628914450&vid=replace_vid&param%5B%5D=updown&callback=tuijsonp6";
    private String author_url = "https://v.youku.com/action/sub?beta&callback"
            + 	"=jQuery1112007896143668629563_1510640686538&vid=replace_vid&ownerid=UMjg1MDgwODA%3D";
    //这里使用StringBuffer的原因是这几个字符串会经常变动，使用StringBuffer会比String好一点
    private StringBuffer curPageNumSb = new StringBuffer();    //当前页码
    private StringBuffer pre_page_num_sb 	= new StringBuffer();	//前一页页码
    private StringBuffer cur_page_url_sb	= new StringBuffer();   //当前网页所属的第一级页面url

    //这些list是第一级页面（视频列表页）能够爬取到的
    private List<String> video_url_list;        //存储该页面所有视频url
    private List<String> video_name_list;       //存储该页面所有视频名称
//    private List<String> release_time_list;     //存储该页面所有发布时间
    private List<String> play_time_list;        //存储该页面所有视频播放次数
    private List<String> play_time_unit_list;   //存储该页面所有视频播放次数的单位
    private List<String> author_name_list;      //存储该页面所有视频作者昵称

    //这些字符串以及整数能够从第二级页面（视频播放页）以及第三级页面（视频简介页）爬到的
    private String video_intro;             //视频简介
    private String request_like_url;		//请求点赞与点踩数url
    private String author_name;				//作者昵称
    private String request_author_url;      //请求作者昵称的url
    private String release_time;            //发布时间url
    private String cur_video_id;		    //当前视频的vid

    private int curVideoIndex;            //当前视频在list中的索引
    private int first_page_play_time = 0;	//视频列表页第一页的播放时间
    private int page_video_sum = 0;			//当前列表页的视频总数
    private int like_time;                  //视频点赞次数
    private int dislike_time;               //视频点踩次数


    @Override
    public void process(Page page) {
        //搜索关键字
        String searchKey = page.getHtml().xpath("//*[@id=\"headq\"]/@value").toString();

        /*
         * 第一级页面（视频列表页）
         * 因为webmagic不能重复爬取同一个页面，
         * 所以我把页面跳转逻辑写在了第二级页面以及第三级页面，
         * 所以第一级页面只负责获取视频列表中能获取到的视频信息（list信息初始化）
         */
        if (page.getUrl().regex(REGEX_PAGE_URL).match()) {

            try {
                curVideoIndex = 0;
                curPageNumSb.setLength(0);
                curPageNumSb.append(page.getHtml().xpath("//li[@class='current']/span/text()").toString());
                cur_page_url_sb.setLength(0);
                cur_page_url_sb.append(page.getUrl().toString());
                page_video_sum = 0;

                //视频列表页的第一页与其他页排版不同，所以单独分为两个解析方法
                if ("1".equals(curPageNumSb.toString())) {
                    //获取当前列表页视频总数
                    page_video_sum = page.getHtml().xpath("//div[@class='s_dir']").all().size();
                    //视频信息
                    video_url_list = page.getHtml().xpath("//h2[@class='base_name']/a[1]/@href").all();
                    video_name_list = page.getHtml().xpath("//h2[@class='base_name']/a[1]/@_log_title").all();
                    play_time_list = page.getHtml().xpath("//span[@class='num']/a/text()").all();
                    play_time_unit_list = page.getHtml().xpath("//span[@class='num']/text()").all();
                    page.addTargetRequest(video_url_list.get(curVideoIndex));

                    //第二页及之后的视频列表页解析
                } else {
                    //获取当前列表页视频总数
                    page_video_sum = page.getHtml().xpath("//div[@class='v']").all().size();
                    //视频信息
                    video_url_list = page.getHtml().xpath("//div[@class='sk-vlist clearfix']/div/div[4]/div[1]/a/@href").all();
                    video_name_list = page.getHtml().xpath("//div[@class='sk-vlist clearfix']/div/div[4]/div[1]/a/@title").all();
                    play_time_list = page.getHtml().xpath("//div[@class='sk-vlist clearfix']/div/div[4]/div[2]/div[2]/span[1]/text()").all();
                    author_name_list = page.getHtml().xpath("//div[@class='sk-vlist clearfix']/div/div[4]/div[2]/div[1]/span[1]/a/text()").all();
                    page.addTargetRequest(video_url_list.get(curVideoIndex));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        /*
         * 第二级页面（视频播放页）
         * 在这个页面我获取剩余大部分视频信息，并存到model中
         * 如果还有视频简介页面，则下一个页面的跳转放进第三级页面
         * 如果没有，则直接控制页面跳转
         */
        else if(page.getUrl().regex(REGEX_VIDEO_PLAY_URL).match()
                ||page.getUrl().regex(REGEX_VIDEO_REDIRECT_URL).match()) {
            try {
//                video_intro_url 	=	page.getHtml().xpath("//a[@class='desc-link']/@href").toString();
                cur_video_id        = JsoupParseUtil.parseValueFromUrl(page.getUrl().toString(), "videoId");
                if(cur_video_id == null){
                    throw new Exception("video_id is null");
                }
                request_like_url    = like_url.replace("replace_vid", cur_video_id);
                request_author_url  = author_url.replace("replace_vid", cur_video_id);
                video_intro         = page.getHtml().xpath("//div[@class='summary']/text()").toString();
                like_time           = PlayTimeUtil.parsingPlayTime(
                        JsoupParseUtil.parseValueFromUrl(request_like_url, "like"));
                dislike_time        = PlayTimeUtil.parsingPlayTime(
                        JsoupParseUtil.parseValueFromUrl(request_like_url, "dislike"));

                //视频播放页的第一页中
                if ("1".equals(curPageNumSb.toString())) {
                    //作者昵称需要动态请求，发布时间直接获取
                    author_name     = JsoupParseUtil.parseValueFromUrl(request_author_url, "author");
                    release_time    = page.getHtml().xpath("//span[@class='cl-lv-2']/text()").toString();
                    if(release_time == null){
                        release_time    = page.getHtml().xpath("//span[@class='video-status']/span/text()").toString();
                    }

                    if (author_name == null) {
                        youkuInfo.setAuthorName("no author");
                    } else {
                        youkuInfo.setAuthorName(author_name);
                    }
                    youkuInfo.setPlayTimes(PlayTimeUtil.parsingPlayTime(play_time_list.get(first_page_play_time)
                            , play_time_unit_list.get(first_page_play_time)));
                    first_page_play_time++;


                } else {//视频列表页的第二页及其他
                    release_time = page.getHtml().xpath("//span[@class='bold mr3']/text()").toString();
                    youkuInfo.setPlayTimes(PlayTimeUtil.parsingPlayTime(play_time_list.get(curVideoIndex)));
                    youkuInfo.setAuthorName(author_name_list.get(curVideoIndex));
                }


                youkuInfo.setVideoUrl(video_url_list.get(curVideoIndex));
                youkuInfo.setVideoName(video_name_list.get(curVideoIndex));
                youkuInfo.setLikeTimes(like_time);
                youkuInfo.setDislikeTimes(dislike_time);
                youkuInfo.setReleaseTime(ReleaseTimeUtil.parseReleaseTime(release_time));

                Thread.sleep((long) (Math.random() + 0.5) * 1000);

                if (video_intro != null) {
                    youkuInfo.setVideoIntro(video_intro);
                } else {
                    youkuInfo.setVideoIntro("no video introduction");
                }
                page.putField("youkuInfo", youkuInfo);
                youkuInfoList.add(youkuInfo);
                curVideoIndex++;
                nextPageRedirect(page);


            } catch (Exception e) {
                printDebugInfo(page, 2);
                if ("Page fault".equals(e.getMessage())) {
                    page.setSkip(true);
                    curVideoIndex++;
                    nextPageRedirect(page);

                }
                e.printStackTrace();

            }

        }else{
            try{
                System.out.println("the url is out of range:\n"+page.getUrl().toString());
                //因为视频列表页的第一页会有其他视频网站（不属于优酷）的视频外链，不属于解析范围
                if(page.toString().contains("/detail/show")
                        ||page.getUrl().toString().contains("list.youku.com/show")){
                    curVideoIndex++;
                }
                nextPageRedirect(page);
                page.setSkip(true);
            } catch (Exception e) {
                printDebugInfo(page, -1);
                e.printStackTrace();

            }


        }


    }
    @Override
    public Site getSite() {
        return site;
    }

    /*
     * 判断当前视频列表页内视频是否处理完成
     * 进行下一个视频索引跳转或者下一页视频列表页跳转
     * @param page
     */
    private void nextPageRedirect(Page page){
        youkuInfo = new YoukuInfo();
        System.out.println(curVideoIndex + "---------------" + page_video_sum);
        if (curVideoIndex >= page_video_sum) {
            System.out.println("Page " + curPageNumSb.toString() + " Completed");
            if ("2".equals(curPageNumSb.toString())) {
                System.out.println("All Pages Completed");
                MainController.finishFlag = true;
            }else{
                cur_page_url_sb.setLength(cur_page_url_sb.length()-1);
                String next_page = cur_page_url_sb.append(Integer.parseInt(curPageNumSb.toString()) + 1).toString();
                page.addTargetRequest(next_page);
            }
            pre_page_num_sb.setLength(0);
            pre_page_num_sb.append(curPageNumSb);

        }else{
            System.out.println("redirect to the next video");
            page.addTargetRequest(video_url_list.get(curVideoIndex));

        }
    }


    private void printDebugInfo(Page page, int pageLevel){
        System.out.println("------------------DebugInfo------------------");
        System.out.println("video url:" + page.getUrl().toString());
        System.out.println("video page number:" + curPageNumSb.toString() + "   page level:" + pageLevel);
        if (youkuInfo.getVideoUrl()!= null){
            System.out.println(youkuInfo);
        }
        System.out.println("video id:"       + cur_video_id
                + "\nauthor request url:"    + request_author_url
                + "\nlike request url:"      + request_like_url );
        System.out.println("---------------------------------------------------");

    }

}
