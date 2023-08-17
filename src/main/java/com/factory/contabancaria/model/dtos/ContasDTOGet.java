package com.factory.contabancaria.model.dtos;

import com.factory.contabancaria.model.ContasModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ContasDTOGet implements Serializable {
    private static final long serialVersionUID = 1L;

    private String numConta;
    private String agencia;
    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;

    public ContasDTOGet (ContasModel contasModel){
        this.numConta = contasModel.getNumConta();
        this.agencia = contasModel.getAgencia();
        this.nomeDoUsuario = contasModel.getNomeDoUsuario();
        this.valorAtualConta = contasModel.getValorAtualConta();
    }

    public ContasDTOGet() {
    }
}
