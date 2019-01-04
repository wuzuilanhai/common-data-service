package com.biubiu.util;

import com.biubiu.constant.SupportedTimePatternEnum;
import com.biubiu.exception.DataException;
import com.biubiu.statement.NamedPreparedStatement;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.Date;

/**
 * Created by Haibiao.Zhang on 2018/12/17.
 */
public class DbUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(DbUtil.class);

    /**
     * 获取所有数据表
     */
    public static List<String> getTables(Connection connection) {
        List<String> tables = Lists.newArrayList();
        DatabaseMetaData metaData = null;
        ResultSet resultSet = null;
        String tableName = null;
        try {
            metaData = connection.getMetaData();
            resultSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                tableName = resultSet.getString("TABLE_NAME");
                tables.add(tableName);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        } finally {
            close(resultSet, null, connection);
        }
        return tables;
    }

    /**
     * 获取所有数据表及表备注
     */
    public static Map<String, String> getTableAndRemark(Connection connection) {
        Map<String, String> tables = new HashMap<>();
        DatabaseMetaData metaData = null;
        ResultSet resultSet = null;
        String tableName = null;
        String tableRemark = null;
        try {
            metaData = connection.getMetaData();
            resultSet = metaData.getTables(null, "%", "%", new String[]{"TABLE"});
            while (resultSet.next()) {
                tableName = resultSet.getString("TABLE_NAME");
                tableRemark = resultSet.getString("REMARKS");
                tables.put(tableName, tableRemark);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        } finally {
            close(resultSet, null, connection);
        }
        return tables;
    }

    /**
     * 获取数据表字段
     */
    public static List<String> getColumns(Connection connection, String tableName) {
        List<String> columns = Lists.newArrayList();
        DatabaseMetaData metaData = null;
        ResultSet resultSet = null;
        String columnName = null;
        try {
            metaData = connection.getMetaData();
            resultSet = metaData.getColumns(null, "%", tableName, "%");
            while (resultSet.next()) {
                columnName = resultSet.getString("COLUMN_NAME");
                columns.add(columnName);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        } finally {
            close(resultSet, null, connection);
        }
        return columns;
    }

    /**
     * 获取数据表字段及其详细信息
     */
    public static Map<String, Map<String, String>> getColumnAndDetail(Connection connection, String tableName) {
        Map<String, Map<String, String>> columns = new HashMap<>();
        DatabaseMetaData metaData = null;
        ResultSet resultSet = null;
        String columnName = null;
        String columnRemark = null;
        String columnType = null;
        try {
            metaData = connection.getMetaData();
            resultSet = metaData.getColumns(null, "%", tableName, "%");
            while (resultSet.next()) {
                columnName = resultSet.getString("COLUMN_NAME");
                columnRemark = resultSet.getString("REMARKS");
                columnType = resultSet.getString("TYPE_NAME");
                Map<String, String> columnDetailMap = new HashMap<>();
                if (!Strings.isNullOrEmpty(columnRemark)) {
                    columnDetailMap.put("REMARKS", columnRemark);
                }
                if (!Strings.isNullOrEmpty(columnType)) {
                    columnDetailMap.put("TYPE_NAME", columnType);
                }
                columns.put(columnName, columnDetailMap);
            }
        } catch (SQLException e) {
            LOGGER.error("SQLException", e);
        } catch (Exception e) {
            LOGGER.error("Exception", e);
        } finally {
            close(resultSet, null, connection);
        }
        return columns;
    }

    /**
     * 查询总记录数
     */
    public static int count(Connection connection, String sql, List<String> keys, Map valueMap) {
        int count = 0;
        NamedPreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = new NamedPreparedStatement(connection, sql);
            initParams(keys, valueMap, preparedStatement);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt(1);  //对总记录数赋值
            }
            return count;
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        } finally {
            if (preparedStatement != null) {
                close(resultSet, preparedStatement.getStatement(), connection);
            } else {
                close(null, null, connection);
            }
        }
        return count;
    }

    /**
     * 查询结果集
     */
    public static Object query(Connection connection, String sql, List<String> keys, Map valueMap) {
        NamedPreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection.setAutoCommit(false);
            //对in查询的参数进行预处理
            sql = parseSqlIfIn(keys, valueMap, sql);
            LOGGER.info("execute sql: {}", sql);
            preparedStatement = new NamedPreparedStatement(connection, sql);
            initParams(keys, valueMap, preparedStatement);
            resultSet = preparedStatement.executeQuery();
            connection.commit();
            return convertList(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            try {
                connection.rollback();
                LOGGER.error(e.getMessage());
            } catch (SQLException e1) {
                LOGGER.error(e1.getMessage());
            }
        } finally {
            if (preparedStatement != null) {
                close(resultSet, preparedStatement.getStatement(), connection);
            } else {
                close(null, null, connection);
            }
        }
        return Collections.EMPTY_LIST;
    }

    /**
     * 对in查询的参数进行预处理
     */
    private static String parseSqlIfIn(List<String> keys, Map<String, Object> valueMap, String sql) {
        List<String> removeData = new ArrayList<>();
        if (valueMap == null) return sql;
        for (Map.Entry<String, Object> entry : valueMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            String str = value.toString();
            if (str.contains(",")) {
                List<String> params = Arrays.asList(str.split(","));
                sql = sql.replace("#" + key, getStringFromList(params));
                removeData.add(key);
            }
        }
        doRemoveData(keys, valueMap, removeData);
        return sql;
    }

    private static void doRemoveData(List<String> keys, Map<String, Object> valueMap, List<String> removeData) {
        for (String key : removeData) {
            if (keys != null) {
                keys.remove(key);
            }
            if (valueMap != null) {
                valueMap.remove(key);
            }
        }
    }

    private static String getStringFromList(List list) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append("'").append(list.get(i)).append("'");
            } else {
                sb.append("'").append(list.get(i)).append("'").append(",");
            }
        }
        return sb.toString().trim();
    }

    /**
     * 初始化参数
     */
    private static void initParams(List<String> keys, Map valueMap, NamedPreparedStatement preparedStatement) throws SQLException {
        if (keys != null && !keys.isEmpty() && valueMap != null) {
            for (String key : keys) {
                Object value = valueMap.get(key);
                if (value == null) {
                    throw new DataException("查询参数不能为空 : " + key);
                }
                String str = value.toString();
                if (value instanceof Integer) {
                    str = value.toString();
                }
                Date date = SupportedTimePatternEnum.parseDate(str);
                if (date != null) {
                    //日期格式转换
                    preparedStatement.setTimestamp(key, new Timestamp(date.getTime()));
                } else {
                    preparedStatement.setObject(key, str);
                }
            }
        }
    }

    /**
     * ResultSet转换为List
     */
    private static List convertList(ResultSet rs) throws SQLException {
        List<Map> result = Lists.newArrayList();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i));
            }
            result.add(rowData);
        }
        return result;
    }

    /**
     * 关闭连接
     */
    private static void close(ResultSet rs, Statement st, Connection connection) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
    }

}
