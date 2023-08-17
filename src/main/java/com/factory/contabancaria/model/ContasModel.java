package com.factory.contabancaria.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "TB_CONTAS")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContasModel {

    //Atributos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 6, nullable = false)
    @JsonProperty("num_conta")
    private String numConta;
    @Column(length = 4, nullable = false)
    @JsonProperty("agencia")
    private String agencia;
    @Column(nullable = false)
    @JsonProperty("nome_do_usuario")
    private String nomeDoUsuario;
    @Column(nullable = false)
    @JsonProperty("valor_atual_conta")
    private BigDecimal valorAtualConta;
    @Column(nullable = false)
    @JsonProperty("valor_fornecido")
    private BigDecimal ValorFornecido;
    @Column(length = 20, nullable = false)
    @JsonProperty("tipo_servico")
    private String tipoServico;
    @JsonProperty("valor_final")
    private BigDecimal valorFinal;

}
