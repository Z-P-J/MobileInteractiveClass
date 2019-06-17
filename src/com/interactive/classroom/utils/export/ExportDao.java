package com.interactive.classroom.utils.export;

import com.interactive.classroom.utils.DatabaseHelper;

/**
 * @author Z-P-J
 */
public class ExportDao {

    /**
     *
     * @param exportBean ExportBean
     */
    public void setExportBegin(ExportBean exportBean) {
        String sql = "insert into user_export(module,user_id,user_name,file_path,file_name,file_url,file_size,export_percent,export_type,export_status,current_query_setup,download_count,limit_time,creator,create_time)";
        sql = sql + " values('zakk','" + exportBean.getUserId() + "','" + exportBean.getUserName() + "','" + exportBean.getFilePath() + "','" + exportBean.getFileName() + "','" + exportBean.getFileUrl() + "'," + exportBean.getFileSize();
        sql = sql + "," + exportBean.getExportPercent() + "," + exportBean.getExportType() + "," + exportBean.getExportStatus() + ",'" + exportBean.getCurrentQuerySetup() + "'";
        sql = sql + "," + exportBean.getDownloadCount() + ",'" + exportBean.getLimitTime() + "','" + exportBean.getCreator() + "','" + exportBean.getCreateTime() + "')";
        DatabaseHelper.executeUpdate(sql);
    }

    /**
     *
     * @param exportBean ExportBean
     */
    public void setExportEnd(ExportBean exportBean) {
        String sql = "update user_export set export_percent=100,export_status=3 where user_id='" + exportBean.getUserId() + "' and file_name='" + exportBean.getFileName() + "'";
        DatabaseHelper.executeUpdate(sql);
    }

    /**
     *
     * @param exportBean ExportBean
     */
    public void setExportPercent(ExportBean exportBean) {
        String sql = "update user_export set export_percent=" + exportBean.getExportPercent() + " where user_id='" + exportBean.getUserId() + "' and file_name='" + exportBean.getFileName() + "'";
        DatabaseHelper.executeUpdate(sql);
    }
}
