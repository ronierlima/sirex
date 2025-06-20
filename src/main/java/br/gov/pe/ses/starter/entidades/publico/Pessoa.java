package br.gov.pe.ses.starter.entidades.publico;

import java.io.Serializable;
import java.time.LocalDate;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.br.CPF;

import br.gov.pe.ses.starter.entidades.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "pessoa")
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = "id")
@Audited(withModifiedFlag = true)
@SequenceGenerator(name = "pessoa_id_seq", sequenceName = "pessoa_id_seq", allocationSize = 1)
public class Pessoa extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "pessoa_id_seq")
	private Long id;

	@NotEmpty
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;

	@CPF(message = "O CPF informado é inválido!")
	@Column(name = "cpf", length = 100, nullable = false)
	private String cpf;

	@Email(message = "O email informado é inválido!")
	@Column(name = "email", length = 100, nullable = false)
	private String email;
	
	@Column(name = "data_nascimento", updatable = false)
	private LocalDate dataNascimento;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidade_cadastro", nullable = false)
	private Unidade unidadeCadastrada;

	@Version
	@NotAudited
	private Integer versao;

}
