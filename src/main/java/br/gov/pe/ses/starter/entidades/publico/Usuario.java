package br.gov.pe.ses.starter.entidades.publico;

import static jakarta.persistence.CascadeType.ALL;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.springframework.format.annotation.DateTimeFormat;

import br.gov.pe.ses.starter.entidades.BaseEntity;
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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.Transient;
import jakarta.persistence.Version;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "usuario")
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1)
@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@ToString(of = "id")
@Audited(withModifiedFlag = true)
public class Usuario extends BaseEntity implements Serializable {

	private static final long serialVersionUID = 8635034142914987625L;

	@Id
	@Column(name = "id", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "usuario_seq")
	private Long id;

	@Column(name = "login", length = 14, nullable = false, unique = true)
	private String login;

	@Column(name = "senha", length = 20, nullable = false)
	private String senha;

	@Column(name = "codigo_redefinicao")
	private String codigoRedefinicao;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_unidade", nullable = false)
	private Unidade unidade;

	@ManyToOne(fetch = FetchType.EAGER, cascade = ALL)
	@JoinColumn(name = "id_pessoa", nullable = false)
	private Pessoa pessoa;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "validade_codigo_redefinicao")
	private Date validadeCodigoRedefinicao;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "perfil_usuario", joinColumns = {
			@JoinColumn(name = "id_usuario", referencedColumnName = "id") }, inverseJoinColumns = @JoinColumn(name = "id_perfil", referencedColumnName = "id"))
	private List<Perfil> perfis;

	@Transient
	private String emailConfirmacao;

	@Transient
	private String senhaConfirmacao;

	@NotAudited
	@Column(name = "reset_token")
	private String resetToken;

	@NotAudited
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_hora_exp_token")
	private LocalDateTime dataHoraExpToken;

	@NotAudited
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "dt_hora_reset_senha")
	private LocalDateTime dataHoraResetSenha;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_unidade", joinColumns = {
			@JoinColumn(name = "id_usuario", referencedColumnName = "id") }, inverseJoinColumns = @JoinColumn(name = "id_unidade", referencedColumnName = "id"))
	private List<Unidade> hospitaisAssociados = new ArrayList<Unidade>();

	@Version
	@NotAudited
	private Integer versao;

	public Usuario() {
		this.pessoa = new Pessoa();	
		perfis = new ArrayList<Perfil>();
		hospitaisAssociados = new ArrayList<Unidade>();

	}

	public boolean isNovo() {
		return id == null;
	}

	public String getStatusCss() {
		if (getAtivo() == false) {
			return "unqualified";
		}

		return "qualified";
	}

}
