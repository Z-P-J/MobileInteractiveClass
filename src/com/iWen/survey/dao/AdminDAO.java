package com.iWen.survey.dao;

import com.iWen.survey.dto.Admin;

import java.util.List;


public interface AdminDAO {
	public boolean addAdmin(Admin admin);
	public boolean updateAdmin(Admin admin);
	public boolean delAdmin(long a_id);
	public boolean checkPwd(String username, String pwd);
	public Admin findAdmin(long a_id);
	public List listAllAdmin();
	public Admin findAdmin(String username);
}
