package com.factory.contabancaria.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaGetDto {
    private String numConta;
    private String agencia;
    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;

}
