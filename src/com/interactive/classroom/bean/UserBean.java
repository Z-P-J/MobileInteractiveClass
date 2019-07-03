package com.interactive.classroom.bean;

import com.interactive.classroom.constant.UserType;
import com.interactive.classroom.utils.TextUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Z-P-J
 */
public class UserBean {

    private String id;
    private String userName = "";
    private String password = "";
    private String name = "";
    private String sex = "";
    private String email = "";
    private String phone = "";
    private String userType;
    private String weChat = "";
    private String grade = "";
    private String className = "";
    private String faculty = "";
    private String registerDate;
    private String studentNum = "";
    
    public UserBean() { }
    
    public UserBean(HttpServletRequest request) {
        setId(request.getParameter("id"));
        setRegisterDate(request.getParameter("register_date"));
        setUserName(request.getParameter("user_name"));
        setName(request.getParameter("name"));
        setSex(request.getParameter("sex"));
        setEmail(request.getParameter("email"));
        setPhone(request.getParameter("phone"));
        setWeChat(request.getParameter("wechat"));
        setGrade(request.getParameter("grade"));
        setClassName(request.getParameter("class"));
        setStudentNum(request.getParameter("student_num"));
        setFaculty(request.getParameter("faculty"));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserType() {
        if (TextUtil.isEmpty(userType)) {
            return UserType.STUDENT.getTypeName();
        }
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(String registerDate) {
        this.registerDate = registerDate;
    }

    public String getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(String studentNum) {
        this.studentNum = studentNum;
    }


    @Override
    public String toString() {
        return "UserBean{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", userType='" + userType + '\'' +
                ", weChat='" + weChat + '\'' +
                ", grade='" + grade + '\'' +
                ", className='" + className + '\'' +
                ", faculty='" + faculty + '\'' +
                ", registerDate='" + registerDate + '\'' +
                ", studentNum='" + studentNum + '\'' +
                '}';
    }
}
