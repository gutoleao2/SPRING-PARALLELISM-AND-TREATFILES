package com.example.challenge.williams.federalrevenue.usecase;

import com.example.challenge.williams.federalrevenue.entity.ContaReceita;
import com.example.challenge.williams.federalrevenue.gateway.ReceitaGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class UpdateAccountsUsecase {

    @Autowired
    ReceitaGateway receitaGateway;

    @Autowired
    GetFileDataUsecase getFileDataUsecase;

    @Autowired
    WriteFileDataUsecase writeFileDataUsecase;
    public static final ForkJoinPool forkJoinPool = new ForkJoinPool(15);

    final Logger logger = Logger.getLogger(UpdateAccountsUsecase.class.getName());

    public void execute() {

        try {
            List<ContaReceita> revenueAccounts = getFileDataUsecase.execute();

            List<CompletableFuture<List<ContaReceita>>> completableFutureList = new ArrayList<>();
            completableFutureList.add(
                    CompletableFuture.supplyAsync(() -> revenueAccounts
                                    .parallelStream()
                                    .map(this::updateAccount)
                                    .collect(Collectors.toList())
                            , forkJoinPool));

            List<ContaReceita> revenueResponseList = completableFutureList.stream()
                    .flatMap(runnable -> runnable.join().stream())
                    .collect(Collectors.toList());

            writeFileDataUsecase.execute(revenueResponseList);
        } catch (RuntimeException ex) {
            logger.warning(ex.getMessage());
            throw new RuntimeException("Update Account Exception");

        }

    }

    private ContaReceita updateAccount(ContaReceita account) {
        logger.info(String.format("Atualizar conta - agencia {%s}, conta:{%s}", account.getAgencia(), account.getConta()));
        LocalDateTime start = LocalDateTime.now();

        try {
            String nrConta = account.getConta().replace("-", "");
            double vlSaldo = Double.parseDouble(account.getSaldo().replace(',', '.'));


            account.setContaAtualizada(
                    receitaGateway.atualizarConta(account.getAgencia(), nrConta, vlSaldo, account.getStatus())
                            ? "SIM" : "NAO"
            );

        } catch (RuntimeException | InterruptedException e) {
            logger.warning(String.format("[ERROR] Falha ao tentar atualizar conta - agencia {%s}, conta:{%s}, error:{%s}"
                    , account.getAgencia(), account.getConta(), e.getMessage()));

            account.setContaAtualizada("NAO");
        }

        Duration duration = Duration.between(LocalDateTime.now(), start);
        logger.info(String.format("[End with duration {%s s}] Atualizar conta - agencia {%s}, conta:{%s}"
                , Math.abs(duration.getSeconds()), account.getAgencia(), account.getConta()));

        return account;
    }


}
