package com.sunzequn.geocities.data.exception;

import java.io.IOException;

/**
 * Created by Sloriac on 15/12/20.
 * <p>
 * Handle exceptions of config files.
 */
public class ConfigException extends IOException {

    public ConfigException() {
        super();
    }

    public ConfigException(String message) {
        super(message);
    }
}
