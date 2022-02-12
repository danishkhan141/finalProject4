package in.co.rays.project_4.exception;

/**
 * RecordNotFoundException thrown when a record not found occurred
 *
 * @author Danish Khan
 * @version 1.0
 * @Copyright (c) SunilOS
 *
 */

public class RecordNotFoundException extends Exception {

    /**
     * @param msg
     *            error message
     */
    public RecordNotFoundException(String msg) {
        super(msg);

    }
}
