package com.example.challenge.williams.federalrevenue.usecase;

import com.example.challenge.williams.federalrevenue.entity.ContaReceita;
import com.example.challenge.williams.federalrevenue.util.FileUtil;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("unchecked")
@Service
public class WriteFileDataUsecase {

    public void execute(List<ContaReceita> data) {

        createDirIfNotExist();

        Arrays.stream(Objects.requireNonNull(new File(FileUtil.folderPath).listFiles()))
                .filter(file -> !file.isDirectory())
                .forEach(file -> {
                    try (
                            Writer writer = Files.newBufferedWriter(
                                    Paths.get(file.getParent() + "/" + LocalDate.now()
                                            , file.getName().replace(".csv", "") + "_out.csv")
                            )
                    ) {
                        StatefulBeanToCsv beanToCsv = new StatefulBeanToCsvBuilder(writer).withSeparator(';').build();
                        beanToCsv.write(data);
                    } catch (Exception e) {
                        throw new RuntimeException(e.getMessage());
                    }
                });

    }

    private void createDirIfNotExist() {
        File directory = new File(FileUtil.folderPath.concat("//").concat(LocalDate.now().toString()));
        if (!directory.exists()) {
            directory.mkdir();
        }
    }
}
