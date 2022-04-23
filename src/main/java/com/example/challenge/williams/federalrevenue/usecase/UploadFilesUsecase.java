package com.example.challenge.williams.federalrevenue.usecase;

import com.example.challenge.williams.federalrevenue.util.FileUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class UploadFilesUsecase {
    public void execute(MultipartFile file) {
        try {
            createDirIfNotExist();
            removeOldsFiles();
            byte[] bytes;
            bytes = file.getBytes();
            Files.write(Paths.get(FileUtil.folderPath + file.getOriginalFilename()), bytes);
        } catch (Exception e) {
            throw new RuntimeException("Exception to upload files!");
        }
    }

    private void createDirIfNotExist() {
        File directory = new File(FileUtil.folderPath);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

    private void removeOldsFiles() {
        File[] files = new File(FileUtil.folderPath).listFiles();
        for (File file : files) {
            if (!file.isDirectory())
                file.delete();
        }
    }
}
