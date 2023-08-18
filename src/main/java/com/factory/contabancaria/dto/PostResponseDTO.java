package com.factory.contabancaria.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class PostResponseDTO implements Serializable {
    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;
    private BigDecimal valorFornecido;
    private String tipoServico;
    private BigDecimal valorFinal;

    public PostResponseDTO() {}
}
