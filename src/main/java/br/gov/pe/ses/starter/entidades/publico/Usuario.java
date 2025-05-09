package br.gov.pe.ses.starter.entidades.publico;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.br.CPF;
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
import jakarta.validation.constraints.Email;
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

	@NotEmpty
	@Column(name = "nome", length = 100, nullable = false)
	private String nome;
	
	@CPF(message = "O CPF informado é inválido!")	
	@Column(name = "cpf", length = 100, nullable = false)
	private String cpf;

	@Email(message = "O email informado é inválido!")	
	@Column(name = "email", length = 100, nullable = false)
	private String email;

	@Column(name = "ativo", nullable = false)
	private boolean ativo;

	@Column(name = "codigo_redefinicao")
	private String codigoRedefinicao;

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name = "validade_codigo_redefinicao")
	private Date validadeCodigoRedefinicao;

	@NotEmpty
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "perfil_usuario", joinColumns = {
			@JoinColumn(name = "id_usuario", referencedColumnName = "id") }, inverseJoinColumns = @JoinColumn(name = "id_perfil", referencedColumnName = "id"))
	private List<Perfil> perfis;	

	@ManyToOne(fetch = FetchType.LAZY)	
	@JoinColumn(name = "id_hospital", nullable = false)
	private Hospital hospital;
	
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
	@JoinTable(name = "usuario_hospital", joinColumns = {
			@JoinColumn(name = "id_usuario", referencedColumnName = "id") }, inverseJoinColumns = @JoinColumn(name = "id_hospital", referencedColumnName = "id"))
	private List<Hospital> hospitaisAssociados = new ArrayList<Hospital>();
	
	@Version
	@NotAudited
	private Integer versao;
	
	
	
	
	public Usuario () {
		perfis = new ArrayList<Perfil>();
		hospitaisAssociados = new ArrayList<Hospital>();
		
	}	
	
	public boolean isNovo() {
		return id == null;
	}
	
	public String getStatusCss() {
		if (ativo == false) {
			return "unqualified";
		}

		return "qualified";
	}

	public void setNome(String nome) {
		this.nome = nome.toUpperCase();
	}
	
}
