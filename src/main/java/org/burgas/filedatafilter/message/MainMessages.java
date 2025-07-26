package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу Main
 * @see org.burgas.filedatafilter.Main
 */
public enum MainMessages {

    ARGUMENTS_RECEIVED_SUCCESSFULLY("\nАргументы с (input) файлами для чтения с расширением .txt получены"),
    ARGUMENTS_WITH_FILES_NOT_FOUND("\nАргументы с (input) файлами с расширением .txt не найдены"),
    ARGUMENTS("Аргументы: %s");

    private final String message;

    MainMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
