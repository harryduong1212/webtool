package com.his.webtool.message;


import java.io.Serial;
import java.io.Serializable;

/**
 * result message
 *
 * @param code message code
 * @param args message arguments
 * @param text message text
 */
public record ResultMessage(String code, Object[] args, String text) implements Serializable {
    /**
     * serialVersionUID
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * emtpy array object
     */
    private static final Object[] EMPTY_ARRAY = new Object[0];

    /**
     * Constructor.<br>
     *
     * @param code message code
     * @param args replacement values of message format
     * @param text default message
     */
    public ResultMessage(String code, Object[] args, String text) {
        this.code = code;
        this.args = args == null ? EMPTY_ARRAY : args;
        this.text = text;
    }

    /**
     * @param code message code (must not be null)
     * @param args replacement values of message format
     * @return ResultMessage instance
     */
    public static ResultMessage fromCode(String code, Object... args) {
        return new ResultMessage(code, args, null);
    }


    /**
     * @param code message code (must not be null)
     * @param args replacement values of message format
     * @return ResultMessage instance
     */
    public static ResultMessage fromCodeAndArgument(String code, Object[] args) {
        return new ResultMessage(code, args, null);
    }

    /**
     * @param text message tet (must not be null)
     * @return ResultMessage instance
     */
    public static ResultMessage fromText(String text) {
        return new ResultMessage(null, null, text);

    }
}
