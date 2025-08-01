package org.burgas.filedatafilter.handler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.burgas.filedatafilter.exception.*;
import org.burgas.filedatafilter.format.FileFormatTypes;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static org.burgas.filedatafilter.handler.ArgumentOptions.*;
import static org.burgas.filedatafilter.message.ArgumentHandlerMessages.*;

/**
 * Класс обработки аргументов программы добавленных через командную строку для чтения и записи;
 */
public final class ArgumentHandler {

    private final Logger logger = LogManager.getLogger(ArgumentHandler.class.getSimpleName());

    /**
     * Аргумент пути output файлов;
     */
    private String outputFilePath;

    /**
     * Аргумент префикса наименований выходных файлов;
     */
    private String prefixOutputFileName;

    /**
     * Аргумент короткой статистики;
     */
    private String shortStatistics;

    /**
     * Аргумент полной статистики;
     */
    private String fullStatistics;

    /**
     * Аргумент перезаписи output файлов;
     */
    private boolean fileWriteAppend;

    /**
     * Динамический массив содержащий пути input файлов;
     */
    private List<String> inputFilePaths = new ArrayList<>();

    /**
     * Ассоциативный массив содержащий пути output файлов;
     */
    private Map<String, String> outputFilePathsMap = new HashMap<>();

    private final List<String> options = List.of("-a", "-f", "-s", "-o", "-p");

    public ArgumentHandler() {
        this.outputFilePath = "";
        this.prefixOutputFileName = "";
        this.shortStatistics = "";
        this.fullStatistics = "";
        this.fileWriteAppend = false;
    }

    /**
     * Метод обработки массива аргументов и распределения по свойствам-опциям
     * @param args массив входящих аргументов;
     */
    public void handleArgs(String[] args) {

        for (String arg : args) {

            if (arg.startsWith("-") && !this.options.contains(arg))
                throw new WrongArgumentOptionException(
                        String.format(WRONG_ARGUMENT_OPTION.getMessage(), arg)
                );

            for (FileFormatTypes format : FileFormatTypes.values()) {
                String fileType = format.getFileType();

                // Проверка на наличие исходных файлов разных текстовых форматов
                if (arg.endsWith(fileType)) {

                    if (Files.exists(Paths.get(arg))) {
                        this.inputFilePaths.add(arg);

                    } else {
                        throw new WrongInputFilePathException(
                                String.format(FILE_NOT_FOUND.getMessage(), arg)
                        );
                    }

                    String message = String.format(INPUT_FILE_DIRECTORY_RECEIVED.getMessage(), arg);
                    out.println(message);
                    this.logger.info(message);
                }
            }
        }

        // Проверка на наличие исходных файлов для чтения;
        if (this.getInputFilePaths().isEmpty())
            throw new ArgumentsNotFoundException(ARGUMENTS_WITH_FILES_NOT_FOUND.getMessage());

        // Обработка аргументов, и формирование состояния, и полей класса;
        for (int i = 0; i < args.length; i++) {

            for (FileFormatTypes fileFormatTypes : FileFormatTypes.values()) {
                String fileType = fileFormatTypes.getFileType();

                if (args[i].equals(OUTPUT_FILE_PATH.getOption()) && args[i + 1].endsWith(fileType))
                    throw new WrongOutputFilePathException(WRONG_OUTPUT_FILE_PATH.getMessage());

                if (
                        (args[i].equals(OUTPUT_FILE_NAME_PREFIX.getOption()) && (args[i + 1].contains("/") || args[i + 1].contains("\\"))) ||
                        (args[i].equals(OUTPUT_FILE_NAME_PREFIX.getOption()) && args[i + 1].endsWith(fileType))
                )
                    throw new WrongOutputFilePrefixException(WRONG_OUTPUT_FILE_PREFIX.getMessage());
            }

            if (args[i].equals(OUTPUT_FILE_PATH.getOption()) && !args[i + 1].startsWith("-")) {
                this.outputFilePath = args[i + 1];
            }

            if (args[i].equals(OUTPUT_FILE_NAME_PREFIX.getOption()) && !args[i + 1].startsWith("-")) {
                this.prefixOutputFileName = args[i + 1];
            }

            if (args[i].equals(OUTPUT_FILE_NAME_PREFIX.getOption()) && args[i + 1].startsWith("-")) {
                throw new WrongOutputFilePrefixException(WRONG_OUTPUT_FILE_PREFIX.getMessage());
            }

            if (args[i].equals(WRITE_APPEND.getOption())) {
                this.fileWriteAppend = true;
            }

            if (args[i].equals(SHORT_STATISTICS.getOption())) {
                this.shortStatistics = args[i];
            }

            if (args[i].equals(FULL_STATISTICS.getOption())) {
                this.fullStatistics = args[i];
            }
        }

        if (this.fullStatistics.equals(FULL_STATISTICS.getOption()) && this.shortStatistics.equals(SHORT_STATISTICS.getOption())) {
            throw new StatisticsArgumentsHandlingException(STATISTICS_ARGUMENT_HANDLING_FAILED.getMessage());
        }

        if (!outputFilePath.endsWith("/") || !outputFilePath.endsWith("\\")) {
            outputFilePath = outputFilePath + "/";
        }

        // Получение директорий результирующих файлов и добавление в ассоциативный массив;
        this.outputFilePathsMap.put("strings", outputFilePath + prefixOutputFileName + "strings.txt");
        this.outputFilePathsMap.put("integers", outputFilePath + prefixOutputFileName + "integers.txt");
        this.outputFilePathsMap.put("floats", outputFilePath + prefixOutputFileName + "floats.txt");
    }

    /**
     * Метод получения приватного поля - директории результирующих файлов;
     * @return объект строкового типа данных;
     */
    public String getOutputFilePath() {
        return outputFilePath;
    }

    /**
     * Метод получения приватного поля - префикса наименования результирующих файлов;
     * @return объект строкового типа данных;
     */
    public String getPrefixOutputFileName() {
        return prefixOutputFileName;
    }

    /**
     * Метод получения приватного поля - режима записи данных в файл;
     * @return объект условного типа данных;
     */
    public boolean isFileWriteAppend() {
        return this.fileWriteAppend;
    }

    /**
     * Метод получения приватного поля - аргумента краткой статистики;
     * @return объект краткой статистики строкового типа;
     */
    public String getShortStatistics() {
        return this.shortStatistics;
    }

    /**
     * Метод получения приватного поля - аргумента полной статистики;
     * @return объект полной статистики строкового типа;
     */
    public String getFullStatistics() {
        return this.fullStatistics;
    }

    /**
     * Метод получения приватного поля - списка местоположений файлов для считывания;
     * @return список строк - местоположений файлов на носителе для считывания из файла;
     */
    public List<String> getInputFilePaths() {
        return this.inputFilePaths;
    }

    /**
     * Метод получения приватного поля - ассоциативного массива местоположений файлов для записи;
     * @return ассоциативный массив местоположений файлов на носителе для записи в файл;
     */
    public Map<String, String> getOutputFilePathsMap() {
        return this.outputFilePathsMap;
    }

    /**
     * Метод изменения приватного поля - директории результирующих файлов;
     * @param outputFilePath строковый параметр;
     */
    public void setOutputFilePath(String outputFilePath) {
        this.outputFilePath = outputFilePath;
    }

    /**
     * Метод изменения приватного поля - префикса наименования результирующих файлов;
     * @param prefixOutputFileName строковый параметр;
     */
    public void setPrefixOutputFileName(String prefixOutputFileName) {
        if (prefixOutputFileName.contains("/") || prefixOutputFileName.contains("\\"))
            throw new WrongOutputFilePrefixException(WRONG_OUTPUT_FILE_PREFIX.getMessage());
        this.prefixOutputFileName = prefixOutputFileName;
    }

    /**
     * Метод изменения приватного поля - аргумента краткой статистики;
     * @param shortStatistics строковый парамер;
     */
    public void setShortStatistics(String shortStatistics) {
        if (shortStatistics != null && !shortStatistics.equals(SHORT_STATISTICS.getOption()))
            throw new WrongStatisticsArgumentException(WRONG_STATISTICS_ARGUMENT.getMessage());
        this.shortStatistics = shortStatistics;
    }

    /**
     * Метод изменения приватного поля - аргумента полной статистики;
     * @param fullStatistics строковый парамер;
     */
    public void setFullStatistics(String fullStatistics) {
        if (fullStatistics != null && !fullStatistics.equals(FULL_STATISTICS.getOption()))
            throw new WrongStatisticsArgumentException(WRONG_STATISTICS_ARGUMENT.getMessage());
        this.fullStatistics = fullStatistics;
    }

    /**
     * Метод изменения приватного поля - режима записи в данных в файл;
     * @param fileWriteAppend условный параметр;
     */
    public void setFileWriteAppend(boolean fileWriteAppend) {
        this.fileWriteAppend = fileWriteAppend;
    }

    /**
     * Метод изменения приватного поля - списка местоположений файлов для считывания;
     * @param inputFilePaths список директорий исходных файлов;
     */
    public void setInputFilePaths(List<String> inputFilePaths) {
        for (String path : inputFilePaths) {

            if (!Files.exists(Path.of(path)))
                throw new WrongInputFilePathException(String.format(FILE_NOT_FOUND.getMessage(), path));
        }
        this.inputFilePaths = inputFilePaths;
    }

    /**
     * Метод изменения приватного поля - ассоциативного массива местоположений файлов для записи;
     * @param outputFilePathsMap ассоциативный массив директорий результирующих файлов;
     */
    public void setOutputFilePathsMap(Map<String, String> outputFilePathsMap) {
        this.outputFilePathsMap = outputFilePathsMap;
    }
}
