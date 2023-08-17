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
public class ContaPostDTO {

    private String nomeCliente;
    private BigDecimal valor_atual_conta;
    private BigDecimal valor_fornecido;
    private String tipo_servico;
}
