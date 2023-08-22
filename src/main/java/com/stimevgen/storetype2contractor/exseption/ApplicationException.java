package com.stimevgen.storetype2contractor.exseption;

import java.io.IOException;

public class ApplicationException extends IOException {

    public ApplicationException() {
    }

    public ApplicationException(String message) {
        super(message);
    }
}
