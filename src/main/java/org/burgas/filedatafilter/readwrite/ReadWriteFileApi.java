package org.burgas.filedatafilter.readwrite;

import java.io.Reader;
import java.io.Writer;

/**
 * Интерфейс для работы с файлами и потоками чтения и записи;
 */
public interface ReadWriteFileApi<T extends Reader, V extends Writer> {

    /**
     * Метод добавления потока для чтения;
     * @param fileName путь к файлу;
     */
    void addReader(String fileName, T t);

    /**
     * Метод добавления потока для записи;
     * @param fileName путь к файлу;
     */
    void addWriter(String fileName, V v);

    /**
     * Метод удаления потока для чтения;
     * @param fileName путь к файлу;
     */
    @SuppressWarnings("unused")
    void removeReader(String fileName);

    /**
     * Метод удаления потока для записи;
     * @param fileName путь к файлу;
     */
    @SuppressWarnings("unused")
    void removeWriter(String fileName);

    /**
     * Метод создания потока для чтения;
     * @param fileName путь к файлу;
     * @return поток для чтения;
     */
    T createReader(String fileName);

    /**
     * Метод создания потока для записи;
     * @param fileName путь к файлу;
     * @param append условие перезаписи;
     * @return поток для записи;
     */
    V createWriter(String fileName, Boolean append);
}
