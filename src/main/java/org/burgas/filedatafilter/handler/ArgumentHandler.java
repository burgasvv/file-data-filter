package org.burgas.filedatafilter.handler;

import org.burgas.filedatafilter.exception.*;
import org.burgas.filedatafilter.format.FileFormatTypes;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static org.burgas.filedatafilter.message.ArgumentHandlerMessages.*;

/**
 * Класс обработки аргументов программы добавленных через командную строку для чтения и записи;
 */
public final class ArgumentHandler {

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

            for (FileFormatTypes format : FileFormatTypes.values()) {
                String fileType = format.getFileType();

                // Проверка на наличие исходных файлов разных текстовых форматов
                if (arg.endsWith(fileType)) {

                    try {
                        new BufferedReader(new FileReader(arg)).close();
                        this.inputFilePaths.add(arg);

                    } catch (FileNotFoundException e) {
                        throw new WrongInputFilePathException(
                                String.format(FILE_NOT_FOUND.getMessage(), arg)
                        );

                    } catch (IOException e) {
                        throw new FileCreationFailureException(FILE_CREATION_FAILURE.getMessage());
                    }

                    out.println("Директория исходного файла: " + arg + " получена");
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

                if (args[i].equals("-o") && args[i + 1].endsWith(fileType))
                    throw new WrongOutputFilePathException(WRONG_OUTPUT_FILE_PATH.getMessage());

                if (
                        (args[i].equals("-p") && (args[i + 1].contains("/") || args[i + 1].contains("\\"))) ||
                        (args[i].equals("-p") && args[i + 1].endsWith(fileType))
                )
                    throw new WrongOutputFilePrefixException(WRONG_OUTPUT_FILE_PREFIX.getMessage());
            }

            if (args[i].equals("-o") && !args[i + 1].startsWith("-")) {
                this.outputFilePath = args[i + 1];
            }

            if (args[i].equals("-p") && !args[i + 1].startsWith("-")) {
                this.prefixOutputFileName = args[i + 1];
            }

            if (args[i].equals("-a")) {
                this.fileWriteAppend = true;
            }

            if (args[i].equals("-s")) {
                this.shortStatistics = args[i];
            }

            if (args[i].equals("-f")) {
                this.fullStatistics = args[i];
            }
        }

        if (this.fullStatistics.equalsIgnoreCase("-f") && this.shortStatistics.equalsIgnoreCase("-s")) {
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
        this.prefixOutputFileName = prefixOutputFileName;
    }

    /**
     * Метод изменения приватного поля - аргумента краткой статистики;
     * @param shortStatistics строковый парамер;
     */
    public void setShortStatistics(String shortStatistics) {
        this.shortStatistics = shortStatistics;
    }

    /**
     * Метод изменения приватного поля - аргумента полной статистики;
     * @param fullStatistics строковый парамер;
     */
    public void setFullStatistics(String fullStatistics) {
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
        inputFilePaths.forEach(
                path -> {
                    try {
                        new BufferedReader(new FileReader(path)).close();

                    } catch (IOException e) {
                        throw new WrongInputFilePathException(
                                String.format(FILE_NOT_FOUND.getMessage(), path)
                        );
                    }
                }
        );
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
