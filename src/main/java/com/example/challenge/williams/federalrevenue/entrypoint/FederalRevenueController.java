package com.example.challenge.williams.federalrevenue.entrypoint;


import com.example.challenge.williams.federalrevenue.entrypoint.response.DefaultResponse;
import com.example.challenge.williams.federalrevenue.usecase.FederalRevenueUseCase;
import com.example.challenge.williams.federalrevenue.usecase.FileListUseCase;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/federal-revenue")
@Api(value = "/federal-revenue", produces = "REST API for ATUALIZAR CONTAS NA RECEITA FEDERAL")
public class FederalRevenueController {

    @Autowired
    private FederalRevenueUseCase federalRevenueUseCase;
    @Autowired
    private FileListUseCase fileListUseCase;

    private final String DEFEAUL_DETAIL_MESSAGE_SUCCESS = "O arquivo atualizado está no seu diretório de trabalho localizado em C:\\uploaded-files\\<data atual> .";
    private final String DEFEAUL_DETAIL_MESSAGE_ERROR = "Verifique o arquivo enviado e tente novamente";

    /**
     * Method to upload multiple files
     *
     * @param file .csv file to work
     * @return FileResponse
     */
    @ApiOperation(value = "Update accounts from file", notes = "Returns a product as per the id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 500, message = "Internal Error")
    })
    @ResponseBody
    @PostMapping("/upload/update-accounts")
    public ResponseEntity<DefaultResponse> updateAccounts(@RequestParam("file") MultipartFile file) {

        try {
            String response = federalRevenueUseCase.execute(file);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new DefaultResponse()
                            .setMessage(response)
                            .setDetailsMessage(DEFEAUL_DETAIL_MESSAGE_SUCCESS));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DefaultResponse()
                    .setMessage(e.getMessage())
                    .setDetailsMessage(DEFEAUL_DETAIL_MESSAGE_ERROR));
        }
    }

    @ResponseBody
    @ApiOperation(value = "Exibir dados do diretorio de trabalho")
    @GetMapping("/files")
    public ResponseEntity<String[]> getListFiles() {
        return ResponseEntity.status(HttpStatus.OK).body(fileListUseCase.execute());
    }

}
