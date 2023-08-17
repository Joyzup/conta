package com.factory.contabancaria.service;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.dtos.ContasDTOGet;
import com.factory.contabancaria.model.dtos.ContasDTOPost;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import net.bytebuddy.dynamic.DynamicType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ContasService {
    @Autowired
    ContasRepository contasRepository;

    //métodos
    public List<ContasDTOGet> listarContas(){
        List<ContasModel> contas = contasRepository.findAll();
        List<ContasDTOGet> contasDTO = new ArrayList<>();

        for(ContasModel conta: contas){
            contasDTO.add(new ContasDTOGet(conta));
        }

        return contasDTO;
    }

    public Optional<ContasDTOGet>exibeContaPorNome(String nomeDoUsuario){
        String nomeDoUsuarioCaixaBaixa = nomeDoUsuario.toLowerCase();

        Optional<ContasModel> conta = contasRepository.findByNome(nomeDoUsuario);

        if(conta.isPresent()){
            return Optional.of(new ContasDTOGet(conta.get()));
        }

        return Optional.empty();
    }

    public Optional<ContasDTOGet> exibeContaPorId(Long id){
        Optional<ContasModel> conta = contasRepository.findById(id);

        if(conta.isPresent()){
            return Optional.of(new ContasDTOGet(conta.get()));
        }
        return Optional.empty();
    }
    public ContasModel cadastrar(ContasModel contasModel, ContaFactory contaFactory){

        int agencia = ThreadLocalRandom.current().nextInt(1111,9999);
        int numConta = ThreadLocalRandom.current().nextInt(111111,999999);
        String agenciaStr = String.valueOf(agencia);
        String numContaStr = String.valueOf(numConta);
        contasModel.setNumConta(numContaStr);
        contasModel.setAgencia(agenciaStr);

        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
        contasModel.setValorFinal(resultado);
        contasModel.setValorAtualConta(resultado);

        return contasRepository.save(contasModel);
    }


    // método para alterar agência
    public ContasModel alterarAgencia(Long id, String agencia){

        ContasModel contaAlterada = contasRepository.findById(id).orElse(null);

        if(contaAlterada != null){
            contaAlterada.setAgencia(agencia);
            return contasRepository.save(contaAlterada);
        }
        return null;
    }

    public ContasModel alterar(Long id, ContasModel contasModel) {

        ContasModel conta = contasRepository.findById(id).orElse(null);

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

}
