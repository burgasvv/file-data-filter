package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу FileDataFilter
 * @see org.burgas.filedatafilter.filter.FileDataFilter
 */
public enum FileDataFilterMessages {

    FILE_NOT_FOUND_FOR_ADDING_TO_READERS("Файл не найден и не может быть добавлен в потоки чтения");

    private final String message;

    FileDataFilterMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
