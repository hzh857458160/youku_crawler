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
