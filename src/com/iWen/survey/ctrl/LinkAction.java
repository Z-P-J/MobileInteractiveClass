package com.iWen.survey.ctrl;

import com.iWen.survey.dao.DAOFactory;
import com.iWen.survey.dao.LinkDAO;
import com.iWen.survey.dto.Link;
import com.iWen.survey.util.StringUtil;

import java.net.URLEncoder;


public class LinkAction extends BaseAction {

    private String message;

    private LinkDAO dao = DAOFactory.getLinkDAO();

    public String AddLink() throws Exception {

        String url = StringUtil.encodeString(request.getParameter("url"));
        String sitename = StringUtil.encodeString(request.getParameter("sitename"));
        String description = StringUtil.encodeString(request.getParameter("description"));


        Link link = new Link();
        link.setLAddtime(new java.sql.Date(new java.util.Date().getTime()));
        link.setLInfo(description);
        link.setLIsLock(false);
        link.setLName(sitename);
        link.setLUrl(url);

        boolean ret1 = dao.addLink(link);
        if (ret1) {
//			response.sendRedirect("../admin/FriendLinkAdmin.jsp");
            return "success";
        } else {
            this.message = URLEncoder.encode("�����������ӳ�������ϵ����Ա", "UTF-8");
            //response.sendRedirect("../admin/OpResult.jsp?op=default&ret=false&words="+URLEncoder.encode("�����������ӳ�������ϵ����Ա", "UTF-8"));
            return "fail";
        }
    }

    public String DelLink() {
        Long lid = Long.valueOf(request.getParameter("lid"));
        LinkDAO dao = DAOFactory.getLinkDAO();

        boolean ret1 = dao.delLink(lid);
        if (ret1) {
//			response.sendRedirect("../admin/FriendLinkAdmin.jsp");
            return "success";
        } else {
//			response.sendRedirect("../admin/OpResult.jsp?op=default&ret=false&words="+URLEncoder.encode("ɾ���������ӳ�������ϵ����Ա", "UTF-8"));
            return "fail";
        }
    }

    public String EditLink() throws Exception {
        Long lid = Long.valueOf(request.getParameter("lid"));
        String url = StringUtil.encodeString(request.getParameter("url"));
        String sitename = StringUtil.encodeString(request.getParameter("sitename"));
        String description = StringUtil.encodeString(request.getParameter("description"));
        LinkDAO dao = DAOFactory.getLinkDAO();

        Link link = dao.findLink(lid);
        link.setLId(lid);
        link.setLInfo(description);
        link.setLIsLock(false);
        link.setLName(sitename);
        link.setLUrl(url);
        boolean ret1 = dao.updateLink(link);
        if (ret1) {
//			response.sendRedirect("../admin/FriendLinkAdmin.jsp");
            return "success";
        } else {
            this.message = URLEncoder.encode("�༭�������ӳ���", "UTF-8");
//			response.sendRedirect("../admin/OpResult.jsp?op=default&ret=false&words=�༭�������ӳ���");
            return "fail";
        }
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
