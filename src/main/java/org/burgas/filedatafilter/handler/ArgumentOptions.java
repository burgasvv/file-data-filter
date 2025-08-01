package org.burgas.filedatafilter.handler;

/**
 * Перечисление опций аргументов;
 */
public enum ArgumentOptions {

    WRITE_APPEND("-a"),
    FULL_STATISTICS("-f"),
    SHORT_STATISTICS("-s"),
    OUTPUT_FILE_PATH("-o"),
    OUTPUT_FILE_NAME_PREFIX("-p");

    private final String option;

    ArgumentOptions(String option) {
        this.option = option;
    }

    public String getOption() {
        return option;
    }
}
