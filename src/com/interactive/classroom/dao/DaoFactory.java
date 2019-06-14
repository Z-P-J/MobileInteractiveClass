package com.interactive.classroom.dao;

import com.interactive.classroom.dao.impl.*;

/**
 * @author Z-P-J
 */
public final class DaoFactory {

    private DaoFactory() {

    }

    /**
     * 获取UserDa实例
     * @return UserDao
     */
    public static UserDao getUserDao() {
        return new UserDaoImpl();
    }

    public static HomeworkDao getHomeworkDao() {
        return new HomeworkDaoImpl();
    }

    public static HomeworkFileDao getHomeworkFileDao() {
        return new HomeworkFileDaoImpl();
    }

    public static StatisticDao getStatisticDao() {
        return new StatisticDaoImpl();
    }

    public static InvestigationDao getInvestigationDao() {
        return new InvestigationDaoImpl();
    }

    public static VoteDao getVoteDao() {
        return new VoteDaoImpl();
    }

    public static FileDao getFileDao() {
        return new FileDaoImpl();
    }

}
