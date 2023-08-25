package com.factory.contabancaria.controller_test;

import com.factory.contabancaria.config.ModelMapperConfig;
import com.factory.contabancaria.controller.ContasController;
import com.factory.contabancaria.model.ContasModel;
import com.factory.contabancaria.model.dto.ContaAssembler;
import com.factory.contabancaria.model.dto.ContaGetDto;
import com.factory.contabancaria.model.dto.ContaPostDto;
import com.factory.contabancaria.model.factory.ContaFactory;
import com.factory.contabancaria.repository.ContasRepository;
import com.factory.contabancaria.service.ContasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(ContasController.class)
@Import({ContaAssembler.class, ModelMapperConfig.class})
public class ContasControllerTest {
    @Autowired
    ContasController contasController;

    @MockBean
    ContaPostDto contaPostDto;

    @MockBean
    ContasService contasService;
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ContaAssembler contaAssembler;
    @MockBean
    ContasRepository contasRepository;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void deveRetornarStausOkEUmaListaDeContas_QuandoListarTodasAsContas() throws Exception {

        List<ContasModel> contasModels = new ArrayList<>();
        ContasModel conta1 = new ContasModel();
        conta1.setNumConta("000000");
        contasModels.add(conta1);

        Mockito.when(this.contasService.listarContas())
                .thenReturn(contasModels);

        mockMvc.perform(get("/api/contas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].numConta").value(conta1.getNumConta()));

    }
    @Test
    public void deveRetornarUmaContaEStatusOk_QuandoBuscarUmaContaPorId() throws Exception {

        ContasModel contasModel = new ContasModel();
        contasModel.setId(1L);
        contasModel.setNumConta("000000");
        contasModel.setValorAtualConta(new BigDecimal(1000));
        contasModel.setAgencia("0000");
        contasModel.setNomeDoUsuario("Jucemeire Marques Lopes");
        ContaGetDto contaGettDto = contaAssembler.toModelGet(contasModel);


        Mockito.when(contasService.exibeContaPorId(Mockito.anyLong()))
                .thenReturn(Optional.of(contasModel));

        mockMvc.perform(get("/api/contas/{id}", 1L))
                .andExpect(status().isOk())
               .andExpect(jsonPath("$.nomeDoUsuario").value(contaGettDto.getNomeDoUsuario()))
               .andExpect(jsonPath("$.agencia").value(contaGettDto.getAgencia()));

    }

    @Test
    public void deveRetornarUmaContaEStatusOk_QuandoBuscarUmaContaPorNome() throws Exception {

        ContasModel contasModel = new ContasModel();
        contasModel.setId(1L);
        contasModel.setNumConta("000000");
        contasModel.setValorAtualConta(new BigDecimal(1000));
        contasModel.setAgencia("0000");
        contasModel.setNomeDoUsuario("Jucemeire Marques Lopes");
        ContaGetDto contaGetDto = contaAssembler.toModelGet(contasModel);
        System.out.println(contaGetDto.getNomeDoUsuario());


        Mockito.when(contasService.nomeDoUsuario("Jucemeire Marques Lopes"))
                .thenReturn(contasModel);

        mockMvc.perform(get("/api/contas/buscar-por-nome", "Jucemeire Marques Lopes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nomeDoUsuario").value("Jucemeire Marques Lopes"))
                .andExpect(jsonPath("$.agencia").value(contaGetDto.getAgencia()));

    }


    @Test
    public void deveRetornarUmaContaEStatusCreated_QuandoCadastrarUmaConta() throws Exception {

        ContasModel contasModel = new ContasModel();
        contasModel.setId(1L);
        contasModel.setNumConta("000000");
        contasModel.setValorAtualConta(new BigDecimal(1000));
        contasModel.setAgencia("0000");
        contasModel.setTipoServico("saque");
        contasModel.setValorFornecido(new BigDecimal(500));
        contasModel.setNomeDoUsuario("Jucemeire Marques Lopes");
        ContaFactory contaFactory = new ContaFactory();
        BigDecimal resultado = contaFactory.tipoServicoConta(contasModel.getTipoServico())
                .calcular(contasModel.getValorAtualConta(), contasModel.getValorFornecido());
        contasModel.setValorFinal(resultado);
        ContaPostDto contaPostDto = contaAssembler.toModelPost(contasModel);

        Mockito.when(contasService.cadastrar(contasModel, contaFactory))
                .thenReturn(contasModel);
        System.out.println(contaPostDto.getNomeDoUsuario());
        System.out.println(contasModel.getNomeDoUsuario());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/contas")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(contaPostDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nome_do_usuario").value(contasModel.getNomeDoUsuario()))
                .andExpect(jsonPath("$.tipo_servico").value(contasModel.getTipoServico()))
                .andExpect(jsonPath("$.valor_atual_conta").value(contasModel.getValorAtualConta()));

    }

}
