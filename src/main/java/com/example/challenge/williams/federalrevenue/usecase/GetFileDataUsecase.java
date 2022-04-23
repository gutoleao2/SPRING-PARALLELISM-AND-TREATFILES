package com.example.challenge.williams.federalrevenue.usecase;

import com.example.challenge.williams.federalrevenue.entity.ContaReceita;
import com.example.challenge.williams.federalrevenue.util.FileUtil;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

@SuppressWarnings("unchecked")
@Service
public class GetFileDataUsecase {

    final Logger logger = Logger.getLogger(GetFileDataUsecase.class.getName());

    public List<ContaReceita> execute() {

        List<ContaReceita> revenueAccounts = new ArrayList<>();

        Arrays.stream(Objects.requireNonNull(new File(FileUtil.folderPath).listFiles())).
                filter(file -> !file.isDirectory()).forEach(file -> {
                    try (
                            Reader reader = Files.newBufferedReader(Paths.get(file.getPath()))
                    ) {
                        ColumnPositionMappingStrategy<ContaReceita> strategy = new ColumnPositionMappingStrategy<ContaReceita>();
                        strategy.setType(ContaReceita.class);
                        String[] memberFieldsToBindTo = {"Agencia", "Conta", "Saldo", "Status", "ContaAtualizada"};
                        strategy.setColumnMapping(memberFieldsToBindTo);

                        CsvToBean csvToBean = new CsvToBeanBuilder(reader)
                                .withMappingStrategy(strategy)
                                .withSeparator(';')
                                .withSkipLines(1)
                                .withType(ContaReceita.class)
                                .withThrowExceptions(false)
                                .withOrderedResults(true)
                                .build();

                        for (ContaReceita contaReceita : (Iterable<ContaReceita>) csvToBean) {
                            revenueAccounts.add(contaReceita);
                        }

                    } catch (Exception e) {

                        logger.warning(String.format("[ERROR] Falha ao tentar atualizar contas do arquivo {%s}, Erro {%s}"
                                , file, e.getMessage()));
                        throw new RuntimeException(e.getMessage());
                    }
                });

        return revenueAccounts;
    }
}
