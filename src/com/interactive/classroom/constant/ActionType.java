package com.interactive.classroom.constant;

/**
 * @author Z-P-J
 */
public enum ActionType {


    //----------------------评论-----------------------
    /**
     * 发布评论action
     */
    ACTION_GET_FILE_COMMENTS("get_file_comments"),

    /**
     * 发布评论action
     */
    ACTION_SUBMIT_COMMENT("submit_comment"),



    //----------------------上传-----------------------
    /**
     * 上传文件action
     */
    ACTION_UPLOAD_FILE("upload_file"),

    /**
     * 获取上传文件进度action
     */
    ACTION_GET_UPLOAD_PROGRESS("get_upload_progress");

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
