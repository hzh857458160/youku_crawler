package com.network.util;

import com.network.model.YoukuInfo;
import com.network.repository.YoukuInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by HanrAx on 2018/3/7 0007.
 */
@Component
public class SaveOpUtil {
    private static SaveOpUtil saveOpUtil;

    @Autowired
    private YoukuInfoRepository youkuInfoRepository;

    @PostConstruct
    public void init(){
        saveOpUtil = this;
    }

    public static void saveYoukuInfo(YoukuInfo youkuInfo){
        saveOpUtil.youkuInfoRepository.save(youkuInfo);
    }

}
