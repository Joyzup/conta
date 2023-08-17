package com.factory.contabancaria.dtos;

import com.factory.contabancaria.model.ContasModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContaAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public ContaPostDTO transfomaModelemDto (ContasModel contasModel){
        return modelMapper.map(contasModel, ContaPostDTO.class);
    }


    public ContaGetDTO toModelGet (ContasModel contasModel){
        return modelMapper.map(contasModel, ContaGetDTO.class);
    }
}
