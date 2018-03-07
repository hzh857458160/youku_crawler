package com.network.controller;

import com.network.TaskExecutor.AsyncTaskService;
import com.network.TaskExecutor.TaskExecutorConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;



/**
 * Created by HanrAx on 2017/11/24 0024.
 */
@Controller
public class MainController {

    public static boolean finishFlag = false;   //用来判断是否爬取完成
    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TaskExecutorConfig.class);
    private AsyncTaskService asyncTaskService = context.getBean(AsyncTaskService.class);

    /**
     * 搜索主页
     * @return index.html
     */
    @GetMapping(value = "/search")
    public String index(){
        return "index";
    }

    /**
     * 前端输入搜索key值后，使用post方式提交到后台，并给前端返回一个确认收到的信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @PostMapping(value = "/search")
    public void getKeyFromHtml(HttpServletRequest request, HttpServletResponse response)
            throws ServletException,IOException{
        response.setHeader("Access-Control-Allow-Origin", "*");
        String result;
        String search_key = request.getParameter("search_key");
        if("".equals(search_key) || search_key == null){
            logger.info("Data Error");
            result = "{\"ok\":\"false\"}";
        }else{
            logger.info("Search Key:"+ search_key);
            request.getSession().setAttribute("searchKey",search_key);
            result = "{\"ok\":\"true\"}";
            //开始爬取数据
            asyncTaskService.executeAsyncTask(search_key);
            context.close();
        }
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();


    }

    /**
     * 提交之后，前端跳转到等待界面
     * @return
     */
    @GetMapping(value = "/search/wait")
    public String waitForCrawl(){
        return "wait";
    }

    /**
     * 前端定时（10s一次）询问数据是否爬取完成
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException
     */
    @PostMapping(value = "/search/wait")
    public void stopWait(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String result = "{\"ok\":\""+ finishFlag + "\"}";
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.print(result);
        out.flush();
        out.close();
    }




}
