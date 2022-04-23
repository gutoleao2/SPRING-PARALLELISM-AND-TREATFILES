package com.example.challenge.williams.federalrevenue.usecase;

import com.example.challenge.williams.federalrevenue.util.FileUtil;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class FileListUseCase {


    public String[] execute() {
        return new File(FileUtil.folderPath).list();
    }


}
