package com.network.TaskExecutor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;

/**
 * Created by HanrAx on 2017/12/14 0014.
 */
@Service
public class AsyncTaskService {

    //通过@Async注解表明该方法是个异步方法，如果注解在类级别，则表明该类所有的方法都是异步方法。
    // 而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
    @Async
    public void executeAsyncTask(String searchKey){
        String url = "https://so.youku.com/search_video/q_" + searchKey;
        Spider.create(new YoukuVideoProcessor())
                .addUrl(url)
                .addPipeline(new DataBasePipeline())
                .thread(5)
                .run();
    }

}
