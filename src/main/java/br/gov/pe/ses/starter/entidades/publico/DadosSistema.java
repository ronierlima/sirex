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
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "dados_sistema")
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = "id")
@Audited(withModifiedFlag = true)
public class DadosSistema extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "Você deve informar o nome")
	private String nome;

	@NotEmpty(message = "Você deve informar a sigla")
	private String sigla;

	@NotEmpty(message = "A Logo Principal é obrigatório!")
	private byte[] logoPrincipal;

	@NotEmpty(message = "A Logo do Rodapé é obrigatório!")
	private byte[] logoRodape;

	@NotEmpty(message = "O Manual é obrigatório!")
	private byte[] manual;

	@Version
	@NotAudited
	@Column(name = "versao", columnDefinition = "integer default 0")
	private Integer versao;

}
