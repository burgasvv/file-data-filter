package org.burgas.filedatafilter.filter;

import java.io.IOException;

/**
 * Интерфейс описывающий фильтрацию данных
 */
public sealed interface DataFilter permits FileDataFilter {

    /**
     * Метод фильтрации данных
     */
    void filter();
}
