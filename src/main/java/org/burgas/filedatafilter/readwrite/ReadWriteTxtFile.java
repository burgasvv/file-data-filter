package org.burgas.filedatafilter.readwrite;

import org.burgas.filedatafilter.exception.ReadWriteFailedException;
import org.burgas.filedatafilter.exception.RemoveReaderOrWriterException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static org.burgas.filedatafilter.message.ReadWriteFileApiMessages.*;

/**
 * Класс реализации для работы с файлами и потоками чтения и записи;
 */
public final class ReadWriteTxtFile implements ReadWriteFileApi<BufferedReader, BufferedWriter> {

    /**
     * Ассоциативный массив, содержащий потоки для чтения;
     */
    private final LinkedHashMap<String, BufferedReader> readers = new LinkedHashMap<>();

    /**
     * Ассоциативный массив, содержащий потоки для записи;
     */
    private final LinkedHashMap<String, BufferedWriter> writers = new LinkedHashMap<>();

    /**
     * Добавление потока для чтения ассоциативный массив;
     * @param fileName путь к файлу;
     * @param reader объект потока для чтения;
     */
    @Override
    public void addReader(final String fileName, final BufferedReader reader) {
        this.readers.put(fileName, reader);
    }

    /**
     * Добавление потока для записи ассоциативный массив;
     * @param fileName путь к файлу;
     * @param writer объект потока для записи;
     */
    @Override
    public void addWriter(final String fileName, final BufferedWriter writer) {
       this.writers.put(fileName, writer);
    }

    /**
     * Удаление потока для чтения из ассоциативного массива;
     * @param fileName путь к файлу;
     */
    @Override
    public void removeReader(final String fileName) {
        try (@SuppressWarnings("unused") BufferedReader bufferedReader = this.readers.remove(fileName)) {
            out.println("Поток чтения удален для txt файла: " + fileName);

        } catch (IOException e) {
            throw new RemoveReaderOrWriterException(REMOVE_READER_FAILED.getMessage());
        }
    }

    /**
     * Удаление потока для записи из ассоциативного массива;
     * @param fileName путь к файлу;
     */
    @Override
    public void removeWriter(final String fileName) {
        try (@SuppressWarnings("unused") BufferedWriter bufferedReader = this.writers.remove(fileName)) {
            out.println("Поток записи удален для txt файла: " + fileName);

        } catch (IOException e) {
            throw new RemoveReaderOrWriterException(REMOVE_WRITER_FAILED.getMessage());
        }
    }

    /**
     * Метод создания потока для чтения;
     * @param fileName наименование и путь к файлу;
     * @return поток чтения из файла;
     */
    @Override
    public BufferedReader createReader(String fileName) {
        try {
            return new BufferedReader(
                    new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8)
            );

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Метод создания потока для записи;
     * @param fileName наименование и путь к файлу;
     * @param append параметр режима записи (с добавлением или перезаписью);
     * @return поток записи в файл;
     */
    @Override
    public BufferedWriter createWriter(String fileName, Boolean append) {
        try {
            return new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(fileName, append), StandardCharsets.UTF_8)
            );

        } catch (FileNotFoundException e) {
            return null;
        }
    }

    /**
     * Метод добавления списка файлов для чтения в ассоциативный массив;
     * @param fileNames список местоположений и наименований файлов на носителе;
     */
    public void addReaders(final List<String> fileNames) {
        for (String fileName : fileNames)
            this.addReader(fileName, this.createReader(fileName));
    }

    /**
     * Метод удаления списка файлов для чтения из ассоциативного массива;
     * @param fileNames список местоположений и наименований файлов на носителе;
     */
    public void removeReaders(final List<String> fileNames) {
        for (String fileName : fileNames)
            this.removeReader(fileName);
    }

    /**
     * Метод для записи в файл из ассоциативно массива;
     * @param fileName наименование и путь к файлу;
     * @param content информация для записи;
     */
    public void write(final String fileName, final String content) {
        BufferedWriter bufferedWriter = this.writers.get(fileName);
        try {
            bufferedWriter.write(content + "\n");
            bufferedWriter.flush();

        } catch (IOException e) {
            throw new ReadWriteFailedException(READ_OR_WRITE_FAILED.getMessage());
        }
    }

    /**
     * Метод получения приватного поля;
     * @return объект ассоциативного массива с потоками для чтения;
     */
    public Map<String, BufferedReader> getReaders() {
        return this.readers;
    }

    /**
     * Метод получения приватного поля;
     * @return объект ассоциативного массива с потоками для записи;
     */
    public Map<String, BufferedWriter> getWriters() {
        return this.writers;
    }
}
