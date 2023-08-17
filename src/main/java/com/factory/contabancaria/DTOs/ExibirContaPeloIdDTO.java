package com.factory.contabancaria.DTOs;

import com.factory.contabancaria.model.ContasModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
//implemento o DTO pegando os respectivos atributos da classe modelo
public class ExibirContaPeloIdDTO implements Serializable {
    private static final Long serialId = 1L;
private String numConta, agencia, nomeDeUsuario;
private BigDecimal BalorAtualConta;
public ExibirContaPeloIdDTO(ContasModel contasModel){
    this.numConta = contasModel.getNumConta();
    this.agencia = contasModel.getAgencia();
    this.nomeDeUsuario = contasModel.getNomeDoUsuario();
}
}
