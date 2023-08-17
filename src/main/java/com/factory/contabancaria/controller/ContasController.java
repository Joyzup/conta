package com.factory.contabancaria.controller;

import com.factory.contabancaria.dtos.ContaAssembler;
import com.factory.contabancaria.dtos.ContaGetDTO;
import com.factory.contabancaria.dtos.ContaPostDTO;
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
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@RestController

@RequestMapping("/api/contas")
public class ContasController {

    @Autowired
    ContasService contasService;

    @Autowired
    ContasRepository contasRepository;

    @Autowired
    ContaAssembler contaAssembler;

    //requisições
    //GET - Pegar as informações do nosso banco
//    @GetMapping
//    public ResponseEntity<List<ContasModel>> listarTodasContas(){
//        return ResponseEntity.ok(contasService.listarContas());
//    }

//    @GetMapping
//    public List<ContaGetDTO> listarTodasContas(){
//        List<ContasModel> contas = contasService.listarContas();
//        List<ContaGetDTO> contaGetDTOS = new ArrayList<>();
//
//        for(ContasModel conta: contas){
//            ContaGetDTO dto = new ContaGetDTO();
//            dto.setNumConta(conta.getNumConta());
//            dto.setAgencia(conta.getAgencia());
//            dto.setNomeDoUsuario(conta.getNomeDoUsuario());
//            contaGetDTOS.add(dto);
//        }
//        return contaGetDTOS;
//    }

    @GetMapping
    public ResponseEntity<List<ContaGetDTO>>listarTodasContas() {

        List<ContasModel> contas = contasService.listarContas();
        List<ContaGetDTO> contaDto = new ArrayList<>();
        for (ContasModel conta : contas) {
            ContaGetDTO contaGetDto = contaAssembler.toModelGet(conta);
            contaDto.add(contaGetDto);
        }
        return ResponseEntity.ok(contaDto);
    }

    @GetMapping(path = "/busca-nome")
    public ResponseEntity<?> buscarPeloNome(String nome) {
        ContasModel contasModel = contasService.nomeDoUsuario(nome);

        if (contasModel.getNomeDoUsuario().isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontada");
        }
        ContaPostDTO novaContaDTO = null;

        if (contasModel.getNomeDoUsuario().equals(nome)){
            novaContaDTO = contaAssembler.transfomaModelemDto(contasModel);
        }
        return ResponseEntity.ok(novaContaDTO);
    }



    @GetMapping(path = "/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id){
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        return ResponseEntity.ok(contaOpcional.get());
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<ContaPostDTO> cadastrarConta(@RequestBody ContasModel contasModel, ContaFactory contaFactory){
        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);
        ContaPostDTO novaDto = contaAssembler.transfomaModelemDto(contasModel);
        return new ResponseEntity<>(novaDto, HttpStatus.CREATED);

    }



    //PUT - Alterar uma conta já existente dentro do banco
    @PutMapping(path = "/{id}")
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel){
        return contasService.alterar(id, contasModel);
    }

    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id){
        contasService.deletarConta(id);
    }

}
