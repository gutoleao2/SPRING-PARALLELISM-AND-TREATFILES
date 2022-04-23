package com.example.challenge.williams.federalrevenue.gateway;

// @Feign ...
public interface ReceitaGateway {

    boolean atualizarConta(String agencia, String conta, double saldo, String status)
            throws RuntimeException, InterruptedException;

}
