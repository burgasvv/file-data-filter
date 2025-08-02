package org.burgas.filedatafilter.readwrite.other;

import com.itextpdf.text.pdf.PdfReader;

import java.io.IOException;

/**
 * Класс реализации для чтения pdf файлов;
 */
public final class ReadPdfFile implements ReadFile<PdfReader> {

    /**
     * Метов для создания потока чтения pdf файлов;
     * @param filename путь к файлу;
     * @return поток чтения pdf файлов;
     */
    @Override
    public PdfReader createReader(final String filename) {
        try {
            return new PdfReader(filename);

        } catch (IOException e) {
            return null;
        }
    }
}
