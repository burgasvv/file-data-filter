package org.burgas.filedatafilter.message;

public enum AllStatisticsMessages {

    ADD_READER_FAILED("Добавление потока чтения не удалось");

    private final String message;

    AllStatisticsMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
