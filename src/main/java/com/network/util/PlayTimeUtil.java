package com.network.util;

/**
 * Created by HanrAx on 2017/12/14 0014.
 */
public class PlayTimeUtil {
    /**
     * 处理播放次数，因为每个主题的第一页的播放时间可能带点可能带万字，所以需要将这些字符转化为int
     * @param playTime 直接从HTML上爬取到的原始播放时间字符串
     * @return 处理后的第一页的播放时间
     */
    public static int parsingPlayTime(String playTime, String unit){
        int play_time = 0;
        String[] num = null;
        if(unit.length() == 4){
            if(playTime.contains(".")){//22.5万
                num = playTime.split("\\.");
                System.out.println(".");
                play_time = Integer.parseInt(num[0])*10000+Integer.parseInt(num[1])*1000;

            }else{// 8751万
                play_time = Integer.parseInt(playTime)*10000;
            }

        }else if(playTime.contains(",")){// 4,242
            System.out.println(",");
            num = playTime.split(",");
            play_time = Integer.parseInt(num[0])*1000+Integer.parseInt(num[1]);
        }

//        System.out.println(play_time);
        return play_time;

    }
    /**
     *处理
     * @param playTime
     * @return
     * @throws Exception
     */
    public static int parsingPlayTime(String playTime) throws Exception{
        int play_time = 0;

        if(playTime == null){
            System.out.println("the playTime is null, maybe the crawler is caught");
            throw new Exception("Page fault");
        }
        if(playTime.contains(",")){// 4,242
            play_time = Integer.parseInt(playTime.replace(",", ""));
        }else{//1234
            play_time = Integer.parseInt(playTime);
        }
        return play_time;

    }
}
