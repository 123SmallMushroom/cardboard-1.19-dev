package com.fungus_soft.bukkitfabric.bukkitimpl;

import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class FakeLogger extends Logger {

    private org.apache.logging.log4j.Logger log4j = LogManager.getLogger("Bukkit");
    private static FakeLogger inst;

    public static FakeLogger getLogger() {
        if (inst == null)
            new FakeLogger();
        return inst;
    }

    public FakeLogger() {
        this("Bukkit", null);
    }

    public FakeLogger(String name, String str) {
        super(name, str);
        inst = this;
    }

    @Override
    public void log(LogRecord lr) {
        if (lr.getThrown() == null)
            log4j.log(convertLevel(lr.getLevel()), lr.getMessage());
        else
            log4j.log(convertLevel(lr.getLevel()), lr.getMessage(), lr.getThrown());
    }

    private Level convertLevel(java.util.logging.Level l) {
        if (l == java.util.logging.Level.ALL)
            return Level.ALL;
        if (l == java.util.logging.Level.CONFIG)
            return Level.TRACE;
        if (l == java.util.logging.Level.WARNING)
            return Level.WARN;
        if (l == java.util.logging.Level.INFO)
            return Level.INFO;
        if (l == java.util.logging.Level.OFF)
            return Level.OFF;
        if (l == java.util.logging.Level.SEVERE)
            return Level.FATAL;
        if (l == java.util.logging.Level.FINE || l == java.util.logging.Level.FINER || l == java.util.logging.Level.FINEST)
            return Level.WARN;
        return Level.ALL;
    }

}