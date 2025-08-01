package org.burgas.filedatafilter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.burgas.filedatafilter.filter.FileDataFilter;
import org.burgas.filedatafilter.handler.ArgumentHandler;
import org.burgas.filedatafilter.readwrite.ReadDocxFile;
import org.burgas.filedatafilter.readwrite.ReadPdfFile;
import org.burgas.filedatafilter.readwrite.ReadWriteTxtFile;
import org.burgas.filedatafilter.statistics.StatisticsService;

import static java.lang.System.out;

/**
 * Основной класс Main для запуска программы через точку входа main
 */
public final class Main {

    private static final Logger logger = LogManager.getLogger(Main.class.getSimpleName());

    public static void main(String[] args) {

        out.println();
        // Создание объекта обработчика аргументов;
        ArgumentHandler argumentHandler = new ArgumentHandler();

        // Обработка аргументов;
        argumentHandler.handleArgs(args);

        // Создание объекта реализации интерфейса для чтения и записи txt файлов;
        ReadWriteTxtFile readWriteTxtFile = new ReadWriteTxtFile();

        // Создание объекта реализации интерфейса для чтения и записи pdf файлов;
        ReadPdfFile readPdfFile = new ReadPdfFile();

        // Создание объекта реализации интерфейса для чтения и записи docx файлов;
        ReadDocxFile readDocxFile = new ReadDocxFile();

        // Создание объекта для фильтрации данных с последующей фильтрацией в методе filter;
        FileDataFilter fileFilter = new FileDataFilter(argumentHandler, readWriteTxtFile, readPdfFile, readDocxFile);
        fileFilter.filter();

        // Создание объекта для расчета статистических данных с дальнейшим получением статистики;
        StatisticsService statisticsService = new StatisticsService(argumentHandler, readWriteTxtFile);
        String statistics = "\n" + statisticsService.getStatistics() + "\n";
        out.println(statistics);
        logger.info("\n{}", statistics);
    }
}
