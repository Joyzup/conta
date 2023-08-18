package com.factory.contabancaria.service;

import com.factory.contabancaria.dto.CriarContaDTO;
import com.factory.contabancaria.dto.ListarContaDTO;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ContasService {
    @Autowired
    ContasRepository contasRepository;

    private static final String CHARACTERS = "123456789";


    public List<ListarContaDTO> listarContas() {
        List<ContasModel> contasModels = contasRepository.findAll();
        List<ListarContaDTO> listaDto = new ArrayList<>();
        for (ContasModel conta : contasModels) {
            ListarContaDTO contaDTO = new ListarContaDTO();
            contaDTO.setNumConta(conta.getNumConta());
            contaDTO.setAgencia(conta.getAgencia());
            contaDTO.setNomeDoUsuario(conta.getNomeDoUsuario());
            contaDTO.setValorAtualConta(conta.getValorAtualConta());
            listaDto.add(contaDTO);

        }
        return listaDto;
    }


    public List<ContasModel> listarContasPorNomeDeUsuario(String nomeDoUsuario) {
        return contasRepository.findByNomeDoUsuario(nomeDoUsuario);
    }


    public Optional<ContasModel> exibeContaPorId(Long id) {
        return contasRepository.findById(id);
    }


    public CriarContaDTO cadastrar(CriarContaDTO criarContaDTO) {
        ContaFactory contaFactory = new ContaFactory();
        ContasModel conta = new ContasModel();
        String randomString = generateRandomString();
        conta.setNomeDoUsuario(criarContaDTO.getNomeDoUsuario());
        conta.setValorAtualConta(criarContaDTO.getValorAtualConta());
        BigDecimal resultado = contaFactory.tipoServicoConta(criarContaDTO.getTipoServico())
                .calcular(criarContaDTO.getValorAtualConta(), criarContaDTO.getValorFornecido());
        conta.setValorAtualConta(resultado);
        conta.setValorFornecido(criarContaDTO.getValorFornecido());
        conta.setTipoServico(criarContaDTO.getTipoServico());
        conta.setNumConta(randomString);
        conta.setAgencia("0001");
        conta.setValorFinal(resultado);
        contasRepository.save(conta);
        CriarContaDTO cadastro = new CriarContaDTO();
        cadastro.setNomeDoUsuario(conta.getNomeDoUsuario());
        cadastro.setValorAtualConta(conta.getValorAtualConta());
        cadastro.setValorFornecido(conta.getValorFornecido());
        cadastro.setTipoServico(conta.getTipoServico());

        return cadastro;


    }

    public static String generateRandomString() {
        StringBuilder randomString = new StringBuilder();

        SecureRandom random = new SecureRandom();

        for (int i = 0; i < 6; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }


    public ContasModel alterar(Long id, ContasModel contasModel,ContaFactory contaFactory) {

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
        if (contasModel.getTipoServico() != null){
            conta.setValorFornecido(contasModel.getValorFornecido());
            BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                    .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
            conta.setValorFinal(resultado);
            conta.setTipoServico(conta.getTipoServico());
            conta.setValorAtualConta(resultado);
        }

        return contasRepository.save(conta);
    }

    public void deletarConta(Long id) {
        contasRepository.deleteById(id);
    }

}
