package com.factory.contabancaria.model.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ContaPutDadosDTO {
    private String numConta;
    private String agencia;
    private String nomeDoUsuario;
}
