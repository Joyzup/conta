package com.factory.contabancaria.service;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.dto.ContaPutDadosDTO;
import com.factory.contabancaria.model.dto.ContaPutServiceDTO;
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
        contasModel.setValorFinal(resultado);
        return contasRepository.save(contasModel);
    }

    public ContasModel alterar(Long id, ContaPutDadosDTO contaPutDadosDTO, ContaFactory contaFactory) {

        ContasModel conta = exibeContaPorId(id).get();

        if (contaPutDadosDTO.getNumConta() != null) {
            conta.setNumConta(contaPutDadosDTO.getNumConta());
        }
        if (contaPutDadosDTO.getAgencia() != null) {
            conta.setAgencia(contaPutDadosDTO.getAgencia());
        }
        if (contaPutDadosDTO.getNomeDoUsuario() != null) {
            conta.setNomeDoUsuario(contaPutDadosDTO.getNomeDoUsuario());
        }

        return contasRepository.save(conta);
    }

    public ContasModel alterarServico(Long id, ContaPutServiceDTO contaPutServiceDTO, ContaFactory contaFactory) {
        ContasModel conta = exibeContaPorId(id).get();

        BigDecimal resultado = contaFactory.tipoServicoConta(contaPutServiceDTO.getTipoServico()).calcular(conta.getValorAtualConta(), contaPutServiceDTO.getValorFornecido());
        conta.setValorFornecido(contaPutServiceDTO.getValorFornecido());
        conta.setValorFinal(contaPutServiceDTO.getValorFornecido());
        conta.setTipoServico(contaPutServiceDTO.getTipoServico());
        conta.setValorAtualConta(conta.getValorAtualConta());

        return contasRepository.save(conta);
    }

    public void deletarConta(Long id){
        contasRepository.deleteById(id);
    }

}
