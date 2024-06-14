package com.his.webtool.exception;

import com.his.webtool.message.ResultMessages;
import org.apache.commons.lang3.StringUtils;

import java.io.Serial;
import java.util.List;

public class ResourceNotFoundException extends ResultMessagesNotificationException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String PREFIX_CODE = "NF";

    /**
     * Constructor for non parameter.
     * <p>
     * By this type of constructor, the exception return general information of
     * message.
     * </p>
     */
    public ResourceNotFoundException() {
        super();
    }

    /**
     * Constructor for specify a code.
     * <p>
     * generate a {@link ResultMessages} instance of a code.
     * </p>
     *
     * @param code message code
     * @param args parameters
     */
    public ResourceNotFoundException(String code, Object... args) {
        super(StringUtils.isNumeric(code.subSequence(2, 2)) ? PREFIX_CODE + code : code, args);
    }


    /**
     * Constructor for specify a code.
     * <p>
     * generate a {@link ResultMessages} instance of a code.
     * </p>
     *
     * @param code:        error message code
     * @param errorFields: Code of error fields
     * @param args:        List of arguments
     */
    public ResourceNotFoundException(String code, List<String> errorFields, Object... args) {
        super(StringUtils.isNumeric(code.subSequence(2, 2)) ? PREFIX_CODE + code : code, errorFields, args);
    }

    /**
     * Constructor for specify a code.
     * <p>
     * generate a {@link ResultMessages} instance of a code.
     * </p>
     *
     * @param code:        error message code
     * @param errorFields: Code of error fields
     */
    public ResourceNotFoundException(String code, List<String> errorFields) {
        super(StringUtils.isNumeric(code.subSequence(2, 2)) ? PREFIX_CODE + code : code, errorFields);
    }

    /**
     * Constructor for specify messages.
     * <p>
     * Takes multiple {@code String} messages as argument.
     * </p>
     *
     * @param messages {@link ResultMessages} instance
     */
    public ResourceNotFoundException(ResultMessages messages) {
        super(messages);
    }

    /**
     * Constructor for specify messages and exception.
     * <p>
     * Takes multiple {@code String} messages and cause of exception as argument.
     * </p>
     *
     * @param messages {@link ResultMessages} instance
     * @param cause    {@link Throwable} instance
     */
    public ResourceNotFoundException(ResultMessages messages, Throwable cause) {
        super(messages, cause);
    }
}
