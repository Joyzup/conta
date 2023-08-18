package com.factory.contabancaria.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class GetResponseDTO implements Serializable {
    private String numConta;
    private String agencia;
    private String nomeUsuario;
    private BigDecimal valorAtualConta;

    public GetResponseDTO() {}
}
