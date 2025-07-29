package org.burgas.filedatafilter;

import org.burgas.filedatafilter.exception.ArgumentsNotFoundException;
import org.burgas.filedatafilter.filter.FileDataFilter;
import org.burgas.filedatafilter.handler.ArgumentHandlerImpl;
import org.burgas.filedatafilter.readwrite.ReadWriteFileApi;
import org.burgas.filedatafilter.statistics.AllStatistics;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static org.burgas.filedatafilter.message.MainMessages.ARGUMENTS_WITH_FILES_NOT_FOUND;

/**
 * Основной класс Main для запуска программы через точку входа main
 */
public final class Main {

    public static void main(String[] args) {

        long start = currentTimeMillis();
        out.println();

        // Создание объекта обработчика аргументов;
        ArgumentHandlerImpl argumentHandlerImpl = new ArgumentHandlerImpl();

        // Обработка аргументов;
        argumentHandlerImpl.handleArgs(args);

        // Проверка на наличие исходных файлов для чтения;
        if (!argumentHandlerImpl.getCheckInputFiles())
            throw new ArgumentsNotFoundException(ARGUMENTS_WITH_FILES_NOT_FOUND.getMessage());

        // Создание объекта реализации интерфейса для чтения и записи;
        ReadWriteFileApi readWriteFileApi = new ReadWriteFileApi();

        // Создание объекта для фильтрации данных с последующей фильтрацией в методе filter;
        FileDataFilter fileFilter = new FileDataFilter(argumentHandlerImpl, readWriteFileApi);
        fileFilter.filter();

        // Создание объекта для расчета статистических данных с дальнейшим получением статистики;
        AllStatistics allStatistics = new AllStatistics(argumentHandlerImpl, readWriteFileApi);
        out.println("\n" + allStatistics.getStatistics());

        long end = currentTimeMillis();
        out.println("\nОтработка в миллисекундах: " + (end - start));
    }
}
