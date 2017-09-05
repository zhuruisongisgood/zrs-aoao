package com.aoao.util;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * <br/>
 * Date: 2016/7/11<br/>
 *
 * @author ZRS
 * @since JDK7
 */
public class WriterUtil {

    public static void Print(HttpServletResponse response, String message){
        PrintWriter writer;
        try {
            writer = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        writer.print(message);
        writer.flush();
        writer.close();
    }
}
