-- public.funcionalidade definição

CREATE TABLE public.funcionalidade (
	id serial4 NOT NULL,
	nome varchar(50) NOT NULL,
	"label" varchar(50) NULL,
	exibir bool NOT NULL,
	id_funcionalidade int8 NOT NULL,
	CONSTRAINT pk_funcionalidade_id PRIMARY KEY (id)
);

-- public.macroregional definição

CREATE TABLE public.macroregional (
	id serial4 NOT NULL,
	nome varchar(10) NOT NULL,
	CONSTRAINT macroregional_pkey PRIMARY KEY (id)
);

-- public.geres definição

CREATE TABLE public.geres (
	id serial4 NOT NULL,
	nome varchar(10) NOT NULL,
	macroregional_id int4 NULL,
	CONSTRAINT geres_pkey PRIMARY KEY (id),
	CONSTRAINT geres_macroregional_id_fkey FOREIGN KEY (macroregional_id) REFERENCES public.macroregional(id)
);

-- public.municipio definição

CREATE TABLE public.municipio (
	id serial4 NOT NULL,
	nome varchar(100) NOT NULL,
	geres_id int4 NULL,
	CONSTRAINT municipio_pkey PRIMARY KEY (id),
	CONSTRAINT municipio_geres_id_fkey FOREIGN KEY (geres_id) REFERENCES public.geres(id)
);

-- public.usuario definição

CREATE TABLE public.usuario (
	id serial4 NOT NULL,
	login varchar(20) NOT NULL,
	senha varchar(255) NOT NULL,
	ativo bool DEFAULT false NOT NULL,
	validade_codigo_redefinicao timestamptz NULL,
	codigo_redefinicao varchar(255) NULL,
	id_unidade int8 NULL,
	id_pessoa int8 NULL,
	usuario_inclusao int8 NULL,
	usuario_alteracao int8 NULL,
	data_inclusao timestamp NULL,
	data_alteracao timestamp NULL,
	reset_token varchar(100) NULL,
	dt_hora_exp_token timestamp NULL,
	dt_hora_reset_senha timestamp NULL,
	versao int8 DEFAULT 0 NOT NULL,
	CONSTRAINT pk_usuario PRIMARY KEY (id),
	CONSTRAINT uk_usuario_login UNIQUE (login)
);

-- public.tipo_unidade definição

-- Drop table

-- DROP TABLE public.tipo_unidade;

CREATE TABLE public.tipo_unidade (
	id bigserial NOT NULL,
	ativo bool NULL,
	data_alteracao timestamp(6) NULL,
	data_inclusao timestamp(6) NULL,
	descricao varchar(255) NULL,
	versao int4 DEFAULT 0 NULL,
	usuario_alteracao int8 NULL,
	usuario_inclusao int8 NULL,
	CONSTRAINT tipo_unidade_pkey PRIMARY KEY (id),
	CONSTRAINT fk3eel6w1i62e9ai1ywjpmyc3oy FOREIGN KEY (usuario_alteracao) REFERENCES public.usuario(id),
	CONSTRAINT fktjmb4hcqvf5sotu9hrob9qwdm FOREIGN KEY (usuario_inclusao) REFERENCES public.usuario(id)
);

-- public.unidade definição

CREATE TABLE public.unidade (
	id serial4 NOT NULL,
	nome varchar(255) NOT NULL,
	cnpj varchar(255) NULL,
	sigla varchar(255) NULL,
	ativo bool DEFAULT true NOT NULL,
	usuario_inclusao int8 NULL,
	usuario_alteracao int8 NULL,
	data_inclusao timestamp NULL,
	data_alteracao timestamp NULL,
	id_municipio int8 NULL,
	id_tipo int8 NULL,
	versao int8 DEFAULT 0 NOT NULL,
	CONSTRAINT pk_unidade PRIMARY KEY (id),
	CONSTRAINT unidade_municipio_fk FOREIGN KEY (id_municipio) REFERENCES public.municipio(id),
	CONSTRAINT unidade_usuario_fk FOREIGN KEY (usuario_inclusao) REFERENCES public.usuario(id),
	CONSTRAINT unidadel_usuario_fk_1 FOREIGN KEY (usuario_alteracao) REFERENCES public.usuario(id),
	CONSTRAINT unidadel_tipo_fk_1 FOREIGN KEY (id_tipo) REFERENCES public.tipo_unidade(id)
);

-- public.perfil definição

CREATE TABLE public.perfil (
	id serial4 NOT NULL,
	nome varchar(50) NOT NULL,
	ativo bool DEFAULT false NOT NULL,
	id_unidade int8 NULL,
	versao int8 DEFAULT 0 NOT NULL,
	CONSTRAINT pk_perfil_id PRIMARY KEY (id),
	CONSTRAINT perfil_unidade_fk FOREIGN KEY (id_unidade) REFERENCES public.unidade(id)
);


-- public.perfil_funcionalidade definição

CREATE TABLE public.perfil_funcionalidade (
	id serial4 NOT NULL,
	id_perfil int8 NOT NULL,
	id_funcionalidade int8 NOT NULL,
	CONSTRAINT pk_perfil_funcionalidade PRIMARY KEY (id),
	CONSTRAINT uq_perfil_funcionalidade UNIQUE (id_perfil, id_funcionalidade)
);

-- public.usuario_ definição

CREATE TABLE public.usuario_unidade (
	id serial4 NOT NULL,
	id_usuario int8 NOT NULL,
	id_unidade int8 NOT NULL,
	CONSTRAINT usuario_unidade_unique UNIQUE (id_usuario, id_unidade),
	CONSTRAINT usuario_unidade_fk FOREIGN KEY (id_unidade) REFERENCES public.unidade(id),
	CONSTRAINT usuario_unidade_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id)
);

-- public.perfil_usuario definição

CREATE TABLE public.perfil_usuario (
	id_usuario int8 NOT NULL,
	id_perfil int8 NOT NULL,
	CONSTRAINT uq_pefil UNIQUE (id_usuario, id_perfil),
	CONSTRAINT fk_perfil FOREIGN KEY (id_perfil) REFERENCES public.perfil(id) MATCH FULL,
	CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id) MATCH FULL
);

CREATE TABLE public.pessoa (
	id serial4 NOT NULL,
	nome varchar(255) NOT NULL,
	cpf varchar(20) NULL,
	email varchar(100) NULL,
	id_unidade_cadastro int8 NULL,
	versao int DEFAULT 0 NULL,
	ativo boolean NULL,
	usuario_inclusao int8 NULL,
	usuario_alteracao int8 NULL,
	data_inclusao timestamp NULL,
	data_alteracao timestamp NULL,
	data_nascimento date NULL,
	CONSTRAINT pessoa_usuario_fk FOREIGN KEY (usuario_inclusao) REFERENCES public.usuario(id),
	CONSTRAINT pessoa_usuario_fk_1 FOREIGN KEY (usuario_alteracao) REFERENCES public.usuario(id)
);


-- public.dados_sistema definição

-- Drop table

-- DROP TABLE public.dados_sistema;

CREATE TABLE public.dados_sistema (
	id bigserial NOT NULL,
	ativo bool NULL,
	data_alteracao timestamp(6) NULL,
	data_inclusao timestamp(6) NULL,
	logo_principal bytea NULL,
	logo_rodape bytea NULL,
    manual bytea NULL,
	nome varchar(255) NULL,
	sigla varchar(255) NULL,
	versao int4 DEFAULT 0 NULL,
	usuario_alteracao int8 NULL,
	usuario_inclusao int8 NULL,
	CONSTRAINT dados_sistema_pkey PRIMARY KEY (id),
	CONSTRAINT fk38bvqxw5a7jdej1bmndgf8wxy FOREIGN KEY (usuario_alteracao) REFERENCES public.usuario(id),
	CONSTRAINT fkjtm8tjhmraq46ngcp5qio36kg FOREIGN KEY (usuario_inclusao) REFERENCES public.usuario(id)
);



INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(1, 'ROLE_CONFIGURACAO', 'Configurações', true, 1);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(2, 'ROLE_USUARIO', 'Usuarios', true, 2);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(3, 'ROLE_USUARIO_INCLUIR', 'Incluir Usuario', true, 2);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(4, 'ROLE_USUARIO_ALTERAR', 'Alterar Usuario', true, 2);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(5, 'ROLE_USUARIO_EXCLUIR', 'Excluir Usuario', true, 2);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(6, 'ROLE_USUARIO_ATIVAR', 'Ativar Usuario', true, 2);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(7, 'ROLE_USUARIO_VISUALIZAR', 'Visualizar Usuario', true, 2);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(8, 'ROLE_PERFIL', 'Perfis', true, 8);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(9, 'ROLE_PERFIL_INCLUIR', 'Incluir Perfil', true, 8);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(10, 'ROLE_PERFIL_ALTERAR', 'Alterar Perfil', true, 8);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(11, 'ROLE_PERFIL_EXCLUIR', 'Excluir Perfil', true, 8);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(12, 'ROLE_PERFIL_ATIVAR', 'Ativar Perfil', true, 8);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(13, 'ROLE_PERFIL_VISUALIZAR', 'Visualizar Perfil', true, 8);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(14, 'ROLE_RELATORIO', 'Relatórios', true, 14);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(21, 'ROLE_PACIENTE', 'Pacientes', true, 21);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(22, 'ROLE_PACIENTE_INCLUIR', 'Incluir Paciente', true, 21);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(23, 'ROLE_PACIENTE_ALTERAR', 'Alterar Paciente', true, 21);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(24, 'ROLE_PACIENTE_EXCLUIR', 'Excluir Paciente', true, 21);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(25, 'ROLE_PACIENTE_VISUALIZAR', 'Visualizar Paciente', true, 21);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(26, 'ROLE_UNIDADE', 'Unidades', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(27, 'ROLE_UNIDADE_INCLUIR', 'Incluir Unidade', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(28, 'ROLE_UNIDADE_ALTERAR', 'Alterar Unidade', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(29, 'ROLE_UNIDADE_EXCLUIR', 'Excluir Unidade', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(43, 'ROLE_TIPO_UNIDADE', 'Tipo de Unidade', true, 43);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(44, 'ROLE_TIPO_UNIDADE_INCLUIR', 'Incluir Tipo de Unidade', true, 43);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(45, 'ROLE_TIPO_UNIDADE_ALTERAR', 'Alterar Tipo de Unidade', true, 43);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(46, 'ROLE_TIPO_UNIDADE__EXCLUIR', 'Excluir Tipo de Unidade', true, 43);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(47, 'ROLE_TIPO_UNIDADE__VISUALIZAR', 'Visualizar Tipo de Unidade', true, 43);

INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(48, 'ROLE_CONFIGURACAO_SISTEMA', 'Configurar Sistema', true, 1);

INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(49, 'ROLE_RELATORIO_USUARIOS_CADASTRADOS', 'Usuarios Cadastrados', true, 14);

INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(30, 'ROLE_UNIDADE_VISUALIZAR', 'Visualizar Unidade', true, 26);

INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(50, 'ROLE_MONITORA_SISTEMA', 'Monitorar Sistema', true, 1);

INSERT INTO public.macroregional
(id, nome)
VALUES(1, 'I');
INSERT INTO public.macroregional
(id, nome)
VALUES(2, 'II');
INSERT INTO public.macroregional
(id, nome)
VALUES(3, 'III');
INSERT INTO public.macroregional
(id, nome)
VALUES(4, 'IV');


INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(1, 'I GERES', 1);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(2, 'II GERES', 1);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(3, 'III GERES', 1);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(4, 'IV GERES', 2);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(5, 'V GERES', 2);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(6, 'VI GERES', 3);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(7, 'VII GERES', 4);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(8, 'VIII GERES', 4);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(9, 'IX GERES', 4);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(10, 'X GERES', 3);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(11, 'XI GERES', 3);
INSERT INTO public.geres
(id, nome, macroregional_id)
VALUES(12, 'XII GERES', 1);

INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(1, 'Abreu e Lima', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(2, 'Araçoiaba', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(3, 'Cabo de Santo Agostinho', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(4, 'Camaragibe', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(5, 'Chã de Alegria', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(6, 'Chã Grande', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(7, 'Fernando de Noronha', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(8, 'Glória de Goitá', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(9, 'Igarassu', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(10, 'Ipojuca', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(11, 'Itamaracá', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(12, 'Itapissuma', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(13, 'Jaboatão dos Guararapes', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(14, 'Moreno', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(15, 'Olinda', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(16, 'Paulista', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(17, 'Pombos', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(18, 'Recife', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(19, 'São Lourenço da Mata', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(20, 'Vitória de Santo Antão', 1);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(21, 'Bom Jardim', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(22, 'Buenos Aires', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(23, 'Carpina', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(24, 'Casinhas', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(25, 'Cumaru', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(26, 'Feira Nova', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(27, 'João Alfredo', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(29, 'Lagoa do Carro', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(30, 'Limoeiro', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(31, 'Machados', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(32, 'Nazaré da Mata', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(34, 'Passira', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(35, 'Paudalho', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(36, 'Salgadinho', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(37, 'Surubim', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(38, 'Tracunhaém', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(39, 'Vertente do Lério', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(40, 'Vicência', 2);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(41, 'Água Preta', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(42, 'Amaraji', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(43, 'Barreiros', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(44, 'Belém de Maria', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(45, 'Catende', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(46, 'Cortês', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(47, 'Escada', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(48, 'Gameleira', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(49, 'Jaqueira', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(50, 'Joaquim Nabuco', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(51, 'Lagoa dos Gatos', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(53, 'Palmares', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(54, 'Primavera', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(55, 'Quipapá', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(56, 'Ribeirão', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(57, 'Rio Formoso', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(58, 'São Benedito do Sul', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(59, 'São José da Coroa Grande', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(60, 'Sirinhaém', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(61, 'Tamandaré', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(62, 'Xexéu', 3);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(63, 'Aliança', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(64, 'Camutanga', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(65, 'Condado', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(66, 'Ferreiros', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(67, 'Goiana', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(68, 'Itambé', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(69, 'Itaquitinga', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(70, 'Macaparana', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(71, 'São Vicente Ferrer', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(72, 'Timbaúba', 12);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(73, 'Agrestina', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(74, 'Alagoinha', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(75, 'Altinho', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(77, 'Belo Jardim', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(78, 'Bezerros', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(79, 'Bonito', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(80, 'Brejo da Madre de Deus', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(82, 'Camocim de São Felix', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(83, 'Caruaru', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(87, 'Ibirajuba', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(89, 'Jurema', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(90, 'Panelas', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(93, 'Riacho das Almas', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(94, 'Sairé', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(97, 'Santa Maria do Cambucá', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(98, 'São Bento do Una', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(103, 'Toritama', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(106, 'Angelim', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(108, 'Brejão', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(110, 'Calçados', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(111, 'Canhotinho', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(113, 'Correntes', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(115, 'Iati', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(116, 'Itaíba', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(117, 'Jucati', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(118, 'Jupi', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(119, 'Lagoa do Ouro', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(121, 'Palmeirina', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(123, 'Saloá', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(125, 'Terezinha', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(126, 'Arcoverde', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(130, 'Inajá', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(132, 'Manarí', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(134, 'Petrolândia', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(135, 'Sertânia', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(138, 'Venturosa', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(139, 'Afogados da Ingazeira', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(142, 'Iguaraci', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(143, 'Ingazeira', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(146, 'Santa Terezinha', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(148, 'Solidão', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(149, 'Tabira', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(150, 'Tuparetama', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(153, 'Carnaubeira da Penha', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(155, 'Floresta', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(159, 'Serra Talhada', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(160, 'Triunfo', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(161, 'Belém do São Francisco', 7);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(164, 'Salgueiro', 7);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(167, 'Verdejante', 7);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(174, 'Santa Maria da Boa Vista', 8);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(178, 'Granito', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(179, 'Ipubi', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(180, 'Moreilândia', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(181, 'Ouricuri', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(183, 'Santa Cruz', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(189, 'Barra de Guabiraba', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(194, 'Cachoeirinha', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(197, 'Cupira', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(198, 'Frei Miguelinho', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(199, 'Gravatá', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(201, 'Jataúba', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(204, 'Pesqueira', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(205, 'Poção', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(208, 'Sanharó', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(209, 'Santa Cruz do Capibaribe', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(212, 'São Caetano', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(213, 'São Joaquim do Monte', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(214, 'Tacaimbó', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(215, 'Taquaritinga do Norte', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(217, 'Vertentes', 4);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(218, 'Águas Belas', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(220, 'Bom Conselho', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(222, 'Caetés', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(225, 'Capoeiras', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(227, 'Garanhuns', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(233, 'Lajedo', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(235, 'Paranatama', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(237, 'São João', 5);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(240, 'Buíque', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(241, 'Custódia', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(242, 'Ibimirim', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(244, 'Jatobá', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(246, 'Pedra', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(249, 'Tacaratu', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(250, 'Tupanatinga', 6);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(253, 'Brejinho', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(254, 'Carnaíba', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(257, 'Itapetim', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(258, 'Quixaba', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(260, 'São José do Egito', 10);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(264, 'Betânia', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(265, 'Calumbi', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(267, 'Flores', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(269, 'Itacuruba', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(270, 'Santa Cruz da Baixa Verde', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(271, 'São José do Belmonte', 11);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(275, 'Cedro', 7);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(276, 'Mirandiba', 7);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(278, 'Serrita', 7);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(279, 'Terra Nova', 7);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(281, 'Afrânio', 8);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(282, 'Cabrobó', 8);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(283, 'Dormentes', 8);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(284, 'Lagoa Grande', 8);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(285, 'Orocó', 8);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(286, 'Petrolina', 8);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(288, 'Araripina', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(289, 'Bodocó', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(290, 'Exu', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(295, 'Parnamirim', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(297, 'Santa Filomena', 9);
INSERT INTO public.municipio
(id, nome, geres_id)
VALUES(298, 'Trindade', 9);


INSERT INTO public.pessoa
(id, nome, cpf, email, id_unidade_cadastro, versao)
VALUES(1, 'Admistrador Geral', '000.000.000-00', 'template@ses.pe.gov.br', 84, 0);

INSERT INTO public.usuario
(id, id_pessoa, login, senha, ativo, validade_codigo_redefinicao, codigo_redefinicao, id_unidade, usuario_inclusao, usuario_alteracao, data_inclusao, data_alteracao, reset_token, dt_hora_exp_token, dt_hora_reset_senha, versao)
VALUES(1, 1, 'estomia.admin', '$2a$12$xovrAHxFy5rLzIkwt9OIvOc0otMAIfPRp.GHIYCZSxhCEWeGplD6O', true, NULL, NULL, 84, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);

INSERT INTO public.tipo_unidade
(id, ativo, data_alteracao, data_inclusao, descricao, versao, usuario_alteracao, usuario_inclusao)
VALUES(1, true, '2025-06-20 11:39:52.662', '2025-06-20 11:39:52.662', 'Hospital', 0, NULL, 1);

INSERT INTO public.unidade
(id, nome, cnpj, sigla, ativo, usuario_inclusao, usuario_alteracao, data_inclusao, data_alteracao, id_municipio, versao, id_tipo)
VALUES(84, 'Hospital Barão de Lucena', '37.471.854/0001-66', 'HBL', true, 1, 1, '2024-08-22 08:50:42.428', '2024-09-13 16:29:25.854', 18, 0, 1);


INSERT INTO public.perfil
(id, nome, ativo, id_unidade, versao)
VALUES(1, 'Administrador', true, 84, 0);

INSERT INTO public.perfil_usuario
(id_usuario, id_perfil)
VALUES(1, 1);

INSERT INTO public.usuario_unidade
(id, id_usuario, id_unidade)
VALUES(1, 1, 84);

INSERT INTO public.perfil_funcionalidade (id_perfil, id_funcionalidade)
SELECT 1 AS id_perfil, f.id AS id_funcionalidade
FROM public.funcionalidade f
where f.id != f.id_funcionalidade;

CREATE SEQUENCE public.revinfo_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;
	
ALTER TABLE public.pessoa ADD CONSTRAINT pessoa_pk PRIMARY KEY (id);


INSERT INTO public.dados_sistema
(id, ativo, data_alteracao, data_inclusao, logo_principal, logo_rodape, nome, sigla, versao, usuario_alteracao, usuario_inclusao, manual)
VALUES(1, true, '2025-06-23 08:25:19.557', NULL, decode('3C7376672077696474683D2231363722206865696768743D223332222076696577426F783D2230203020313637203332222066696C6C3D226E6F6E652220786D6C6E733D22687474703A2F2F7777772E77332E6F72672F323030302F737667223E0A3C7061746820643D224D37362E3534363920352E32355632302E373831324337362E353336352032322E333132352037362E313937392032332E363435382037352E353331322032342E373831324337342E383634362032352E393136372037332E393231392032362E373931372037322E373033312032372E343036324337312E343934382032382E303130342037302E313034322032382E333132352036382E353331322032382E333132354336362E313335342032382E333132352036342E323133352032372E363631352036322E373635362032362E333539344336312E333238312032352E303436392036302E353833332032332E323334342036302E353331322032302E3932313956352E32354836322E343337355632302E363430364336322E343337352032322E353537332036322E393834342032342E303436392036342E303738312032352E313039344336352E313731392032362E313631352036362E363536322032362E363837352036382E353331322032362E363837354337302E343036322032362E363837352037312E383835342032362E313536322037322E393638382032352E303933384337342E303632352032342E303331322037342E363039342032322E353532312037342E363039342032302E3635363256352E32354837362E353436395A222066696C6C3D227768697465222F3E0A3C7061746820643D224D38332E383433382032362E3337354839352E303738315632384838312E3930363256352E32354838332E383433385632362E3337355A222066696C6C3D227768697465222F3E0A3C7061746820643D224D3130392E39303620362E3839303632483130322E313039563238483130302E31383856362E38393036324839322E3430363256352E3235483130392E39303656362E38393036325A222066696C6C3D227768697465222F3E0A3C7061746820643D224D3131352E393338203238483131342E30313656352E3235483131352E3933385632385A222066696C6C3D227768697465222F3E0A3C7061746820643D224D3132342E36373220352E32354C3133332E3036322032352E333238314C3134312E34383420352E3235483134342E303437563238483134322E3132355631382E303933384C3134322E32383120372E393337354C3133332E383132203238483133322E3332384C3132332E38393120382E30313536324C3132342E3034372031382E30333132563238483132322E31323556352E3235483132342E3637325A222066696C6C3D227768697465222F3E0A3C7061746820643D224D3136322E3337352032312E363235483135322E3034374C3134392E373033203238483134372E3638384C3135362E32393720352E3235483135382E3132354C3136362E373334203238483136342E3733344C3136322E3337352032312E3632355A4D3135322E3634312031392E39383434483136312E3736364C3135372E32303320372E35393337354C3135322E3634312031392E393834345A222066696C6C3D227768697465222F3E0A3C706174682066696C6C2D72756C653D226576656E6F64642220636C69702D72756C653D226576656E6F64642220643D224D33392E3937303920382E35373031374333392E3931313220382E34323732392033392E3737313720382E33333438342033392E36313720382E33333438344833372E353039334C33382E36353720362E39353033374333382E37353220362E38333635332033382E3737313120362E36373833372033382E3730383320362E35343436364333382E3634353520362E34313039352033382E3531303620362E33323533372033382E33363220362E33323533374831352E323332364C382E363733363120302E313035313843382E3534363433202D302E3031343737363620382E3335333336202D302E3033343634323120382E323036323520302E303538353732394C342E353236343320322E33363232314C342E353233333620322E33363532364C342E353139353320322E33363637394C302E313733313520352E323139373843302E31363234323420352E323236363620302E31353730363120352E323337333520302E31343731303120352E323435373643302E31323536343920352E323632353720302E31303732363120352E323830393120302E3039303430353920352E333031353443302E3037353038323920352E333139383720302E3036323832343520352E333338323120302E3035313333323220352E333538383443302E3033393037333820352E33383120302E3033303634363120352E343033313620302E3032323938343620352E343236383443302E30313533323320352E343531323920302E3031303732363120352E343735373420302E303037363631353220352E353031373243302E303036383935333720352E3531343731203020352E35323534203020352E3533393136433020352E353531333820302E303035333633303620352E353630353520302E303036383935333720352E353732373843302E303039313933383220352E353937393920302E3031343535363920352E363231363820302E3032323231383420352E363436383943302E3032393837393920352E3637323120302E3033383330373620352E363935373920302E3035313333323220352E373139343843302E3035363639353220352E373239343120302E3035373436313420352E373430313120302E3036333539303620352E373530303443302E3036383935333720352E373538343420302E3037383931333720352E3736313520302E3038353034323920352E373639313443302E31313138353820352E383033353220302E313433323720352E383331373920302E31383030343620352E383534373143302E31393338333620352E383633313220302E32303435363320352E383733383220302E32313833353320352E383830363943302E32363839313920352E393035313420302E33323430383220352E393231313920302E33383338343220352E393231313948352E36313734334C362E373938383320382E38363035324C362E383030333620382E383632383156382E383635314C31342E343836342032372E323239324C392E39343932352033312E3333353343392E38333230332033312E3434313520392E37393231392033312E36303820392E38343936352033312E3735353543392E39303633352033312E3930332031302E303438392033322031302E323036372033324831362E373338394331362E373839352033322031362E383339332033312E393839332031362E3838362033312E393730324331362E393332372033312E393531312031362E393734392033312E393232382031372E303130312033312E383837374C32302E3434342032382E343530324332302E343438362032382E343535352032302E343530312032382E343632342032302E343535352032382E343637384C32332E3730372033312E383830384332332E373739382033312E3935382032332E383735362033312E393739342032332E393836372033324332392E383839392033312E393637312033382E383436322033312E393337332033392E323637362033312E393830394333392E333037342033312E393933392033392E3334382033322033392E333838362033324333392E353139372033322033392E363437362033312E393333352033392E373138392033312E383138324333392E393430332033312E3435392033392E393338372033312E343535322032382E363236352032302E323630334C33392E3838373420382E39383838384333392E3939373720382E38373733332034302E3032393920382E37313330352033392E3937303920382E35373031375A4D33372E3534383320372E30383836374C33362E3531363320382E33333438344831372E333531384C31362E3033373920372E30383836374833372E353438335A4D382E333631373820302E3836323336314C31362E3234303920382E333334383448372E34313332394C352E323034343720322E38333839384C382E333631373820302E3836323336315A4D312E363631373820352E31353633364C342E353438363420332E323631354C352E3331303220352E313536333648312E36363137385A4D32312E303130322032372E393430364332312E303030322032372E393239392032302E393836342032372E393235332032302E393735372032372E393136394C32332E373230312032352E313730315633302E373834344C32312E303130322032372E393430365A4D33382E353034352033312E3231334333372E3035382033312E313931362033332E3436342033312E313832352032342E343836322033312E323332315632342E363531334332342E343836322032342E3538312032342E343631372032342E353138332032342E343238382032342E3436314C32382E303836342032302E373939374333312E363933342032342E333730312033362E373939382032392E343531392033382E353034352033312E3231335A4D31362E383732322033302E393432354C372E373238393420392E303938394833362E363030364333362E3632383220392E31303530312033362E3635353820392E31313334322033362E3638333420392E31313334324333362E3730353620392E31313334322033362E3732363320392E31303237322033362E3734383520392E303938394833382E363934354C31362E383732322033302E393432355A222066696C6C3D227768697465222F3E0A3C2F7376673E0A','hex'), decode('3C7376672077696474683D2231363822206865696768743D223338222076696577426F783D2230203020313638203338222066696C6C3D226E6F6E652220786D6C6E733D22687474703A2F2F7777772E77332E6F72672F323030302F737667223E0A3C7061746820643D224D37362E3534363920372E32355632322E373831324337362E353336352032342E333132352037362E313937392032352E363435382037352E353331322032362E373831324337342E383634362032372E393136372037332E393231392032382E373931372037322E373033312032392E343036324337312E343934382033302E303130342037302E313034322033302E333132352036382E353331322033302E333132354336362E313335342033302E333132352036342E323133352032392E363631352036322E373635362032382E333539344336312E333238312032372E303436392036302E353833332032352E323334342036302E353331322032322E3932313956372E32354836322E343337355632322E363430364336322E343337352032342E353537332036322E393834342032362E303436392036342E303738312032372E313039344336352E313731392032382E313631352036362E363536322032382E363837352036382E353331322032382E363837354337302E343036322032382E363837352037312E383835342032382E313536322037322E393638382032372E303933384337342E303632352032362E303331322037342E363039342032342E353532312037342E363039342032322E3635363256372E32354837362E353436395A4D38332E383433382032382E3337354839352E303738315633304838312E3930363256372E32354838332E383433385632382E3337355A4D3130392E39303620382E3839303632483130322E313039563330483130302E31383856382E38393036324839322E3430363256372E3235483130392E39303656382E38393036325A4D3131352E393338203330483131342E30313656372E3235483131352E3933385633305A4D3132342E36373220372E32354C3133332E3036322032372E333238314C3134312E34383420372E3235483134342E303437563330483134322E3132355632302E303933384C3134322E32383120392E393337354C3133332E383132203330483133322E3332384C3132332E3839312031302E303135364C3132342E3034372032302E30333132563330483132322E31323556372E3235483132342E3637325A4D3136322E3337352032332E363235483135322E3034374C3134392E373033203330483134372E3638384C3135362E32393720372E3235483135382E3132354C3136362E373334203330483136342E3733344C3136322E3337352032332E3632355A4D3135322E3634312032312E39383434483136312E3736364C3135372E32303320392E35393337354C3135322E3634312032312E393834345A222066696C6C3D2223323132313231222F3E0A3C706174682066696C6C2D72756C653D226576656E6F64642220636C69702D72756C653D226576656E6F64642220643D224D33392E393730392031302E353730324333392E393131322031302E343237332033392E373731372031302E333334382033392E3631372031302E333334384833372E353039334C33382E36353720382E39353033374333382E37353220382E38333635332033382E3737313120382E36373833372033382E3730383320382E35343436364333382E3634353520382E34313039352033382E3531303620382E33323533372033382E33363220382E33323533374831352E323332364C382E363733363120322E313035313843382E353436343320312E393835323220382E333533333620312E393635333620382E323036323520322E30353835374C342E353236343320342E33363232314C342E353233333620342E33363532364C342E353139353320342E33363637394C302E313733313520372E323139373843302E31363234323420372E323236363620302E31353730363120372E323337333520302E31343731303120372E323435373643302E31323536343920372E323632353720302E31303732363120372E323830393120302E3039303430353920372E333031353443302E3037353038323920372E333139383720302E3036323832343520372E333338323120302E3035313333323220372E333538383443302E3033393037333820372E33383120302E3033303634363120372E343033313620302E3032323938343620372E343236383443302E30313533323320372E343531323920302E3031303732363120372E343735373420302E303037363631353220372E353031373243302E303036383935333720372E3531343731203020372E35323534203020372E3533393136433020372E353531333820302E303035333633303620372E353630353520302E303036383935333720372E353732373843302E303039313933383220372E353937393920302E3031343535363920372E363231363820302E3032323231383420372E363436383943302E3032393837393920372E3637323120302E3033383330373620372E363935373920302E3035313333323220372E373139343843302E3035363639353220372E373239343120302E3035373436313420372E373430313120302E3036333539303620372E373530303443302E3036383935333720372E373538343420302E3037383931333720372E3736313520302E3038353034323920372E373639313443302E31313138353820372E383033353220302E313433323720372E383331373920302E31383030343620372E383534373143302E31393338333620372E383633313220302E32303435363320372E383733383220302E32313833353320372E383830363943302E32363839313920372E393035313420302E33323430383220372E393231313920302E33383338343220372E393231313948352E36313734334C362E37393838332031302E383630354C362E38303033362031302E383632385631302E383635314C31342E343836342032392E323239324C392E39343932352033332E3333353343392E38333230332033332E3434313520392E37393231392033332E36303820392E38343936352033332E3735353543392E39303633352033332E3930332031302E303438392033342031302E323036372033344831362E373338394331362E373839352033342031362E383339332033332E393839332031362E3838362033332E393730324331362E393332372033332E393531312031362E393734392033332E393232382031372E303130312033332E383837374C32302E3434342033302E343530324332302E343438362033302E343535352032302E343530312033302E343632342032302E343535352033302E343637384C32332E3730372033332E383830384332332E373739382033332E3935382032332E383735362033332E393739342032332E393836372033344332392E383839392033332E393637312033382E383436322033332E393337332033392E323637362033332E393830394333392E333037342033332E393933392033392E3334382033342033392E333838362033344333392E353139372033342033392E363437362033332E393333352033392E373138392033332E383138324333392E393430332033332E3435392033392E393338372033332E343535322032382E363236352032322E323630334C33392E383837342031302E393838394333392E393937372031302E383737332034302E303239392031302E373133312033392E393730392031302E353730325A4D33372E3534383320392E30383836374C33362E353136332031302E333334384831372E333531384C31362E3033373920392E30383836374833372E353438335A4D382E333631373820322E38363233364C31362E323430392031302E3333343848372E34313332394C352E323034343720342E38333839384C382E333631373820322E38363233365A4D312E363631373820372E31353633364C342E353438363420352E323631354C352E3331303220372E313536333648312E36363137385A4D32312E303130322032392E393430364332312E303030322032392E393239392032302E393836342032392E393235332032302E393735372032392E393136394C32332E373230312032372E313730315633322E373834344C32312E303130322032392E393430365A4D33382E353034352033332E3231334333372E3035382033332E313931362033332E3436342033332E313832352032342E343836322033332E323332315632362E363531334332342E343836322032362E3538312032342E343631372032362E353138332032342E343238382032362E3436314C32382E303836342032322E373939374333312E363933342032362E333730312033362E373939382033312E343531392033382E353034352033332E3231335A4D31362E383732322033322E393432354C372E37323839342031312E303938394833362E363030364333362E363238322031312E3130352033362E363535382031312E313133342033362E363833342031312E313133344333362E373035362031312E313133342033362E373236332031312E313032372033362E373438352031312E303938394833382E363934354C31362E383732322033322E393432355A222066696C6C3D2223323132313231222F3E0A3C2F7376673E0A','hex'), 'Starter SES', 'Starter', 1, 1, NULL, decode('255044462D312E330A25938C8B9E205265706F72744C61622047656E6572617465642050444620646F63756D656E7420687474703A2F2F7777772E7265706F72746C61622E636F6D0A312030206F626A0A3C3C0A2F4631203220302052202F46322033203020520A3E3E0A656E646F626A0A322030206F626A0A3C3C0A2F42617365466F6E74202F48656C766574696361202F456E636F64696E67202F57696E416E7369456E636F64696E67202F4E616D65202F4631202F53756274797065202F5479706531202F54797065202F466F6E740A3E3E0A656E646F626A0A332030206F626A0A3C3C0A2F42617365466F6E74202F48656C7665746963612D426F6C64202F456E636F64696E67202F57696E416E7369456E636F64696E67202F4E616D65202F4632202F53756274797065202F5479706531202F54797065202F466F6E740A3E3E0A656E646F626A0A342030206F626A0A3C3C0A2F436F6E74656E7473203820302052202F4D65646961426F78205B20302030203539352E32373536203834312E38383938205D202F506172656E74203720302052202F5265736F7572636573203C3C0A2F466F6E74203120302052202F50726F63536574205B202F504446202F54657874202F496D61676542202F496D61676543202F496D61676549205D0A3E3E202F526F746174652030202F5472616E73203C3C0A0A3E3E200A20202F54797065202F506167650A3E3E0A656E646F626A0A352030206F626A0A3C3C0A2F506167654D6F6465202F5573654E6F6E65202F5061676573203720302052202F54797065202F436174616C6F670A3E3E0A656E646F626A0A362030206F626A0A3C3C0A2F417574686F722028616E6F6E796D6F757329202F4372656174696F6E446174652028443A32303235303632333133333733352B30302730302729202F43726561746F7220285265706F72744C616220504446204C696272617279202D207777772E7265706F72746C61622E636F6D29202F4B6579776F726473202829202F4D6F64446174652028443A32303235303632333133333733352B30302730302729202F50726F647563657220285265706F72744C616220504446204C696272617279202D207777772E7265706F72746C61622E636F6D29200A20202F5375626A6563742028756E73706563696669656429202F5469746C652028756E7469746C656429202F54726170706564202F46616C73650A3E3E0A656E646F626A0A372030206F626A0A3C3C0A2F436F756E742031202F4B696473205B203420302052205D202F54797065202F50616765730A3E3E0A656E646F626A0A382030206F626A0A3C3C0A2F46696C746572205B202F415343494938354465636F6465202F466C6174654465636F6465205D202F4C656E677468203133320A3E3E0A73747265616D0A4761714C5030622F6523275350473A3B75454967584D214165356026704B64295023492C53685A5C5E4B21332E41732B55574142263A495A2D71412D32665944754D732C6B3F4533745E2C277023304D57572E323E3F276F213932304426276747242C4A6B687050292B3724456A4158266830323B31712153506D2D253C412331737E3E656E6473747265616D0A656E646F626A0A787265660A3020390A303030303030303030302036353533352066200A30303030303030303733203030303030206E200A30303030303030313134203030303030206E200A30303030303030323231203030303030206E200A30303030303030333333203030303030206E200A30303030303030353336203030303030206E200A30303030303030363034203030303030206E200A30303030303030393030203030303030206E200A30303030303030393539203030303030206E200A747261696C65720A3C3C0A2F4944200A5B3C39663134306662346164616432393432306531613136313961333636643639623E3C39663134306662346164616432393432306531613136313961333636643639623E5D0A25205265706F72744C61622067656E6572617465642050444620646F63756D656E74202D2D206469676573742028687474703A2F2F7777772E7265706F72746C61622E636F6D290A0A2F496E666F2036203020520A2F526F6F742035203020520A2F53697A6520390A3E3E0A7374617274787265660A313138310A2525454F460A','hex'));

CREATE TABLE public.paciente (
	id bigserial NOT NULL,
	ativo bool NULL,
	data_alteracao timestamp(6) NULL,
	data_inclusao timestamp(6) NULL,
	cartao_sus varchar(40) NULL,
	prontuario varchar(20) NULL,
	versao int4 NULL,
	usuario_alteracao int8 NULL,
	usuario_inclusao int8 NULL,
	id_pessoa int8 NOT NULL,
	id_unidade_cadastro int8 NOT NULL,
	CONSTRAINT paciente_pkey PRIMARY KEY (id),
	CONSTRAINT fk8evp076vfhbugdqoturver2o2 FOREIGN KEY (usuario_alteracao) REFERENCES public.usuario(id),
	CONSTRAINT fkco20m2pyvnegvt15yt7ru3gu6 FOREIGN KEY (id_unidade_cadastro) REFERENCES public.unidade(id),
	CONSTRAINT fkhkgprbh872qgl2c1sjx8rs2b2 FOREIGN KEY (usuario_inclusao) REFERENCES public.usuario(id),
	CONSTRAINT fkr7gw7jr8c8fcg5cdjugjpkp8k FOREIGN KEY (id_pessoa) REFERENCES public.pessoa(id)
);
