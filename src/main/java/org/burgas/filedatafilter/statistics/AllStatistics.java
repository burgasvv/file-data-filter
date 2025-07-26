package org.burgas.filedatafilter.statistics;

import org.burgas.filedatafilter.handler.ArgumentHandler;

/**
 * Класс для сбора всей статистики
 */
public final class AllStatistics {

    /**
     * Ссылка на объект Обработчик аргументов;
     */
    private final ArgumentHandler argumentHandler;

    /**
     * Ссылки на объекты статистики разных типов данных
     */
    private final StringStatistics stringStatistics;
    private final LongStatistics longStatistics;
    private final FloatStatistics floatStatistics;

    public AllStatistics(final ArgumentHandler argumentHandler) {
        this.argumentHandler = argumentHandler;
        this.stringStatistics = new StringStatistics();
        this.longStatistics = new LongStatistics();
        this.floatStatistics = new FloatStatistics();
    }

    public StringStatistics getStringStatistics() {
        return stringStatistics;
    }

    public LongStatistics getLongStatistics() {
        return longStatistics;
    }

    public FloatStatistics getFloatStatistics() {
        return floatStatistics;
    }

    /**
     * Статистика по строковым, вещественным и целочисленным типам данных;
     * @return получение статистики в виде строки;
     */
    public String getStatistics() {
        return "\n" + this.stringStatistics.getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics()) + "\n\n" +
               this.longStatistics
                       .getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics()) + "\n\n" +
               this.floatStatistics
                       .getStatistics(this.argumentHandler.getShortStatistics(), this.argumentHandler.getFullStatistics());
    }
}
