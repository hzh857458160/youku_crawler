package com.network.util;

/**
 * Created by HanrAx on 2017/12/14 0014.
 */
public class UnicodeUtil {
    /**
     * 将Unicode编码的字符转为中文编码字符
     * @param uni_string
     * @return
     */
    public static String unicodeToChinese(String uni_string){
        StringBuffer sb = new StringBuffer();
        int index = uni_string.indexOf("\\u");
        if(index > 0){
            String head = uni_string.substring(0,index);
            sb.append(head);
        }
        String[] hex = uni_string.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {
            int hexlen = hex[i].length();
            int data;
            if(hexlen > 4){
                String extra = hex[i].substring(4, hexlen);
                hex[i] = hex[i].substring(0,4);
                data = Integer.parseInt(hex[i], 16);
                sb.append((char) data);
                sb.append(extra);
            }else{
                data = Integer.parseInt(hex[i], 16);
                sb.append((char) data);
            }

        }
        return sb.toString();
    }
}
