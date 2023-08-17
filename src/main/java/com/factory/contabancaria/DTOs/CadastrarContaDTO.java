package com.factory.contabancaria.DTOs;

import com.factory.contabancaria.model.ContasModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
//crio o DTO para a requisição post com os respectivos atributos
public class CadastrarContaDTO implements Serializable {
    private static  final  Long serialId = 1L;
    private String nomeDeUsuario;
private BigDecimal valorAtualConta, valorFornecido;

private String tipoServico;
    public CadastrarContaDTO(ContasModel contasModel){
this.nomeDeUsuario = contasModel.getNomeDoUsuario();
this.valorAtualConta = contasModel.getValorAtualConta();
this.valorFornecido = contasModel.getValorFornecido();
   this.tipoServico = contasModel.getTipoServico();
    }
}
