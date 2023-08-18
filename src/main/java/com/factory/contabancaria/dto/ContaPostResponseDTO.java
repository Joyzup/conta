package com.factory.contabancaria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ContaPostResponseDTO {
    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;
    private BigDecimal valorFornecido;
    private String tipoServico;
}
