package org.burgas.filedatafilter.statistics;

import org.burgas.filedatafilter.handler.ArgumentHandlerImpl;
import org.burgas.filedatafilter.readwrite.ReadWriteFileApi;

import java.io.BufferedReader;
import java.util.List;

/**
 * Класс для сбора всей статистики
 */
public final class AllStatistics {

    /**
     * Ссылка на объект Обработчик аргументов;
     */
    private final ArgumentHandlerImpl argumentHandlerImpl;

    /**
     * Ссылка на объект для чтения и записи файлов;
     */
    private final ReadWriteFileApi readWriteFileApi;

    /**
     * Ссылки на объекты статистики разных типов данных;
     */
    private final StringStatistics stringStatistics;
    private final LongStatistics longStatistics;
    private final DoubleStatistics doubleStatistics;

    public AllStatistics(final ArgumentHandlerImpl argumentHandlerImpl, final ReadWriteFileApi readWriteFileApi) {
        this.argumentHandlerImpl = argumentHandlerImpl;
        this.readWriteFileApi = readWriteFileApi;
        this.stringStatistics = new StringStatistics();
        this.longStatistics = new LongStatistics();
        this.doubleStatistics = new DoubleStatistics();
    }

    /**
     * Чтение файлов распределения данных и получение статистики по строковым, вещественным и целочисленным типам данных;
     * @return получение статистики в виде строки;
     */
    public String getStatistics() {
        String strings = this.argumentHandlerImpl.getOutputFilePathsMap().get("strings");
        String integers = this.argumentHandlerImpl.getOutputFilePathsMap().get("integers");
        String floats = this.argumentHandlerImpl.getOutputFilePathsMap().get("floats");

        handleReaders(strings, integers, floats);
        addElementsToStatistics(strings, integers, floats);

        return this.stringStatistics.getStatistics(
                               this.argumentHandlerImpl.getShortStatistics(), this.argumentHandlerImpl.getFullStatistics()) + "\n\n" +
               this.longStatistics.getStatistics(
                               this.argumentHandlerImpl.getShortStatistics(), this.argumentHandlerImpl.getFullStatistics()) + "\n\n" +
               this.doubleStatistics.getStatistics(
                               this.argumentHandlerImpl.getShortStatistics(), this.argumentHandlerImpl.getFullStatistics());
    }

    /**
     * Приватный метод добавления и удаления потоков чтения для файлов;
     * @param strings путь к файлу, содержащему строковые значения;
     * @param integers путь к файлу, содержащему целочисленные значения;
     * @param floats путь к файлу, содержащему вещественные значения;
     */
    private void handleReaders(final String strings, final String integers, final String floats) {
        this.readWriteFileApi.removeReaders(this.argumentHandlerImpl.getInputFilePaths());
        this.readWriteFileApi.addReaders(List.of(strings, integers, floats));
    }

    /**
     * Приватный метод добавления элементов в статистику;
     * @param strings путь к файлу, содержащему строковые значения;
     * @param integers путь к файлу, содержащему целочисленные значения;
     * @param floats путь к файлу, содержащему вещественные значения;
     */
    private void addElementsToStatistics(final String strings, final String integers, final String floats) {
        BufferedReader stringsBufferedReader = this.readWriteFileApi.getReaders().get(strings);
        if (stringsBufferedReader != null) {
            stringsBufferedReader.lines()
                    .forEach(this.stringStatistics::add);
        }

        BufferedReader longsBufferedReader = this.readWriteFileApi.getReaders().get(integers);
        if (longsBufferedReader != null) {
            longsBufferedReader.lines()
                    .forEach(string -> this.longStatistics.add(Long.parseLong(string)));
        }

        BufferedReader floatsBufferedReader = this.readWriteFileApi.getReaders().get(floats);
        if (floatsBufferedReader != null) {
            floatsBufferedReader.lines()
                    .forEach(string -> this.doubleStatistics.add(Double.parseDouble(string)));
        }
    }
}
