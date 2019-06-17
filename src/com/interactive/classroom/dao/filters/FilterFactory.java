package com.interactive.classroom.dao.filters;

/**
 * @author Z-P-J
 */
public class FilterFactory {

    private FilterFactory() { }

    /**
     *
     * @return FileFilter
     */
    public static FileFilter getFileFilter() {
        return new FileFilter();
    }

    /**
     *
     * @return HomeworkFilter
     */
    public static HomeworkFilter getHomeworkFilter() {
        return new HomeworkFilter();
    }

    /**
     *
     * @return UserFilter
     */
    public static UserFilter getUserFilter() {
        return new UserFilter();
    }

}
