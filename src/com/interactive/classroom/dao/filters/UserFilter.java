package com.interactive.classroom.dao.filters;

import com.interactive.classroom.utils.TextUtil;

/**
 * @author Z-P-J
 */
public class UserFilter extends BaseFilter {

    UserFilter() { }

    @Override
    public String getQuerySql(String tableName) {
        String sql = "select * from " + tableName;
//        if (!TextUtil.isEmpty(homeworkId)) {
//            sql += " where homework_id=" + homeworkId;
//        }
        if (!TextUtil.isEmpty(keyword)) {
            sql = checkWhere(sql);
            sql += "(file_name like '%" + keyword + "%' or uploader_name like '%" + keyword + "%')";
        }
//        if (!TextUtil.isEmpty(timeFrom) && !TextUtil.isEmpty(timeTo)) {
//            sql = checkWhere(sql);
//            sql += "upload_time between '" + timeFrom + "' and '" + timeTo + "'";
//        }
//        if (!TextUtil.isEmpty(minSize) && !TextUtil.isEmpty(maxSize)) {
//            sql = checkWhere(sql);
//            sql += "file_size between " + minSize + " and " + maxSize;
//        }
        sql += wrapOrder(order);
        return sql;
    }

    @Override
    public String getDefaultOrder() {
        return "register_date";
    }

    @Override
    public UserFilter setOrder(String order) {
        this.order = order;
        return this;
    }

    @Override
    public UserFilter setUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public UserFilter setUserType(String userType) {
        this.userType = userType;
        return this;
    }

    @Override
    public UserFilter setKeyword(String keyword) {
        this.keyword = keyword;
        return this;
    }

}
