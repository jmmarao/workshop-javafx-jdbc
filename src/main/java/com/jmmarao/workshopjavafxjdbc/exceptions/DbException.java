package com.jmmarao.workshopjavafxjdbc.exceptions;

import java.io.Serial;

public class DbException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1710612901178794830L;

    public DbException(String message) {
        super(message);
    }
}
