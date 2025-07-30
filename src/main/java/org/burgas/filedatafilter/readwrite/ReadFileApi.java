package org.burgas.filedatafilter.readwrite;

/**
 * Интерфейс для чтения pdf и docx файлов;
 * @param <T> обобщение для потока чтения данных;
 */
public sealed interface ReadFileApi<T> permits ReadPdfFile, ReadDocxFile {

    /**
     * Метода создания потока для чтения данных;
     * @param fileName путь к файлу;
     * @return поток для чтения данных;
     */
    T createReader(String fileName);
}
