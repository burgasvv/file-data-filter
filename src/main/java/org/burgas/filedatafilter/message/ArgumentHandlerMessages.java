package org.burgas.filedatafilter.message;

import org.burgas.filedatafilter.handler.ArgumentHandler;

/**
 * Перечисление сообщений относящихся к классу ArgumentHandler;
 * @see ArgumentHandler
 */
public enum ArgumentHandlerMessages {

    STATISTICS_ARGUMENT_HANDLING_FAILED("Ошибка при обработке параметров статистики: параметр статистики возможен только один"),
    ARGUMENTS_WITH_FILES_NOT_FOUND("\nАргументы с (input) файлами не найдены"),
    FILE_CREATION_FAILURE("Невозможно создать файл"),
    WRONG_OUTPUT_FILE_PREFIX("Wrong output file prefix"),
    WRONG_OUTPUT_FILE_PATH("Неверный путь к output файлам"),
    FILE_NOT_FOUND("Файл (input) чтения %s не найден на носителе");

    private final String message;

    ArgumentHandlerMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
