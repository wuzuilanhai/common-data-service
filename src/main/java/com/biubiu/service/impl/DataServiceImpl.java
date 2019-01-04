package com.biubiu.service.impl;

import com.alibaba.fastjson.JSON;
import com.biubiu.config.MultiDataSourceConfig;
import com.biubiu.constant.KpiOperationEnum;
import com.biubiu.exception.DataException;
import com.biubiu.mapper.KpiMapper;
import com.biubiu.po.Kpi;
import com.biubiu.service.DataService;
import com.biubiu.util.DbUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Haibiao.Zhang on 2018/12/17.
 */
@Service
public class DataServiceImpl implements DataService {

    @Autowired
    private KpiMapper kpiMapper;

    @Override
    public Object kpi(Integer code, String json) {
        Kpi kpi = kpiMapper.selectByPrimaryKey(code);
        if (kpi == null) throw new DataException(KpiOperationEnum.NOT_SUPPORT.getMessage());
        Connection connection = MultiDataSourceConfig.getConnection(kpi.getDatabase());
        String queryParams = kpi.getQueryParams();
        List<String> list = null;
        if (!StringUtils.isEmpty(queryParams)) {
            list = new ArrayList<>(Arrays.asList(queryParams.split(",")));
        }
        return DbUtil.query(connection, kpi.getSql(), list, JSON.parseObject(json, Map.class));
    }

}
