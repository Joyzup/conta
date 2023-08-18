package com.factory.contabancaria.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListarContaDTO {

    //Atributos

    private String numConta;
    private String agencia;
    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;

}
