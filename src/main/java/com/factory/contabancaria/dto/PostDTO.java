package com.factory.contabancaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private String nomeDoUsuarioDTO;
    private BigDecimal valorAtualContaDTO;
    private BigDecimal valorFornecidoDTO;
    private String tipoServicoDTO;
    private BigDecimal valorFinalDTO;



}
