package com.factory.contabancaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetDTO {
    private String numContaDTO;
    private String agenciaDTO;
    private String nomeUsuarioDTO;
    private BigDecimal valorAtualContaDTO;

}
