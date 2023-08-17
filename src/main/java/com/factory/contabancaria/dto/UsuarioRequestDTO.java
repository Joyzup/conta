package com.factory.contabancaria.dto;

import javax.persistence.Column;
import java.math.BigDecimal;

public class UsuarioRequestDTO {

    @Column(nullable = false)
    private String nomeDoUsuario;

    @Column(nullable = false)
    private BigDecimal valorAtualConta;

    @Column(nullable = false)
    private BigDecimal  valorFornecido;

    @Column(length = 20, nullable = false)
    private String tipoServico;

    public UsuarioRequestDTO(String nomeDoUsuario, BigDecimal valorAtualConta, BigDecimal valorFornecido, String tipoServico) {
        this.nomeDoUsuario = nomeDoUsuario;
        this.valorAtualConta = valorAtualConta;
        this.valorFornecido = valorFornecido;
        this.tipoServico = tipoServico;
    }

    public String getNomeDoUsuario() {
        return nomeDoUsuario;
    }

    public void setNomeDoUsuario(String nomeDoUsuario) {
        this.nomeDoUsuario = nomeDoUsuario;
    }

    public BigDecimal getValorAtualConta() {
        return valorAtualConta;
    }

    public void setValorAtualConta(BigDecimal valorAtualConta) {
        this.valorAtualConta = valorAtualConta;
    }

    public BigDecimal getValorFornecido() {
        return valorFornecido;
    }

    public void setValorFornecido(BigDecimal valorFornecido) {
        this.valorFornecido = valorFornecido;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }
}
