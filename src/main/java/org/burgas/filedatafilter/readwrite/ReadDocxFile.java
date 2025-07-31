package org.burgas.filedatafilter.readwrite;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Класс реализации для чтения docx файлов;
 */
public final class ReadDocxFile implements ReadFile<XWPFDocument> {

    /**
     * Метов для создания потока чтения docx файлов;
     * @param fileName путь к файлу;
     * @return поток чтения docx файлов;
     */
    @Override
    public XWPFDocument createReader(final String fileName) {
        try {
            return new XWPFDocument(new FileInputStream(fileName));

        } catch (IOException e) {
            return null;
        }
    }
}
