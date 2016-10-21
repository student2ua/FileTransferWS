package com.ecwo.fileTransfer.ws;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.OutputStream;

/**
 * User: tor
 * Date: 21.10.16
 * Time: 20:08
 * https://web.archive.org/web/20130527080241/http://www.java2s.com/Open-Source/Java/Testing/jacareto/jacareto/toolkit/log4j/LogOutputStream.java.htm
 * OR http://sysgears.com/articles/how-to-redirect-stdout-and-stderr-writing-to-a-log4j-appender/
 */
public class Log4jOutputStream extends OutputStream {
    private Logger logger;
    private Level level;
    private String mem;

    public Log4jOutputStream() {
    }

    public Log4jOutputStream(Logger logger, Level level) {
        this.logger = logger;
        this.level = level;
        mem = "";
    }

    public Logger getLogger() {
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Override
    public void write(int b) throws IOException {
        byte[] bytes = new byte[1];
        bytes[0] = (byte) (b & 0xff);
        mem = mem + new String(bytes);

        if (mem.endsWith("\n")) {
            mem = mem.substring(0, mem.length() - 1);
            flush();
        }
    }

    @Override
    public void flush() {
        logger.log(level, mem);
        mem = "";
    }
}
