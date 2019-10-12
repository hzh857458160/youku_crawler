package com.network.controller;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log
public class IndexController {

    /**
     * 搜索主页
     *
     * @return index.html
     */
    @GetMapping(value = "/search")
    public String index() {
        return "index";
    }

    /**
     * 提交之后，前端跳转到等待界面
     *
     * @return
     */
    @GetMapping(value = "/search/wait")
    public String waitForCrawl() {
        return "wait";
    }
}
