package com.example.challenge.williams.federalrevenue.usecase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static com.example.challenge.williams.federalrevenue.util.FileUtil.getExtensionByStringHandling;
import static com.example.challenge.williams.federalrevenue.util.FileUtil.requiredFileType;

@Service
public class FederalRevenueUseCase {

    @Autowired
    UploadFilesUsecase uploadFilesUsecase;
    @Autowired
    UpdateAccountsUsecase updateAccountsUsecase;

    public String execute(MultipartFile file) {

        try {
            if (!requiredFileType.equalsIgnoreCase(getExtensionByStringHandling(file.getOriginalFilename())))
                throw new RuntimeException("Arquivo não permitido");

            uploadFilesUsecase.execute(file);
            updateAccountsUsecase.execute();
            return "Success!";
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }


}
