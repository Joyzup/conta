package com.factory.contabancaria.service;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.dtos.ContaPutDadosDTO;
import com.factory.contabancaria.model.dtos.ContaPutServicoDTO;
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

    public Optional<ContasModel> exibeContaPorNome(String nome){
        return contasRepository.findByNomeDoUsuario(nome);
    }

    public ContasModel cadastrar(ContasModel contasModel, ContaFactory contaFactory){
        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
        contasModel.setValorFinal(resultado);
        contasModel.setValorAtualConta(contasModel.getValorFinal());
        return contasRepository.save(contasModel);
    }

    public ContasModel alterarDados(Long id, ContaPutDadosDTO contaPutDadosDTO, ContaFactory contaFactory) {

        ContasModel conta = exibeContaPorId(id).get();

        if (contaPutDadosDTO.getNumConta() != null) {
            conta.setNumConta(contaPutDadosDTO.getNumConta());
        }
        if (contaPutDadosDTO.getAgencia() != null) {
            conta.setAgencia(contaPutDadosDTO.getAgencia());
        }
        if (contaPutDadosDTO.getNomeDoUsuario() != null){
           conta.setNomeDoUsuario(contaPutDadosDTO.getNomeDoUsuario());
        }

        return contasRepository.save(conta);
    }

    public ContasModel alterarTipoServico(Long id, ContaPutServicoDTO contaPutServicoDTO, ContaFactory contaFactory) {

        ContasModel conta = exibeContaPorId(id).get();

        BigDecimal resultado = contaFactory.tipoServicoConta(contaPutServicoDTO.getTipoServico())
                .calcular(conta.getValorAtualConta(), contaPutServicoDTO.getValorFornecido());
        conta.setValorFornecido(contaPutServicoDTO.getValorFornecido());
        conta.setValorFinal(resultado);
        conta.setTipoServico(contaPutServicoDTO.getTipoServico());
        conta.setValorAtualConta(conta.getValorAtualConta());

        return contasRepository.save(conta);
    }

    public void deletarConta(Long id){
        contasRepository.deleteById(id);
    }

}
