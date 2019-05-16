package com.iWen.survey.ctrl;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseAction extends ActionSupport implements ServletRequestAware, ServletResponseAware {

    protected HttpServletRequest request;
    protected HttpServletResponse response;

    @Override
    public void setServletRequest(HttpServletRequest arg0) {
        this.request = arg0;
    }

    @Override
    public void setServletResponse(HttpServletResponse arg0) {
        this.response = arg0;
    }

}
