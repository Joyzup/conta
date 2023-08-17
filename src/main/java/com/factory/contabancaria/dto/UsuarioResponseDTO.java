package com.factory.contabancaria.dto;

import java.math.BigDecimal;

public class UsuarioResponseDTO {

    private String numConta;
    private String agencia;
    private String nomeUsuario;
    private BigDecimal valorAtualConta;

    public UsuarioResponseDTO(String numConta, String agencia, String nomeUsuario, BigDecimal valorAtualConta) {
        this.numConta = numConta;
        this.agencia = agencia;
        this.nomeUsuario = nomeUsuario;
        this.valorAtualConta = valorAtualConta;
    }

    public String getNumConta() {
        return numConta;
    }

    public void setNumConta(String numConta) {
        this.numConta = numConta;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public BigDecimal getValorAtualConta() {
        return valorAtualConta;
    }

    public void setValorAtualConta(BigDecimal valorAtualConta) {
        this.valorAtualConta = valorAtualConta;
    }
}
