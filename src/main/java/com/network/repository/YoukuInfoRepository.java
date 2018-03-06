package com.network.repository;

import com.network.model.YoukuInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository类：接口类，JpaRepository<model类名, model的id类型>
 * Created by HanrAx on 2017/12/15 0015.
 */
public interface YoukuInfoRepository extends JpaRepository<YoukuInfo, Integer> {
}
