package com.factory.contabancaria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaPutServiceDTO {
    private String tipoServico;
    private BigDecimal ValorFornecido;
}
