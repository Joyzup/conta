package com.factory.contabancaria.model.dto;

import com.factory.contabancaria.model.ContasModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContaAssembler {
    @Autowired
    private ModelMapper modelMapper;

    public ContaPostDto toModelPost(ContasModel contasModel) {

        return modelMapper.map(contasModel, ContaPostDto.class);

    }

    public ContaGetDto toModelGet(ContasModel contasModel) {

        return modelMapper.map(contasModel, ContaGetDto.class);

    }

    public Optional<ContaGetDto> toModelGetOptional(ContasModel contasModel) {

        return Optional.ofNullable(modelMapper.map(contasModel, ContaGetDto.class));

    }

}
