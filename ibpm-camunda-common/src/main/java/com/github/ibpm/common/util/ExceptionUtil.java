package com.github.ibpm.common.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ExceptionUtil {

    /**
     * get full stack trace message
     * @param t throwable
     * @return stack's error msg
     */
    public static String getStackTrace(Throwable t) {
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw, true));
        return sw.getBuffer().toString();
    }

}
