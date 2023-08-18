package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.CriarContaDTO;
import com.factory.contabancaria.dto.ListarContaDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/contas")
public class ContasController {

    @Autowired
    ContasService contasService;

    @Autowired
    ContasRepository contasRepository;


    @GetMapping
    public ResponseEntity<List<ListarContaDTO>> listarTodasContas() {
        List<ListarContaDTO> contaDTOS = contasService.listarContas();
        return ResponseEntity.ok(contaDTOS);
    }



    private ListarContaDTO mapToDTO(ContasModel conta) {
        ListarContaDTO dto = new ListarContaDTO();
        dto.setNumConta(conta.getNumConta());
        dto.setAgencia(conta.getAgencia());
        dto.setNomeDoUsuario(conta.getNomeDoUsuario());
        dto.setValorAtualConta(conta.getValorAtualConta());
        return dto;
    }
//No postman usar o form-data para essa requisição
    @GetMapping(value = "buscarPorNome")
    public ResponseEntity<List<ListarContaDTO>> listarContasPorNomeUsuario(@RequestParam (name = "nomeDoUsuario") String nomeDoUsuario) {
        List<ContasModel> contas = contasService.listarContasPorNomeDeUsuario(nomeDoUsuario);

        List<ListarContaDTO> dtos = contas.stream()
                .map(conta -> mapToDTO(conta))
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Object> exibeUmaContaPeloId(@PathVariable(value = "id")Long id){
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }
        return ResponseEntity.ok(contaOpcional.get());
    }

    //    POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<CriarContaDTO> cadastrarConta(@RequestBody CriarContaDTO criarContaDTO) {
        CriarContaDTO novaConta = contasService.cadastrar(criarContaDTO);
        return new ResponseEntity<>(novaConta, HttpStatus.CREATED);
    }




   // PUT - Alterar uma conta já existente dentro do banco
    @PutMapping(path = "/{id}")
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel ,
                                      ContaFactory contaFactory){
        return contasService.alterar(id, contasModel,contaFactory);
    }

    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id) {
        contasService.deletarConta(id);
    }

}
