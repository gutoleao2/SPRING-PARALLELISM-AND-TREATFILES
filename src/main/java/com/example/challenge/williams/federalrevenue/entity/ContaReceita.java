package com.example.challenge.williams.federalrevenue.entity;

public class ContaReceita {

    public String Agencia;
    public String Conta;
    public String Saldo;
    public String Status;
    public String ContaAtualizada;

    public ContaReceita() {
    }

    public ContaReceita(String agencia, String conta, String saldo, String status, String contaAtualizada) {
        Agencia = agencia;
        Conta = conta;
        Saldo = saldo;
        Status = status;
        ContaAtualizada = contaAtualizada;
    }

    public String getAgencia() {
        return Agencia;
    }

    public void setAgencia(String agencia) {
        Agencia = agencia;
    }

    public String getConta() {
        return Conta;
    }

    public void setConta(String conta) {
        Conta = conta;
    }

    public String getSaldo() {
        return Saldo;
    }

    public void setSaldo(String saldo) {
        Saldo = saldo;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public String getContaAtualizada() {
        return ContaAtualizada;
    }

    public void setContaAtualizada(String status) {
        ContaAtualizada = status;
    }

    @Override
    public String toString() {
        return "ContaReceita{" +
                "Agencia=" + Agencia +
                ", Conta=" + Conta +
                ", Saldo=" + Saldo +
                ", Status='" + Status + '\'' +
                ", ContaAtualizada='" + ContaAtualizada + '\'' +
                '}';
    }
}
