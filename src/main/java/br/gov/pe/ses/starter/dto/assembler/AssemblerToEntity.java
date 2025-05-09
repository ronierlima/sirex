package br.gov.pe.ses.starter.dto.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AssemblerToEntity<D, E> {
	
    private final ModelMapper modelMapper;

    public AssemblerToEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public E toEntity(D dto, Class<E> entityClass) {
        return modelMapper.map(dto, entityClass);
    }

    public List<E> toListEntity(List<D> list, Class<E> entityClass) {
        return list.stream().map((dto) -> toEntity(dto, entityClass)).collect(Collectors.toList());
    }
}