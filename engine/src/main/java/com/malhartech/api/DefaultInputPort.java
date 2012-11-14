/**
 * Copyright (c) 2012-2012 Malhar, Inc.
 * All rights reserved.
 */
package com.malhartech.api;

import com.malhartech.api.Operator.InputPort;
import java.lang.reflect.ParameterizedType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default abstract implementation for input ports.
 * An operator would typically create a derived inner class that defines process method.
 * This class is designed for use with a transient field, i.e. not to be serialized with the operator state.
 */
public abstract class DefaultInputPort<T> implements InputPort<T>, Sink<T>
{
  private static final Logger logger = LoggerFactory.getLogger(DefaultInputPort.class);
  final private Operator operator;
  protected boolean connected = false;

  public DefaultInputPort(Operator module)
  {
    this.operator = module;
  }

  @Override
  final public Operator getOperator()
  {
    return operator;
  }

  @Override
  public Sink<T> getSink()
  {
    return this;
  }

  @Override
  public void setConnected(boolean connected)
  {
    this.connected = connected;
  }

  @Override
  public Class<? extends StreamCodec<T>> getStreamCodec()
  {
    ParameterizedType superclass = (ParameterizedType)getClass().getGenericSuperclass();
    Class<T> actualType = (Class<T>)superclass.getActualTypeArguments()[0];
    logger.debug("parameterized type = {}", actualType);
    return null;
  }

  /**
   * Processing logic to be implemented in derived class.
   */
  @Override
  public abstract void process(T tuple);
}
