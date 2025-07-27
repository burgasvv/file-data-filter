package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу ReadWriteFileApi
 * @see org.burgas.filedatafilter.readwrite.ReadWriteFileApi
 */
public enum ReadWriteFileApiMessages {

    FILE_FOR_READER_CREATION_FAILURE("Невозможно создать reader по причине отсутствия файла"),
    FILE_FOR_WRITER_CREATION_FAILURE("Невозможно создать writer по причине отсутствия файла"),
    READ_OR_WRITE_FAILED("Невозможно выполнить операцию чтения или записи"),
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
