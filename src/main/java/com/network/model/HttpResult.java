package com.network.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class HttpResult {

    private String errcode;
    private String msg;
    private String attach;

    public String toJSON() {
        return JSON.toJSONString(this);
    }
}
