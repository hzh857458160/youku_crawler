package com.network.TaskExecutor;

import com.network.model.YoukuInfo;
import com.network.repository.YoukuInfoRepository;
import com.network.util.JsoupParseUtil;
import com.network.util.SaveOpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Map;

/**
 * Created by HanrAx on 2017/12/15 0015.
 */

@Component
public class DataBasePipeline implements Pipeline{

    private String url = "http://localhost:8080/youkuinfos";

    @Override
    public void process(ResultItems resultItems, Task task) {

        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if ("youkuInfo".equals(entry.getKey())) {
                YoukuInfo youkuInfo = (YoukuInfo) entry.getValue();
                SaveOpUtil.saveYoukuInfo(youkuInfo);
            }
        }
    }



}
