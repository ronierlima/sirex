package br.gov.pe.ses.starter.entidades.publico;

import java.io.Serializable;
import java.util.List;

import org.hibernate.envers.Audited;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@Entity
@Table(name = "funcionalidade")
@SequenceGenerator(name = "funcionalidade_id_seq", sequenceName = "funcionalidade_id_seq", allocationSize = 1)
@Audited(withModifiedFlag = true)
public class Funcionalidade implements Serializable {

	
	private static final long serialVersionUID = 3415587721663768426L;
	
	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "funcionalidade_id_seq")
	private Long id;

	@Column(name = "nome", length = 50, nullable = false)
	private String nome;

	@Column(name = "label", length = 50)
	private String label;

	@Column(name = "exibir")
	private boolean exibir;
	
	@ManyToOne
	@JoinColumn(name = "id_funcionalidade")
	private Funcionalidade funcionalidadePai;
	
	@Transient
	private List<Funcionalidade> filhas;

}
