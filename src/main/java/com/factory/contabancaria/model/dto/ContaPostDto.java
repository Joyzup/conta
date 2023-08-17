package com.factory.contabancaria.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ContaPostDto {

    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;
    private BigDecimal ValorFornecido;
    private String tipoServico;


}
