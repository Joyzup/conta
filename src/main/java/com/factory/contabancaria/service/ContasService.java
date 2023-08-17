package com.factory.contabancaria.service;

import com.factory.contabancaria.dto.ContaGetDTO;
import com.factory.contabancaria.dto.ContaPostDTO;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipo_servico())
                .calcular(contasModel.getValor_atual_conta(), contasModel.getValor_fornecido());
        return contasRepository.save(contasModel);

    }


    public ContasModel alterar(Long id, ContasModel contasModel) {

        ContasModel contacadastrada = exibeContaPorId(id).get();

        if (contasModel.getNumero_conta() != null || contasModel.getAgencia() != null) {
            contacadastrada.setNomeCliente(contasModel.getNomeCliente());
            contacadastrada.setNumero_conta(contasModel.getNumero_conta());
            contacadastrada.setAgencia(contasModel.getAgencia());
            contacadastrada.setValor_fornecido(contasModel.getValor_fornecido());
            contacadastrada.setTipo_servico(contasModel.getTipo_servico());

            ContaFactory contaFactory = new ContaFactory();
            contacadastrada.setValor_atual_conta(contaFactory.tipoServicoConta(contasModel.getTipo_servico())
                    .calcular(contacadastrada.getValor_atual_conta(), contacadastrada.getValor_fornecido()));
        }

        return contasRepository.save(contacadastrada);
    }

    public void deletarConta(Long id) {
        contasRepository.deleteById(id);
    }

    //Método para cadastrar uma conta conforme o DTO
    public ContasModel cadastrarContaDTO(ContaPostDTO contaPostDTO, ContaFactory contaFactory) {

        ContasModel contasModel = new ContasModel();
        contasModel.setNomeCliente(contaPostDTO.getNomeCliente());
        contasModel.setValor_atual_conta(contaPostDTO.getValor_atual_conta());
        contasModel.setValor_fornecido(contaPostDTO.getValor_fornecido());
        contasModel.setTipo_servico(contaPostDTO.getTipo_servico());

        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipo_servico())
                .calcular(contasModel.getValor_atual_conta(), contasModel.getValor_fornecido());

        contasModel.setValor_atual_conta(resultado);

        return contasRepository.save(contasModel);
    }

    //Método para listar todas as contas conforme o DTO
    public List<ContaGetDTO> listarTodasContasDTO() {
        List<ContasModel> contas = contasRepository.findAll();

        List<ContaGetDTO> contaGetDTOS = new ArrayList<>();
        for (ContasModel conta : contas) {

            ContaGetDTO DTO = new ContaGetDTO();
            DTO.setNumero_conta(conta.getNumero_conta());
            DTO.setAgencia(conta.getAgencia());
            DTO.setNomeCliente(conta.getNomeCliente());
            DTO.setValor_atual_conta(conta.getValor_atual_conta());

            contaGetDTOS.add(DTO);
        }

        return contaGetDTOS;
    }

    //Método para listar contas por nome do usuário conforme DTO
    public List<ContaGetDTO> listarContasPorNome(String nome_cliente) {
        List<ContasModel> contas = contasRepository.findByNomeCliente(nome_cliente);

        List<ContaGetDTO> contaGetDTOS = new ArrayList<>();
        for (ContasModel conta : contas) {
            ContaGetDTO DTO = new ContaGetDTO();
            DTO.setNumero_conta(conta.getNumero_conta());
            DTO.setAgencia(conta.getAgencia());
            DTO.setNomeCliente(conta.getNomeCliente());
            DTO.setValor_atual_conta(conta.getValor_atual_conta());

            contaGetDTOS.add(DTO);
        }

        return contaGetDTOS;
    }

}
