package com.factory.contabancaria.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaGetDTO {
    private String numConta;
    private String agencia;
    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;
}
