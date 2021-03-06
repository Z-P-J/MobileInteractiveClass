package com.interactive.classroom.constant;

/**
 * @author Z-P-J
 */
public enum ActionType {

    //===================================================登录================================================
    ACTION_LOG_IN("log_in"),

    ACTION_SIGN_UP("sign_up"),

    ACTION_CHECK_USER_NAME("check_user_name"),


    //===================================================用户================================================
    /**
     * 查询用户
     */
    ACTION_QUERY_USRES("query_users"),

    /**
     * 获取某用户详细信息
     */
    ACTION_GET_USRE_DETAIL("get_user_detail"),

    /**
     * 删除用户
     */
    ACTION_DELETE_USER("delete_user"),

    /**
     * 更新用户信息
     */
    ACTION_UPDATE_USER("update_user"),

    /**
     * 添加用户
     */
    ACTION_ADD_USER("add_user"),
    /**
     * 导出用户数据
     */
    ACTION_EXPORT_USERS("export_users"),

    //===========================================================文件====================================================
    /**
     * 获取所有文件信息action（管理员）
     */
    ACTION_QUERY_FILES("query_files"),

    /**
     * 将文件到处到Excel文档action（管理员）
     */
    ACTION_EXPORT_FILES("export_files"),

    /**
     * 删除文件action
     */
    ACTION_DELETE_FILE("delete_file"),

    /**
     * 获取文件详细信息action
     */
    ACTION_GET_FILE_DETAIIL("get_file_detail"),

    /**
     * 更新文件信息action
     */
    ACTION_UPDATE_FILE_INFO("update_file_info"),
    
    
    //=========================================================考勤=====================================================
    /**
     * 获取考勤信息
     */
    ACTION_GET_ATTENDANCES("get_attendances"),
    /**
     * （学生）进行考勤
     */
    ACTION_CHECK_ATTENDANCE("check_attendance"),
    /**
     * 查询考勤信息
     */
    ACTION_QUERY_ATTENDANCES("query_attendances"),
    /**
     * （教师）发布考勤
     */
    ACTION_PUBLISH_ATTENDANCE("publish_attendance"),
    /**
     * 考勤信息导出
     */
    ACTION_EXPORT_ATTENDANCES("export_attendances"),
    /**
     * 删除考勤信息
     */
    ACTION_DELETE_ATTENDANCES("delete_attendances"),
    /**
     * 获取考勤学生信息
     */
    ACTION_QUERY_ATTENDANCE_USERS("query_attendance_users"),

    //=========================================================作业=====================================================
    /**
     * 查询作业
     */
    ACTION_QUERY_HOMEWORKS("query_homeworks"),
    /**
     * 发布作业
     */
    ACTION_PUBLISH_HOMEWORK("publish_homework"),
    /**
     * 删除作业信息
     */
    ACTION_DELETE_HOMEWORK("delete_homework"),
    /**
     * 导出作业
     */
    ACTION_EXPORT_HOMEWORKS("export_homeworks"),
    /**
     * 更新作业信息
     */
    ACTION_UPDATE_HOMEWORK("update_homework"),


    //========================================================课程=====================================================
    /**
     * 查询课程
     */
    ACTION_QUERY_COURSES("query_courses"),
    /**
     * 删除课程
     */
    ACTION_DELETE_COURSE("delete_course"),
    /**
     * 更新课程信息
     */
    ACTION_UPDATE_COURSE("update_course"),
    /**
     * 添加课程
     */
    ACTION_ADD_COURSE("add_course"),
    /**
     * 导出课程信息
     */
    ACTION_EXPORT_COURSES("export_courses"),
    /**
     * 加入课程
     */
    ACTION_JOIN_COURSE("join_course"),
    /**
     * 退出课程
     */
    ACTION_EXIT_COURSE("exit_course"),





    //=======================================================统计=======================================================
    /**
     * 统计
     */
    ACTION_START_STATISTIC("start_statistic"),


    //======================================================打印========================================================
    /**
     * 获取待打印的数据
     */
    ACTION_GET_PRINT_DATA("get_print_data"),

    //=======================================================评论=======================================================
    /**
     * 发布评论action
     */
    ACTION_GET_FILE_COMMENTS("get_file_comments"),

    /**
     * 发布评论action
     */
    ACTION_SUBMIT_COMMENT("submit_comment"),

    //======================================================上传========================================================
    /**
     * 上传文件action
     */
    ACTION_UPLOAD_FILE("upload_file"),

    /**
     * 获取上传文件进度action
     */
    ACTION_GET_UPLOAD_PROGRESS("get_upload_progress");

    /**
     * The String of action
     */
    private String action;

    ActionType(String action) {
        this.action = action;
    }

    /**
     * 比较action
     * @param action the action
     * @return 是否相等
     */
    public boolean equals(String action) {
        return this.action.equals(action);
    }

}
