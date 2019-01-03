package com.biubiu.service;

/**
 * Created by Haibiao.Zhang on 2019/1/3.
 */
public interface DataService {

    /**
     * 获取kpi统计数据
     *
     * @param code kpi编号
     * @param json 查询json
     * @return kpi统计数据
     */
    Object kpi(Integer code, String json);

}
