package com.shared.exception;

import com.shared.error.GenericError;
import com.shared.error.TypeExceptionEnum;
import lombok.Getter;
import java.io.Serial;

@Getter
public class Z4GreedMoviesException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private final TypeExceptionEnum type;
    private final String code;
    private final String message;

    public Z4GreedMoviesException(GenericError codeError) {
        super();
        this.type = codeError.getType();
        this.code = codeError.getCode();
        this.message = codeError.getMessage();
    }

    public Z4GreedMoviesException(GenericError codeError, String message) {
        super();
        this.type = codeError.getType();
        this.code = codeError.getCode();
        this.message = message;
    }

}