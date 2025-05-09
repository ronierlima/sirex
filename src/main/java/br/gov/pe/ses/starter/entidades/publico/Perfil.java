package br.gov.pe.ses.starter.entidades.publico;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "perfil")
@SequenceGenerator(name = "perfil_id_seq", sequenceName = "perfil_id_seq", allocationSize = 1)
@Audited(withModifiedFlag = true)
public class Perfil implements Serializable {

	private static final long serialVersionUID = -6059539290314608823L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "perfil_id_seq")
	private Long id;

	@NotEmpty
	@Column(name = "nome", length = 50, nullable = false)
	private String nome;

	
	@SuppressWarnings("deprecation")
	@Fetch(FetchMode.SUBSELECT)
	@ManyToMany(fetch = FetchType.EAGER)
	@Cascade(CascadeType.SAVE_UPDATE)
	@JoinTable(name = "perfil_funcionalidade", joinColumns = {
			@JoinColumn(name = "id_perfil", referencedColumnName = "id") }, inverseJoinColumns = @JoinColumn(name = "id_funcionalidade", referencedColumnName = "id"))
	private List<Funcionalidade> funcionalidades;

	@Column(name = "ativo", nullable = false)
	private boolean ativo;

	@Transient
	Set<Funcionalidade> selecionadas = new HashSet<>();
		
	@ManyToOne(fetch = FetchType.LAZY)
	@NotAudited
	@JoinColumn(name = "id_hospital")
	private Hospital hospital;
	
	@Version
	@NotAudited
	private Integer versao;

	public String getStatusCss() {
		if (ativo == false) {
			return "unqualified";
		}

		return "qualified";
	}

	public List<Funcionalidade> getFilhas(Funcionalidade pai) {
		return getFuncionalidades().stream().filter(f -> f.getFuncionalidadePai().equals(pai)).toList();
	}
	
	public Perfil() {
		funcionalidades = new ArrayList<Funcionalidade>();
	
	}

}
