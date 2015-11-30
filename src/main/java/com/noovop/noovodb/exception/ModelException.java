/**
 * 
 */
package com.noovop.noovodb.exception;

/**
 * Noovop Exception class.
 * 
 * @author Noovop
 * @version 1.0
 */
public class ModelException extends Exception {

  private static final long serialVersionUID = 1L;

  final static Integer DEFAULT_ERROR_CODE = 0;

  /**
   * The error code
   */
  private final Integer code;

  /**
   * Creates a new {@link ModelException}.
   */
  public ModelException() {
    super();
    this.code = DEFAULT_ERROR_CODE;
  }

  /**
   * Creates a new {@link ModelException}.
   * 
   * @param code the error code
   * @param message the error message
   * @param cause the error cause
   */
  public ModelException(Integer code, String message, Throwable cause) {
    super(message, cause);
    this.code = code;
  }

  /**
   * Creates a new {@link ModelException}.
   * 
   * @param message the error message
   * @param cause the error cause
   */
  public ModelException(String message, Throwable cause) {
    super(message, cause);
    this.code = DEFAULT_ERROR_CODE;
  }

  /**
   * Creates a new {@link ModelException}.
   * 
   * @param message the error message
   */
  public ModelException(String message) {
    super(message);
    this.code = DEFAULT_ERROR_CODE;
  }

  /**
   * Creates a new {@link ModelException}.
   * 
   * @param cause the error cause
   */
  public ModelException(Throwable cause) {
    super(cause);
    this.code = DEFAULT_ERROR_CODE;
  }

  /**
   * @return the code
   */
  public Integer getCode() {
    return code;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("[code=");
    builder.append(code);
    builder.append(" - details=");
    builder.append(super.toString());
    builder.append("]");
    return builder.toString();
  }

}
