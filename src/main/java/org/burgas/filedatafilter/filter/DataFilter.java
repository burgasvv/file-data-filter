package org.burgas.filedatafilter.filter;

/**
 * Интерфейс описывающий фильтрацию данных
 */
public sealed interface DataFilter permits FileDataFilter {

    /**
     * Метод фильтрации данных
     */
    void filter();
}
