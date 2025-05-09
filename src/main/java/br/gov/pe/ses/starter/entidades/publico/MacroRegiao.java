package br.gov.pe.ses.starter.entidades.publico;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "macroregional")
@SequenceGenerator(name = "macroregional_id_seq", sequenceName = "macroregional_id_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = "id")
public class MacroRegiao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id	
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "macroregional_id_seq")
	@Column(name = "id")
	private Integer id;
	
	@NotEmpty
	@Column(name = "nome")	
	private String nome;
	

}