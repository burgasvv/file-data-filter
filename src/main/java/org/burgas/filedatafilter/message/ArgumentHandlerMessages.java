package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу ArgumentHandler
 * @see org.burgas.filedatafilter.handler.ArgumentHandlerImpl
 */
public enum ArgumentHandlerMessages {

    ARGUMENTS_WITH_FILES_NOT_FOUND("\nАргументы с (input) файлами с расширением .txt не найдены"),
    FILE_CREATION_FAILURE("Невозможно создать файл"),
    WRONG_OUTPUT_FILE_PREFIX("Wrong output file prefix"),
    WRONG_OUTPUT_FILE_PATH("Неверный путь к output файлам"),
    FILE_NOT_FOUND("Файл (input) чтения с расширением .txt %s не найден на носителе");

    private final String message;

    ArgumentHandlerMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
