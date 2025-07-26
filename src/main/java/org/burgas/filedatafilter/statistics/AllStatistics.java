package org.burgas.filedatafilter.statistics;

import org.burgas.filedatafilter.exception.AddReaderFailedException;
import org.burgas.filedatafilter.handler.ArgumentHandler;
import org.burgas.filedatafilter.readwrite.ReadWriteFileApi;

import java.io.FileNotFoundException;
import java.util.List;

import static org.burgas.filedatafilter.message.AllStatisticsMessages.ADD_READER_FAILED;

/**
 * Класс для сбора всей статистики
 */
public final class AllStatistics {

    /**
     * Ссылка на объект Обработчик аргументов;
     */
    private final ArgumentHandler argumentHandler;

    /**
     * Ссылка на объект для чтения и записи файлов;
     */
    private final ReadWriteFileApi readWriteFileApi;

    /**
     * Ссылки на объекты статистики разных типов данных;
     */
    private final StringStatistics stringStatistics;
    private final LongStatistics longStatistics;
    private final FloatStatistics floatStatistics;

    public AllStatistics(final ArgumentHandler argumentHandler, ReadWriteFileApi readWriteFileApi) {
        this.argumentHandler = argumentHandler;
        this.readWriteFileApi = readWriteFileApi;
        this.stringStatistics = new StringStatistics();
        this.longStatistics = new LongStatistics();
        this.floatStatistics = new FloatStatistics();
    }

    /**
     * Чтение с файлов распределения данных и получение статистики по строковым, вещественным и целочисленным типам данных;
     * @return получение статистики в виде строки;
     */
    public String getStatistics() {
        String strings = this.argumentHandler.getOutputFilePathsMap().get("strings");
        String integers = this.argumentHandler.getOutputFilePathsMap().get("integers");
        String floats = this.argumentHandler.getOutputFilePathsMap().get("floats");

        try {
            this.readWriteFileApi.addReaders(List.of(strings, integers, floats));

        } catch (FileNotFoundException e) {
            throw new AddReaderFailedException(ADD_READER_FAILED.getMessage());
        }

        this.readWriteFileApi.getReaders().get(strings).lines().forEach(this.stringStatistics::add);
        this.readWriteFileApi.getReaders().get(integers).lines().forEach(s -> this.longStatistics.add(Long.parseLong(s)));
        this.readWriteFileApi.getReaders().get(floats).lines().forEach(s -> this.floatStatistics.add(Float.parseFloat(s)));

        return "\n" + this.stringStatistics.getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics()) + "\n\n" +
               this.longStatistics
                       .getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics()) + "\n\n" +
               this.floatStatistics
                       .getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics());
    }
}
