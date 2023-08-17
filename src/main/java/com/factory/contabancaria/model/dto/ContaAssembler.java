package com.factory.contabancaria.model.dto;

import com.factory.contabancaria.model.ContasModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContaAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ContaPostDto toModel (ContasModel contasModel) {

        return modelMapper.map(contasModel, ContaPostDto.class);

    }

}
