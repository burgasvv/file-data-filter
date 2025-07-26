package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу ArgumentHandler
 * @see org.burgas.filedatafilter.handler.ArgumentHandler
 */
public enum ArgumentHandlerMessages {

    FILE_NOT_FOUND("Файл (input) чтения с расширением .txt %s не найден на носителе");

    private final String message;

    ArgumentHandlerMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
