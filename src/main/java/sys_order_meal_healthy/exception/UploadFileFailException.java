package sys_order_meal_healthy.exception;

public class UploadFileFailException extends BaseException {
    public UploadFileFailException(String message, Throwable cause) {
        super(message,  cause);
    }

    public UploadFileFailException(String message) {
        super(message);
    }
}
