package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.AlteracaoDTO;
import com.factory.contabancaria.dto.GetResponseDTO;
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

    @GetMapping(path = "/{nomeDoUsuario}")
    public ResponseEntity<GetResponseDTO> buscarContaPorNome(@PathVariable String nomeDoUsuario) {
        Optional<ContasModel> contasOptional = contasService.exibeContaPorNomeDoUsuario(nomeDoUsuario);
        return contasOptional.map(conta -> ResponseEntity.ok(
                GetResponseDTO.builder()
                        .agencia(conta.getAgencia())
                        .numConta(conta.getNumConta())
                        .nomeUsuario(conta.getNomeDoUsuario())
                        .valorAtualConta(conta.getValorAtualConta())
                        .build()
        )).orElseGet(() -> ResponseEntity.notFound().build());
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
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel){
        return contasService.alterar(id, contasModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContasModel> alterarValorEspecifico(@PathVariable Long id, @RequestBody AlteracaoDTO alteracao) {
        Optional<ContasModel> conta = contasService.exibeContaPorId(id);
        if (conta.isEmpty()) return ResponseEntity.notFound().build();
        ContasModel contaAlterada = conta.get();
        switch (alteracao.getCampo()) {
            case "numConta":
                contaAlterada.setNumConta(alteracao.getValor());
                break;
            case "agencia":
                contaAlterada.setAgencia(alteracao.getValor());
                break;
            case "nomeDoUsuario":
                contaAlterada.setNomeDoUsuario(alteracao.getValor());
                break;
            case "valorAtualConta":
                contaAlterada.setValorAtualConta(new BigDecimal(alteracao.getValor()));
                break;
            case "valorFornecido":
                contaAlterada.setValorFornecido(new BigDecimal(alteracao.getValor()));
                break;
            case "tipoServico":
                contaAlterada.setTipoServico(alteracao.getValor());
                break;
            default:
                throw new IllegalArgumentException("Campo não reconhecido: " + alteracao.getCampo());
        }
        return ResponseEntity.ok(contasService.alterar(id, contaAlterada));
    }

    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id){
        contasService.deletarConta(id);
    }

}
