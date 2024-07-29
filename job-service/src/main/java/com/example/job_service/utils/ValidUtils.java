package com.example.job_service.utils;

public class ValidUtils {

    public static boolean isValidJobStatus(String status) {
        return status.equals("interview") || status.equals("declined") || status.equals("pending");
    }

    public static boolean isValidJobType(String type) {
        return type.equals("full-time") || type.equals("part-time") || type.equals("internship");
    }
}
