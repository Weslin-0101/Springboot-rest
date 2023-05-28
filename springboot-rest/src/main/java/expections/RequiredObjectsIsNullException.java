package expections;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredObjectsIsNullException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public RequiredObjectsIsNullException() {
        super("It is not allowed to persist a null object!");
    }

    public RequiredObjectsIsNullException(String msg) {
        super(msg);
    }
}
