package com.factory.contabancaria.model.factory;

import java.math.BigDecimal;

public class CalculoSaque implements CalculoConta{
    @Override
    public BigDecimal calcular(BigDecimal valorAtualConta, BigDecimal valorFornecido){
        if (valorFornecido.compareTo(valorAtualConta)>0){
            throw new IllegalArgumentException("Valor fornecido é maior do que o valor atual da conta");
        }
        BigDecimal resultado = valorAtualConta.subtract(valorFornecido);
        return resultado;
    }
}
