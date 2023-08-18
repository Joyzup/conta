package com.factory.contabancaria.dto;

import com.factory.contabancaria.model.ContasModel;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
public class PostRequestDTO implements Serializable {
    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;
    private BigDecimal valorFornecido;
    private String tipoServico;

    public PostRequestDTO() {}
}
