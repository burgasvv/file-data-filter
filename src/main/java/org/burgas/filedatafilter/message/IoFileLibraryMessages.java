package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу IoFileLibrary
 * @see org.burgas.filedatafilter.io.IoFileLibrary
 */
public enum IoFileLibraryMessages {

    REMOVE_READER_FAILED("Не удалось удалить поток чтения"),
    REMOVE_WRITER_FAILED("Не удалось удалить поток записи");

    private final String message;

    IoFileLibraryMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
