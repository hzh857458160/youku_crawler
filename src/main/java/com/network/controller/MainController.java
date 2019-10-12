package com.network.controller;

import com.alibaba.fastjson.JSONObject;
import com.network.TaskExecutor.AsyncTaskService;
import com.network.config.TaskExecutorConfig;
import com.network.model.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



/**
 * Created by HanrAx on 2017/11/24 0024.
 */
@RestController
@Slf4j
public class MainController {

    public static boolean finishFlag = false;   //用来判断是否爬取完成



    /**
     * 前端输入搜索key值后，使用post方式提交到后台，并给前端返回一个确认收到的信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping(value = "/search")
    public String getKeyFromHtml(HttpServletRequest request,
                                 HttpServletResponse response) {
        HttpResult httpResult = new HttpResult();
        String searchKey = request.getParameter("SEARCH_KEY");

        try (AnnotationConfigApplicationContext context =
                     new AnnotationConfigApplicationContext(TaskExecutorConfig.class)) {
            if (StringUtils.isNotEmpty(searchKey)) {
                log.info("SEARCH_KEY is null");
                httpResult.setErrcode("101");
                httpResult.setMsg("SEARCH_KEY can not be null");
                return httpResult.toJSON();
            }

            log.info("the search key is [{}]", searchKey);
            request.getSession().setAttribute("searchKey", searchKey);
            httpResult.setErrcode("0");
            httpResult.setMsg("youku crawler start!");
            AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);
            asyncTaskService.executeAsyncTask(searchKey);


        } catch (Exception e) {
            log.error("Unknown error: {}", e.getMessage());
            httpResult.setErrcode("103");
            httpResult.setMsg("Unknown error: " + e.getMessage());
        }

        return httpResult.toJSON();
    }


    /**
     * 前端定时（10s一次）询问数据是否爬取完成
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @PostMapping(value = "/search/wait")
    public String stopWait(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        JSONObject attachJson = new JSONObject();
        attachJson.put("ok", finishFlag);

        HttpResult httpResult = new HttpResult();
        httpResult.setErrcode("0");
        httpResult.setMsg("result sent success");
        httpResult.setAttach(attachJson.toJSONString());
        return httpResult.toJSON();
    }




}
