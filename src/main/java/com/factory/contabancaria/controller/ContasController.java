package com.factory.contabancaria.controller;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.dto.ContaAssembler;
import com.factory.contabancaria.model.dto.ContaGetDto;
import com.factory.contabancaria.model.dto.ContaPostDto;
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
    ContaAssembler contaAssembler;

    @Autowired
    ContasRepository contasRepository;

    //requisições
    //GET - Pegar as informações do nosso banco
    @GetMapping
    public ResponseEntity<List<ContaGetDto>> listarTodasContas(){

        List<ContasModel> contas = contasService.listarContas();

        List<ContaGetDto> contasDto = new ArrayList<>();
        for (ContasModel conta : contas) {

            ContaGetDto contaGetDto = contaAssembler.toModelGet(conta);
            contasDto.add(contaGetDto);

        }

        return ResponseEntity.ok(contasDto);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id) {
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);

        if (contaOpcional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }

        ContaPostDto novaContaDto = null;

        if (contaOpcional.isPresent()) {
            ContasModel conta = contaOpcional.get();
            novaContaDto = contaAssembler.toModelPost(conta);
        }

        return ResponseEntity.ok(novaContaDto);
    }


    @GetMapping(path = "/buscar-por-nome")
    public ResponseEntity<?> exibeUmaContaPeloNome(String nome) {
        ContasModel contaModel = contasService.nomeDoUsuario(nome);

        if (contaModel.getNomeDoUsuario().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }

        ContaPostDto novaContaDto = null;

        if (contaModel.getNomeDoUsuario().equals(nome)) {

            novaContaDto = contaAssembler.toModelPost(contaModel);
        }

        return ResponseEntity.ok(novaContaDto);
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<ContaPostDto> cadastrarConta(@RequestBody ContasModel contasModel,
                                                       ContaFactory contaFactory){
        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);

        ContaPostDto novaContaDto = contaAssembler.toModelPost(contasModel);

        return new ResponseEntity<>(novaContaDto, HttpStatus.CREATED);
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
