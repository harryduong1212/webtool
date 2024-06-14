package com.his.webtool.message;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * result messages
 */
@EqualsAndHashCode
@ToString
public class ResultMessages implements Serializable, Iterable<ResultMessage> {

  /** serialVersionUID */
  @Serial
  private static final long serialVersionUID = 1L;
  /**
  * message list
  */
  @Getter
  private final List<ResultMessage> list = new ArrayList<>();

  /**
  * Constructor.
  * @param messages messages to add
  */
  public ResultMessages(ResultMessage... messages) {
    if (messages != null) {
      addAll(messages);
    }
  }

  /**
  * add a ResultMessage
  * @param message ResultMessage instance
  * @return this result messages
  */
  public ResultMessages add(ResultMessage message) {
    if (message != null) {
      this.list.add(message);
    }
    return this;
  }

  /**
  * add code to create and add ResultMessages
  * @param code message code
  * @return this result messages
  */
  public ResultMessages add(String code) {
    if (code != null) {
      this.add(ResultMessage.fromCode(code));
    }
    return this;
  }

  /**
  * add code and args to create and add ResultMessages
  * @param code message code
  * @param args replacement values of message format
  * @return this result messages
  */
  public ResultMessages add(String code, Object... args) {
    if (code != null) {
      this.add(ResultMessage.fromCode(code, args));
    }
    return this;
  }

  /**
  * add all messages (excludes <code>null</code> message)<br>
  * <p>
  * if <code>messages</code> is <code>null</code>, no message is added.
  * </p>
  * @param messages messages to add
  * @return this messages
  */
  public ResultMessages addAll(ResultMessage... messages) {
    if (messages != null) {
      for (ResultMessage message : messages) {
        add(message);
      }
    }
    return this;
  }

  /**
  * add all messages (excludes <code>null</code> message)<br>
  * <p>
  * if <code>messages</code> is <code>null</code>, no message is added.
  * </p>
  * @param messages messages to add
  * @return this messages
  */
  public ResultMessages addAll(Collection<ResultMessage> messages) {
    if (messages != null) {
      for (ResultMessage message : messages) {
        add(message);
      }
    }
    return this;
  }

  /**
   * returns whether messages are not empty.
   * @return whether messages are not empty
   */
  public boolean isNotEmpty() {
    return !list.isEmpty();
  }

  @Override
  public Iterator<ResultMessage> iterator() {
    return list.iterator();
  }
}
