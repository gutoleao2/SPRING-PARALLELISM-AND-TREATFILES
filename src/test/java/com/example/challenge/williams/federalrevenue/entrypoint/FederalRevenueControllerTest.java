package com.example.challenge.williams.federalrevenue.entrypoint;

import com.example.challenge.williams.federalrevenue.entrypoint.response.DefaultResponse;
import com.example.challenge.williams.federalrevenue.usecase.FederalRevenueUseCase;
import com.example.challenge.williams.federalrevenue.usecase.FileListUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FederalRevenueController.class)
@ActiveProfiles("test")
class FederalRevenueControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FederalRevenueUseCase service;

    @MockBean
    private FileListUseCase fileListUseCase;

    @InjectMocks
    private FederalRevenueController controller;
    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private String name = "file";
    private String originalFileName = "contas.csv";

    private String contentType = "text/csv";


    @Test
    @DisplayName("Faz upload de arquivo e envia dados para receita")
    public void test() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.
                webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(MockMvcRequestBuilders
                        .multipart("/federal-revenue/upload/update-accounts")
                        .file(new MockMultipartFile(name, originalFileName, contentType, getMultipartFileContent("contas.csv"))))
                .andExpect(status().is(200));
    }

    @Test
    @DisplayName("updateAccountsSuccess")
    public void updateAccountsSuccess() {

        when(service.execute(any()))
                .thenReturn("Sucesso");

        ResponseEntity<DefaultResponse> response = controller
                .updateAccounts(new MockMultipartFile(name
                        , originalFileName
                        , contentType
                        , getMultipartFileContent("contas.csv")));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("wupdateAccountsSuccess")
    public void updateAccountsSuccessw() {

        when(service.execute(new MockMultipartFile(name
                , originalFileName
                , contentType
                , getMultipartFileContent("contas.csv")))
        )
                .thenReturn("Sucesso");

        ResponseEntity<DefaultResponse> response = controller
                .updateAccounts(new MockMultipartFile(name
                        , originalFileName
                        , contentType
                        , getMultipartFileContent("contas.csv")));

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("updateAccountsInvalidFile")
    public void updateAccountsInvalidFile() {

        when(service.execute(any()))
                .thenThrow(new RuntimeException("Arquivo não permitido"));

        ResponseEntity<DefaultResponse> response = controller
                .updateAccounts(new MockMultipartFile(name
                        , originalFileName
                        , contentType
                        , getMultipartFileContent("contas.csv")));
        Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Assertions.assertEquals("Arquivo não permitido", response.getBody().getMessage());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("givenWorkDirectoryContent")
    public void givenWorkDirectoryContent() {

        when(fileListUseCase.execute())
                .thenReturn(new String[]{});

        ResponseEntity<String[]> response = controller.getListFiles();
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
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