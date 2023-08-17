package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.ContaGetDTO;
import com.factory.contabancaria.dto.ContaPostDTO;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contas")
public class ContasController {

    @Autowired
    ContasService contasService;

    @Autowired
    ContasRepository contasRepository;

    //requisições
    //GET - Pegar as informações do nosso banco
    @GetMapping
    public ResponseEntity<List<ContasModel>> listarTodasContas() {
        return ResponseEntity.ok(contasService.listarContas());
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id) {
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        return ResponseEntity.ok(contaOpcional.get());
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<ContasModel> cadastrarConta(@RequestBody ContasModel contasModel,
                                                      ContaFactory contaFactory) {
        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }


    //PUT - Alterar uma conta já existente dentro do banco
    @PutMapping(path = "/{id}")
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel) {
        return contasService.alterar(id, contasModel);
    }

    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id) {
        contasService.deletarConta(id);
    }

    //POST - Cria uma nova conta dentro do banco com alguns atributos conforme DTO
    @PostMapping(path = "/dto")
    public ResponseEntity<ContaPostDTO> cadastrarContaDTO(@RequestBody ContaPostDTO contaPostDTO,
                                                          ContaFactory contaFactory) {
        ContasModel contasModel = new ContasModel();
        contasModel.setNomeCliente(contaPostDTO.getNomeCliente());
        contasModel.setValor_atual_conta(contaPostDTO.getValor_atual_conta());
        contasModel.setValor_fornecido(contaPostDTO.getValor_fornecido());
        contasModel.setTipo_servico(contaPostDTO.getTipo_servico());

        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);
        ContaPostDTO postDTO = new ContaPostDTO();
        postDTO.setTipo_servico(novaConta.getTipo_servico());
        postDTO.setValor_atual_conta(novaConta.getValor_atual_conta());
        postDTO.setValor_fornecido(novaConta.getValor_fornecido());
        postDTO.setNomeCliente(novaConta.getNomeCliente());

//        contaGetDTO.setNumero_conta(novaConta.getNumero_conta());
//        contaGetDTO.setAgencia(novaConta.getAgencia());
//        contaGetDTO.setNomeCliente(novaConta.getNomeCliente());
//        contaGetDTO.setValor_atual_conta(novaConta.getValor_atual_conta());

        return new ResponseEntity<>(postDTO, HttpStatus.CREATED);

    }

    //GET - Listar as informações do banco com os atributos conforme DTO
    @GetMapping(path = "/dto")
    public ResponseEntity<List<ContaGetDTO>> listarTodasContasDTO() {
        List<ContasModel> contas = contasService.listarContas();

        List<ContaGetDTO> contaGetDTOS = new ArrayList<>();
        for (ContasModel conta : contas) {
            ContaGetDTO DTO = new ContaGetDTO();
            DTO.setNumero_conta(conta.getNumero_conta());
            DTO.setAgencia(conta.getAgencia());
            DTO.setNomeCliente(conta.getNomeCliente());
            DTO.setValor_atual_conta(conta.getValor_atual_conta());

            contaGetDTOS.add(DTO);
        }
        return ResponseEntity.ok(contaGetDTOS);
    }

    //Endpoint GET para listar contas por nome do usuario conforme DTO
    @GetMapping(path = "/dto/get/{nomeUsuario}")
    public ResponseEntity<List<ContaGetDTO>> listarContasPorNome(@PathVariable String nomeDoUsuario) {
        List<ContaGetDTO> contaPorNome = contasService.listarContasPorNome(nomeDoUsuario);
        return ResponseEntity.ok(contaPorNome);
    }

}
