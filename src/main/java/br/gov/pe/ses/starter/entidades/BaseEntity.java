package br.gov.pe.ses.starter.entidades;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import br.gov.pe.ses.starter.entidades.publico.Usuario;
import br.gov.pe.ses.starter.security.UtilUserDetails;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Data;

@Data
@MappedSuperclass
@Audited(withModifiedFlag = true)
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean ativo = true;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_inclusao", updatable = false)
	private Usuario usuarioInclusao;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_alteracao")
	private Usuario usuarioAlteracao;

	@CreationTimestamp
	@Column(name = "data_inclusao", updatable = false)
	private Date dataInclusao;

	@UpdateTimestamp
	@Column(name = "data_alteracao")
	private Date dataAlteracao;

	@PrePersist
	public void prePersist() {
		ativo = true;
		dataInclusao = new Date();
		usuarioInclusao = UtilUserDetails.getUsuarioLogado();
	}

	@PreUpdate
	public void preUpdate() {
		dataAlteracao = new Date();
		usuarioAlteracao = UtilUserDetails.getUsuarioLogado();

	}

}
