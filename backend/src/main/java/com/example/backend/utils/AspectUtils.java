package com.example.backend.utils;

public class AspectUtils {

    public static String getArguments(Object[] args) {
        StringBuilder arguments = new StringBuilder();
        for (Object arg : args) {
            arguments.append(arg).append(", ");
        }
        if (arguments.length() > 2) {
            arguments.setLength(arguments.length() - 2);
        }
        return arguments.toString();
    }
}
