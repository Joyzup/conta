package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.AtualizacaoNumContaDTO;
import com.factory.contabancaria.dto.ContaGetResponseDTO;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    public ResponseEntity<List<ContasModel>> listarTodasContas(){
        return ResponseEntity.ok(contasService.listarContas());
    }

    @GetMapping("usuario-por-id/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id){
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        return ResponseEntity.ok(contaOpcional.get());
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<ContasModel> cadastrarConta(@RequestBody ContasModel contasModel, ContaFactory contaFactory){
        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
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

    //GET - seleciona conta por nome do usuário
    @GetMapping("usuario-por-nome/{nomeUsuario}")
    public ResponseEntity<ContaGetResponseDTO>exibeContaPorNomeUsuario(@PathVariable String nomeUsuario) {
        Optional<ContasModel> contassOptional = contasService.exibeContaPorNomeUsuario(nomeUsuario);
            return contassOptional.map(conta -> {
                ContaGetResponseDTO contaGetResponseDTO = new ContaGetResponseDTO();
                contaGetResponseDTO.setNumConta(conta.getNumConta());
                contaGetResponseDTO.setNomeUsuario(conta.getNomeDoUsuario());
                contaGetResponseDTO.setAgencia(conta.getAgencia());
                contaGetResponseDTO.setValorAtualConta(conta.getValorAtualConta());

                return ResponseEntity.ok(contaGetResponseDTO);
                    }).orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("{id}/atualizar-numero")
    public ResponseEntity<String>atualizaNumConta(@PathVariable Long id, @RequestBody AtualizacaoNumContaDTO atualizacaoNumContaDTO) {
        Optional<ContasModel>contasOptional = contasService.exibeContaPorId(id);
        return contasOptional.map(contaExistente -> {
            contaExistente.setNumConta(atualizacaoNumContaDTO.getNovoNumConta());
            contasService.salvar(contaExistente);

            return ResponseEntity.ok("Número da conta atualizado com sucesso.");
        }).orElse(ResponseEntity.notFound().build());
    }

}
