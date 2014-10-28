package arrow.framework.exception;

public class ArrowException extends Exception {
  public ArrowException(Exception e) {
    super(e);
  }

  public ArrowException(String message) {
    super(message);
  }

  public ArrowException() {
    super();
  }
}
