package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.GetDTO;
import com.factory.contabancaria.dto.PostDTO;
import com.factory.contabancaria.mapper.GetMapper;
import com.factory.contabancaria.mapper.PostMapper;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contas")
public class ContasController {

    @Autowired
    ContasService contasService;

    @Autowired
    ContasRepository contasRepository;

    @Autowired
    GetMapper getMapper;

    @Autowired
    PostMapper postMapper;


    //requisições
    //GET - Pegar as informações do nosso banco
    @GetMapping
    public ResponseEntity<List<GetDTO>> listarTodasContas() {
        List<ContasModel> contas = contasService.listarContas();

        List<GetDTO> dtos = contas.stream()
                .map(conta -> getMapper.getDTO(conta))
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id) {
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);

        if (contaOpcional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        ContasModel contaId = contaOpcional.get();
        return ResponseEntity.ok(getMapper.getDTO(contaId));
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<ContasModel> cadastrarConta(@RequestBody ContasModel contasModel, ContaFactory contaFactory) {
        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    //PUT - Alterar uma conta já existente dentro do banco
    @PutMapping
    public PostDTO atualizarConta(@RequestBody ContasModel contasModel) {
        ContasModel movimentaConta = contasService.alterar(contasModel);
        return new ResponseEntity<>(postMapper.postDTO(movimentaConta),HttpStatus.CREATED).getBody();
    }

    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id) {
        contasService.deletarConta(id);
    }

}
