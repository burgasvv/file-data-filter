package org.burgas.filedatafilter.format;

/**
 * Перечисление форматов файлов;
 */
public enum FileFormatTypes {

    TXT(".txt"),
    PDF(".pdf"),
    DOCX(".docx");

    private final String fileType;

    FileFormatTypes(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
