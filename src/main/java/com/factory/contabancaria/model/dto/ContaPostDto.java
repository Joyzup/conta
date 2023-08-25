package com.factory.contabancaria.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class ContaPostDto {
   @JsonProperty("nome_do_usuario")
    private String nomeDoUsuario;
    @JsonProperty("valor_atual_conta")
    private BigDecimal valorAtualConta;
    private BigDecimal ValorFornecido;
    @JsonProperty("tipo_servico")
    private String tipoServico;


}
