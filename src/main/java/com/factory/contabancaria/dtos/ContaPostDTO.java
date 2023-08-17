package com.factory.contabancaria.dtos;

import com.factory.contabancaria.model.ContasModel;
import lombok.Data;
import java.math.BigDecimal;

@Data
public class ContaPostDTO {

    private String nomeDoUsuario;

    private BigDecimal valorAtualConta;

    private BigDecimal ValorFornecido;

    private String tipoServico;
}
