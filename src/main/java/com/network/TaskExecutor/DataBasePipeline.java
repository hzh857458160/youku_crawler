package com.network.TaskExecutor;

import com.network.model.YoukuInfo;
import com.network.repository.YoukuInfoRepository;
import com.network.util.JsoupParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;
import java.util.Map;

/**
 * Created by HanrAx on 2017/12/15 0015.
 */
public class DataBasePipeline implements Pipeline{

    @Autowired
    private YoukuInfoRepository youkuInfoRepository;


    @Override
    public void process(ResultItems resultItems, Task task) {

        String url = "http://localhost:8080/youkuinfos";
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if ("youkuInfo".equals(entry.getKey())) {
                YoukuInfo youkuInfo=(YoukuInfo) entry.getValue();
                try {
                    JsoupParseUtil.addYoukuInfo(url,youkuInfo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }

}
