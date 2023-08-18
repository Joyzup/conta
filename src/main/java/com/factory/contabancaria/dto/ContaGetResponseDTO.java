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
public class ContaGetResponseDTO {
    private String numConta;
    private String agencia;
    private String nomeUsuario;
    private BigDecimal valorAtualConta;
}
