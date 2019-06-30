package com.interactive.classroom.dao.filters;

import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public abstract class BaseFilter {

    /**
     * 排序字段
     */
    protected String order;

    /**
     * 用户ID
     */
    protected String userId;

    /**
     * 用户类型
     */
    protected String userType;

    /**
     * 查询关键词
     */
    protected String keyword;

    /**
     * 统计时间间隔
     */
    protected String timeInterval;

    /**
     * 统计或查询的开始时间
     */
    protected String timeFrom;

    /**
     * 统计或查询的结束时间
     */
    protected String timeTo;

    //---------------------------------------abstract methods--------------------------------------

    /**
     * 获取查询sql语句
     * @param tableName the table name
     * @return sql
     */
    public abstract String getQuerySql(String tableName);

    /**
     * 获取统计sql语句
     * @param tableName the table name
     * @return sql
     */
    public abstract String getStatisticSql(String tableName);

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

    /**
     *
     * @param timeInterval 时间间隔
     * @return BaseFilter
     */
    public abstract BaseFilter setTimeInterval(String timeInterval);

    /**
     *
     * @param timeFrom 开始时间
     * @return BaseFilter
     */
    public abstract BaseFilter setTimeFrom(String timeFrom);

    /**
     *
     * @param timeTo 结束时间
     * @return BaseFilter
     */
    public abstract BaseFilter setTimeTo(String timeTo);

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

    public String getTimeInterval() {
        if ("hour".equals(timeInterval)) {
            timeInterval = "%Y-%m-%d %h";
        } else if ("day".equals(timeInterval)) {
            timeInterval = "%Y-%m-%d";
        } else if ("month".equals(timeInterval)) {
            timeInterval = "%Y-%m";
        }
        return timeInterval;
    }

    public String getTimeFrom() {
        return timeFrom;
    }

    public String getTimeTo() {
        return timeTo;
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
