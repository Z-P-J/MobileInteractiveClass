package com.interactive.classroom.dao;

import com.interactive.classroom.dao.impl.*;

/**
 * 面向接口编程，工厂模式
 * @author Z-P-J
 */
public final class DaoFactory {

    /**
     *私有化构造器
     */
    private DaoFactory() { }

    /**
     * 获取UserDao实例
     * @return UserDao
     */
    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    /**
     * 获取HomeworkDao实例
     * @return com.interactive.classroom.dao.HomeworkDao
     */
    public static HomeworkDao getHomeworkDao() {
        return new HomeworkDaoImpl();
    }

    /**
     * 获取HomeworkFileDao实例
     * @return com.interactive.classroom.dao.HomeworkFileDao
     */
    public static HomeworkFileDao getHomeworkFileDao() {
        return new HomeworkFileDaoImpl();
    }

    /**
     *获取StaticDao实例
     * @return com.interactive.classroom.dao.StatisticDao
     */
    public static StatisticDao getStatisticDao() {
        return new StatisticDaoImpl();
    }

    /**
     *获取InvestigationDao实例
     * @return com.interactive.classroom.dao.InvestigationDao
     */
    public static InvestigationDao getInvestigationDao() {
        return new InvestigationDaoImpl();
    }

    /**
     *获取VoteDao实例
     * @return com.interactive.classroom.dao.VoteDao
     */
    public static VoteDao getVoteDao() {
        return new VoteDaoImpl();
    }

    /**
     *获取FileDao实例
     * @return com.interactive.classroom.dao.FileDao
     */
    public static FileDao getFileDao() {
        return new FileDaoImpl();
    }

}
