package com.factory.contabancaria.model.dtos;

import com.factory.contabancaria.model.ContasModel;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
public class ContasDTOPost implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomeDoUsuario;
    private BigDecimal valorAtualConta;
    private BigDecimal valorFornecido;
    private String tipoServico;
    //private BigDecimal valorFinal;

    public ContasDTOPost (ContasModel contasModel){
        this.nomeDoUsuario = contasModel.getNomeDoUsuario();
        this.valorAtualConta = contasModel.getValorAtualConta();
        this.valorFornecido = contasModel.getValorFornecido();
        this.tipoServico = contasModel.getTipoServico();
        //this.valorFinal = contasModel.getValorFinal();
    }

    public ContasDTOPost() {
    }

}
