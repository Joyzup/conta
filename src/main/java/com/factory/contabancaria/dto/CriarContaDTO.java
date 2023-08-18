package com.factory.contabancaria.dto;

import lombok.*;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CriarContaDTO {
    //Atributos

    private String nomeDoUsuario;

    private BigDecimal valorAtualConta;

    private BigDecimal ValorFornecido;

    private String tipoServico;

}
