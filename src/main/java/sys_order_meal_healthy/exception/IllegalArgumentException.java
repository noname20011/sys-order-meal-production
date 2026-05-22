package sys_order_meal_healthy.exception;

public class IllegalArgumentException extends BaseException {
    public IllegalArgumentException(String message) {
        super(message);
    }

    public IllegalArgumentException(String message,  Throwable cause) {
        super(message, cause);
    }
}
