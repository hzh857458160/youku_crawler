package com.network.controller;

import com.network.model.YoukuInfo;
import com.network.repository.YoukuInfoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by HanrAx on 2017/12/16 0016.
 */
@RestController
public class YoukuInfoController {

    private static final Logger logger = LoggerFactory.getLogger(YoukuInfoController.class);
    @Autowired
    private YoukuInfoRepository youkuInfoRepository;

    /**
     * 查询数据库中的数据，返回list
     * @return
     */
    @GetMapping(value = "/youkuinfos")
    public List<YoukuInfo> youkuInfoList(){
        return youkuInfoRepository.findAll();
    }

    /**
     * 添加一条youkuinfo数据到数据库
     * @param youkuInfoValue
     */
    @PostMapping(value = "/youkuinfos")
    public void youkuInfoAdd(@RequestParam("youkuinfovalue") String youkuInfoValue){
        if (youkuInfoValue.contains(",")){
            String[] value = youkuInfoValue.split(",");
            if (value.length == 12){
                YoukuInfo youkuInfo = new YoukuInfo();
                youkuInfo.setVideoUrl(value[0]);
                youkuInfo.setVideoName(value[1]);
                youkuInfo.setVideoType(value[2]);
                youkuInfo.setVideoIntro(value[3]);
                youkuInfo.setVideoIntroUrl(value[4]);
                youkuInfo.setReleaseTime(value[5]);
                youkuInfo.setAuthorName(value[6]);
                youkuInfo.setPlayTimes(Integer.parseInt(value[7]));
                youkuInfo.setLikeTimes(Integer.parseInt(value[8]));
                youkuInfo.setDislikeTimes(Integer.parseInt(value[9]));
                youkuInfo.setDownloadUrl(value[10]);
                youkuInfo.setVideoTheme(value[11]);
                youkuInfoRepository.save(youkuInfo);
                logger.info("Add Success");
            }else{
                logger.info("paramerter length is "+value.length);
                logger.info(value.toString());
            }

        }


    }

    /**
     * 删除一条记录
     * @param id
     */
    @DeleteMapping(value = "/youkuinfos/{id}")
    public void youkuInfoDelete(@PathVariable("id") Integer id){
        youkuInfoRepository.delete(id);
    }

    /**
     * 取得一条记录
     * @param id
     */
    @GetMapping(value = "/youkuinfos/{id}")
    public YoukuInfo getOneYoukuInfo(@PathVariable("id") Integer id){
        return youkuInfoRepository.findOne(id);
    }
}
