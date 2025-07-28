package org.burgas.filedatafilter.filter;

import org.burgas.filedatafilter.exception.FileNotFoundException;
import org.burgas.filedatafilter.exception.ReadWriteFailedException;
import org.burgas.filedatafilter.handler.ArgumentHandlerImpl;
import org.burgas.filedatafilter.readwrite.ReadWriteFileApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

import static java.lang.String.valueOf;
import static java.lang.System.out;
import static org.burgas.filedatafilter.message.FileDataFilterMessages.FILE_NOT_FOUND_FOR_ADDING_TO_READERS;
import static org.burgas.filedatafilter.message.ReadWriteFileApiMessages.READ_OR_WRITE_FAILED;

/**
 * Класс, позволяющий считывать данные из файлов,
 * распределять данные по их типу, и записывать в файл;
 */
public final class FileDataFilter implements DataFilter {

    /**
     * Ссылка на объект Обработчик аргументов;
     */
    private final ArgumentHandlerImpl argumentHandlerImpl;

    /**
     * Ссылка на объект для чтения и записи файлов;
     */
    private final ReadWriteFileApi readWriteFileApi;

    /**
     * Конструктор для создания объектов и добавления файлов для считывания данных и дальнейшей обработки;
     */
    public FileDataFilter(final ArgumentHandlerImpl argumentHandlerImpl, final ReadWriteFileApi readWriteFileApi) {
        this.argumentHandlerImpl = argumentHandlerImpl;
        this.readWriteFileApi = readWriteFileApi;
    }

    /**
     * Метод записи в файл с учетом проверки на существование данного файла по его наименованию;
     * @param filename наименование файла;
     * @param content информация для записи;
     */
    private void writeToFile(String filename, String content) {

        // Проверка на наличие потока записи и запись в файл;
        if (this.readWriteFileApi.getWriters().containsKey(filename))
            this.readWriteFileApi.write(filename, content);

        else {
            BufferedWriter fileWriter = this.readWriteFileApi.createFileWriter(filename, this.argumentHandlerImpl.isFileWriteAppend());
            this.readWriteFileApi.addWriter(filename, fileWriter);
            this.readWriteFileApi.write(filename, content);
        }
    }

    /**
     * Метод, в котором происходит считывание информации в виде строкового типа данных из полученных файлов,
     * преобразование в целочисленные и вещественные типы данных и запись в соответствующие их типу файлы;
     */
    @Override
    public void filter() {
        try {
            // Добавление потоков чтения для исходных файлов перед фильтрацией;
            this.readWriteFileApi.addReaders(this.argumentHandlerImpl.getInputFilePaths());

        } catch (FileNotFoundException e) {
            throw new org.burgas.filedatafilter.exception.FileNotFoundException(
                    FILE_NOT_FOUND_FOR_ADDING_TO_READERS.getMessage()
            );
        }

        // Получение директорий для результирующих файлов;
        Map<String, String> outputFilePathsMap = this.argumentHandlerImpl.getOutputFilePathsMap();

        // Цикл для чтения данных из исходных файлов, распределения и записи в результирующие файлы;
        for (Map.Entry<String, BufferedReader> entry : this.readWriteFileApi.getReaders().entrySet()) {

            try {
                // Получения потока для чтения;
                BufferedReader reader = entry.getValue();

                while (reader.ready()) {

                    // Получение строки из исходного файла;
                    String line = reader.readLine();

                    try {
                        // Преобразование в целочисленный тип данных и запись в соответствующий файл;
                        long aLong = Long.parseLong(line);
                        this.writeToFile(outputFilePathsMap.get("integers"), valueOf(aLong));

                    } catch (NumberFormatException e) {

                        try {
                            // Преобразование в вещественный тип данных и запись в соответствующий файл;
                            double aDouble = Double.parseDouble(line);
                            this.writeToFile(outputFilePathsMap.get("floats"), valueOf(aDouble));

                        } catch (NumberFormatException e2) {

                            // Запись строки в соответствующий файл;
                            this.writeToFile(outputFilePathsMap.get("strings"), line);
                        }
                    }
                }

            } catch (IOException e) {
                throw new ReadWriteFailedException(READ_OR_WRITE_FAILED.getMessage());
            }
        }

        out.println("\nДанные распределены и записаны в файлы\n");
    }
}
