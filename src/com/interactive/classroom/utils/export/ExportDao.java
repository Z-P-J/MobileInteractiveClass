package com.interactive.classroom.utils.export;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.interactive.classroom.dao.ylx_db;

public class ExportDao{
	public JSONObject getExportRecord(String action,String dbName,String userId) throws SQLException, IOException, JSONException {
		String resultMsg="ok";
		int resultCode=0;
		List jsonList = new ArrayList();
		try {
			ylx_db query_db = new ylx_db(dbName);
			/*----------构造sql语句，根据传递过来的查询条件参数----------*/
			String sql="select * from user_export where user_id='"+userId+"' and export_status>0 and export_status<>3";
			/*----------构造sql语句完毕----------*/
			ResultSet rs = query_db.executeQuery(sql);
			while (rs.next()) {
				List list = new ArrayList();
				list.add(rs.getString("id"));
				list.add(rs.getString("module"));
				list.add(rs.getString("user_id"));
				list.add(rs.getString("user_name"));
				list.add(rs.getString("file_path"));
				list.add(rs.getString("file_name"));
				list.add(rs.getString("file_url"));
				list.add(rs.getString("file_size"));
				list.add(rs.getString("export_percent"));
				list.add(rs.getString("export_type"));
				list.add(rs.getString("export_status"));
				list.add(rs.getString("download_count"));
				list.add(rs.getString("current_query_setup"));
				list.add(rs.getString("creator"));
				list.add(rs.getString("create_time"));
				jsonList.add(list);
			}
			rs.close();
			query_db.close();
		} catch (SQLException sqlexception) {
			sqlexception.printStackTrace();
			resultCode=10;
			resultMsg="查询数据库出现错误！"+sqlexception.getMessage();
		}
		//下面开始构建返回的json
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("aaData",jsonList);
		jsonObj.put("result_msg",resultMsg);//如果发生错误就设置成"error"等
		jsonObj.put("result_code",resultCode);//返回0表示正常，不等于0就表示有错误产生，错误代码
		return jsonObj;
	}
	/*
	 * 功能：
	 */
	public void setExportBegin(ExportBean exportBean){
		ylx_db query_db = new ylx_db(exportBean.getDbName());
		String sql="insert into user_export(module,user_id,user_name,file_path,file_name,file_url,file_size,export_percent,export_type,export_status,current_query_setup,download_count,limit_time,creator,create_time)";
		sql=sql+" values('zakk','"+ exportBean.getUserId()+"','"+ exportBean.getUserName()+"','"+ exportBean.getFilePath()+"','"+ exportBean.getFileName()+"','"+ exportBean.getFileUrl()+"',"+ exportBean.getFileSize();
		sql=sql+","+ exportBean.getExportPercent()+","+ exportBean.getExportType()+","+ exportBean.getExportStatus()+",'"+ exportBean.getCurrentQuerySetup()+"'";
		sql=sql+","+ exportBean.getDownloadCount()+",'"+ exportBean.getLimitTime()+"','"+ exportBean.getCreator()+"','"+ exportBean.getCreateTime()+"')";
		query_db.executeUpdate(sql);
		query_db.close();
	}
	/*
	 * 功能：
	 */
	public void setExportEnd(ExportBean exportBean){
		ylx_db query_db = new ylx_db(exportBean.getDbName());
		String sql="update user_export set export_percent=100,export_status=3 where user_id='"+ exportBean.getUserId()+"' and file_name='"+ exportBean.getFileName()+"'";
		query_db.executeUpdate(sql);
		query_db.close();
	}
	/*
	 * 功能：
	 */
	public void setExportPercent(ExportBean exportBean){
		ylx_db query_db = new ylx_db(exportBean.getDbName());
		String sql="update user_export set export_percent="+ exportBean.getExportPercent()+" where user_id='"+ exportBean.getUserId()+"' and file_name='"+ exportBean.getFileName()+"'";
		query_db.executeUpdate(sql);
		query_db.close();
	}
}