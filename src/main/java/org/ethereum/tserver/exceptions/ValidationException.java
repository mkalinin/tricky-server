package org.ethereum.tserver.exceptions;

/**
 * @author Mikhail Kalinin
 * @since 05/14/2015
 */
//TODO add more convenient and proper exception mechanism
public class ValidationException extends RuntimeException {

    public ValidationException(String s) {
        super(s);
    }

    public ValidationException(Throwable throwable) {
        super(throwable);
    }
}
