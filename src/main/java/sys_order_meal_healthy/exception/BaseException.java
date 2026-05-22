package sys_order_meal_healthy.exception;

public class BaseException extends RuntimeException {
    BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    BaseException(String message) {
        super(message);
    }
}
