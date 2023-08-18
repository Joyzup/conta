package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.ContaGetDto;
import com.factory.contabancaria.dto.ContaPostDto;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping(path = "/id/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id) {
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        return ResponseEntity.ok(contaOpcional.get());
    }

    @GetMapping(path = "/nome/{nomeDoUsuario}")
    public ResponseEntity<?> exibeUmaContaPeloNomeUsuario(@PathVariable String nomeDoUsuario) {
        Optional<ContasModel> contaOpcional = Optional.ofNullable(contasService.exibeContaPorNomeDoUsuario(nomeDoUsuario));
        if (contaOpcional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        ContasModel conta = contaOpcional.get();
        ContaGetDto responseDTO = new ContaGetDto();
        responseDTO.setNumConta(conta.getNumConta());
        responseDTO.setAgencia(conta.getAgencia());
        responseDTO.setNomeDoUsuario(conta.getNomeDoUsuario());
        responseDTO.setValorAtualConta(conta.getValorAtualConta());

        return ResponseEntity.ok(responseDTO);
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<ContaPostDto> cadastrarConta(@RequestBody ContasModel contasModel, ContaFactory contaFactory) {
        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);

        ContaPostDto responseDTO = new ContaPostDto();
        responseDTO.setNomeDoUsuario(novaConta.getNomeDoUsuario());
        responseDTO.setValorAtualConta(novaConta.getValorAtualConta()); // Alterado para valorAtualConta

        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }


    //PUT - Alterar uma conta já existente dentro do banco
    @PutMapping(path = "/{id}")
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel) {
        return contasService.alterar(id, contasModel);
    }

    @PutMapping(path = "/{id}/nome")
    public ResponseEntity<ContasModel> atualizarContaNome(@PathVariable Long id, @RequestBody String novoNome) {
        ContasModel contaAtualizada = contasService.atualizarContaNome(id, novoNome);
        return ResponseEntity.ok(contaAtualizada);
    }


    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id) {
        contasService.deletarConta(id);
    }

}
