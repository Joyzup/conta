package com.factory.contabancaria.model.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ContaGetDto {

    private String numConta;
    private String agencia;
    @JsonProperty("nome_do_usuario")
    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;


}
