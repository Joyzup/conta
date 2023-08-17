package com.factory.contabancaria.model.factory;

import org.springframework.stereotype.Component;

@Component

public class ContaFactory {

    public CalculoConta tipoServicoConta(String tipo_servico) {
        {
            if (tipo_servico.equalsIgnoreCase("deposito")) {
                return new CalculoDeposito();

            } else if (tipo_servico.equalsIgnoreCase("saque")) {
                return new CalculoSaque();

            } else {
                throw new RuntimeException("Tipo de serviço de conta inválido! " + tipo_servico);
            }
        }

    }
}

