package com.shared.utils;

import com.shared.enums.ValueEnum;
import com.shared.error.GeneralErrorEnum;
import com.shared.error.GenericError;
import com.shared.exception.Z4GreedMoviesException;
import java.util.Collection;

public class ValidateUtil {

    public static void validateUnique(Boolean exists, ValueEnum existingValueName, String existingValue) {
        ValidateUtil.evaluateTrue(exists, GeneralErrorEnum.ER000005,existingValueName.getValue(), existingValue);
    }

    public static void evaluateTrue(boolean expression, GenericError codeError){
        if (expression) {
            throwZ4GreedMoviesException(codeError);
        }
    }

    public static void evaluateFalse(boolean expression, GenericError codeError){
        if (!expression) {
            throwZ4GreedMoviesException(codeError);
        }
    }

    public static void evaluateTrue(boolean expression, GenericError codeError, Object... value) {
        if (expression) {
            throwZ4GreedMoviesException(codeError, value);
        }
    }

    public static void evaluateFalse(boolean expression, GenericError codeError, Object... value) {
        if (!expression) {
            throwZ4GreedMoviesException(codeError, value);
        }

    }

    public static void hasData(Collection<?> list, GenericError codeError) {
        if (!hasData(list)) {
            throwZ4GreedMoviesException(codeError);
        }
    }

    public static void hasData(Collection<?> list, Object[] valueEmpty) {
        if (!hasData(list)) {
            throwZ4GreedMoviesException(GeneralErrorEnum.ER000003, valueEmpty);
        }
    }

    public static boolean hasData(Collection<?> list){
        return list != null && !list.isEmpty();
    }

    private static void throwZ4GreedMoviesException(GenericError codeError) {
        throw new Z4GreedMoviesException(codeError);
    }

    private static void throwZ4GreedMoviesException(GenericError codeError, Object[] value) {
        String message = String.format(codeError.getMessage(), value);
        throw new Z4GreedMoviesException(codeError, message);
    }

    public static Z4GreedMoviesException throwNotFoundException(Object... messageError) {
        return getZ4GreedMoviesException(GeneralErrorEnum.ER000004, messageError);
    }

    public static Z4GreedMoviesException throwNotFoundException(String valueNotFound) {
        return getZ4GreedMoviesException(GeneralErrorEnum.ER000002, (Object) valueNotFound);
    }

    private static Z4GreedMoviesException getZ4GreedMoviesException(GeneralErrorEnum errorEnum, Object... messageError) {
        String message = String.format(errorEnum.getMessage(), messageError);
        return new Z4GreedMoviesException(errorEnum, message);
    }

}