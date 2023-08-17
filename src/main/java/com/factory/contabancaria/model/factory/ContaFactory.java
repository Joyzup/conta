package com.factory.contabancaria.model.factory;

import com.factory.contabancaria.enums.TipoDeServico;

public class ContaFactory {

    public CalculoConta tipoServicoConta(TipoDeServico tipoServico){

        if (tipoServico == TipoDeServico.DEPOSITO) return new CalculoDeposito();
        else if (tipoServico == TipoDeServico.SAQUE) return new CalculoSaque();
        else return null;

    }

}
