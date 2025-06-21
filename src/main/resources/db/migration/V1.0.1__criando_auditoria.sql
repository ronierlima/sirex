-- DROP SCHEMA auditoria;

CREATE SCHEMA auditoria AUTHORIZATION postgres;

-- auditoria.revinfo definição

-- Drop table

-- DROP TABLE auditoria.revinfo;

CREATE TABLE auditoria.revinfo (
	id int4 NOT NULL,
	"timestamp" int8 NOT NULL,
	dt_hora_acao timestamp(6) NULL,
	usuario varchar(255) NULL,
	usuario_id int8 NULL,
	CONSTRAINT revinfo_pkey PRIMARY KEY (id)
);

-- auditoria.funcionalidade_aud definição

-- Drop table

-- DROP TABLE auditoria.funcionalidade_aud;

CREATE TABLE auditoria.funcionalidade_aud (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	exibir bool NULL,
	exibir_mod bool NULL,
	"label" varchar(50) NULL,
	label_mod bool NULL,
	nome varchar(50) NULL,
	nome_mod bool NULL,
	id_funcionalidade int8 NULL,
	funcionalidade_pai_mod bool NULL,
	CONSTRAINT funcionalidade_aud_pkey PRIMARY KEY (rev, id)
);

-- auditoria.unidade_aud definição

-- Drop table

-- DROP TABLE auditoria.unidade_aud;

CREATE TABLE auditoria.unidade_aud (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	ativo bool NULL,
	ativo_mod bool NULL,
	data_alteracao timestamp(6) NULL,
	data_alteracao_mod bool NULL,
	data_inclusao timestamp(6) NULL,
	data_inclusao_mod bool NULL,
	cnpj varchar(255) NULL,
	cnpj_mod bool NULL,
	nome varchar(255) NULL,
	nome_mod bool NULL,
	sigla varchar(255) NULL,
	sigla_mod bool NULL,
	id_tipo_unidade int8 NULL,
	id_tipo_unidade_mod bool NULL,
	usuario_alteracao int8 NULL,
	usuario_alteracao_mod bool NULL,
	usuario_inclusao int8 NULL,
	usuario_inclusao_mod bool NULL,
	CONSTRAINT unidade_aud_pkey PRIMARY KEY (rev, id)
);

-- auditoria.perfil_aud definição

-- Drop table

-- DROP TABLE auditoria.perfil_aud;

CREATE TABLE auditoria.perfil_aud (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	ativo bool NULL,
	nome varchar(50) NULL,
	ativo_mod bool NULL,
	nome_mod bool NULL,
	funcionalidades_mod bool NULL,
	CONSTRAINT perfil_aud_pkey PRIMARY KEY (rev, id)
);

-- auditoria.perfil_funcionalidade_aud definição

-- Drop table

-- DROP TABLE auditoria.perfil_funcionalidade_aud;

CREATE TABLE auditoria.perfil_funcionalidade_aud (
	rev int4 NOT NULL,
	id_perfil int8 NOT NULL,
	id_funcionalidade int8 NOT NULL,
	revtype int2 NULL,
	CONSTRAINT perfil_funcionalidade_aud_pkey PRIMARY KEY (id_perfil, rev, id_funcionalidade)
);

-- auditoria.perfil_usuario_aud definição

-- Drop table

-- DROP TABLE auditoria.perfil_usuario_aud;

CREATE TABLE auditoria.perfil_usuario_aud (
	id int8 NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	id_usuario int8 NOT NULL,
	id_usuario_mod bool NULL,
	id_perfil int8 NOT NULL,
	id_perfil_mod bool NULL,
	CONSTRAINT perfil_usuario_aud_pk PRIMARY KEY (id_usuario, rev, id_perfil)
);

-- auditoria.usuario_aud definição

-- Drop table

-- DROP TABLE auditoria.usuario_aud;

CREATE TABLE auditoria.usuario_aud (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	login varchar(20) NULL,
	login_mod bool NULL,
	senha varchar(255) NULL,
	senha_mod bool NULL,
	nome varchar(100) NULL,
	nome_mod bool NULL,
	email varchar(100) NULL,
	email_mod bool NULL,
	ativo bool NULL,
	ativo_mod bool NULL,
	codigo_redefinicao varchar(255) NULL,
	codigo_redefinicao_mod bool NULL,
	id_unidade int8 NULL,
	id_pessoa int8 NULL,
	cpf varchar(30) NULL,
	cpf_mod bool NULL,
	validade_codigo_redefinicao timestamp NULL,
	validade_codigo_redefinicao_mod bool NULL,
	unidade_mod bool NULL,
	pessoa_mod bool NULL,
	perfis_mod bool NULL,
	data_alteracao timestamp(6) NULL,
	data_alteracao_mod bool NULL,
	data_inclusao timestamp(6) NULL,
	data_inclusao_mod bool NULL,
	usuario_alteracao int8 NULL,
	usuario_alteracao_mod bool NULL,
	usuario_inclusao int8 NULL,
	usuario_inclusao_mod bool NULL,
	hospitais_associados_mod bool NULL,
	CONSTRAINT usuario_aud_pk PRIMARY KEY (rev, id)
);

-- auditoria.usuario_unidade_aud definição

-- Drop table

-- DROP TABLE auditoria.usuario_usuario_aud;

CREATE TABLE auditoria.usuario_unidade_aud (
	id int8 NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	id_usuario int8 NOT NULL,
	usuario_mod bool NULL,
	id_unidade int8 NOT NULL,
	unidade_mod bool NULL,
	CONSTRAINT usuario_unidade_aud_unique_id UNIQUE (rev, id_usuario, id_unidade)
);


-- auditoria.tipo_unidade_aud definição

-- Drop table

-- DROP TABLE auditoria.tipo_unidade_aud;

CREATE TABLE auditoria.tipo_unidade_aud (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	ativo bool NULL,
	ativo_mod bool NULL,
	data_alteracao timestamp(6) NULL,
	data_alteracao_mod bool NULL,
	data_inclusao timestamp(6) NULL,
	data_inclusao_mod bool NULL,
	descricao varchar(255) NULL,
	descricao_mod bool NULL,
	usuario_alteracao int8 NULL,
	usuario_alteracao_mod bool NULL,
	usuario_inclusao int8 NULL,
	usuario_inclusao_mod bool NULL,
	CONSTRAINT tipo_unidade_aud_pkey PRIMARY KEY (rev, id),
	CONSTRAINT fk3rbk9icqa17x0g67cdyr07d5j FOREIGN KEY (rev) REFERENCES auditoria.revinfo(id)
);

-- auditoria.pessoa_aud definição

-- Drop table

-- DROP TABLE auditoria.pessoa_aud;

CREATE TABLE auditoria.pessoa_aud (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	ativo bool NULL,
	ativo_mod bool NULL,
	data_alteracao timestamp(6) NULL,
	data_alteracao_mod bool NULL,
	data_inclusao timestamp(6) NULL,
	data_inclusao_mod bool NULL,
	cpf varchar(100) NULL,
	cpf_mod bool NULL,
	data_nascimento date NULL,
	data_nascimento_mod bool NULL,
	email varchar(100) NULL,
	email_mod bool NULL,
	nome varchar(100) NULL,
	nome_mod bool NULL,
	usuario_alteracao int8 NULL,
	usuario_alteracao_mod bool NULL,
	usuario_inclusao int8 NULL,
	usuario_inclusao_mod bool NULL,
	id_unidade_cadastro int8 NULL,
	unidade_cadastrada_mod bool NULL,
	CONSTRAINT pessoa_aud_pkey PRIMARY KEY (rev, id),
	CONSTRAINT fk2p4i11bp3x1ag2btxetrkagmh FOREIGN KEY (rev) REFERENCES auditoria.revinfo(id)
);

-- auditoria.dados_sistema_aud definição

-- Drop table

-- DROP TABLE auditoria.dados_sistema_aud;

CREATE TABLE auditoria.dados_sistema_aud (
	id int8 NOT NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	ativo bool NULL,
	ativo_mod bool NULL,
	data_alteracao timestamp(6) NULL,
	data_alteracao_mod bool NULL,
	data_inclusao timestamp(6) NULL,
	data_inclusao_mod bool NULL,
	logo_principal bytea NULL,
	logo_principal_mod bool NULL,
	logo_rodape bytea NULL,
	logo_rodape_mod bool NULL,
	nome varchar(255) NULL,
	nome_mod bool NULL,
	sigla varchar(255) NULL,
	sigla_mod bool NULL,
	usuario_alteracao int8 NULL,
	usuario_alteracao_mod bool NULL,
	usuario_inclusao int8 NULL,
	usuario_inclusao_mod bool NULL,
	CONSTRAINT dados_sistema_aud_pkey PRIMARY KEY (rev, id),
	CONSTRAINT fk1yds0a1g4xf61kpj44epp4wpg FOREIGN KEY (rev) REFERENCES auditoria.revinfo(id)
);