package com.sunzequn.geo.data.exception;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * Handle exceptions about the custom queues,
 * that is <code>Queue</code> and <code>UrlQueue</code>.
 */
public class QueueException extends Exception {

    public QueueException() {
        super();
    }

    public QueueException(String message) {
        super(message);
    }
}
