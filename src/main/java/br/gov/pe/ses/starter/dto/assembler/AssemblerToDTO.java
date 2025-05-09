package br.gov.pe.ses.starter.dto.assembler;


import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssemblerToDTO<E, D> {
	
    private final ModelMapper modelMapper;

    public AssemblerToDTO(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public D toDto(E entity, Class<D> dtoClass) {
        return modelMapper.map(entity, dtoClass);
    }

    public List<D> toListDto(List<E> list, Class<D> dtoClass) {
        return list.stream().map((dto) -> toDto(dto, dtoClass)).collect(Collectors.toList());
    }
}

