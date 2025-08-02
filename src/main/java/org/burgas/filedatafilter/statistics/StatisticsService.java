package org.burgas.filedatafilter.statistics;

import org.burgas.filedatafilter.handler.ArgumentHandler;
import org.burgas.filedatafilter.readwrite.buffered.BufferedReaderWriter;

import java.io.BufferedReader;
import java.util.List;

/**
 * Класс для сбора всей статистики
 */
public final class StatisticsService {

    /**
     * Ссылка на объект Обработчик аргументов;
     */
    private final ArgumentHandler argumentHandler;

    /**
     * Ссылка на объект для чтения и записи файлов;
     */
    private final BufferedReaderWriter bufferedReaderWriter;

    /**
     * Ссылки на объекты статистики разных типов данных;
     */
    private final StringStatistics stringStatistics = new StringStatistics();
    private final LongStatistics longStatistics = new LongStatistics();
    private final DoubleStatistics doubleStatistics = new DoubleStatistics();

    public StatisticsService(final ArgumentHandler argumentHandler, final BufferedReaderWriter bufferedReaderWriter) {
        this.argumentHandler = argumentHandler;
        this.bufferedReaderWriter = bufferedReaderWriter;
    }

    /**
     * Чтение файлов распределения данных и получение статистики по строковым, вещественным и целочисленным типам данных;
     * @return получение статистики в виде строки;
     */
    public String getStatistics() {

        // Получение директорий результирующих файлов;
        String strings = this.argumentHandler.getOutputFilePathsMap().get("strings");
        String integers = this.argumentHandler.getOutputFilePathsMap().get("integers");
        String floats = this.argumentHandler.getOutputFilePathsMap().get("floats");

        this.handleReaders(List.of(strings, integers, floats));
        this.addElementsToStatistics(strings, integers, floats);

        // Получение всей статистики по всем типам данных;
        return this.stringStatistics.getStatistics(
                               this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics()) + "\n\n" +
               this.longStatistics.getStatistics(
                               this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics()) + "\n\n" +
               this.doubleStatistics.getStatistics(
                               this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics());
    }

    /**
     * Приватный метод добавления и удаления потоков чтения для файлов;
     * @param fileNames наименования результирующих файлов;
     */
    private void handleReaders(final List<String> fileNames) {
        // Добавление потоков чтения для результирующих файлов;
        this.bufferedReaderWriter.addReaders(fileNames);
    }

    /**
     * Приватный метод добавления элементов в статистику;
     * @param strings путь к файлу, содержащему строковые значения;
     * @param integers путь к файлу, содержащему целочисленные значения;
     * @param floats путь к файлу, содержащему вещественные значения;
     */
    private void addElementsToStatistics(final String strings, final String integers, final String floats) {

        // Чтение данных из результирующего файла для строк и отправка для обработки в сервис строковой статистики;
        BufferedReader stringsBufferedReader = this.bufferedReaderWriter.getReaders().get(strings);
        if (stringsBufferedReader != null) {
            stringsBufferedReader.lines()
                    .forEach(this.stringStatistics::add);
        }

        // Чтение данных из результирующего файла для целых чисел и отправка для обработки в сервис целочисленной статистики;
        BufferedReader longsBufferedReader = this.bufferedReaderWriter.getReaders().get(integers);
        if (longsBufferedReader != null) {
            longsBufferedReader.lines()
                    .forEach(string -> this.longStatistics.add(Long.parseLong(string)));
        }

        // Чтение данных из результирующего файла для вещественных чисел и отправка для обработки в сервис вещественной статистики;
        BufferedReader floatsBufferedReader = this.bufferedReaderWriter.getReaders().get(floats);
        if (floatsBufferedReader != null) {
            floatsBufferedReader.lines()
                    .forEach(string -> this.doubleStatistics.add(Double.parseDouble(string)));
        }
    }
}
