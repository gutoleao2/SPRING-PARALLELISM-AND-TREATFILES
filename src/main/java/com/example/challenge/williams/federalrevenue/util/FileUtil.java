package com.example.challenge.williams.federalrevenue.util;

import java.util.Optional;

public final class FileUtil {

    public FileUtil() {

    }

    public static final String folderPath = "C:\\uploaded-files//";
    public static final String requiredFileType = "csv";

    public static String getExtensionByStringHandling(String filename) {
        return Optional.of(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .filter(f -> f.equalsIgnoreCase(requiredFileType))
                .orElse("undefined");
    }

}

