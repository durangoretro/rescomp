package com.durangoretro.rescomp;

/**
 * Application Status Codes
 */
public enum Status {


    /**
     * OK
     */
    OK(0),
    /**
     * Invalid parameters or invalid options
     */
    INVALID_PARAMETERS(1),
    /**
     * Operation Error
     */
    ERROR(3),
    /**
     * Invalid Mode
     */
    UNKNOWMODE(2);

    public int getCode() {
        return code;
    }

    private final int code;


    Status(int code) {
        this.code = code;
    }
}
