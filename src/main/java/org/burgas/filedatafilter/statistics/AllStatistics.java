package org.burgas.filedatafilter.statistics;

import org.burgas.filedatafilter.handler.ArgumentHandler;
import org.burgas.filedatafilter.io.IoFileLibrary;

import java.io.FileNotFoundException;
import java.util.List;

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
    private final IoFileLibrary ioFileLibrary;

    /**
     * Ссылки на объекты статистики разных типов данных;
     */
    private final StringStatistics stringStatistics;
    private final LongStatistics longStatistics;
    private final FloatStatistics floatStatistics;

    public AllStatistics(final ArgumentHandler argumentHandler, IoFileLibrary ioFileLibrary) {
        this.argumentHandler = argumentHandler;
        this.ioFileLibrary = ioFileLibrary;
        this.stringStatistics = new StringStatistics();
        this.longStatistics = new LongStatistics();
        this.floatStatistics = new FloatStatistics();
    }

    /**
     * Чтение с файлов распределения данных и получение статистики по строковым, вещественным и целочисленным типам данных;
     * @return получение статистики в виде строки;
     */
    public String getStatistics() throws FileNotFoundException {
        String strings = this.argumentHandler.getOutputFilePathsMap().get("strings");
        String integers = this.argumentHandler.getOutputFilePathsMap().get("integers");
        String floats = this.argumentHandler.getOutputFilePathsMap().get("floats");
        this.ioFileLibrary.addReaders(List.of(strings, integers, floats));

        this.ioFileLibrary.getReaders().get(strings).lines().forEach(this.stringStatistics::add);
        this.ioFileLibrary.getReaders().get(integers).lines().forEach(s -> this.longStatistics.add(Long.parseLong(s)));
        this.ioFileLibrary.getReaders().get(floats).lines().forEach(s -> this.floatStatistics.add(Float.parseFloat(s)));

        return "\n" + this.stringStatistics.getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics()) + "\n\n" +
               this.longStatistics
                       .getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics()) + "\n\n" +
               this.floatStatistics
                       .getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics());
    }
}
