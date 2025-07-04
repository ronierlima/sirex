
package br.gov.pe.ses.starter.entidades.publico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import jakarta.validation.constraints.Email;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import br.gov.pe.ses.starter.entidades.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "unidade")
@SequenceGenerator(sequenceName = "unidade_id_seq", name = "unidade_id_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = "id")
@Audited(withModifiedFlag = true)
public class Unidade extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "unidade_id_seq")
	private Long id;

	@NotEmpty(message = "Você deve informar o Nome do Hospital")
	private String nome;

	@NotNull(message = "Você deve informar o CNPJ")
	@Column(name = "cnpj", length = 18, nullable = false, unique = true)
	private String cnpj;

	@Column(name = "sigla")
	private String sigla;

	private String cnes;

	private String telefone;

	@Email
	private String email;

	private String logradouro;
	private String numero;
	private String complemento;
	private String bairro;
	private String cep;

	@NotAudited
	@ManyToOne
	@JoinColumn(name = "id_municipio", referencedColumnName = "id")
	private Municipio municipio = new Municipio();

	@NotAudited
	@OneToMany(mappedBy = "unidade", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Perfil> perfis = new ArrayList<>();

	@NotAudited
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_tipo")
	private TipoUnidade tipo;

	@Version
	@NotAudited
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

}
