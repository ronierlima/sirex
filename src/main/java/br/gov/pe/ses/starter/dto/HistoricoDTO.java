package br.gov.pe.ses.starter.dto;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.hibernate.envers.RevisionType;

import br.gov.pe.ses.starter.entidades.auditoria.UsuarioRevEntity;
import br.gov.pe.ses.starter.enums.TipoRevisao;
import lombok.Data;

@SuppressWarnings("rawtypes")
@Data
public class HistoricoDTO<T> implements Comparable<HistoricoDTO> {

	private T entidade;
    private UsuarioRevEntity usuario;
    private RevisionType tipo;
    private Set<String> atributosModificados;
    private String tipoAcao;
    private String nomeSimplesEntidade;
    private String idEntidade;
    private String detalhesEntidade;
	
	public HistoricoDTO(T entidade, UsuarioRevEntity usuario, RevisionType tipo,Set<String> atributosModificados) {
		this.entidade = entidade;
		this.usuario = usuario;
		this.tipo = tipo;
		this.atributosModificados = atributosModificados;
		this.tipoAcao = TipoRevisao.obterTipoRevisao(tipo);
		this.nomeSimplesEntidade = entidade.getClass().getSimpleName();
		this.idEntidade = getIdFromToString();
	}

	@Override
	public int compareTo(HistoricoDTO historicoDTO) {
		return this.usuario.getRevisionDate().compareTo(historicoDTO.getUsuario().getRevisionDate());		
	}
	
	public String getIdFromToString() {
	    String strEntidade = entidade.toString();
	    Pattern pattern = Pattern.compile("\\d+");
	    Matcher matcher = pattern.matcher(strEntidade);
	    if (matcher.find()) {
	        return matcher.group();
	    }
	    return null;
	}
	
	public HistoricoDTO() {
		
	}
	
}
