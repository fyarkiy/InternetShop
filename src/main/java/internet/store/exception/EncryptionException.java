package internet.store.exception;

public class EncryptionException extends RuntimeException {
    public EncryptionException(String message, Throwable e) {
        super(message, e);
    }
}
