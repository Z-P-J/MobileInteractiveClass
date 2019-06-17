package com.interactive.classroom.dao.filters;

import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public abstract class BaseFilter {

    protected String order;

    protected String userId;

    protected String userType;

    protected String keyword;

    //---------------------------------------abstract methods--------------------------------------

    /**
     * 获取查询sql语句
     * @param tableName the table name
     * @return sql
     */
    public abstract String getQuerySql(String tableName);

    /**
     * 获取默认排序
     * @return String
     */
    public abstract String getDefaultOrder();

    /**
     * 设置排序规则
     * @param order 排序
     * @return BaseFilter
     */
    public abstract BaseFilter setOrder(String order);

    /**
     * 设置用户ID
     * @param userId 用户id
     * @return BaseFilter
     */
    public abstract BaseFilter setUserId(String userId);

    /**
     * 设置用户类型
     * @param userType 用户类型
     * @return BaseFilter
     */
    public abstract BaseFilter setUserType(String userType);

    /**
     * 设置关键词
     * @param keyword 关键词
     * @return BaseFilter
     */
    public abstract BaseFilter setKeyword(String keyword);

    //-------------------------------------------getter---------------------------------------

    public String getOrder() {
        return order;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserType() {
        return userType;
    }

    public String getKeyword() {
        return keyword;
    }

    //-----------------------------------------methods related to getQuerySql-----------------------------------

    String checkWhere(String sql) {
        if (sql.contains("where")) {
            sql += " and ";
        } else {
            sql += " where ";
        }
        return sql;
    }

    String wrapOrder(String order) {
        String orderBy;
        if (TextUtil.isEmpty(order) || "null".equals(order)) {
            orderBy = " order by " + getDefaultOrder() + " desc";
        } else {
            orderBy = " order by " + order + " desc";
        }
        return orderBy;
    }
}
