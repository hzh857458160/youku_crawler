package com.network.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by HanrAx on 2017/12/14 0014.
 */
public class ReleaseTimeUtil {

    /**
     * 解析发布时间，在视频播放页面的右边，有时候会出现视频发布日期，有时候不是
     * @param releaseTime
     * @return null或者正确日期
     */
    public static String parseReleaseTime(String releaseTime){
        String release_time = releaseTime;
        if(release_time == null||"".equals(release_time)){
            return null;
        }else{
            Matcher m = Pattern.compile("[^0-9]").matcher(release_time);
            release_time = m.replaceAll("").trim();
            if(release_time.length()<5){
                System.out.println("the number is too short");
                return null;
            }else{
                return release_time;
            }
        }


    }
}
