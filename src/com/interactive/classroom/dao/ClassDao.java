package com.interactive.classroom.dao;

/**
 * @author Z-P-J
 */
public interface ClassDao {

    /**
     * 表名
     */
    String TABLE_NAME = "class_manage";
    /**
     * 数据库中的字段
     */
    String[] LABELS = {"id", "homework_id", "uploader_id", "uploader_name", "file_name", "file_size", "upload_time", "download_link"};
    /**
     * 数据库中字段中文解释
     */
    String[] LABELS_CH = {"ID", "作业ID", "文件ID", "上传者ID", "上传者", "文件名", "文件大小", "上传时间", "下载地址"};



}
