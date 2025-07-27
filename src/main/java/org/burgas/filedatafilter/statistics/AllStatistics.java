package org.burgas.filedatafilter.statistics;

import org.burgas.filedatafilter.exception.AddReaderFailedException;
import org.burgas.filedatafilter.handler.ArgumentHandlerImpl;
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
    private final FloatStatistics floatStatistics;

    public AllStatistics(final ArgumentHandlerImpl argumentHandlerImpl, ReadWriteFileApi readWriteFileApi) {
        this.argumentHandlerImpl = argumentHandlerImpl;
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
        String strings = this.argumentHandlerImpl.getOutputFilePathsMap().get("strings");
        String integers = this.argumentHandlerImpl.getOutputFilePathsMap().get("integers");
        String floats = this.argumentHandlerImpl.getOutputFilePathsMap().get("floats");

        try {
            this.readWriteFileApi.addReaders(List.of(strings, integers, floats));

        } catch (FileNotFoundException e) {
            throw new AddReaderFailedException(ADD_READER_FAILED.getMessage());
        }

        this.readWriteFileApi.getReaders().get(strings).lines().forEach(this.stringStatistics::add);
        this.readWriteFileApi.getReaders().get(integers).lines().forEach(s -> this.longStatistics.add(Long.parseLong(s)));
        this.readWriteFileApi.getReaders().get(floats).lines().forEach(s -> this.floatStatistics.add(Float.parseFloat(s)));

        return this.stringStatistics.getStatistics(this.argumentHandlerImpl.getShortStatistics(), this.argumentHandlerImpl.getFullStatistics()) + "\n\n" +
               this.longStatistics.getStatistics(this.argumentHandlerImpl.getShortStatistics(), this.argumentHandlerImpl.getFullStatistics()) + "\n\n" +
               this.floatStatistics.getStatistics(this.argumentHandlerImpl.getShortStatistics(), this.argumentHandlerImpl.getFullStatistics());
    }
}
