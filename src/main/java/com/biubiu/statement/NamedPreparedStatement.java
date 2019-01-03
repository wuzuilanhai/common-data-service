package com.biubiu.statement;

import com.biubiu.exception.DataException;

import java.sql.*;
import java.util.*;

/**
 * Created by Haibiao.Zhang on 2019/1/3.
 */
public class NamedPreparedStatement {

    private final PreparedStatement statement;

    private final Map<String, List<Integer>> indexMap;

    public NamedPreparedStatement(Connection connection, String sql) throws SQLException {
        indexMap = new HashMap<>();
        String parsedSql = parse(sql, indexMap);
        this.statement = connection.prepareStatement(parsedSql);
    }

    public void setObject(String name, Object value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setObject(index, value);
        }
    }

    public void setString(String name, String value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setString(index, value);
        }
    }

    public void setInt(String name, int value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setInt(index, value);
        }
    }

    public void setLong(String name, long value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setLong(index, value);
        }
    }

    public void setTimestamp(String name, Timestamp value) throws SQLException {
        List<Integer> indexes = getIndexes(name);
        for (int index : indexes) {
            statement.setTimestamp(index, value);
        }
    }

    public boolean execute() throws SQLException {
        return statement.execute();
    }

    public ResultSet executeQuery() throws SQLException {
        return statement.executeQuery();
    }

    public int executeUpdate() throws SQLException {
        return statement.executeUpdate();
    }

    public void close() throws SQLException {
        statement.close();
    }

    public void addBatch() throws SQLException {
        statement.addBatch();
    }

    public int[] executeBatch() throws SQLException {
        return statement.executeBatch();
    }

    public PreparedStatement getStatement() {
        return statement;
    }

    /**
     * 解析sql
     *
     * @param sql      原始sql
     * @param indexMap 索引map
     * @return 解析后的sql
     */
    private String parse(String sql, Map<String, List<Integer>> indexMap) {
        int length = sql.length();
        StringBuilder parsedSql = new StringBuilder(length);
        boolean inSingleQuote = false;
        boolean inDoubleQuote = false;
        int index = 1;
        for (int i = 0; i < length; i++) {
            char c = sql.charAt(i);
            if (inSingleQuote) {
                if (c == '\'') {
                    inSingleQuote = false;
                }
            } else if (inDoubleQuote) {
                if (c == '"') {
                    inDoubleQuote = false;
                }
            } else {
                if (c == '\'') {
                    inSingleQuote = true;
                } else if (c == '"') {
                    inDoubleQuote = true;
                } else if (c == ':' && i + 1 < length
                        && Character.isJavaIdentifierStart(sql.charAt(i + 1))) {
                    int j = i + 2;
                    while (j < length
                            && Character.isJavaIdentifierPart(sql.charAt(j))) {
                        j++;
                    }
                    String name = sql.substring(i + 1, j);
                    c = '?';
                    i += name.length();
                    List<Integer> indexList = indexMap.computeIfAbsent(name, k -> new LinkedList<>());
                    indexList.add(index);
                    index++;
                }
            }
            parsedSql.append(c);
        }
        return parsedSql.toString();
    }

    /**
     * 获取索引数组
     */
    private List<Integer> getIndexes(String name) {
        List<Integer> indexes = indexMap.get(name);
        if (indexes == null) {
            throw new DataException("Parameter not found: " + name);
        }
        return indexes;
    }

}
