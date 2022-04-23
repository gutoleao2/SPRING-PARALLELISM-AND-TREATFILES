package com.example.challenge.williams.federalrevenue.usecase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@WebMvcTest(controllers = FederalRevenueUseCase.class)
@ActiveProfiles("test")
class FederalRevenueUseCaseTest {


    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private FederalRevenueUseCase useCase;

    @MockBean
    UploadFilesUsecase uploadFilesUsecase;
    @MockBean
    UpdateAccountsUsecase updateAccountsUsecase;

    private String name = "file";
    private String originalFileName = "contas.csv";

    private String contentType = "text/csv";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    @DisplayName("Sucesso no fluxo - Envia dados para receita")
    public void updateAccountsSuccess() {
        String response = useCase.execute(new MockMultipartFile(name, originalFileName, contentType, getMultipartFileContent("")));
        Assertions.assertEquals("Success", response);
    }

    byte[] getMultipartFileContent(String name) {
        Path path = Paths.get("/csvfiles/" + name);
        byte[] content = null;
        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e) {
        }

        return content;
    }

}