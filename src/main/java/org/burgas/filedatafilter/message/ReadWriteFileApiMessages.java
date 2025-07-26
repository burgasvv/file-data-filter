package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу ReadWriteFileApi
 * @see org.burgas.filedatafilter.readwrite.ReadWriteFileApi
 */
public enum ReadWriteFileApiMessages {

    REMOVE_READER_FAILED("Не удалось удалить поток чтения"),
    REMOVE_WRITER_FAILED("Не удалось удалить поток записи");

    private final String message;

    ReadWriteFileApiMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
