package br.gov.pe.ses.starter.dto.mapper;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.hibernate.envers.RevisionType;

import br.gov.pe.ses.starter.dto.HistoricoDTO;
import br.gov.pe.ses.starter.entidades.auditoria.UsuarioRevEntity;

public class HistoricoMapper {

	@SuppressWarnings("unchecked")
	public static <T> HistoricoDTO<T> map(Object[] registro) {
		 
		 	T entidade = (T) registro[0];
			UsuarioRevEntity usuario = (UsuarioRevEntity) registro[1];
			RevisionType tipo = (RevisionType) registro[2];
			Set<String> atributosModificados = (Set<String>) registro[3];
		 
	        return new HistoricoDTO<>(entidade, usuario, tipo,atributosModificados);	        
	       
	    }

	 public static <T> List<HistoricoDTO<T>> mapList(List<Object[]> registros) {
	        return registros.stream()
	                .map(HistoricoMapper::<T>map)
	                .collect(Collectors.toList());
	 }
	
}
