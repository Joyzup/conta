package com.factory.contabancaria.model;

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
    @Column(length = 15)
    private String numero_conta;
    @Column(length = 6)
    private String agencia;
    @Column(nullable = false)
    private String nomeCliente;
    @Column(nullable = false)
    private BigDecimal valor_atual_conta;
    @Column(nullable = false)
    private BigDecimal valor_fornecido;
    @Column(length = 20, nullable = false)
    private String tipo_servico;

//Não vi utilidade para essa coluna valor_final
//    private BigDecimal valor_final;

}
