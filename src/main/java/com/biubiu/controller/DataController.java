package com.biubiu.controller;

import com.biubiu.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Haibiao.Zhang on 2019/1/3.
 */
@RestController
@RequestMapping("/data")
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     * kpi指标统计
     */
    @GetMapping("/kpi/{code}")
    public Object kpi(@PathVariable("code") Integer code, String json) {
        return dataService.kpi(code,json);
    }

}
