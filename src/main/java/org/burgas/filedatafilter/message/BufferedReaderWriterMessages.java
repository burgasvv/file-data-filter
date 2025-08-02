package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу BufferedReaderWriter;
 * @see org.burgas.filedatafilter.readwrite.buffered.BufferedReaderWriter
 */
public enum BufferedReaderWriterMessages {

    READER_DELETED("Поток чтения удален для txt файла: %s"),
    WRITER_DELETED("Поток записи удален для txt файла: %s"),
    READ_OR_WRITE_FAILED("Невозможно выполнить операцию чтения или записи"),
    REMOVE_READER_FAILED("Не удалось удалить поток чтения из ассоциативного массива"),
    REMOVE_WRITER_FAILED("Не удалось удалить поток записи из ассоциативного массива");

    private final String message;

    BufferedReaderWriterMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
