package br.gov.pe.ses.starter.entidades.publico;

import java.io.Serializable;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.gov.pe.ses.starter.entidades.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "tipo_unidade")
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Audited(withModifiedFlag = true)
@SequenceGenerator(name = "tipo_unidade_id_seq", sequenceName = "tipo_unidade_id_seq", allocationSize = 1)
public class TipoUnidade extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "tipo_unidade_id_seq")
	private Long id;

	@NotEmpty(message = "Você deve informar a Descrição")
	private String descricao;

	@Version
	@NotAudited
	@Column(name = "versao", columnDefinition = "integer default 0")
	private Integer versao;

	public boolean isNovo() {
		return id == null;
	}

	public boolean isExistente() {
		return id != null;
	}

	public String getStatusCss() {
		if (getAtivo() == false) {
			return "unqualified";
		}

		return "qualified";
	}

	@Override
	public String toString() {
		return id.toString();
	}
}
