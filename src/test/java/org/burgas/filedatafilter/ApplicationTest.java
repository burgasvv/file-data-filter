package org.burgas.filedatafilter;

import org.burgas.filedatafilter.filter.FileDataFilter;
import org.burgas.filedatafilter.handler.ArgumentHandlerImpl;
import org.burgas.filedatafilter.readwrite.ReadDocxFile;
import org.burgas.filedatafilter.readwrite.ReadPdfFile;
import org.burgas.filedatafilter.readwrite.ReadWriteTxtFile;
import org.burgas.filedatafilter.statistics.StatisticsService;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static java.lang.System.out;

public final class ApplicationTest {

    @Test
    public void testing() {

        // Создание объекта обработчика аргументов;
        ArgumentHandlerImpl argumentHandler = new ArgumentHandlerImpl();
        argumentHandler.setFileWriteAppend(false);
        argumentHandler.setFullStatistics("-f");

        argumentHandler.setOutputFilePath("src/test/resources/output/");
        argumentHandler.setPrefixOutputFileName("test_result_");
        argumentHandler.setInputFilePaths(
                List.of(
                        "src/test/resources/input/test_in1.txt",
                        "src/test/resources/input/test_in2.txt"
                )
        );
        argumentHandler.setOutputFilePathsMap(
                Map.of(
                        "strings", argumentHandler.getOutputFilePath() + argumentHandler.getPrefixOutputFileName() + "strings",
                        "integers", argumentHandler.getOutputFilePath() + argumentHandler.getPrefixOutputFileName() + "integers",
                        "floats", argumentHandler.getOutputFilePath() + argumentHandler.getPrefixOutputFileName() + "floats"
                )
        );

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
        out.println("\n" + statisticsService.getStatistics());
    }
}
