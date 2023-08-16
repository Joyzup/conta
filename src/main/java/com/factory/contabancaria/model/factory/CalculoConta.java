package com.factory.contabancaria.model.factory;

import java.math.BigDecimal;

public interface CalculoConta {
    BigDecimal calcular(BigDecimal valorAtualConta, BigDecimal valorFornecido);
}
