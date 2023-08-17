package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.ContaDTO;
import com.factory.contabancaria.dto.ContaDTOPost;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.factory.CalculoDeposito;
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
  public List<ContaDTO> listarTodasContas() {
    List<ContasModel> contas = contasService.listarContas();
    List<ContaDTO> contasDTO = new ArrayList<>();

    for (ContasModel conta : contas) {
      ContaDTO dto = new ContaDTO();
      dto.setNumConta(conta.getNumConta());
      dto.setAgencia(conta.getAgencia());
      dto.setNomeDoUsuario(conta.getNomeDoUsuario());
      dto.setValorAtualConta(conta.getValorAtualConta());
      contasDTO.add(dto);
    }
    return contasDTO;
  }

  @GetMapping(path = "/{id}")
  public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id) {
    Optional<ContasModel> contaOptional = contasService.exibeContaPorId(id);
    if (contaOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
    }
    ContasModel conta = contaOptional.get();
    ContaDTO dto = new ContaDTO();
    dto.setNumConta(conta.getNumConta());
    dto.setAgencia(conta.getAgencia());
    dto.setNomeDoUsuario(conta.getNomeDoUsuario());
    dto.setValorAtualConta(conta.getValorAtualConta());

    return ResponseEntity.ok(dto);
  }

  // Seleciona uma conta pelo nome do usuário
  @GetMapping(path = "/usuario")
  public ResponseEntity<?> exibeUmaContPeloNome(@RequestParam String nome) {
    Optional<ContasModel> contaOptional = contasService.exibeContaPorNome(nome);
    if (contaOptional.isEmpty()) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
    }
    ContasModel conta = contaOptional.get();
    ContaDTO dto = new ContaDTO();
    dto.setNumConta(conta.getNumConta());
    dto.setAgencia(conta.getAgencia());
    dto.setNomeDoUsuario(conta.getNomeDoUsuario());
    dto.setValorAtualConta(conta.getValorAtualConta());

    return ResponseEntity.ok(dto);
  }

  @PostMapping
  public ResponseEntity<ContaDTOPost> cadastrarConta(@RequestBody ContasModel contasModel,
                                                     ContaFactory contaFactory) {
    ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);

    ContaDTOPost contaDTO = new ContaDTOPost();
    contaDTO.setNomeDoUsuario(novaConta.getNomeDoUsuario());
    contaDTO.setValorAtualConta(novaConta.getValorAtualConta());
    contaDTO.setValorFornecido(novaConta.getValorFornecido());
    contaDTO.setTipoServico(novaConta.getTipoServico());

    return new ResponseEntity<>(contaDTO, HttpStatus.CREATED);
  }

  //PUT - Alterar uma conta já existente dentro do banco
  @PutMapping(path = "/{id}")
  public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel) {
    return contasService.alterar(id, contasModel);
  }

  // PUT - Realizar um saque ou deposíto
  @PutMapping(path = "/servico/{id}")
  public ContasModel atualizaUsuario(@PathVariable Long id, @RequestBody ContasModel contasModel,
                                     ContaFactory contaFactory) {
    return contasService.alteraNomeUsuario(id, contasModel, contaFactory);
  }

  //DELETE - Deleta uma conta já existente dentro do banco
  @DeleteMapping(path = "/{id}")
  public void deletarConta(@PathVariable Long id) {
    contasService.deletarConta(id);
  }

}
