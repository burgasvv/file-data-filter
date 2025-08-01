package org.burgas.filedatafilter.message;

/**
 * Перечисление сообщений относящихся к классу FileDataFilter;
 * @see org.burgas.filedatafilter.filter.FileDataFilter
 */
public enum FileDataFilterMessages {

    OUTPUT_STREAM_NOT_FOUND("Невозможно найти поток для записи по причине неверного пути к результирующему файлу"),
    READ_TXT_FILE_FAILED("Невозможно прочитать txt файл"),
    READ_PDF_FILE_FAILED("Невозможно прочитать pdf файл"),
    READ_DOCX_FILE_FAILED("Невозможно прочитать docx файл"),
    DISTRIBUTED_DATA_WRITTEN("Данные распределены и записаны в файлы"),
    FILE_NOT_FOUND_FOR_ADDING_TO_READERS("Файл не найден и не может быть добавлен в потоки чтения");

    private final String message;

    FileDataFilterMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
