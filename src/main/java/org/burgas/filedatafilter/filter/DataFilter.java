package org.burgas.filedatafilter.filter;

import java.io.IOException;

public sealed interface DataFilter permits FileDataFilter {

    void filter() throws IOException;
}
