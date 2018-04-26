package com.javarush.task.task38.task3811;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(value= RetentionPolicy.RUNTIME) //1) Должна быть доступна во время выполнения программы.
@Target(ElementType.TYPE)
public @interface Ticket {

    public enum Priority {LOW, MEDIUM, HIGH}

    Priority priority() default Priority.MEDIUM;

    String[] tags() default {};

    String createdBy() default "Amigo";
}
