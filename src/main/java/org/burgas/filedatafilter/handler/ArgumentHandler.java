package org.burgas.filedatafilter.handler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.out;
import static org.burgas.filedatafilter.message.ArgumentHandlerMessages.FILE_NOT_FOUND;

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
    private final List<String> inputFilePaths;

    /**
     * Ассоциативный массив содержащий пути output файлов;
     */
    private final Map<String, String> outputFilePathsMap;

    /**
     * Конструктор со встроенной логикой обработки массива аргументов и их записи в свойства класса;
     */
    public ArgumentHandler() {
        this.shortStatistics = "";
        this.fullStatistics = "";
        this.outputFilePath = "";
        this.prefixOutputFileName = "";
        this.fileWriteAppend = false;

        this.inputFilePaths = new ArrayList<>();
        this.outputFilePathsMap = new HashMap<>(
                Map.of(
                        "strings", "strings.txt",
                        "integers", "integers.txt",
                        "floats", "floats.txt"
                )
        );
    }

    public boolean isInputFiles(String[] args) throws IOException {
        boolean inputFiles = false;
        for (String arg : args) {

            if (arg.endsWith(".txt")) {

                try {
                    new BufferedReader(new FileReader(arg)).close();

                } catch (FileNotFoundException e) {
                    throw new FileNotFoundException(String.format(FILE_NOT_FOUND.getMessage(), arg));
                }

                inputFiles = true;
                out.println("Файл для чтения: " + arg + " получен");
            }
        }
        return inputFiles;
    }

    public void handleArgs(String[] args) {
        for (int i = 0; i < args.length; i++) {

            if (args[i].equals("-o") && !args[i + 1].startsWith("-"))
                this.outputFilePath = args[i + 1];

            if (args[i].equals("-p") && !args[i + 1].startsWith("-"))
                this.prefixOutputFileName = args[i + 1];

            if (args[i].equals("-a"))
                this.fileWriteAppend = true;

            if (args[i].equals("-s"))
                this.shortStatistics = args[i];

            if (args[i].equals("-f"))
                this.fullStatistics = args[i];
        }

        for (String param : args) {
            if (param.endsWith(".txt"))
                this.inputFilePaths.add(param);
        }

        this.outputFilePathsMap.replace("strings", outputFilePath + prefixOutputFileName + "strings.txt");
        this.outputFilePathsMap.replace("integers", outputFilePath + prefixOutputFileName + "integers.txt");
        this.outputFilePathsMap.replace("floats", outputFilePath + prefixOutputFileName + "floats.txt");
    }

    /**
     * Метод получения приватного поля режима добавления в данных в файл;
     * @return Объект условного типа данных;
     */
    public boolean isFileWriteAppend() {
        return this.fileWriteAppend;
    }

    /**
     * Метод получения приватного поля аргумента краткой статистики;
     * @return объект краткой статистики строкового типа;
     */
    public String getShortStatistics() {
        return this.shortStatistics;
    }

    /**
     * Метод получения приватного поля аргумента полной статистики;
     * @return объект полной статистики строкового типа;
     */
    public String getFullStatistics() {
        return this.fullStatistics;
    }

    /**
     * Метод получения приватного поля списка местоположений файлов для считывания;
     * @return список строк - местоположений файлов на носителе для считывания из файла;
     */
    public List<String> getInputFilePaths() {
        return this.inputFilePaths;
    }

    /**
     * Метод получения приватного поля: ассоциативного массива местоположений файлов для записи;
     * @return ассоциативный массив местоположений файлов на носителе для записи в файл;
     */
    public Map<String, String> getOutputFilePathsMap() {
        return this.outputFilePathsMap;
    }

    @Override
    public String toString() {
        return "ParameterHandler{" +
               "pathResult='" + this.outputFilePath + '\'' +
               ", prefixNameResult='" + this.prefixOutputFileName + '\'' +
               ", shortStatistics='" + this.shortStatistics + '\'' +
               ", fullStatistics='" + this.fullStatistics + '\'' +
               ", fileWriteAppend=" + this.fileWriteAppend +
               ", inputFilePaths=" + this.inputFilePaths +
               ", resultPathMap=" + this.outputFilePathsMap +
               '}';
    }
}
