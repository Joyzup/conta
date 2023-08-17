package com.factory.contabancaria.mapper;

import com.factory.contabancaria.dto.GetDTO;
import com.factory.contabancaria.model.ContasModel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetMapper {
    public GetDTO getDTO(ContasModel contasModel) {
        GetDTO dto = new GetDTO();

        dto.setNumContaDTO(contasModel.getNumConta());
        dto.setAgenciaDTO(contasModel.getAgencia());
        dto.setNomeUsuarioDTO(contasModel.getNomeDoUsuario());
        dto.setValorAtualContaDTO(contasModel.getValorAtualConta());

        return dto;
    }
}
