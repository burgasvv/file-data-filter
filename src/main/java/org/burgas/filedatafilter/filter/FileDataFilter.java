package org.burgas.filedatafilter.filter;

import org.burgas.filedatafilter.handler.ArgumentHandler;
import org.burgas.filedatafilter.io.IoFileLibrary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import static java.lang.String.valueOf;

/**
 * Класс, позволяющий считывать данные из файлов,
 * распределять данные по их типу, и записывать в файл;
 */
public final class FileDataFilter implements DataFilter {

    /**
     * Ссылка на объект Обработчик аргументов;
     */
    private final ArgumentHandler argumentHandler;

    /**
     * Ссылка на объект для чтения и записи файлов;
     */
    private final IoFileLibrary ioFileLibrary;

    /**
     * Конструктор для создания объектов и добавления файлов для считывания данных и дальнейшей обработки;
     * @throws FileNotFoundException исключение, получаемое при отсутствии файлов для считывания;
     */
    public FileDataFilter(final ArgumentHandler argumentHandler, final IoFileLibrary ioFileLibrary) throws FileNotFoundException {
        this.argumentHandler = argumentHandler;
        this.ioFileLibrary = ioFileLibrary;
        this.ioFileLibrary.addReaders(argumentHandler.getInputFilePaths());
    }

    /**
     * Метод записи в файл с учетом проверки на существование данного файла по его наименованию;
     * @param filename наименование файла;
     * @param content информация для записи;
     * @throws IOException исключение, получаемое в случае невозможности записи;
     */
    private void writeToFile(String filename, String content)
            throws IOException {

        if (this.ioFileLibrary.getWriters().containsKey(filename))
            this.ioFileLibrary.write(filename, content);

        else {
            BufferedWriter fileWriter = this.ioFileLibrary.createFileWriter(filename, this.argumentHandler.isFileWriteAppend());
            this.ioFileLibrary.addWriter(filename, fileWriter);
            this.ioFileLibrary.write(filename, content);
        }
    }

    /**
     * Метод, в котором происходит считывание информации в виде строкового типа данных из полученных файлов,
     * преобразование в целочисленные и вещественные типы данных и запись в соответствующие их типу файлы;
     */
    @Override
    public void filter() throws IOException {
        Map<String, String> resultPathMap = this.argumentHandler.getOutputFilePathsMap();

        for (Map.Entry<String, BufferedReader> entry : this.ioFileLibrary.getReaders().entrySet()) {
            BufferedReader reader = entry.getValue();

            while (reader.ready()) {
                String line = reader.readLine();

                try {
                    long aLong = Long.parseLong(line);
                    this.writeToFile(resultPathMap.get("integers"), valueOf(aLong));

                } catch (NumberFormatException e) {

                    try {
                        float aFloat = Float.parseFloat(line);
                        this.writeToFile(resultPathMap.get("floats"), valueOf(aFloat));

                    } catch (NumberFormatException e2) {
                        this.writeToFile(resultPathMap.get("strings"), line);
                    }
                }
            }
        }
    }
}
