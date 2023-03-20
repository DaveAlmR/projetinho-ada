package org.atividade.util;

public class CustomExceptions {
    public static RuntimeException getInternError(Exception e) {
        return new RuntimeException(String.format("Erro interno (%s)", e.getMessage()));
    }
}
