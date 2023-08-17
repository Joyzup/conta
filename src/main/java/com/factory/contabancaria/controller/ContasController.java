package com.factory.contabancaria.controller;

import com.factory.contabancaria.dto.UsuarioRequestDTO;
import com.factory.contabancaria.dto.UsuarioResponseDTO;
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
import java.util.Random;

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
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodasContas(){
        List<ContasModel> contas = contasService.listarContas();
        List<UsuarioResponseDTO> usuarioResponseDTOList = new ArrayList<>();

        for (ContasModel conta : contas) {
            UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                    conta.getNumConta(),
                    conta.getAgencia(),
                    conta.getNomeDoUsuario(),
                    conta.getValorAtualConta()
            );
            usuarioResponseDTOList.add(usuarioResponseDTO);
        }

        return ResponseEntity.ok(usuarioResponseDTOList);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<?> exibeUmaContaPeloId(@PathVariable Long id){
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);
        if (contaOpcional.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada, tente novamente!");
        }

        ContasModel conta = contaOpcional.get();
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                conta.getNumConta(),
                conta.getAgencia(),
                conta.getNomeDoUsuario(),
                conta.getValorAtualConta()
        );

        return ResponseEntity.ok(usuarioResponseDTO);
    }

    @GetMapping("/usuario/{nomeUsuario}")
    public ResponseEntity<UsuarioResponseDTO> getContaByNomeUsuario(@PathVariable String nomeUsuario) {
        ContasModel conta = contasRepository.findByNomeDoUsuario(nomeUsuario);

        if (conta == null) {
            return ResponseEntity.notFound().build();
        }

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO(
                conta.getNomeDoUsuario(),
                conta.getAgencia(),
                conta.getNomeDoUsuario(),
                conta.getValorAtualConta()
        );
        return ResponseEntity.ok(usuarioResponseDTO);
    }

    //POST - Cria uma nova conta dentro do banco
    @PostMapping
    public ResponseEntity<UsuarioRequestDTO> cadastrarConta(@RequestBody UsuarioRequestDTO usuarioRequestDTO, ContaFactory contaFactory) {
        ContasModel contasModel = new ContasModel();
        contasModel.setNumConta(String.valueOf(gerarNumeroAleatorio(1000, 9999)));
        contasModel.setAgencia("0001");
        contasModel.setNomeDoUsuario(usuarioRequestDTO.getNomeDoUsuario());
        contasModel.setValorAtualConta(usuarioRequestDTO.getValorAtualConta());
        contasModel.setValorFornecido(usuarioRequestDTO.getValorFornecido());
        contasModel.setTipoServico(usuarioRequestDTO.getTipoServico());

        if ("DEPOSITO".equalsIgnoreCase(usuarioRequestDTO.getTipoServico())) {
            contasModel.setValorAtualConta(contasModel.getValorAtualConta().add(usuarioRequestDTO.getValorFornecido()));
        } else if ("SAQUE".equalsIgnoreCase(usuarioRequestDTO.getTipoServico())) {
            contasModel.setValorAtualConta(contasModel.getValorAtualConta().subtract(usuarioRequestDTO.getValorFornecido()));
        }

        ContasModel novaConta = contasService.cadastrar(contasModel, contaFactory);

        usuarioRequestDTO.setValorAtualConta(novaConta.getValorAtualConta());

        return new ResponseEntity<>(usuarioRequestDTO, HttpStatus.CREATED);
    }

    public static int gerarNumeroAleatorio(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

    //PUT - Alterar uma conta já existente dentro do banco
   /* @PutMapping(path = "/{id}")
    public ContasModel atualizarConta(@PathVariable Long id, @RequestBody ContasModel contasModel){
        return contasService.alterar(id, contasModel);
    }
    */
    @PutMapping(path = "/{id}")
    public ResponseEntity<ContasModel> alterarConta(@PathVariable Long id, @RequestBody UsuarioRequestDTO usuarioRequestDTO) {
        Optional<ContasModel> contaOpcional = contasService.exibeContaPorId(id);

        if (contaOpcional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        ContasModel contasModel = contaOpcional.get();
        contasModel.setNomeDoUsuario(usuarioRequestDTO.getNomeDoUsuario());
        contasModel.setValorAtualConta(usuarioRequestDTO.getValorAtualConta());
        contasModel.setValorFornecido(usuarioRequestDTO.getValorFornecido());
        contasModel.setTipoServico(usuarioRequestDTO.getTipoServico());

        ContasModel contaAtualizada = contasService.alterar(id, contasModel);
        return ResponseEntity.ok(contaAtualizada);
    }

    //DELETE - Deleta uma conta já existente dentro do banco
    @DeleteMapping(path = "/{id}")
    public void deletarConta(@PathVariable Long id){
        contasService.deletarConta(id);
    }

}
