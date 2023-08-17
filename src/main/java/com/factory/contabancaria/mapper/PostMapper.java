package com.factory.contabancaria.mapper;

import com.factory.contabancaria.dto.PostDTO;
import com.factory.contabancaria.model.ContasModel;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostDTO postDTO(ContasModel contasModel) {
        PostDTO dto = new PostDTO();

        dto.setNomeDoUsuarioDTO(contasModel.getNomeDoUsuario());
        dto.setTipoServicoDTO(contasModel.getTipoServico());
        dto.setValorAtualContaDTO(contasModel.getValorAtualConta());
        dto.setValorFornecidoDTO(contasModel.getValorFornecido());
        dto.setValorFinalDTO(contasModel.getValorFinal());

        return dto;
    }
}
