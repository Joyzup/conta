package com.factory.contabancaria.service;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.exceptions.AccountNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContasService {
    private final List<ContasModel> conta = new ArrayList<>();
    @Autowired
    ContasRepository contasRepository;

    //métodos
    public List<ContasModel> listarContas() {
        return contasRepository.findAll();
    }

    public Optional<ContasModel> exibeContaPorId(Long id) {
        return contasRepository.findById(id);
    }

    //criando exibir contas por nome usando Optional e stream com fiuter para facilitar na implementação e gerar uma resposta de erro caso o nome não for encontrado.
    public Optional<ContasModel> exibirContasPorNome(String nomeDeUsuario) {
        return conta.stream().filter(contasModel -> contasModel.getNomeDoUsuario().equalsIgnoreCase(nomeDeUsuario))
                .findFirst();
    }
//alterei no método post para que o valor atual seja incluido no valor fornecido e já mostre o valor na subtração ou adição. Para isso,  adicionei a variável atualizarConta que pega o o valor atual e soma com o resultado. Em seguida ele seta o valor atalizado no valor atual.
 public ContasModel cadastrar(ContasModel contasModel, ContaFactory contaFactory) {
        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
        BigDecimal valorAtualizado = contasModel.getValorAtualConta().add(resultado);
        contasModel.setValorAtualConta(valorAtualizado);
        contasModel.setValorFinal(BigDecimal.ZERO);

        return contasRepository.save(contasModel);
    }

    public ContasModel alterarContaPorId(Long id, ContasModel contasModel) {

        ContasModel conta = exibeContaPorId(id).get();

        if (contasModel.getNumConta() != null) {
            conta.setNumConta(contasModel.getNumConta());
        }
        if (contasModel.getAgencia() != null) {
            conta.setAgencia(contasModel.getAgencia());
        }

        return contasRepository.save(conta);
    }

    public ContasModel alterarContaPorNome(String nomeDeUsuario, ContasModel contasModel) {
        ContasModel conta = exibirContasPorNome(nomeDeUsuario).get();

        if (contasModel.getNumConta() != null) {
            conta.setNumConta(contasModel.getNumConta());
        }
        if (contasModel.getAgencia() != null) {
            conta.setAgencia(contasModel.getAgencia());
        }

   return  contasRepository.save(conta);
    }
//alterei um nome específico para uma conta específica. Para isso criei um método void que pega o Optional exibirContaPorId . Então criei uma exceção conta não encontrada que receberá a anotação Responce e extenderá do RuntimeException para validar caso a conta não forencontrada.
public void atualizarNomeUsuarioConta(Long id, String novoUsuario){
        Optional<ContasModel>  optionalContasModel = exibeContaPorId(id);
        if (optionalContasModel.isPresent()){
        ContasModel contasModel = optionalContasModel.get();
        contasModel.setNomeDoUsuario(novoUsuario);
        contasRepository.save(contasModel);

        } else {
            throw new AccountNotFoundException("Conta não encontrada!");
        }
}
    public void deletarContaPorId(Long id){
        contasRepository.deleteById(id);
    }




}


