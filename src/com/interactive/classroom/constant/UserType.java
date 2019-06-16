package com.interactive.classroom.constant;

/**
 * @author Z-P-J
 * @date 2019/5/31 0:57
 */
public enum UserType {

    /**
     * 用户类型：学生
     * */
    STUDENT("student"),
    /**
     * 用户类型：老师
     * */
    TEACHER("teacher"),
    /**
     * 用户类型：管理员
     * */
    MANAGER("manager");


    private String typeName;

    UserType(String typeName) {
        this.typeName = typeName;
    }

//    public String getTypeName() {
//        return typeName;
//    }

    public boolean equals(String userType) {
        return typeName.equals(userType);
    }
}
