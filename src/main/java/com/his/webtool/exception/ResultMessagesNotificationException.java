package com.his.webtool.exception;

import com.his.webtool.message.ResultMessages;
import org.springframework.context.support.DefaultMessageSourceResolvable;

import java.io.Serial;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * result message for notification
 */
public abstract class ResultMessagesNotificationException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Instance of {@link ResultMessages}
     */
    private final ResultMessages resultMessages;

    /**
     * message code argument constructor
     */
    protected ResultMessagesNotificationException() {
        this(new ResultMessages());
    }

    /**
     * message code argument constructor
     *
     * @param code message code
     * @param args parameters
     */
    protected ResultMessagesNotificationException(String code, Object... args) {
        this(new ResultMessages().add(code, args));
    }

    /**
     * message code argument constructor
     *
     * @param code message code
     * @param args parameters
     */
    protected ResultMessagesNotificationException(String code, List<String> errorFields, Object... args) {
        this();

        Object[] objects = errorFields
                .stream()
                .map(i -> new DefaultMessageSourceResolvable(new String[]{i}, i))
                .toArray(Object[]::new);

        Object[] concatArgs = Stream.concat(Arrays.stream(objects), Arrays.stream(args))
                .toArray(Object[]::new);

        this.resultMessages.add(code, concatArgs);
    }


    /**
     * Single argument constructor
     *
     * @param messages instance of {@link ResultMessages}
     */
    protected ResultMessagesNotificationException(ResultMessages messages) {
        this(messages, null);
    }

    /**
     * Two argument constructor
     *
     * @param messages instance of {@link ResultMessages}
     * @param cause    {@link Throwable} instance
     */
    protected ResultMessagesNotificationException(ResultMessages messages, Throwable cause) {
        super(cause);
        if (messages == null) {
            throw new IllegalArgumentException("messages must not be null");
        }
        this.resultMessages = messages;
    }

    /**
     * Returns the messages in String format
     *
     * @return String messages
     */

    @Override
    public String getMessage() {
        return resultMessages.toString();
    }

    /**
     * Returns the {@link ResultMessages} instance
     *
     * @return {@link ResultMessages} instance
     */
    public ResultMessages getResultMessages() {
        return resultMessages;
    }

}
