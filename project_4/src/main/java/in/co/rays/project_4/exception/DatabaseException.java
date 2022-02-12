package in.co.rays.project_4.exception;

/**
 * DatabaseException is propogated by DAO classes when an unhandled Database
 * exception occurred
 *
 * @author Danish Khan
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

public class DatabaseException extends Exception {

    /**
     * @param msg
     *            : Error message
     */
    public DatabaseException(String msg) {
        super(msg);
    }
}