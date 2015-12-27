package com.sunzequn.geocities.data.exception;

import java.io.IOException;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * Handle exceptions of parsers.
 */
public class ParseException extends Exception {

    public ParseException() {
        super();
    }

    public ParseException(String message) {
        super(message);
    }
}
