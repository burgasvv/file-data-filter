package org.burgas.filedatafilter.format;

public enum FileFormatTypes {

    TXT(".txt"),
    JAVA(".java"),
    HTML(".html");

    private final String fileType;

    FileFormatTypes(String fileType) {
        this.fileType = fileType;
    }

    public String getFileType() {
        return fileType;
    }
}
