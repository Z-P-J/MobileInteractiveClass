package com.iWen.survey.pager;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

public class PageControl  {
	PageListener pageListener;

	int countRecord = 0;
	int sizePage = 8;
	int currentPage = 0;
	int countPage = 0;
	int recordstart = 0;
	String url = null;
	String connector = "?";
	String pageForward = null;

	PageConfig pageConfig = null;
	String pageUp = "<font face=���� size=2>��һҳ</font>";
	String pageDown = "<font face=���� size=2>��һҳ</font>";
	String firstPage = "<font face=���� size=2>��ҳ</font>";
	String lastPage = "<font face=���� size=2>ĩҳ</font>";


	/**
	 * ��ȡ�ܼ�¼��
	 * @return
	 */
	public int getCountRecord() {
		if (this.countRecord == 0)
			this.countRecord = this.pageListener.getCount(this.pageConfig);

		return this.countRecord;
	}

	/**
	 * ���ɲ����ء���ҳ����HTML���
	 * @return
	 */
	public String getFirstPageHTML() {
		StringBuffer sb = new StringBuffer("");
		if (this.countRecord == 0)
			this.countRecord = getCountRecord();

		if ((this.currentPage == 1) || (this.countRecord <= 0)) {
			sb.append(this.firstPage);
		} else {
			sb.append("<a href='");
			sb.append(this.url);
			sb.append(this.connector);
			sb.append("pageid=");
			sb.append(1);
			sb.append("'>");
			sb.append(this.firstPage);
			sb.append("</a>");
		}
		String firstPage = sb.toString();
		return firstPage;
	}

	/**
	 * �Զ��塰��ҳ�����ӵ��ı�����
	 * @param firstPage
	 */
	public void setFirstPageHTML(String firstPage) {

		this.firstPage = firstPage;
	}

	/**
	 * ���ɲ����ء�ĩҳ����HTML���
	 * @return
	 */
	public String getLastPageHTML() {
		StringBuffer sb = new StringBuffer("");
		int countRecords = this.currentPage * this.sizePage;
		if (this.countRecord == 0)
			this.countRecord = getCountRecord();

		if (this.countPage == 0)
			this.countPage = getCountPage();

		if ((countRecords >= this.countRecord) || (this.countRecord <= 0)) {
			sb.append(this.lastPage);
		} else {
			sb.append("<a href='");
			sb.append(this.url);
			sb.append(this.connector);
			sb.append("pageid=");
			sb.append(this.countPage);
			sb.append("'>");
			sb.append(this.lastPage);
			sb.append("</a>");
		}
		String lastPage = sb.toString();
		return lastPage;
	}

	/**
	 * �Զ��塰ĩҳ�����ӵ��ı�����
	 * @param firstPage
	 */
	public void setLastPageHTML(String lastPage) {

		this.lastPage = lastPage;
	}

	/**
	 * ���ɲ����ء���һҳ����HTML���
	 * @return
	 */
	public String getPageDownHTML() {
		StringBuffer sb = new StringBuffer("");
		int pageid = this.currentPage + 1;
		int countRecords = this.currentPage * this.sizePage;
		if (this.countRecord == 0)
			this.countRecord = getCountRecord();

		if (this.countPage == 0)
			this.countPage = getCountPage();

		if ((countRecords >= this.countRecord) || (this.countPage == 0)) {
			sb.append(this.pageDown);
		} else {
			sb.append("<a href='");
			sb.append(this.url);
			sb.append(this.connector);
			sb.append("pageid=");
			sb.append(pageid);
			sb.append("'>");
			sb.append(this.pageDown);
			sb.append("</a>");
		}
		String pageDown = sb.toString();
		return pageDown;
	}

	/**
	 * �Զ��塰��һҳ�����ӵ��ı�����
	 * @return
	 */
	public void setPageDownHTML(String pageDown) {

		this.pageDown = pageDown;
	}

	/**
	 * ���ɲ����ء���һҳ����HTML���
	 * @return
	 */
	public String getPageUpHTML() {
		StringBuffer sb = new StringBuffer("");
		int pageid = this.currentPage - 1;
		if (pageid <= 0) {
			sb.append(this.pageUp);
		} else {
			sb.append("<a href='");
			sb.append(this.url);
			sb.append(this.connector);
			sb.append("pageid=");
			sb.append(pageid);
			sb.append("'>");
			sb.append(this.pageUp);
			sb.append("</a>");
		}
		String pageUp = sb.toString();
		return pageUp;
	}

	/**
	 * �Զ��塰��һҳ�����ӵ��ı�����
	 * @return
	 */
	public void setPageUpHTML(String pageUp) {

		this.pageUp = pageUp;
	}

	/**
	 * ����ÿҳ����
	 * @return
	 */
	public int getSizePage() {
		return this.sizePage;
	}

	/**
	 * ����ÿҳ��ʾ����
	 * @param sizePage
	 */
	public void setSizePage(int sizePage) {
		this.sizePage = sizePage;
	}

	/**
	 * ����"��ҳ��"HTML
	 * @return
	 */
	public String getCountPageHTML() {
		if (this.countRecord == 0)
			this.countRecord = getCountRecord();

		if (this.countRecord < this.sizePage) {
			this.countPage = 1;
		} else if (this.countRecord % this.sizePage == 0)
			this.countPage = (this.countRecord / this.sizePage);
		else {
			this.countPage = (this.countRecord / this.sizePage + 1);
		}

		return "<font face=���� size=2>/��" + this.countPage + "ҳ</font>";
	}

	/**
	 * ������ҳ�� 
	 * @return
	 */
	public int getCountPage() {
		if (this.countRecord == 0)
			this.countRecord = getCountRecord();

		if (this.countRecord < this.sizePage) {
			this.countPage = 1;
		} else if (this.countRecord % this.sizePage == 0)
			this.countPage = (this.countRecord / this.sizePage);
		else {
			this.countPage = (this.countRecord / this.sizePage + 1);
		}

		return this.countPage;
	}

	/**
	 * ��ȡ��¼����
	 * @return
	 */
	public List  getRecord() {
		List  list = null;
		this.recordstart = (this.sizePage * (this.currentPage - 1));
		list = this.pageListener.doSelect(this.recordstart, this.sizePage,
				this.pageConfig);
	 
	 
		return list;
	}

	/**
	 * ����ҳ�����������ʵ����PageListener�ӿڵ���Ķ�����
	 * @param pageListener
	 */
	private void setPageListener(PageListener pageListener) {
		this.pageListener = pageListener;
	}

	/**
	 * �������ò���
	 * @param pageListener
	 * @param pageConfig
	 * @param url
	 */
	public PageControl(PageListener pageListener, PageConfig pageConfig, String url){
		this.setPageListener(pageListener);
		this.setPageConfig(pageConfig);
		this.setUrl(url);
	}
	/**
	 * ���÷���URL����ʽ
	 * @param url
	 */
	private void setUrl(String url) {
		HttpServletRequest request = this.pageConfig.getRequest();
		String newUrl=this.getNewUrl(request, url);
	    this.url = newUrl;
	    if (url.indexOf("?") != -1)
	      this.connector = "&";
	    else
	      this.connector = "?";
	}

	/**
	 * ���ص�ǰҳ��
	 * @return
	 */
	public int getCurrentPage() {
		return this.currentPage;
	}

	/**
	 * ����"��ǰҳ��"HTML
	 * @return
	 * <font face=���� size=2>��ǰ��Nҳ</font>
	 */
	public String getCurrentPageHTML() {
		return "<font face=���� size=2> ��ǰ��" + this.currentPage + "ҳ</font>";
	}

	/**
	 * ���ɲ����ء���ת����HTML��ǩ
	 * @return
	 */
	public String getPageForwardHTML() {
		if (this.countPage == 0)
			this.countPage = getCountPage();

		StringBuffer sb = new StringBuffer("");
		sb.append("<font face='����' size='2'>��ת��</font>");
		sb
				.append("<input id='pageto' onkeydown='on_pageKeydown()' name=pageto type=text size='2' style='border:1px solid #EEE6D0; width: 30; height: 16'>");
		sb
				.append("<font face='����' size='2'>ҳ&nbsp;<button onclick='on_pageClick()' style='font-size:12px;width: 25; height: 20'>GO</button>");
		sb.append("</font>");
		sb.append("\n<script type='text/javascript'>\n");
		sb.append("function on_pageKeydown()\n");
		sb.append("{\t if(event.keyCode==13) on_pageClick();}\n");
		sb.append("function on_pageClick()");
		sb.append("{var page = pageto.value;");
		sb.append("if(page=='')");
		sb.append("{alert('���������ݲ��Ϸ���');}");
		sb.append("else if(isNaN(page)) {");
		sb.append("alert('���������ݲ��Ϸ���');}");
		sb.append("else if(page>");
		sb.append(this.countPage);
		sb.append("||page<=0)");
		sb.append("{alert('���������ݲ��Ϸ���');}");
		sb.append("else\t{\twindow.location.href='");
		sb.append(this.url);
		sb.append(this.connector);
		sb.append("pageid='+page;");
		sb.append("}\t}</script>");
		String pageForward = sb.toString();
		return pageForward;
	}

	public PageConfig getPageConfig() {
		return this.pageConfig;
	}

	private void setPageConfig(PageConfig pageConfig) {
		HttpServletRequest request = null;
		this.pageConfig = pageConfig;
		request = this.pageConfig.getRequest();
		try {
			this.currentPage = Integer.parseInt(request.getParameter("pageid"));
		} catch (Exception e) {
			this.currentPage = 1;
		}
	}
	/**
	 * ��REQUEST�е�Parameter��װ��URL��QueryString
	 * @param request
	 * @param url
	 * @return
	 */
	private String getNewUrl(HttpServletRequest request, String url)
	  {
	    String newUrl = null;
	    String connector = "?";
	    
	    if ((request != null) && (url != null)) {
	      StringBuffer sburl = new StringBuffer(url);
	      Enumeration enu = request.getParameterNames();
	      while (enu.hasMoreElements()) {
	        String paraName = (String)enu.nextElement();
	        String paraValue = request.getParameter(paraName);
	        
	        if (sburl.indexOf("?") != -1)
	              connector = "&";
	        if ((!(paraName.equals("pageid")))&&url.indexOf(paraName)==-1) {
	 
	            sburl.append(connector);
	            sburl.append(paraName);
	            sburl.append("=");
	            sburl.append(paraValue);
	            
	          }
	      }
	      newUrl = sburl.toString();
	    }
	    return newUrl;
	  }
}
