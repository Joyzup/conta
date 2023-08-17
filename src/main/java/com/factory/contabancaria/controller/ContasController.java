package com.factory.contabancaria.controller;

import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.dto.ContaDTO;
import com.factory.contabancaria.model.dto.ContaPutDadosDTO;
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
    public ResponseEntity<List<ContasModel>> listarTodasContas(){
        return ResponseEntity.ok(contasService.listarContas());
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
    public ResponseEntity<ContasModel> cadastrarConta(@RequestBody ContasModel contasModel, ContaFactory contaFactory){
        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }

    //PUT - Alterar uma conta já existente dentro do banco
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> atualizarConta(@PathVariable Long id, @RequestBody ContaPutDadosDTO contaPutDadosDTO, ContaFactory contaFactory) {
        ContasModel contasModel = contasService.alterar(id, contaPutDadosDTO, contaFactory);

        ContaDTO contaDTO = new ContaDTO();
        contaDTO.setNumConta(contasModel.getNumConta());
        contaDTO.setAgencia(contasModel.getAgencia());
        contaDTO.setNomeDoUsuario(contasModel.getNomeDoUsuario());
        contaDTO.setValorAtualConta(contasModel.getValorFinal());
        contaDTO.setTipoServico(contasModel.getTipoServico());
        contaDTO.setValorFornecido(contasModel.getValorFornecido());

        return ResponseEntity.ok(contaDTO);
    }


    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id){
        contasService.deletarConta(id);
    }

}
