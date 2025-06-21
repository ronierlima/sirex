package br.gov.pe.ses.starter.entidades.publico;

import static jakarta.persistence.CascadeType.ALL;

import java.io.Serializable;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.gov.pe.ses.starter.entidades.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "paciente")
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = "id")
@Audited(withModifiedFlag = true)
public class Paciente extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidade_cadastro", nullable = false)
	private Unidade unidadeCadastro;

	@Column(name = "prontuario", length = 20)
	private String prontuario;

	@Column(name = "cartaoSus", length = 40)
	private String cartaoSus;

	@ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
	@JoinColumn(name = "id_pessoa", nullable = false)
	private Pessoa pessoa;

	@Version
	@NotAudited
	private Integer versao;

	public Paciente() {
		this.pessoa = new Pessoa();
	}

	public boolean isNovo() {
		return id == null;
	}

}
