package org.burgas.filedatafilter;

import org.burgas.filedatafilter.exception.ArgumentsNotFoundException;
import org.burgas.filedatafilter.filter.FileDataFilter;
import org.burgas.filedatafilter.handler.ArgumentHandler;
import org.burgas.filedatafilter.readwrite.ReadWriteFileApi;
import org.burgas.filedatafilter.statistics.AllStatistics;

import java.io.IOException;
import java.util.Arrays;

import static java.lang.System.currentTimeMillis;
import static java.lang.System.out;
import static org.burgas.filedatafilter.message.MainMessages.*;

/**
 * Основной класс Main для запуска программы через точку входа main
 */
public final class Main {

    public static void main(String[] args) throws IOException {

        long start = currentTimeMillis();
        out.println();

        ArgumentHandler argumentHandler = new ArgumentHandler();
        boolean inputFiles = argumentHandler.isInputFiles(args);

        if (!inputFiles)
            throw new ArgumentsNotFoundException(ARGUMENTS_WITH_FILES_NOT_FOUND.getMessage());

        out.println(ARGUMENTS_RECEIVED_SUCCESSFULLY.getMessage());
        out.printf(ARGUMENTS.getMessage(), Arrays.toString(args) + "\n\n");

        argumentHandler.handleArgs(args);

        ReadWriteFileApi readWriteFileApi = new ReadWriteFileApi();

        FileDataFilter fileFilter = new FileDataFilter(argumentHandler, readWriteFileApi);
        fileFilter.filter();

        readWriteFileApi.removeReaders(argumentHandler.getInputFilePaths());

        AllStatistics allStatistics = new AllStatistics(argumentHandler, readWriteFileApi);
        out.println(allStatistics.getStatistics());

        long end = currentTimeMillis();
        out.println("\nОтработка в миллисекундах: " + (end - start));
    }
}
