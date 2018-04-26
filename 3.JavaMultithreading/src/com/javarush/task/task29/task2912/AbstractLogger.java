package com.javarush.task.task29.task2912;

import java.util.logging.Logger;

public abstract class AbstractLogger implements com.javarush.task.task29.task2912.Logger {
    int level;
    com.javarush.task.task29.task2912.Logger next;

    @Override
    public void inform(String message, int level) {
        if (this.level <= level) {
            info(message);
        }
        if (next != null) {
            next.inform(message, level);
        }
    }

    @Override
    public void setNext(com.javarush.task.task29.task2912.Logger next) {
        this.next = next;
    }
}
