package com.interactive.classroom.vote;
/*
 * 待完成：用MVC模式分开DB和Action操作
 * 增删改查看导印统功能的实现
 */

import com.interactive.classroom.base.BaseHttpServlet;
import com.interactive.classroom.bean.VoteBean;
import com.interactive.classroom.dao.DaoFactory;
import com.interactive.classroom.dao.VoteDao;
import com.interactive.classroom.utils.ServletUtil;
import com.interactive.classroom.utils.TimeUtil;
import com.interactive.classroom.utils.export.ExportUtil;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletAction extends BaseHttpServlet {
    //这里是需要改的,module和sub
    public String module = "vote";
    public String sub = "file";

    public String preFix = module + "_" + sub;
    public String resultPath = module + "/" + sub;
    public String resultPage = "result.jsp";
    public String resultUrl = resultPath + "/" + resultPage;
    public String redirectPath = module + "/" + sub;
    public String redirectPage = "record_list.jsp";
    public String redirectUrl = redirectPath + "/" + redirectPage;

    @Override
    protected void handleAction(HttpServletRequest request, HttpServletResponse response, String action) {
        try {
            switch (action) {
                case "get_record":
                    getRecord(request, response);
                    break;
                case "get_record_view":
                    getRecordView(request, response);
                    break;
                case "add_record":
                    addRecord(request, response);
                    break;
                case "modify_record":
                    modifyRecord(request, response);
                    break;
                case "delete_record":
                    deleteRecord(request, response);
                    break;
                case "set_record_top":
                    setRecordTop(request, response);
                    break;
                case "export_record":
                    JSONObject jsonObj = ExportUtil.exportRecord(request, response, module, sub, "调查管理");
                    onEnd(request, response, jsonObj);
                    break;
                default:
                    processError(request, response, 2, "[" + module + "/" + sub + "/ServletAction]没有对应的action处理过程，请检查action是否正确！action=" + action, resultPath, resultPage, redirectPath, redirectPage);
                    break;
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /*
     * 功能：查询记录
     */
    public void getRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String type = request.getParameter("type");
        String timeFrom = request.getParameter("time_from");
        String timeTo = request.getParameter("time_to");
        String sortIndex = request.getParameter("sort_index");
        String orderBy = request.getParameter("order_by");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty()))
            existResultset = "0";

        debug("[getRecord]收到页面传过来的参数是：" + existResultset + "," + action + "," + type + "," + id + "," + timeFrom + "," + timeTo);

        VoteBean vote = new VoteBean();
        vote.setId(id);
        vote.setAction(action);
        vote.setType(type);
        vote.setTitle(title);
        vote.setContent(content);
        vote.setTimeFrom(timeFrom);
        vote.setTimeTo(timeTo);
        vote.setUserId(userId);
        vote.setUserRole(userType);
        vote.setSortIndex(sortIndex);
        vote.setOrderBy(orderBy);
        JSONObject jsonObj = null;
        if (existResultset.equals("1")) {
            //要求提取之前查询结果，如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(module + "_" + sub + "_get_record_result") != null) {
                jsonObj = (JSONObject) session.getAttribute(module + "_" + sub + "_get_record_result");
            } else {
                //如果没有就报错
                jsonObj = new JSONObject();
                jsonObj.put("result_code", 10);
                jsonObj.put("result_msg", "exist_resultset参数不当，服务器当前没有结果数据！请重新设置！");
            }
        } else {
            //如果是新查询
            VoteDao voteDao = DaoFactory.getVoteDao();
            jsonObj = voteDao.getRecord(vote);
            session.setAttribute(module + "_" + sub + "_get_record_result", jsonObj);
        }
        jsonObj.put("user_id", userId);
        jsonObj.put("user_name", userName);
        jsonObj.put("user_role", userType);
        jsonObj.put("user_avatar", userAvatar);
        jsonObj.put("action", action);
        /*--------------------数据查询完毕，根据交互方式返回数据--------------------*/

        redirectUrl = "record_list.jsp?exist_resultset=1";
        String url = redirectPath + "/record_list.jsp?exist_resultset=1";
        onEnd(request, response, jsonObj, url);
    }

    public void getRecordView(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String index = request.getParameter("index");
        String existResultset = request.getParameter("exist_resultset");
        if ((existResultset == null) || (existResultset.equals("null") || existResultset.isEmpty())) {
            existResultset = "0";
        }

        debug("收到页面传过来的参数是：exist_resultset=" + existResultset + ",action=" + action + ",id=" + id + ",index=" + index);

        JSONObject jsonObj;
        VoteBean query = new VoteBean();
        query.setAction(action);
        query.setUserId(userId);
        if (existResultset.equals("1")) {            //如果是新查询
            //如果有就取出来，如果没有就重新查询一次，并且保存进session里
            if (session.getAttribute(module + "_" + sub + "_get_record_result") != null) {
                JSONObject json = (JSONObject) session.getAttribute(module + "_" + sub + "_get_record_result");
                debug(json.toString());
                jsonObj = ServletUtil.getResultSetNavigateId(id, index, json);
                jsonObj.put("user_id", userId);
                jsonObj.put("user_name", userName);
                jsonObj.put("action", action);
                jsonObj.put("result_code", 0);
                jsonObj.put("result_msg", "ok");
                json = (JSONObject) session.getAttribute(module + "_" + sub + "_get_record_result");
                //showDebug("[getRecordView]重新取出来的数据是："+json.toString());
            } else {
                //如果没有就重新查询一次
                debug("[getRecordView]没有就重新查询一次。");
                VoteDao voteDao = DaoFactory.getVoteDao();
                jsonObj = voteDao.getRecord(query);
                jsonObj.put("user_id", userId);
                jsonObj.put("user_name", userName);
                jsonObj.put("action", action);
                jsonObj.put("result_code", 0);
                jsonObj.put("result_msg", "ok");
                session.setAttribute(module + "_" + sub + "_get_record_result", jsonObj);
            }
        } else {
            debug("[getRecordView]existsResult=0，重新查询");
            VoteDao voteDao = DaoFactory.getVoteDao();
            jsonObj = voteDao.getRecord(query);
            jsonObj.put("user_id", userId);
            jsonObj.put("user_name", userName);
            jsonObj.put("action", action);
            session.setAttribute(module + "_" + sub + "_get_record_result", jsonObj);
        }

        onEndDefault(request, response, jsonObj);
    }

    public void addRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String voteId = request.getParameter("vote_id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String status = request.getParameter("status");
        String limitTime = request.getParameter("end_time");
        JSONObject jsonObj;
        String createTime = TimeUtil.currentDate();
        VoteDao voteDao = DaoFactory.getVoteDao();
        VoteBean file = new VoteBean();
        file.setAction(action);
        file.setParentId(voteId);
        file.setTitle(title);
        file.setContent(content);
        file.setLimitTime(limitTime);
        file.setUserId(userId);
        file.setCreator(userName);
        file.setStatus(status);
        file.setCreateTime(request.getParameter("publish_time"));

        jsonObj = voteDao.addRecord(file);
        log("用户 " + userName + " 于 " + createTime + " 添加了 [" + module + "][" + sub + "] 记录", "添加记录", module);

        onEndDefault(request, response, jsonObj);
    }

    /*
     * 功能：修改记录
     */
    public void modifyRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String limitTime = request.getParameter("limit_time");
        String status = request.getParameter("status");
        JSONObject jsonObj = null;
        if (id != null) {
            String createTime = TimeUtil.currentDate();
            VoteDao voteDao = DaoFactory.getVoteDao();
            VoteBean file = new VoteBean();
            file.setId(id);
            file.setTitle(title);
            file.setContent(content);
            file.setLimitTime(limitTime);
            file.setCreator(userName);
            file.setCreateTime(createTime);
            file.setStatus(status);
            jsonObj = voteDao.modifyRecord(file);
            log("用户 " + userName + " 于 " + createTime + " 修改了 [" + module + "][" + sub + "] 记录", "修改记录", module);
        }

        onEndDefault(request, response, jsonObj);
    }

    public void deleteRecord(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String[] ids = request.getParameterValues("id");
        JSONObject jsonObj = null;
        if (ids != null) {
            String createTime = TimeUtil.currentDate();
            VoteDao voteDao = DaoFactory.getVoteDao();
            jsonObj = voteDao.deleteRecord(action, ids, userName, createTime);
            log("用户 " + userName + " 于 " + createTime + " 删除了 [" + module + "][" + sub + "] 记录", "删除记录", module);
        }
        onEndDefault(request, response, jsonObj);
    }

    private void setRecordTop(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String action = request.getParameter("action");
        String id = request.getParameter("id");
        String type = request.getParameter("type");
        VoteDao voteDao = DaoFactory.getVoteDao();
        JSONObject jsonObj = voteDao.setRecordTop(action, type, userId, id);
        jsonObj.put("user_id", userId);
        jsonObj.put("user_name", userName);
        session.setAttribute(module + "_" + sub + "_get_record_result", jsonObj);
        onEndDefault(request, response, jsonObj);
    }

}
