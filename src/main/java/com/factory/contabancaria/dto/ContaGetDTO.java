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
public class ContaGetDTO {

    private String numero_conta;
    private String agencia;
    private String nomeCliente;
    private BigDecimal valor_atual_conta;

}
