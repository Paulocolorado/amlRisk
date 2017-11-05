/**
 *
 * @author LeonSoftware 2017
 */
package com.leonsoftware.amlgestionriesgo.exception;


public class SisgriException extends Exception {

    /**
     * Creates a new instance of <code>SisgriException</code> without detail
     * message.
     */
    public SisgriException() {
    }

    /**
     * Constructs an instance of <code>SisgriException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public SisgriException(String msg) {
        super(msg);
    }
}
