package com.factory.contabancaria.dto;

import com.factory.contabancaria.model.ContasModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ContaDTOPost {
  private String nomeDoUsuario;
  private BigDecimal valorAtualConta;
  private BigDecimal ValorFornecido;
  private String tipoServico;

}
