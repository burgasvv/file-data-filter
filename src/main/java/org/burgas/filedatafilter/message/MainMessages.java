package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу Main
 * @see org.burgas.filedatafilter.Main
 */
public enum MainMessages {

    ARGUMENTS_WITH_FILES_NOT_FOUND("\nАргументы с (input) файлами с расширением .txt не найдены");

    private final String message;

    MainMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
