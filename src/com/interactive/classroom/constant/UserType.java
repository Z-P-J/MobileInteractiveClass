package com.interactive.classroom.constant;

/**
 * @author Z-P-J
 * @date 2019/5/31 0:57
 */
public enum UserType {

    /**
     * 用户类型：学生
     * */
    STUDENT(0, "student"),
    /**
     * 用户类型：老师
     * */
    TEACHER(1, "teacher"),
    /**
     * 用户类型：管理员
     * */
    MANAGER(2, "manager");


    private int id;
    private String typeName;

    UserType(int id, String typeName) {
        this.id = id;
        this.typeName = typeName;
    }

    public int getId() {
        return id;
    }

    public String getTypeName() {
        return typeName;
    }
}
