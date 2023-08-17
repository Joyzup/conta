package com.factory.contabancaria.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaPutDadosDTO {
    private String numConta;
    private String agencia;
    private String nomeDoUsuario;

}
