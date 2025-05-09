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
	nome varchar(100) NOT NULL,
	email varchar(100) NOT NULL,
	ativo bool DEFAULT false NOT NULL,
	validade_codigo_redefinicao timestamptz NULL,
	codigo_redefinicao varchar(255) NULL,
	id_hospital int8 NULL,
	cpf varchar(30) NULL,
	usuario_inclusao int8 NULL,
	usuario_alteracao int8 NULL,
	data_inclusao timestamp NULL,
	data_alteracao timestamp NULL,
	reset_token varchar(100) NULL,
	dt_hora_exp_token timestamp NULL,
	dt_hora_reset_senha timestamp NULL,
	versao int8 DEFAULT 0 NOT NULL,
	CONSTRAINT cpf_unique UNIQUE (cpf),
	CONSTRAINT pk_usuario PRIMARY KEY (id),
	CONSTRAINT uk_usuario_email UNIQUE (email),
	CONSTRAINT uk_usuario_login UNIQUE (login)
);

-- public.hospital definição

CREATE TABLE public.hospital (
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
	versao int8 DEFAULT 0 NOT NULL,
	CONSTRAINT pk_hospital PRIMARY KEY (id),
	CONSTRAINT hospital_municipio_fk FOREIGN KEY (id_municipio) REFERENCES public.municipio(id),
	CONSTRAINT hospital_usuario_fk FOREIGN KEY (usuario_inclusao) REFERENCES public.usuario(id),
	CONSTRAINT hospital_usuario_fk_1 FOREIGN KEY (usuario_alteracao) REFERENCES public.usuario(id)
);

-- public.perfil definição

CREATE TABLE public.perfil (
	id serial4 NOT NULL,
	nome varchar(50) NOT NULL,
	ativo bool DEFAULT false NOT NULL,
	id_hospital int8 NULL,
	versao int8 DEFAULT 0 NOT NULL,
	CONSTRAINT pk_perfil_id PRIMARY KEY (id),
	CONSTRAINT perfil_hospital_fk FOREIGN KEY (id_hospital) REFERENCES public.hospital(id)
);


-- public.perfil_funcionalidade definição

CREATE TABLE public.perfil_funcionalidade (
	id serial4 NOT NULL,
	id_perfil int8 NOT NULL,
	id_funcionalidade int8 NOT NULL,
	CONSTRAINT pk_perfil_funcionalidade PRIMARY KEY (id),
	CONSTRAINT uq_perfil_funcionalidade UNIQUE (id_perfil, id_funcionalidade)
);

-- public.usuario_hospital definição

CREATE TABLE public.usuario_hospital (
	id serial4 NOT NULL,
	id_usuario int8 NOT NULL,
	id_hospital int8 NOT NULL,
	CONSTRAINT usuario_hospital_unique UNIQUE (id_usuario, id_hospital),
	CONSTRAINT usuario_hospital_hospital_fk FOREIGN KEY (id_hospital) REFERENCES public.hospital(id),
	CONSTRAINT usuario_hospital_usuario_fk FOREIGN KEY (id_usuario) REFERENCES public.usuario(id)
);

-- public.perfil_usuario definição

CREATE TABLE public.perfil_usuario (
	id_usuario int8 NOT NULL,
	id_perfil int8 NOT NULL,
	CONSTRAINT uq_pefil UNIQUE (id_usuario, id_perfil),
	CONSTRAINT fk_perfil FOREIGN KEY (id_perfil) REFERENCES public.perfil(id) MATCH FULL,
	CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id) MATCH FULL
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
VALUES(26, 'ROLE_HOSPITAL', 'Hospitais', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(27, 'ROLE_HOSPITAL_INCLUIR', 'Incluir Hospital', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(28, 'ROLE_HOSPITAL_ALTERAR', 'Alterar Hospital', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(29, 'ROLE_HOSPITAL_EXCLUIR', 'Excluir Hospital', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(30, 'ROLE_HOSPITAL_ATIVAR', 'Ativar Hospital', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(33, 'ROLE_ESTOQUE_GERAL_INCLUIR', 'Incluir Produto no Estoque', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(34, 'ROLE_ESTOQUE_GERAL_ALTERAR', 'Alterar Produto do Estoque', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(35, 'ROLE_ESTOQUE_GERAL_EXCLUIR', 'Excluir Produto do Estoque', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(37, 'ROLE_ESTOQUE_GERAL_TRANSFERENCIA', 'Transferência de Estoque', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(39, 'ROLE_RELATORIO', 'Emitir Relatórios', true, 14);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(50, 'ROLE_ATENDIMENTO', 'Atendimento', true, 50);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(51, 'ROLE_INICIAR_ATENDIMENTO', 'Iniciar Atendimento', true, 50);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(53, 'ROLE_VISUALIZAR_ATENDIMENTO', 'Visualizar Atendimento', true, 50);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(54, 'ROLE_ALTERAR_ATENDIMENTO', 'Alterar Atendimento', true, 50);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(55, 'ROLE_EXCLUIR_ATENDIMENTO', 'Excluir Atendimento', true, 50);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(52, 'ROLE_INICIAR_ATENDIMENTO_EMERGENGIAL', 'Iniciar Atendimento Emergencial', true, 50);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(56, 'ROLE_INCLUIR_ATENDIMENTO', 'Incluir Atendimento', true, 50);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(57, 'ROLE_ESTOQUE_FARMACIA', 'Estoque da Farmácia', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(58, 'ROLE_ESTOQUE_FARMACIA_VISUALIZAR_ESTOQUE', 'Visualizar Estoque', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(59, 'ROLE_ESTOQUE_FARMACIA_SOLICITAR', 'Solicitar Transferência de Estoque', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(60, 'ROLE_ESTOQUE_FARMACIA_VISUALIZAR_SOLICITACAO', 'Visualizar Solicitações de Estoque', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(61, 'ROLE_ESTOQUE_FARMACIA_ALTERAR_SOLICITACAO', 'Alterar Solicitação de Estoque', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(32, 'ROLE_ESTOQUE_GERAL', 'Estoque Geral', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(36, 'ROLE_ESTOQUE_GERAL_VISUALIZAR', 'Visualizar Produtos em Estoque', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(64, 'ROLE_ESTOQUE_GERAL_VISUALIZAR_SOLICITACAO', 'Visualizar Solicitações da Farmácia', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(62, 'ROLE_ESTOQUE_FARMACIA_EXCLUIR_SOLICITACAO', 'Excluir Solicitação de Estoque', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(63, 'ROLE_ESTOQUE_GERAL_ATENDER_SOLICITACAO', 'Atender Solicitação da Farmácia', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(42, 'ROLE_LIBERAR_ESTOQUE_FARMACIA', 'Liberar Produtos', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(38, 'ROLE_ALTERA_CONFIGURACAO', 'Alterar Configurações', true, 1);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(65, 'ROLE_ESTOQUE_GERAL_NOTIFICACOES', 'Notificações', true, 32);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(66, 'ROLE_ESTOQUE_FARMACIA_NOTIFICACOES', 'Notificações', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(69, 'ROLE_ESTOQUE_FARMACIA_RESP_RETIRADA', 'Incluir CNPJ', true, 57);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(67, 'ROLE_MANUTENCAO', 'Manutenção', true, 67);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(68, 'ROLE_MANUTENCAO_REVERTER_ATENDIMENTOS', 'Reverter Atendimentos', true, 67);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(70, 'ROLE_MANUTENCAO_INVENTARIO_FARMACIA', 'Inventário da Farmácia', true, 67);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(71, 'ROLE_PACIENTE_VISUALIZAR_DADOS_SAUDE', 'Visualizar Dados de Saúde', true, 21);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(72, 'ROLE_PACIENTE_EDITAR_DADOS_SAUDE', 'Editar Dados de Saúde', true, 21);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(73, 'ROLE_MANUTENCAO_INVENTARIO_GERAL', 'Inventário do Estoque Geral', true, 67);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(31, 'ROLE_HOSPITAL_VISUALIZAR', 'Visualizar Hospital', true, 26);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(74, 'ROLE_RELATORIO_ATIVIDADES', 'Relatório de Atividades', true, 14);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(75, 'ROLE_PACIENTE_BOLSA_TESTE', 'Bolsas de Teste', true, 21);
INSERT INTO public.funcionalidade
(id, nome, "label", exibir, id_funcionalidade)
VALUES(76, 'ROLE_PACIENTE_TRANSFERENCIA', 'Transferir Pacientes', true, 21);

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



INSERT INTO public.usuario
(id, login, senha, nome, email, ativo, validade_codigo_redefinicao, codigo_redefinicao, id_hospital, cpf, usuario_inclusao, usuario_alteracao, data_inclusao, data_alteracao, reset_token, dt_hora_exp_token, dt_hora_reset_senha, versao)
VALUES(1, 'estomia.admin', '$2a$12$xovrAHxFy5rLzIkwt9OIvOc0otMAIfPRp.GHIYCZSxhCEWeGplD6O', 'Administrador Geral', 'noreply@saude.pe.gov.br', true, NULL, NULL, 84, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0);

INSERT INTO public.hospital
(id, nome, cnpj, sigla, ativo, usuario_inclusao, usuario_alteracao, data_inclusao, data_alteracao, id_municipio, versao)
VALUES(84, 'Hospital Barão de Lucena', '37.471.854/0001-66', 'HBL', true, 1, 1, '2024-08-22 08:50:42.428', '2024-09-13 16:29:25.854', 18, 0);


INSERT INTO public.perfil
(id, nome, ativo, id_hospital, versao)
VALUES(1, 'Administrador', true, 84, 0);

INSERT INTO public.perfil_usuario
(id_usuario, id_perfil)
VALUES(1, 1);

INSERT INTO public.usuario_hospital
(id, id_usuario, id_hospital)
VALUES(1, 1, 84);

INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3413, 1, 5);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3414, 1, 6);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3415, 1, 70);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3416, 1, 7);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3417, 1, 71);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3418, 1, 72);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3419, 1, 9);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3420, 1, 73);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3421, 1, 10);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3422, 1, 11);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3423, 1, 12);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3424, 1, 13);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3425, 1, 22);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3426, 1, 23);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3427, 1, 24);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3428, 1, 25);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3429, 1, 27);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3430, 1, 28);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3431, 1, 29);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3432, 1, 30);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3433, 1, 31);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3434, 1, 33);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3435, 1, 34);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3436, 1, 35);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3437, 1, 36);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3438, 1, 37);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3439, 1, 38);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3440, 1, 39);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3441, 1, 42);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3442, 1, 51);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3443, 1, 52);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3444, 1, 53);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3445, 1, 54);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3446, 1, 55);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3447, 1, 56);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3448, 1, 58);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3449, 1, 59);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3450, 1, 60);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3451, 1, 61);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3452, 1, 62);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3453, 1, 63);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3454, 1, 64);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3455, 1, 65);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3456, 1, 66);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3457, 1, 3);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3458, 1, 4);
INSERT INTO public.perfil_funcionalidade
(id, id_perfil, id_funcionalidade)
VALUES(3459, 1, 68);

CREATE SEQUENCE public.revinfo_seq
	INCREMENT BY 50
	MINVALUE 1
	MAXVALUE 9223372036854775807
	START 1
	CACHE 1
	NO CYCLE;

