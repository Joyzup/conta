package com.factory.contabancaria.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AlteracaoDTO {
    private String campo;
    private String valor;

    public AlteracaoDTO() {}
}
