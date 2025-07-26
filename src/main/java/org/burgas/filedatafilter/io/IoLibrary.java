package org.burgas.filedatafilter.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 * Интерфейс для работы с файлами и потоками чтения и записи;
 */
public sealed interface IoLibrary permits IoFileLibrary {

    /**
     * Метод добавления потока для чтения;
     * @param fileName путь к файлу;
     * @param reader объект потока для чтения;
     */
    void addReader(String fileName, BufferedReader reader);

    /**
     * Метод добавления потока для записи;
     * @param fileName путь к файлу;
     * @param writer объект потока для записи;
     */
    void addWriter(String fileName, BufferedWriter writer);

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
}
