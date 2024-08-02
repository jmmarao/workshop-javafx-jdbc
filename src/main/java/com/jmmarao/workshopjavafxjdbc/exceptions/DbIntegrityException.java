package com.jmmarao.workshopjavafxjdbc.exceptions;

import java.io.Serial;

public class DbIntegrityException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -5991856823945042949L;

    public DbIntegrityException(String msg) {
        super(msg);
    }
}
