package com.factory.contabancaria.service;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.SortedMap;

@Service
public class ContasService {
    @Autowired
    ContasRepository contasRepository;

    @Autowired
    ContaFactory contaFactory;

    //métodos
    public List<ContasModel> listarContas() {
        return contasRepository.findAll();
    }

    public Optional<ContasModel> exibeContaPorId(Long id) {
        return contasRepository.findById(id);
    }

    public ContasModel cadastrar(ContasModel contasModel, ContaFactory contaFactory) {

            //Correção do bug que não permitia criar conta nova sem passar tipoServico
            //e que te obrigava a passar valorFornecido para ele calcular um valorFinal
            //que não fazia sentido
            contasModel.setValorFinal(contasModel.getValorAtualConta());
            contasModel.setValorFornecido(BigDecimal.valueOf(0));
            contasModel.setTipoServico("criar");

        return contasRepository.save(contasModel);
    }

    public ContasModel alterar(ContasModel contasModel) {
        //Pegado o id do usuário
        Long id = findIdByNomeDoUsuario(contasModel.getNomeDoUsuario());

        ContasModel conta = exibeContaPorId(id).get();

        if (contasModel.getNumConta() != null) {
            conta.setNumConta(contasModel.getNumConta());
        }
        if (contasModel.getAgencia() != null) {
            conta.setAgencia(contasModel.getAgencia());
        }
        if (contasModel.getTipoServico() != null) {
            conta.setTipoServico(contasModel.getTipoServico());
        }
        if (contasModel.getValorFornecido() != null) {
            conta.setValorFornecido(contasModel.getValorFornecido());


            //Pegando o valor atual que o cliente tem na conta
            BigDecimal valorAtual = findValorAtualContaById(id);
            contasModel.setValorAtualConta(valorAtual);

            BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                    .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());

            conta.setValorFinal(resultado);

            conta.setValorAtualConta(resultado);
        }

        return contasRepository.save(conta);
    }

    //Método para pegar o valor atual da conta
    public BigDecimal findValorAtualContaById(Long id) {
        return contasRepository.findValorAtualContaById(id);
    }

    //Método para pegar o id a partir do nome do usuário
    public Long findIdByNomeDoUsuario(String nomedousuario) {
        return contasRepository.findIdByNomeDoUsuario(nomedousuario);
    }
    public void deletarConta(Long id) {
        contasRepository.deleteById(id);
    }

}
