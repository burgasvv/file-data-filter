package org.burgas.filedatafilter.filter;

import org.burgas.filedatafilter.exception.ReadWriteFailedException;
import org.burgas.filedatafilter.handler.ArgumentHandlerImpl;
import org.burgas.filedatafilter.readwrite.ReadWriteFileApi;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static java.lang.String.valueOf;
import static java.lang.System.*;
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
     * @throws IOException исключение, получаемое в случае невозможности записи;
     */
    private void writeToFile(String filename, String content)
            throws IOException {

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
            this.readWriteFileApi.addReaders(this.argumentHandlerImpl.getInputFilePaths());

        } catch (FileNotFoundException e) {
            throw new org.burgas.filedatafilter.exception.FileNotFoundException(
                    FILE_NOT_FOUND_FOR_ADDING_TO_READERS.getMessage()
            );
        }

        Map<String, String> outputFilePathsMap = this.argumentHandlerImpl.getOutputFilePathsMap();

        for (Map.Entry<String, BufferedReader> entry : this.readWriteFileApi.getReaders().entrySet()) {
            BufferedReader reader = entry.getValue();

            try {
                while (reader.ready()) {
                    String line = reader.readLine();

                    try {
                        long aLong = Long.parseLong(line);
                        this.writeToFile(outputFilePathsMap.get("integers"), valueOf(aLong));

                    } catch (NumberFormatException e) {

                        try {
                            double aDouble = Double.parseDouble(line);
                            this.writeToFile(outputFilePathsMap.get("floats"), valueOf(aDouble));

                        } catch (NumberFormatException e2) {
                            this.writeToFile(outputFilePathsMap.get("strings"), line);
                        }
                    }
                }

            } catch (IOException e) {
                throw new ReadWriteFailedException(READ_OR_WRITE_FAILED.getMessage());
            }
        }

        out.println("Данные распределены и записаны в файлы\n");
    }
}
