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

-- auditoria.hospital_aud definição

-- Drop table

-- DROP TABLE auditoria.hospital_aud;

CREATE TABLE auditoria.hospital_aud (
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
	usuario_alteracao int8 NULL,
	usuario_alteracao_mod bool NULL,
	usuario_inclusao int8 NULL,
	usuario_inclusao_mod bool NULL,
	CONSTRAINT hospital_aud_pkey PRIMARY KEY (rev, id)
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
	id_hospital int8 NULL,
	cpf varchar(30) NULL,
	cpf_mod bool NULL,
	validade_codigo_redefinicao timestamp NULL,
	validade_codigo_redefinicao_mod bool NULL,
	hospital_mod bool NULL,
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

-- auditoria.usuario_hospital_aud definição

-- Drop table

-- DROP TABLE auditoria.usuario_hospital_aud;

CREATE TABLE auditoria.usuario_hospital_aud (
	id int8 NULL,
	rev int4 NOT NULL,
	revtype int2 NULL,
	id_usuario int8 NOT NULL,
	usuario_mod bool NULL,
	id_hospital int8 NOT NULL,
	hospital_mod bool NULL,
	CONSTRAINT usuario_hospital_aud_unique_id UNIQUE (rev, id_usuario, id_hospital)
);