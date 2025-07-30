package org.burgas.filedatafilter.handler;

/**
 * Интерфейс описывающий обработку аргументов приложения;
 */
public interface ArgumentHandler {

    /**
     * Метод обработки аргументов;
     * @param args массив входящих аргументов;
     */
    void handleArgs(String[] args);
}
