package utils;


public class PermissionException extends RuntimeException{
    public PermissionException() {

    }

    public PermissionException(String msg) {
        super(msg);
    }
}
