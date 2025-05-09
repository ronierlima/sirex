package br.gov.pe.ses.starter.dto.assembler;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GenericoAssembler {
	
    private final ModelMapper modelMapper;

    public GenericoAssembler(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <T, U> U toModel(T entity, Class<U> modelClass) {
        return modelMapper.map(entity, modelClass);
    }

    public <T, U> void copyToObject(T input, U domainObject) {
        modelMapper.map(input, domainObject);
    }

    public <T, U> List<U> toCollectionModel(List<T> entities, Class<U> modelClass) {
        return entities.stream().map(entity -> toModel(entity, modelClass)).collect(Collectors.toList());
    }
}