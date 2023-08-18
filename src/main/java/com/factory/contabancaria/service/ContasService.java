package com.factory.contabancaria.service;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContasService {
    @Autowired
    ContasRepository contasRepository;

    //métodos
    public List<ContasModel> listarContas(){
        return contasRepository.findAll();
    }

    public Optional<ContasModel> exibeContaPorId(Long id){
        return contasRepository.findById(id);
    }

    public ContasModel cadastrar(ContasModel contasModel, ContaFactory contaFactory){
        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
        contasModel.setValorAtualConta(resultado);
        contasModel.setValorFinal(resultado);
        return contasRepository.save(contasModel);
    }

    public ContasModel alterar(Long id, ContasModel contasModel) {

        ContasModel conta = exibeContaPorId(id).get();

        if (contasModel.getNumConta() != null) {
            conta.setNumConta(contasModel.getNumConta());
        }
        if (contasModel.getAgencia() != null) {
            conta.setAgencia(contasModel.getAgencia());
        }

        return contasRepository.save(conta);
    }

    public void deletarConta(Long id){
        contasRepository.deleteById(id);
    }

    public Optional<ContasModel> exibeContaPorNomeUsuario(String nomeUsuario) {
        return contasRepository.findByNomeDoUsuario(nomeUsuario);
    }

    public ContasModel atualizaNumConta(Long id, String novoNumero) {
        Optional<ContasModel> contasOptional = exibeContaPorId(id);

        if (contasOptional.isPresent()) {
            ContasModel contasModel = contasOptional.get();
            contasModel.setNumConta(novoNumero);
            return contasRepository.save(contasModel);
        } else {
            return null;
        }
    }

    public ContasModel salvar (ContasModel contasModel) {
        return contasRepository.save(contasModel);
    }

}
