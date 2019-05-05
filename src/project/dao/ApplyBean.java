package project.dao;

import utility.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class ApplyBean {
	private int id;
	private String projectId;
	private String projectName;
	private String userId;
	private String userName;
	private String managerId;
	private String managerName;
	private String sessionId;
	private String applyType;
	private String applyContent;
	private String creator;
	private String createTime;

	private String result;
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getManagerId() {
		return managerId;
	}

	public void setManagerId(String managerId) {
		this.managerId = managerId;
	}
	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getApplyType() {
		return applyType;
	}

	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}

	public String getApplyContent() {
		return applyContent;
	}

	public void setApplyContent(String applyContent) {
		this.applyContent = applyContent;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public ApplyBean() {
	}

	public ApplyBean(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String userName = (String) session.getAttribute("user_name");
		String id = request.getParameter("id");
		String userId = session.getAttribute("user_id") == null ? null : (String) session.getAttribute("user_id");

		setProjectId(request.getParameter("project_id"));
		setProjectName(request.getParameter("project_name"));
		setApplyContent(request.getParameter("apply_content"));
		setApplyType(request.getParameter("apply_type"));
		setResult(request.getParameter("result"));
		setUserId(userId);
		setUserName(userName);
		setCreator(userName);
		setId(Integer.parseInt(id));
		setSessionId(session.getId());
		setCreateTime(TimeUtil.currentDate());
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}
}
