package com.shared.error;

import static com.shared.error.TypeExceptionEnum.WARNING;

public enum GeneralErrorEnum  implements GenericError {
    ER000001("ER000001", "%s es requerido", WARNING),
    ER000002("ER000002", "%s no existe", WARNING),
    ER000003("ER000003", "%s no contiene elementos", WARNING),
    ER000004("ER000004", "No existe %s con ID: %s", WARNING),
    ER000005("ER000005", "Ya existe un registro con este %s: %s", WARNING);

    private GeneralErrorEnum(String code, String message, TypeExceptionEnum type) {
        this.code = code;
        this.message = message;
        this.type = type;
    }

    private final String code;
    private final String message;
    private final TypeExceptionEnum type;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    @Override
    public TypeExceptionEnum getType() {
        return this.type;
    }

}
