package com.factory.contabancaria.controller;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.dtos.ContasDTOGet;
import com.factory.contabancaria.model.dtos.ContasDTOPost;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/contas")
public class ContasController {

    @Autowired
    ContasService contasService;
    @Autowired
    ContasRepository contasRepository;

    //requisições
    //GET - Pegar todas as informações do banco
    @GetMapping
    public ResponseEntity<List<ContasDTOGet>> listarTodasContas(){
        return ResponseEntity.ok(contasService.listarContas());
    }

    // GET - Busca uma conta pelo id
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> exibeContaPeloId(@PathVariable Long id){

        Optional<ContasDTOGet> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        return ResponseEntity.ok(contaOpcional.get());
    }

    // GET - Busca uma conta pelo nome
    @GetMapping(path = "/busca/{nomeDoUsuario}")
    public ResponseEntity<?> exibeContaPeloNome(@PathVariable String nomeDoUsuario){

        Optional<ContasDTOGet> contaDTO = contasService.exibeContaPorNome(nomeDoUsuario);

        if(contaDTO.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        return ResponseEntity.ok(contaDTO.get());
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<ContasDTOPost> cadastrarConta(@RequestBody ContasModel contasModel, ContaFactory contaFactory){
        contasService.cadastrar(contasModel, contaFactory);
        ContasDTOPost novaContaResponse = new ContasDTOPost();

        novaContaResponse.setValorAtualConta(contasModel.getValorAtualConta());
        novaContaResponse.setTipoServico(contasModel.getTipoServico());
        novaContaResponse.setNomeDoUsuario(contasModel.getNomeDoUsuario());
        novaContaResponse.setValorFornecido(contasModel.getValorFornecido());

        return new ResponseEntity<>(novaContaResponse, HttpStatus.CREATED);
    }

    //PUT - Alterar uma conta já existente dentro do banco
    @PutMapping(path = "/{id}")
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel){
        return contasService.alterar(id, contasModel);
    }

    //PUT- FAZ UMA ALTERAÇÃO EM ESPECÍFICO
    @PutMapping (path = "/agencia/{id}")
    public ContasModel atualizarAgencia(@PathVariable Long id, @RequestBody String agencia) {
        return contasService.alterarAgencia(id,agencia);
    }


    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id){
        contasService.deletarConta(id);
    }

}
