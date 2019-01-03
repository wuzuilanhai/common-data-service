package com.biubiu.service.impl;

import com.alibaba.fastjson.JSON;
import com.biubiu.config.MultiDataSourceConfig;
import com.biubiu.constant.KpiEnum;
import com.biubiu.constant.KpiOperationEnum;
import com.biubiu.exception.DataException;
import com.biubiu.service.DataService;
import com.biubiu.util.DbUtil;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.util.Map;

/**
 * Created by Haibiao.Zhang on 2018/12/17.
 */
@Service
public class DataServiceImpl implements DataService {

    @Override
    public Object kpi(Integer code, String json) {
        KpiEnum kpiEnum = KpiEnum.getByCode(code);
        if (kpiEnum == null) throw new DataException(KpiOperationEnum.NOT_SUPPORT.getMessage());
        Connection connection = MultiDataSourceConfig.getConnection(kpiEnum.getDatabase());
        return DbUtil.query(connection, kpiEnum.getSql(), KpiEnum.getQueryParamsByCode(code), JSON.parseObject(json, Map.class));
    }

}
