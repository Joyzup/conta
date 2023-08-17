package com.factory.contabancaria.dtos;

import com.factory.contabancaria.model.ContasModel;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;

@Data
public class ContaGetDTO {
    private String agencia;
    private String numConta;

    private String nomeDoUsuario;

    private BigDecimal valorAtualConta;


}
