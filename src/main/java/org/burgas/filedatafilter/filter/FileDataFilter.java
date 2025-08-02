package org.burgas.filedatafilter.filter;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.burgas.filedatafilter.exception.ReadFileFailedException;
import org.burgas.filedatafilter.format.FileFormatTypes;
import org.burgas.filedatafilter.handler.ArgumentHandler;
import org.burgas.filedatafilter.readwrite.other.ReadDocxFile;
import org.burgas.filedatafilter.readwrite.other.ReadPdfFile;
import org.burgas.filedatafilter.readwrite.buffered.BufferedReaderWriter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;

import static java.lang.String.valueOf;
import static java.lang.System.out;
import static org.burgas.filedatafilter.message.FileDataFilterMessages.*;

/**
 * Класс, позволяющий считывать данные из файлов,
 * распределять данные по их типу, и записывать в файл;
 */
public final class FileDataFilter implements DataFilter {

    private final Logger logger = LogManager.getLogger(FileDataFilter.class.getSimpleName());

    /**
     * Ссылка на объект Обработчик аргументов;
     */
    private final ArgumentHandler argumentHandler;

    /**
     * Ссылка на объект для чтения и записи txt файлов;
     */
    private final BufferedReaderWriter bufferedReaderWriter;

    /**
     * Ссылка на объект для чтения pdf файлов;
     */
    private final ReadPdfFile readPdfFile;

    /**
     * Ссылка на объект для чтения docx файлов;
     */
    private final ReadDocxFile readDocxFile;

    /**
     * Конструктор для создания объектов и добавления файлов для считывания данных и дальнейшей обработки;
     */
    public FileDataFilter(
            final ArgumentHandler argumentHandler, final BufferedReaderWriter bufferedReaderWriter,
            final ReadPdfFile readPdfFile, final ReadDocxFile readDocxFile
    ) {
        this.argumentHandler = argumentHandler;
        this.bufferedReaderWriter = bufferedReaderWriter;
        this.readPdfFile = readPdfFile;
        this.readDocxFile = readDocxFile;
    }

    /**
     * Метод записи в файл с учетом проверки на существование данного файла по его наименованию;
     * @param filename наименование файла;
     * @param content информация для записи;
     */
    private void writeToFile(String filename, String content) {

        // Проверка на наличие потока записи и запись в файл;
        if (this.bufferedReaderWriter.getWriters().containsKey(filename))
            this.bufferedReaderWriter.write(filename, content);

        else {
            BufferedWriter fileWriter = this.bufferedReaderWriter.createWriter(filename, this.argumentHandler.isFileWriteAppend());
            this.bufferedReaderWriter.addWriter(filename, fileWriter);
            this.bufferedReaderWriter.write(filename, content);
        }
    }

    /**
     * Метод для типизации строк и записи в соответствующие файлы;
     * @param line строка для типизации и записи в файл;
     * @param outputFilePathsMap директории с результирующими файлами для записи;
     */
    private void handleDataTypes(String line, Map<String, String> outputFilePathsMap) {
        try {
            // Преобразование в целочисленный тип данных и запись в соответствующий файл;
            long aLong = Long.parseLong(line);
            this.writeToFile(outputFilePathsMap.get("integers"), valueOf(aLong));

        } catch (NumberFormatException e) {

            try {
                // Преобразование в вещественный тип данных и запись в соответствующий файл;
                double aDouble = Double.parseDouble(line);
                this.writeToFile(outputFilePathsMap.get("floats"), valueOf(aDouble));

            } catch (NumberFormatException e2) {

                // Запись строки в соответствующий файл;
                this.writeToFile(outputFilePathsMap.get("strings"), line);
            }
        }
    }

    /**
     * Метод для чтения данных из файлов разных форматов,
     * типизации данных и их записи в соответствующие типу данных файлы;
     */
    @Override
    public void filter() {
        // Получение директорий для результирующих файлов;
        Map<String, String> outputFilePathsMap = this.argumentHandler.getOutputFilePathsMap();

        for (String inputFilePath : this.argumentHandler.getInputFilePaths()) {

            if (inputFilePath.endsWith(FileFormatTypes.TXT.getFileType())) {
                readTxtFiles(inputFilePath, outputFilePathsMap);

            } else if (inputFilePath.endsWith(FileFormatTypes.PDF.getFileType())) {
                readPdfFiles(inputFilePath, outputFilePathsMap);

            } else if (inputFilePath.endsWith(FileFormatTypes.DOCX.getFileType())) {
                readDocxFiles(inputFilePath, outputFilePathsMap);
            }
        }
        String message = DISTRIBUTED_DATA_WRITTEN.getMessage();
        out.println("\n" + message);
        this.logger.info(message);
    }

    /**
     * Метод для чтения данных из исходных docx файлов;
     * @param inputFilePath директория исходного файла для чтения;
     * @param outputFilePathsMap директории результирующих файлов для записи;
     */
    private void readDocxFiles(String inputFilePath, Map<String, String> outputFilePathsMap) {
        try (XWPFDocument docxReader = this.readDocxFile.createReader(inputFilePath)) {

            if (docxReader != null) {
                // С помощью цикла проходим по параграфам docx документа и разделяем текст на строки;
                for (XWPFParagraph paragraph : docxReader.getParagraphs()) {
                    String text = paragraph.getText();
                    String[] splitLines = text.split("\n");

                    // Отправка данных на типизацию и запись;
                    for (String line : splitLines)
                        this.handleDataTypes(line, outputFilePathsMap);
                }
            }

        } catch (IOException e) {
            throw new ReadFileFailedException(READ_DOCX_FILE_FAILED.getMessage());
        }
    }

    /**
     * Метод для чтения данных из исходных pdf файлов;
     * @param inputFilePath директория исходного файла для чтения;
     * @param outputFilePathsMap директории результирующих файлов для записи;
     */
    private void readPdfFiles(String inputFilePath, Map<String, String> outputFilePathsMap) {
        try {
            PdfReader pdfReader = this.readPdfFile.createReader(inputFilePath);

            if (pdfReader != null) {
                // С помощью цикла проходим по страницам pdf документа и разделяем текст на строки;
                for (int i = 1; i <= pdfReader.getNumberOfPages(); i++) {
                    String pageText = PdfTextExtractor.getTextFromPage(pdfReader, i);
                    String[] splitLines = pageText.split("\n");

                    // Отправка данных на типизацию и запись;
                    for (int j = 0; j < splitLines.length - 1; j++)
                        this.handleDataTypes(splitLines[j], outputFilePathsMap);
                }

                pdfReader.close();
            }

        } catch (IOException e) {
            throw new ReadFileFailedException(READ_PDF_FILE_FAILED.getMessage());
        }
    }

    /**
     * Метод для чтения данных из исходных txt файлов;
     * @param inputFilePath директория исходного файла для чтения;
     * @param outputFilePathsMap директории результирующих файлов для записи;
     */
    private void readTxtFiles(String inputFilePath, Map<String, String> outputFilePathsMap) {
        try (BufferedReader fileReader = this.bufferedReaderWriter.createReader(inputFilePath)){

            if (fileReader != null) {
                // Чтение и отправка данных на типизацию и запись;
                fileReader.lines().forEach(
                        line -> this.handleDataTypes(line, outputFilePathsMap)
                );
            }

        } catch (IOException e) {
            throw new ReadFileFailedException(READ_TXT_FILE_FAILED.getMessage());
        }
    }
}
