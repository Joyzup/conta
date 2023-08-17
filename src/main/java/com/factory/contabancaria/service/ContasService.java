package com.factory.contabancaria.service;

import com.factory.contabancaria.enums.TipoDeServico;
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

    public ContasModel findByNomeDoUsuario(String nomeUsuario) {
        return contasRepository.findByNomeDoUsuario(nomeUsuario);
    }

    public ContasModel cadastrar(ContasModel contasModel, ContaFactory contaFactory){
        BigDecimal resultado = contaFactory.tipoServicoConta(TipoDeServico.valueOf(contasModel.getTipoServico()))
                .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
        contasModel.setValorAtualConta(resultado);
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
        if (contasModel.getNomeDoUsuario() != null) {
            conta.setNomeDoUsuario(contasModel.getNomeDoUsuario());
        }
        if (contasModel.getValorAtualConta()!=null){
            if (contasModel.getTipoServico().equalsIgnoreCase("DEPOSITO")) conta.setValorAtualConta(contasModel.getValorAtualConta().add(contasModel.getValorFornecido()));
            else conta.setValorAtualConta(contasModel.getValorAtualConta().subtract(contasModel.getValorFornecido()));
        }
        if (contasModel.getTipoServico().equalsIgnoreCase("DEPOSITO")) {
            conta.setTipoServico(String.valueOf(TipoDeServico.DEPOSITO));
        } else conta.setTipoServico(String.valueOf(TipoDeServico.SAQUE));

        return contasRepository.save(conta);
    }

    public void deletarConta(Long id){
        contasRepository.deleteById(id);
    }

}
