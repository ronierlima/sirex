package br.gov.pe.ses.starter.entidades.publico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "hospital")
@SequenceGenerator(sequenceName = "hospital_id_seq", name = "hospital_id_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = "id")
@Audited(withModifiedFlag = true)
public class Hospital extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_id_seq")
	private Long id;

	@NotEmpty(message = "Você deve informar o Nome do Hospital")
	private String nome;

	@NotNull(message = "Você deve informar o CNPJ")
	private String cnpj;

	@Column(name = "sigla")
	private String sigla;

	@NotAudited
	@ManyToOne
	@JoinColumn(name = "id_municipio", referencedColumnName = "id", nullable = true)
	private Municipio municipio = new Municipio();

	@NotAudited
	@OneToMany(mappedBy = "hospital", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
	private List<Perfil> perfis = new ArrayList<>();

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
