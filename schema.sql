
    create table alunos (
        ativo bit not null,
        dia_vencimento integer not null,
        dt_nascimento date,
        serie integer,
        valor_mensalidade decimal(38,2) not null,
        sala varchar(5),
        created_at datetime(6) not null,
        fk_escola bigint not null,
        fk_transporte bigint,
        fk_usuario bigint not null,
        id_aluno bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(45) not null,
        professor varchar(45) not null,
        primary key (id_aluno)
    ) engine=InnoDB;

    create table despesas (
        valor_despesa decimal(38,2),
        created_at datetime(6) not null,
        data_despesa datetime(6),
        fk_transporte bigint not null,
        id_despesa bigint not null auto_increment,
        updated_at datetime(6) not null,
        descricao varchar(255),
        primary key (id_despesa)
    ) engine=InnoDB;

    create table enderecos (
        created_at datetime(6) not null,
        id_endereco bigint not null auto_increment,
        updated_at datetime(6) not null,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        primary key (id_endereco)
    ) engine=InnoDB;

    create table escolas (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_escola bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(255) not null,
        nivel_ensino enum ('CRECHE','ENSINO_FUNDAMENTAL','ENSINO_MEDIO','PRE_ESCOLA'),
        primary key (id_escola)
    ) engine=InnoDB;

    create table eventos (
        date date not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        priority varchar(20) not null,
        type varchar(20) not null,
        description TEXT,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table funcionarios (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_transporte bigint not null,
        fk_usuario bigint not null,
        id_funcionario bigint not null auto_increment,
        updated_at datetime(6) not null,
        cpf varchar(255),
        nome varchar(255) not null,
        primary key (id_funcionario)
    ) engine=InnoDB;

    create table mensalidades_aluno (
        ano integer not null,
        data_pagamento date,
        data_vencimento date not null,
        mes integer not null,
        valor_mensalidade decimal(38,2) not null,
        created_at datetime(6) not null,
        fk_aluno bigint not null,
        fk_pagamento bigint,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        status enum ('ATRASADO','CANCELADO','PAGO','PENDENTE') not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamentos (
        valor_pagamento decimal(38,2),
        created_at datetime(6) not null,
        data_pagamento datetime(6),
        fk_funcionario bigint not null,
        id_pagamento bigint not null auto_increment,
        updated_at datetime(6) not null,
        primary key (id_pagamento)
    ) engine=InnoDB;

    create table responsaveis (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_responsavel bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(9) not null,
        tel2 varchar(9),
        cpf varchar(14),
        nome varchar(45) not null,
        email varchar(255),
        primary key (id_responsavel)
    ) engine=InnoDB;

    create table responsavel_aluno (
        aluno_id bigint not null,
        responsavel_id bigint not null
    ) engine=InnoDB;

    create table transportes (
        capacidade integer,
        placa varchar(7) not null,
        created_at datetime(6) not null,
        id_transporte bigint not null auto_increment,
        updated_at datetime(6) not null,
        modelo varchar(255),
        primary key (id_transporte)
    ) engine=InnoDB;

    create table usuarios (
        created_at datetime(6) not null,
        fk_transporte bigint,
        id_usuario bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(15) not null,
        tel2 varchar(15),
        email varchar(100) not null,
        nome varchar(100) not null,
        password_hash varchar(255) not null,
        role enum ('ADMIN','COMMON') not null,
        primary key (id_usuario)
    ) engine=InnoDB;

    alter table funcionarios 
       add constraint uk_funcionario_usuario_cpf unique (fk_usuario, cpf);

    alter table responsaveis 
       add constraint uk_responsavel_usuario_cpf unique (fk_usuario, cpf);

    alter table usuarios 
       add constraint UKc3nvophml2aswq259wpfd12t8 unique (fk_transporte);

    alter table usuarios 
       add constraint UKkfsp0s1tflm1cwlj8idhqsad0 unique (email);

    alter table alunos 
       add constraint FKb8eacven1yc3t957d917cxjab 
       foreign key (fk_escola) 
       references escolas (id_escola);

    alter table alunos 
       add constraint FKc3endfht84dtmu4lvopa2dgod 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table alunos 
       add constraint FKqu80wr5eduhgwgoi1f2ktfgp7 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table despesas 
       add constraint FKhhq5afu6r78ldianv3yrensb8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table escolas 
       add constraint FKe40veo7npy9vfns4f6401yfv7 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table escolas 
       add constraint FKniscjgwhbkvnif82vycp7dmxg 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table eventos 
       add constraint FK8va2859gxy5whjpikk7uug6q6 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table funcionarios 
       add constraint FK1acfgeh4r5rmy0vqf5vr2dya0 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table funcionarios 
       add constraint FK1k5cc8jk7p063ywgoc4piroe8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table funcionarios 
       add constraint FKd4arse49sf5cf85kqw51cl1fm 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table mensalidades_aluno 
       add constraint FKi0i3uorvri4jc5vr1rrfs366x 
       foreign key (fk_aluno) 
       references alunos (id_aluno);

    alter table mensalidades_aluno 
       add constraint FKrwfjc9b0ni8j30gbgj15ru677 
       foreign key (fk_pagamento) 
       references pagamentos (id_pagamento);

    alter table pagamentos 
       add constraint FKj9jue57fdysgi2gevvtje0ri6 
       foreign key (fk_funcionario) 
       references funcionarios (id_funcionario);

    alter table responsaveis 
       add constraint FKdspgsvgub7mpyw2e20kd1odmf 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table responsaveis 
       add constraint FKb5sjslnfuhb4orat465rqtwgw 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table responsavel_aluno 
       add constraint FK9y3pygcjhmfu11pntgcx65t78 
       foreign key (aluno_id) 
       references alunos (id_aluno);

    alter table responsavel_aluno 
       add constraint FKho3xcju3jdwykdxidt1uahgae 
       foreign key (responsavel_id) 
       references responsaveis (id_responsavel);

    alter table usuarios 
       add constraint FKh53ry2r3nv39msdk4yun1gib0 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    create table alunos (
        ativo bit not null,
        dia_vencimento integer not null,
        dt_nascimento date,
        serie integer,
        valor_mensalidade decimal(38,2) not null,
        sala varchar(5),
        created_at datetime(6) not null,
        fk_escola bigint not null,
        fk_transporte bigint,
        fk_usuario bigint not null,
        id_aluno bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(45) not null,
        professor varchar(45) not null,
        primary key (id_aluno)
    ) engine=InnoDB;

    create table despesas (
        valor_despesa decimal(38,2),
        created_at datetime(6) not null,
        data_despesa datetime(6),
        fk_transporte bigint not null,
        id_despesa bigint not null auto_increment,
        updated_at datetime(6) not null,
        descricao varchar(255),
        primary key (id_despesa)
    ) engine=InnoDB;

    create table enderecos (
        created_at datetime(6) not null,
        id_endereco bigint not null auto_increment,
        updated_at datetime(6) not null,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        primary key (id_endereco)
    ) engine=InnoDB;

    create table escolas (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_escola bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(255) not null,
        nivel_ensino enum ('CRECHE','ENSINO_FUNDAMENTAL','ENSINO_MEDIO','PRE_ESCOLA'),
        primary key (id_escola)
    ) engine=InnoDB;

    create table eventos (
        date date not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        priority varchar(20) not null,
        type varchar(20) not null,
        description TEXT,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table funcionarios (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_transporte bigint not null,
        fk_usuario bigint not null,
        id_funcionario bigint not null auto_increment,
        updated_at datetime(6) not null,
        cpf varchar(255),
        nome varchar(255) not null,
        primary key (id_funcionario)
    ) engine=InnoDB;

    create table mensalidades_aluno (
        ano integer not null,
        data_pagamento date,
        data_vencimento date not null,
        mes integer not null,
        valor_mensalidade decimal(38,2) not null,
        created_at datetime(6) not null,
        fk_aluno bigint not null,
        fk_pagamento bigint,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        status enum ('ATRASADO','CANCELADO','PAGO','PENDENTE') not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamentos (
        valor_pagamento decimal(38,2),
        created_at datetime(6) not null,
        data_pagamento datetime(6),
        fk_funcionario bigint not null,
        id_pagamento bigint not null auto_increment,
        updated_at datetime(6) not null,
        primary key (id_pagamento)
    ) engine=InnoDB;

    create table responsaveis (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_responsavel bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(9) not null,
        tel2 varchar(9),
        cpf varchar(14),
        nome varchar(45) not null,
        email varchar(255),
        primary key (id_responsavel)
    ) engine=InnoDB;

    create table responsavel_aluno (
        aluno_id bigint not null,
        responsavel_id bigint not null
    ) engine=InnoDB;

    create table transportes (
        capacidade integer,
        placa varchar(7) not null,
        created_at datetime(6) not null,
        id_transporte bigint not null auto_increment,
        updated_at datetime(6) not null,
        modelo varchar(255),
        primary key (id_transporte)
    ) engine=InnoDB;

    create table usuarios (
        created_at datetime(6) not null,
        fk_transporte bigint,
        id_usuario bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(15) not null,
        tel2 varchar(15),
        email varchar(100) not null,
        nome varchar(100) not null,
        password_hash varchar(255) not null,
        role enum ('ADMIN','COMMON') not null,
        primary key (id_usuario)
    ) engine=InnoDB;

    alter table funcionarios 
       add constraint uk_funcionario_usuario_cpf unique (fk_usuario, cpf);

    alter table responsaveis 
       add constraint uk_responsavel_usuario_cpf unique (fk_usuario, cpf);

    alter table usuarios 
       add constraint UKc3nvophml2aswq259wpfd12t8 unique (fk_transporte);

    alter table usuarios 
       add constraint UKkfsp0s1tflm1cwlj8idhqsad0 unique (email);

    alter table alunos 
       add constraint FKb8eacven1yc3t957d917cxjab 
       foreign key (fk_escola) 
       references escolas (id_escola);

    alter table alunos 
       add constraint FKc3endfht84dtmu4lvopa2dgod 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table alunos 
       add constraint FKqu80wr5eduhgwgoi1f2ktfgp7 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table despesas 
       add constraint FKhhq5afu6r78ldianv3yrensb8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table escolas 
       add constraint FKe40veo7npy9vfns4f6401yfv7 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table escolas 
       add constraint FKniscjgwhbkvnif82vycp7dmxg 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table eventos 
       add constraint FK8va2859gxy5whjpikk7uug6q6 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table funcionarios 
       add constraint FK1acfgeh4r5rmy0vqf5vr2dya0 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table funcionarios 
       add constraint FK1k5cc8jk7p063ywgoc4piroe8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table funcionarios 
       add constraint FKd4arse49sf5cf85kqw51cl1fm 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table mensalidades_aluno 
       add constraint FKi0i3uorvri4jc5vr1rrfs366x 
       foreign key (fk_aluno) 
       references alunos (id_aluno);

    alter table mensalidades_aluno 
       add constraint FKrwfjc9b0ni8j30gbgj15ru677 
       foreign key (fk_pagamento) 
       references pagamentos (id_pagamento);

    alter table pagamentos 
       add constraint FKj9jue57fdysgi2gevvtje0ri6 
       foreign key (fk_funcionario) 
       references funcionarios (id_funcionario);

    alter table responsaveis 
       add constraint FKdspgsvgub7mpyw2e20kd1odmf 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table responsaveis 
       add constraint FKb5sjslnfuhb4orat465rqtwgw 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table responsavel_aluno 
       add constraint FK9y3pygcjhmfu11pntgcx65t78 
       foreign key (aluno_id) 
       references alunos (id_aluno);

    alter table responsavel_aluno 
       add constraint FKho3xcju3jdwykdxidt1uahgae 
       foreign key (responsavel_id) 
       references responsaveis (id_responsavel);

    alter table usuarios 
       add constraint FKh53ry2r3nv39msdk4yun1gib0 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    create table alunos (
        ativo bit not null,
        dia_vencimento integer not null,
        dt_nascimento date,
        serie integer,
        valor_mensalidade decimal(38,2) not null,
        sala varchar(5),
        created_at datetime(6) not null,
        fk_escola bigint not null,
        fk_transporte bigint,
        fk_usuario bigint not null,
        id_aluno bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(45) not null,
        professor varchar(45) not null,
        primary key (id_aluno)
    ) engine=InnoDB;

    create table despesas (
        valor_despesa decimal(38,2),
        created_at datetime(6) not null,
        data_despesa datetime(6),
        fk_transporte bigint not null,
        id_despesa bigint not null auto_increment,
        updated_at datetime(6) not null,
        descricao varchar(255),
        primary key (id_despesa)
    ) engine=InnoDB;

    create table enderecos (
        created_at datetime(6) not null,
        id_endereco bigint not null auto_increment,
        updated_at datetime(6) not null,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        primary key (id_endereco)
    ) engine=InnoDB;

    create table escolas (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_escola bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(255) not null,
        nivel_ensino enum ('CRECHE','ENSINO_FUNDAMENTAL','ENSINO_MEDIO','PRE_ESCOLA'),
        primary key (id_escola)
    ) engine=InnoDB;

    create table eventos (
        date date not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        priority varchar(20) not null,
        type varchar(20) not null,
        description TEXT,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table funcionarios (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_transporte bigint not null,
        fk_usuario bigint not null,
        id_funcionario bigint not null auto_increment,
        updated_at datetime(6) not null,
        cpf varchar(255),
        nome varchar(255) not null,
        primary key (id_funcionario)
    ) engine=InnoDB;

    create table mensalidades_aluno (
        ano integer not null,
        data_pagamento date,
        data_vencimento date not null,
        mes integer not null,
        valor_mensalidade decimal(38,2) not null,
        created_at datetime(6) not null,
        fk_aluno bigint not null,
        fk_pagamento bigint,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        status enum ('ATRASADO','CANCELADO','PAGO','PENDENTE') not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamentos (
        valor_pagamento decimal(38,2),
        created_at datetime(6) not null,
        data_pagamento datetime(6),
        fk_funcionario bigint not null,
        id_pagamento bigint not null auto_increment,
        updated_at datetime(6) not null,
        primary key (id_pagamento)
    ) engine=InnoDB;

    create table responsaveis (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_responsavel bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(9) not null,
        tel2 varchar(9),
        cpf varchar(14),
        nome varchar(45) not null,
        email varchar(255),
        primary key (id_responsavel)
    ) engine=InnoDB;

    create table responsavel_aluno (
        aluno_id bigint not null,
        responsavel_id bigint not null
    ) engine=InnoDB;

    create table transportes (
        capacidade integer,
        placa varchar(7) not null,
        created_at datetime(6) not null,
        id_transporte bigint not null auto_increment,
        updated_at datetime(6) not null,
        modelo varchar(255),
        primary key (id_transporte)
    ) engine=InnoDB;

    create table usuarios (
        created_at datetime(6) not null,
        fk_transporte bigint,
        id_usuario bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(15) not null,
        tel2 varchar(15),
        email varchar(100) not null,
        nome varchar(100) not null,
        password_hash varchar(255) not null,
        role enum ('ADMIN','COMMON') not null,
        primary key (id_usuario)
    ) engine=InnoDB;

    alter table funcionarios 
       add constraint uk_funcionario_usuario_cpf unique (fk_usuario, cpf);

    alter table responsaveis 
       add constraint uk_responsavel_usuario_cpf unique (fk_usuario, cpf);

    alter table usuarios 
       add constraint UKc3nvophml2aswq259wpfd12t8 unique (fk_transporte);

    alter table usuarios 
       add constraint UKkfsp0s1tflm1cwlj8idhqsad0 unique (email);

    alter table alunos 
       add constraint FKb8eacven1yc3t957d917cxjab 
       foreign key (fk_escola) 
       references escolas (id_escola);

    alter table alunos 
       add constraint FKc3endfht84dtmu4lvopa2dgod 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table alunos 
       add constraint FKqu80wr5eduhgwgoi1f2ktfgp7 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table despesas 
       add constraint FKhhq5afu6r78ldianv3yrensb8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table escolas 
       add constraint FKe40veo7npy9vfns4f6401yfv7 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table escolas 
       add constraint FKniscjgwhbkvnif82vycp7dmxg 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table eventos 
       add constraint FK8va2859gxy5whjpikk7uug6q6 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table funcionarios 
       add constraint FK1acfgeh4r5rmy0vqf5vr2dya0 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table funcionarios 
       add constraint FK1k5cc8jk7p063ywgoc4piroe8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table funcionarios 
       add constraint FKd4arse49sf5cf85kqw51cl1fm 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table mensalidades_aluno 
       add constraint FKi0i3uorvri4jc5vr1rrfs366x 
       foreign key (fk_aluno) 
       references alunos (id_aluno);

    alter table mensalidades_aluno 
       add constraint FKrwfjc9b0ni8j30gbgj15ru677 
       foreign key (fk_pagamento) 
       references pagamentos (id_pagamento);

    alter table pagamentos 
       add constraint FKj9jue57fdysgi2gevvtje0ri6 
       foreign key (fk_funcionario) 
       references funcionarios (id_funcionario);

    alter table responsaveis 
       add constraint FKdspgsvgub7mpyw2e20kd1odmf 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table responsaveis 
       add constraint FKb5sjslnfuhb4orat465rqtwgw 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table responsavel_aluno 
       add constraint FK9y3pygcjhmfu11pntgcx65t78 
       foreign key (aluno_id) 
       references alunos (id_aluno);

    alter table responsavel_aluno 
       add constraint FKho3xcju3jdwykdxidt1uahgae 
       foreign key (responsavel_id) 
       references responsaveis (id_responsavel);

    alter table usuarios 
       add constraint FKh53ry2r3nv39msdk4yun1gib0 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    create table alunos (
        ativo bit not null,
        dia_vencimento integer not null,
        dt_nascimento date,
        serie integer,
        valor_mensalidade decimal(38,2) not null,
        sala varchar(5),
        created_at datetime(6) not null,
        fk_escola bigint not null,
        fk_transporte bigint,
        fk_usuario bigint not null,
        id_aluno bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(45) not null,
        professor varchar(45) not null,
        primary key (id_aluno)
    ) engine=InnoDB;

    create table despesas (
        valor_despesa decimal(38,2),
        created_at datetime(6) not null,
        data_despesa datetime(6),
        fk_transporte bigint not null,
        id_despesa bigint not null auto_increment,
        updated_at datetime(6) not null,
        descricao varchar(255),
        primary key (id_despesa)
    ) engine=InnoDB;

    create table enderecos (
        created_at datetime(6) not null,
        id_endereco bigint not null auto_increment,
        updated_at datetime(6) not null,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        primary key (id_endereco)
    ) engine=InnoDB;

    create table escolas (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_escola bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(255) not null,
        nivel_ensino enum ('CRECHE','ENSINO_FUNDAMENTAL','ENSINO_MEDIO','PRE_ESCOLA'),
        primary key (id_escola)
    ) engine=InnoDB;

    create table eventos (
        date date not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        priority varchar(20) not null,
        type varchar(20) not null,
        description TEXT,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table funcionarios (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_transporte bigint not null,
        fk_usuario bigint not null,
        id_funcionario bigint not null auto_increment,
        updated_at datetime(6) not null,
        cpf varchar(255),
        nome varchar(255) not null,
        primary key (id_funcionario)
    ) engine=InnoDB;

    create table mensalidades_aluno (
        ano integer not null,
        data_pagamento date,
        data_vencimento date not null,
        mes integer not null,
        valor_mensalidade decimal(38,2) not null,
        created_at datetime(6) not null,
        fk_aluno bigint not null,
        fk_pagamento bigint,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        status enum ('ATRASADO','CANCELADO','PAGO','PENDENTE') not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamentos (
        valor_pagamento decimal(38,2),
        created_at datetime(6) not null,
        data_pagamento datetime(6),
        fk_funcionario bigint not null,
        id_pagamento bigint not null auto_increment,
        updated_at datetime(6) not null,
        primary key (id_pagamento)
    ) engine=InnoDB;

    create table responsaveis (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_responsavel bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(9) not null,
        tel2 varchar(9),
        cpf varchar(14),
        nome varchar(45) not null,
        email varchar(255),
        primary key (id_responsavel)
    ) engine=InnoDB;

    create table responsavel_aluno (
        aluno_id bigint not null,
        responsavel_id bigint not null
    ) engine=InnoDB;

    create table transportes (
        capacidade integer,
        placa varchar(7) not null,
        created_at datetime(6) not null,
        id_transporte bigint not null auto_increment,
        updated_at datetime(6) not null,
        modelo varchar(255),
        primary key (id_transporte)
    ) engine=InnoDB;

    create table usuarios (
        created_at datetime(6) not null,
        fk_transporte bigint,
        id_usuario bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(15) not null,
        tel2 varchar(15),
        email varchar(100) not null,
        nome varchar(100) not null,
        password_hash varchar(255) not null,
        role enum ('ADMIN','COMMON') not null,
        primary key (id_usuario)
    ) engine=InnoDB;

    alter table funcionarios 
       add constraint uk_funcionario_usuario_cpf unique (fk_usuario, cpf);

    alter table responsaveis 
       add constraint uk_responsavel_usuario_cpf unique (fk_usuario, cpf);

    alter table usuarios 
       add constraint UKc3nvophml2aswq259wpfd12t8 unique (fk_transporte);

    alter table usuarios 
       add constraint UKkfsp0s1tflm1cwlj8idhqsad0 unique (email);

    alter table alunos 
       add constraint FKb8eacven1yc3t957d917cxjab 
       foreign key (fk_escola) 
       references escolas (id_escola);

    alter table alunos 
       add constraint FKc3endfht84dtmu4lvopa2dgod 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table alunos 
       add constraint FKqu80wr5eduhgwgoi1f2ktfgp7 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table despesas 
       add constraint FKhhq5afu6r78ldianv3yrensb8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table escolas 
       add constraint FKe40veo7npy9vfns4f6401yfv7 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table escolas 
       add constraint FKniscjgwhbkvnif82vycp7dmxg 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table eventos 
       add constraint FK8va2859gxy5whjpikk7uug6q6 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table funcionarios 
       add constraint FK1acfgeh4r5rmy0vqf5vr2dya0 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table funcionarios 
       add constraint FK1k5cc8jk7p063ywgoc4piroe8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table funcionarios 
       add constraint FKd4arse49sf5cf85kqw51cl1fm 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table mensalidades_aluno 
       add constraint FKi0i3uorvri4jc5vr1rrfs366x 
       foreign key (fk_aluno) 
       references alunos (id_aluno);

    alter table mensalidades_aluno 
       add constraint FKrwfjc9b0ni8j30gbgj15ru677 
       foreign key (fk_pagamento) 
       references pagamentos (id_pagamento);

    alter table pagamentos 
       add constraint FKj9jue57fdysgi2gevvtje0ri6 
       foreign key (fk_funcionario) 
       references funcionarios (id_funcionario);

    alter table responsaveis 
       add constraint FKdspgsvgub7mpyw2e20kd1odmf 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table responsaveis 
       add constraint FKb5sjslnfuhb4orat465rqtwgw 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table responsavel_aluno 
       add constraint FK9y3pygcjhmfu11pntgcx65t78 
       foreign key (aluno_id) 
       references alunos (id_aluno);

    alter table responsavel_aluno 
       add constraint FKho3xcju3jdwykdxidt1uahgae 
       foreign key (responsavel_id) 
       references responsaveis (id_responsavel);

    alter table usuarios 
       add constraint FKh53ry2r3nv39msdk4yun1gib0 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    create table alunos (
        ativo bit not null,
        dia_vencimento integer not null,
        dt_nascimento date,
        serie integer,
        valor_mensalidade decimal(38,2) not null,
        sala varchar(5),
        created_at datetime(6) not null,
        fk_escola bigint not null,
        fk_transporte bigint,
        fk_usuario bigint not null,
        id_aluno bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(45) not null,
        professor varchar(45) not null,
        primary key (id_aluno)
    ) engine=InnoDB;

    create table despesas (
        valor_despesa decimal(38,2),
        created_at datetime(6) not null,
        data_despesa datetime(6),
        fk_transporte bigint not null,
        id_despesa bigint not null auto_increment,
        updated_at datetime(6) not null,
        descricao varchar(255),
        primary key (id_despesa)
    ) engine=InnoDB;

    create table enderecos (
        created_at datetime(6) not null,
        id_endereco bigint not null auto_increment,
        updated_at datetime(6) not null,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        primary key (id_endereco)
    ) engine=InnoDB;

    create table escolas (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_escola bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(255) not null,
        nivel_ensino enum ('CRECHE','ENSINO_FUNDAMENTAL','ENSINO_MEDIO','PRE_ESCOLA'),
        primary key (id_escola)
    ) engine=InnoDB;

    create table eventos (
        date date not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        priority varchar(20) not null,
        type varchar(20) not null,
        description TEXT,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table funcionarios (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_transporte bigint not null,
        fk_usuario bigint not null,
        id_funcionario bigint not null auto_increment,
        updated_at datetime(6) not null,
        cpf varchar(255),
        nome varchar(255) not null,
        primary key (id_funcionario)
    ) engine=InnoDB;

    create table mensalidades_aluno (
        ano integer not null,
        data_pagamento date,
        data_vencimento date not null,
        mes integer not null,
        valor_mensalidade decimal(38,2) not null,
        created_at datetime(6) not null,
        fk_aluno bigint not null,
        fk_pagamento bigint,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        status enum ('ATRASADO','CANCELADO','PAGO','PENDENTE') not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamentos (
        valor_pagamento decimal(38,2),
        created_at datetime(6) not null,
        data_pagamento datetime(6),
        fk_funcionario bigint not null,
        id_pagamento bigint not null auto_increment,
        updated_at datetime(6) not null,
        primary key (id_pagamento)
    ) engine=InnoDB;

    create table responsaveis (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_responsavel bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(9) not null,
        tel2 varchar(9),
        cpf varchar(14),
        nome varchar(45) not null,
        email varchar(255),
        primary key (id_responsavel)
    ) engine=InnoDB;

    create table responsavel_aluno (
        aluno_id bigint not null,
        responsavel_id bigint not null
    ) engine=InnoDB;

    create table transportes (
        capacidade integer,
        placa varchar(7) not null,
        created_at datetime(6) not null,
        id_transporte bigint not null auto_increment,
        updated_at datetime(6) not null,
        modelo varchar(255),
        primary key (id_transporte)
    ) engine=InnoDB;

    create table usuarios (
        created_at datetime(6) not null,
        fk_transporte bigint,
        id_usuario bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(15) not null,
        tel2 varchar(15),
        email varchar(100) not null,
        nome varchar(100) not null,
        password_hash varchar(255) not null,
        role enum ('ADMIN','COMMON') not null,
        primary key (id_usuario)
    ) engine=InnoDB;

    alter table funcionarios 
       add constraint uk_funcionario_usuario_cpf unique (fk_usuario, cpf);

    alter table responsaveis 
       add constraint uk_responsavel_usuario_cpf unique (fk_usuario, cpf);

    alter table usuarios 
       add constraint UKc3nvophml2aswq259wpfd12t8 unique (fk_transporte);

    alter table usuarios 
       add constraint UKkfsp0s1tflm1cwlj8idhqsad0 unique (email);

    alter table alunos 
       add constraint FKb8eacven1yc3t957d917cxjab 
       foreign key (fk_escola) 
       references escolas (id_escola);

    alter table alunos 
       add constraint FKc3endfht84dtmu4lvopa2dgod 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table alunos 
       add constraint FKqu80wr5eduhgwgoi1f2ktfgp7 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table despesas 
       add constraint FKhhq5afu6r78ldianv3yrensb8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table escolas 
       add constraint FKe40veo7npy9vfns4f6401yfv7 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table escolas 
       add constraint FKniscjgwhbkvnif82vycp7dmxg 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table eventos 
       add constraint FK8va2859gxy5whjpikk7uug6q6 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table funcionarios 
       add constraint FK1acfgeh4r5rmy0vqf5vr2dya0 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table funcionarios 
       add constraint FK1k5cc8jk7p063ywgoc4piroe8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table funcionarios 
       add constraint FKd4arse49sf5cf85kqw51cl1fm 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table mensalidades_aluno 
       add constraint FKi0i3uorvri4jc5vr1rrfs366x 
       foreign key (fk_aluno) 
       references alunos (id_aluno);

    alter table mensalidades_aluno 
       add constraint FKrwfjc9b0ni8j30gbgj15ru677 
       foreign key (fk_pagamento) 
       references pagamentos (id_pagamento);

    alter table pagamentos 
       add constraint FKj9jue57fdysgi2gevvtje0ri6 
       foreign key (fk_funcionario) 
       references funcionarios (id_funcionario);

    alter table responsaveis 
       add constraint FKdspgsvgub7mpyw2e20kd1odmf 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table responsaveis 
       add constraint FKb5sjslnfuhb4orat465rqtwgw 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table responsavel_aluno 
       add constraint FK9y3pygcjhmfu11pntgcx65t78 
       foreign key (aluno_id) 
       references alunos (id_aluno);

    alter table responsavel_aluno 
       add constraint FKho3xcju3jdwykdxidt1uahgae 
       foreign key (responsavel_id) 
       references responsaveis (id_responsavel);

    alter table usuarios 
       add constraint FKh53ry2r3nv39msdk4yun1gib0 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    create table alunos (
        ativo bit not null,
        dia_vencimento integer not null,
        dt_nascimento date,
        serie integer,
        valor_mensalidade decimal(38,2) not null,
        sala varchar(5),
        created_at datetime(6) not null,
        fk_escola bigint not null,
        fk_transporte bigint,
        fk_usuario bigint not null,
        id_aluno bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(45) not null,
        professor varchar(45) not null,
        primary key (id_aluno)
    ) engine=InnoDB;

    create table despesas (
        valor_despesa decimal(38,2),
        created_at datetime(6) not null,
        data_despesa datetime(6),
        fk_transporte bigint not null,
        id_despesa bigint not null auto_increment,
        updated_at datetime(6) not null,
        descricao varchar(255),
        primary key (id_despesa)
    ) engine=InnoDB;

    create table enderecos (
        created_at datetime(6) not null,
        id_endereco bigint not null auto_increment,
        updated_at datetime(6) not null,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        primary key (id_endereco)
    ) engine=InnoDB;

    create table escolas (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_escola bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(255) not null,
        nivel_ensino enum ('CRECHE','ENSINO_FUNDAMENTAL','ENSINO_MEDIO','PRE_ESCOLA'),
        primary key (id_escola)
    ) engine=InnoDB;

    create table eventos (
        date date not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        priority varchar(20) not null,
        type varchar(20) not null,
        description TEXT,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table funcionarios (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_transporte bigint not null,
        fk_usuario bigint not null,
        id_funcionario bigint not null auto_increment,
        updated_at datetime(6) not null,
        cpf varchar(255),
        nome varchar(255) not null,
        primary key (id_funcionario)
    ) engine=InnoDB;

    create table mensalidades_aluno (
        ano integer not null,
        data_pagamento date,
        data_vencimento date not null,
        mes integer not null,
        valor_mensalidade decimal(38,2) not null,
        created_at datetime(6) not null,
        fk_aluno bigint not null,
        fk_pagamento bigint,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        status enum ('ATRASADO','CANCELADO','PAGO','PENDENTE') not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamentos (
        valor_pagamento decimal(38,2),
        created_at datetime(6) not null,
        data_pagamento datetime(6),
        fk_funcionario bigint not null,
        id_pagamento bigint not null auto_increment,
        updated_at datetime(6) not null,
        primary key (id_pagamento)
    ) engine=InnoDB;

    create table responsaveis (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_responsavel bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(9) not null,
        tel2 varchar(9),
        cpf varchar(14),
        nome varchar(45) not null,
        email varchar(255),
        primary key (id_responsavel)
    ) engine=InnoDB;

    create table responsavel_aluno (
        aluno_id bigint not null,
        responsavel_id bigint not null
    ) engine=InnoDB;

    create table transportes (
        capacidade integer,
        placa varchar(7) not null,
        created_at datetime(6) not null,
        id_transporte bigint not null auto_increment,
        updated_at datetime(6) not null,
        modelo varchar(255),
        primary key (id_transporte)
    ) engine=InnoDB;

    create table usuarios (
        created_at datetime(6) not null,
        fk_transporte bigint,
        id_usuario bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(15) not null,
        tel2 varchar(15),
        email varchar(100) not null,
        nome varchar(100) not null,
        password_hash varchar(255) not null,
        role enum ('ADMIN','COMMON') not null,
        primary key (id_usuario)
    ) engine=InnoDB;

    alter table funcionarios 
       add constraint uk_funcionario_usuario_cpf unique (fk_usuario, cpf);

    alter table responsaveis 
       add constraint uk_responsavel_usuario_cpf unique (fk_usuario, cpf);

    alter table usuarios 
       add constraint UKc3nvophml2aswq259wpfd12t8 unique (fk_transporte);

    alter table usuarios 
       add constraint UKkfsp0s1tflm1cwlj8idhqsad0 unique (email);

    alter table alunos 
       add constraint FKb8eacven1yc3t957d917cxjab 
       foreign key (fk_escola) 
       references escolas (id_escola);

    alter table alunos 
       add constraint FKc3endfht84dtmu4lvopa2dgod 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table alunos 
       add constraint FKqu80wr5eduhgwgoi1f2ktfgp7 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table despesas 
       add constraint FKhhq5afu6r78ldianv3yrensb8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table escolas 
       add constraint FKe40veo7npy9vfns4f6401yfv7 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table escolas 
       add constraint FKniscjgwhbkvnif82vycp7dmxg 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table eventos 
       add constraint FK8va2859gxy5whjpikk7uug6q6 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table funcionarios 
       add constraint FK1acfgeh4r5rmy0vqf5vr2dya0 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table funcionarios 
       add constraint FK1k5cc8jk7p063ywgoc4piroe8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table funcionarios 
       add constraint FKd4arse49sf5cf85kqw51cl1fm 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table mensalidades_aluno 
       add constraint FKi0i3uorvri4jc5vr1rrfs366x 
       foreign key (fk_aluno) 
       references alunos (id_aluno);

    alter table mensalidades_aluno 
       add constraint FKrwfjc9b0ni8j30gbgj15ru677 
       foreign key (fk_pagamento) 
       references pagamentos (id_pagamento);

    alter table pagamentos 
       add constraint FKj9jue57fdysgi2gevvtje0ri6 
       foreign key (fk_funcionario) 
       references funcionarios (id_funcionario);

    alter table responsaveis 
       add constraint FKdspgsvgub7mpyw2e20kd1odmf 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table responsaveis 
       add constraint FKb5sjslnfuhb4orat465rqtwgw 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table responsavel_aluno 
       add constraint FK9y3pygcjhmfu11pntgcx65t78 
       foreign key (aluno_id) 
       references alunos (id_aluno);

    alter table responsavel_aluno 
       add constraint FKho3xcju3jdwykdxidt1uahgae 
       foreign key (responsavel_id) 
       references responsaveis (id_responsavel);

    alter table usuarios 
       add constraint FKh53ry2r3nv39msdk4yun1gib0 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    create table alunos (
        ativo bit not null,
        dia_vencimento integer not null,
        dt_nascimento date,
        serie integer,
        valor_mensalidade decimal(38,2) not null,
        sala varchar(5),
        created_at datetime(6) not null,
        fk_escola bigint not null,
        fk_transporte bigint,
        fk_usuario bigint not null,
        id_aluno bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(45) not null,
        professor varchar(45) not null,
        primary key (id_aluno)
    ) engine=InnoDB;

    create table despesas (
        valor_despesa decimal(38,2),
        created_at datetime(6) not null,
        data_despesa datetime(6),
        fk_transporte bigint not null,
        id_despesa bigint not null auto_increment,
        updated_at datetime(6) not null,
        descricao varchar(255),
        primary key (id_despesa)
    ) engine=InnoDB;

    create table enderecos (
        created_at datetime(6) not null,
        id_endereco bigint not null auto_increment,
        updated_at datetime(6) not null,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        primary key (id_endereco)
    ) engine=InnoDB;

    create table escolas (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_escola bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(255) not null,
        nivel_ensino enum ('CRECHE','ENSINO_FUNDAMENTAL','ENSINO_MEDIO','PRE_ESCOLA'),
        primary key (id_escola)
    ) engine=InnoDB;

    create table eventos (
        date date not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        priority varchar(20) not null,
        type varchar(20) not null,
        description TEXT,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table funcionarios (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_transporte bigint not null,
        fk_usuario bigint not null,
        id_funcionario bigint not null auto_increment,
        updated_at datetime(6) not null,
        cpf varchar(255),
        nome varchar(255) not null,
        primary key (id_funcionario)
    ) engine=InnoDB;

    create table mensalidades_aluno (
        ano integer not null,
        data_pagamento date,
        data_vencimento date not null,
        mes integer not null,
        valor_mensalidade decimal(38,2) not null,
        created_at datetime(6) not null,
        fk_aluno bigint not null,
        fk_pagamento bigint,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        status enum ('ATRASADO','CANCELADO','PAGO','PENDENTE') not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamentos (
        valor_pagamento decimal(38,2),
        created_at datetime(6) not null,
        data_pagamento datetime(6),
        fk_funcionario bigint not null,
        id_pagamento bigint not null auto_increment,
        updated_at datetime(6) not null,
        primary key (id_pagamento)
    ) engine=InnoDB;

    create table responsaveis (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_responsavel bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(9) not null,
        tel2 varchar(9),
        cpf varchar(14),
        nome varchar(45) not null,
        email varchar(255),
        primary key (id_responsavel)
    ) engine=InnoDB;

    create table responsavel_aluno (
        aluno_id bigint not null,
        responsavel_id bigint not null
    ) engine=InnoDB;

    create table transportes (
        capacidade integer,
        placa varchar(7) not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id_transporte bigint not null auto_increment,
        updated_at datetime(6) not null,
        modelo varchar(255),
        primary key (id_transporte)
    ) engine=InnoDB;

    create table usuarios (
        created_at datetime(6) not null,
        id_usuario bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(15) not null,
        tel2 varchar(15),
        email varchar(100) not null,
        nome varchar(100) not null,
        password_hash varchar(255) not null,
        role enum ('ADMIN','COMMON') not null,
        primary key (id_usuario)
    ) engine=InnoDB;

    alter table funcionarios 
       add constraint uk_funcionario_usuario_cpf unique (fk_usuario, cpf);

    alter table responsaveis 
       add constraint uk_responsavel_usuario_cpf unique (fk_usuario, cpf);

    alter table usuarios 
       add constraint UKkfsp0s1tflm1cwlj8idhqsad0 unique (email);

    alter table alunos 
       add constraint FKb8eacven1yc3t957d917cxjab 
       foreign key (fk_escola) 
       references escolas (id_escola);

    alter table alunos 
       add constraint FKc3endfht84dtmu4lvopa2dgod 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table alunos 
       add constraint FKqu80wr5eduhgwgoi1f2ktfgp7 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table despesas 
       add constraint FKhhq5afu6r78ldianv3yrensb8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table escolas 
       add constraint FKe40veo7npy9vfns4f6401yfv7 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table escolas 
       add constraint FKniscjgwhbkvnif82vycp7dmxg 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table eventos 
       add constraint FK8va2859gxy5whjpikk7uug6q6 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table funcionarios 
       add constraint FK1acfgeh4r5rmy0vqf5vr2dya0 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table funcionarios 
       add constraint FK1k5cc8jk7p063ywgoc4piroe8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table funcionarios 
       add constraint FKd4arse49sf5cf85kqw51cl1fm 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table mensalidades_aluno 
       add constraint FKi0i3uorvri4jc5vr1rrfs366x 
       foreign key (fk_aluno) 
       references alunos (id_aluno);

    alter table mensalidades_aluno 
       add constraint FKrwfjc9b0ni8j30gbgj15ru677 
       foreign key (fk_pagamento) 
       references pagamentos (id_pagamento);

    alter table pagamentos 
       add constraint FKj9jue57fdysgi2gevvtje0ri6 
       foreign key (fk_funcionario) 
       references funcionarios (id_funcionario);

    alter table responsaveis 
       add constraint FKdspgsvgub7mpyw2e20kd1odmf 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table responsaveis 
       add constraint FKb5sjslnfuhb4orat465rqtwgw 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table responsavel_aluno 
       add constraint FK9y3pygcjhmfu11pntgcx65t78 
       foreign key (aluno_id) 
       references alunos (id_aluno);

    alter table responsavel_aluno 
       add constraint FKho3xcju3jdwykdxidt1uahgae 
       foreign key (responsavel_id) 
       references responsaveis (id_responsavel);

    alter table transportes 
       add constraint FKt4rocvt4s5rq7akl1l53v3hac 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    create table alunos (
        ativo bit not null,
        dia_vencimento integer not null,
        dt_nascimento date,
        serie integer,
        valor_mensalidade decimal(38,2) not null,
        sala varchar(5),
        created_at datetime(6) not null,
        fk_escola bigint not null,
        fk_transporte bigint,
        fk_usuario bigint not null,
        id_aluno bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(45) not null,
        professor varchar(45) not null,
        primary key (id_aluno)
    ) engine=InnoDB;

    create table despesas (
        valor_despesa decimal(38,2),
        created_at datetime(6) not null,
        data_despesa datetime(6),
        fk_transporte bigint not null,
        id_despesa bigint not null auto_increment,
        updated_at datetime(6) not null,
        descricao varchar(255),
        primary key (id_despesa)
    ) engine=InnoDB;

    create table enderecos (
        created_at datetime(6) not null,
        id_endereco bigint not null auto_increment,
        updated_at datetime(6) not null,
        bairro varchar(255),
        cep varchar(255),
        cidade varchar(255),
        complemento varchar(255),
        logradouro varchar(255),
        numero varchar(255),
        primary key (id_endereco)
    ) engine=InnoDB;

    create table escolas (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_escola bigint not null auto_increment,
        updated_at datetime(6) not null,
        nome varchar(255) not null,
        nivel_ensino enum ('CRECHE','ENSINO_FUNDAMENTAL','ENSINO_MEDIO','PRE_ESCOLA'),
        primary key (id_escola)
    ) engine=InnoDB;

    create table eventos (
        date date not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        priority varchar(20) not null,
        type varchar(20) not null,
        description TEXT,
        title varchar(255) not null,
        primary key (id)
    ) engine=InnoDB;

    create table funcionarios (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_transporte bigint not null,
        fk_usuario bigint not null,
        id_funcionario bigint not null auto_increment,
        updated_at datetime(6) not null,
        cpf varchar(255),
        nome varchar(255) not null,
        primary key (id_funcionario)
    ) engine=InnoDB;

    create table mensalidades_aluno (
        ano integer not null,
        data_pagamento date,
        data_vencimento date not null,
        mes integer not null,
        valor_mensalidade decimal(38,2) not null,
        created_at datetime(6) not null,
        fk_aluno bigint not null,
        fk_pagamento bigint,
        id bigint not null auto_increment,
        updated_at datetime(6) not null,
        status enum ('ATRASADO','CANCELADO','PAGO','PENDENTE') not null,
        primary key (id)
    ) engine=InnoDB;

    create table pagamentos (
        valor_pagamento decimal(38,2),
        created_at datetime(6) not null,
        data_pagamento datetime(6),
        fk_funcionario bigint not null,
        id_pagamento bigint not null auto_increment,
        updated_at datetime(6) not null,
        primary key (id_pagamento)
    ) engine=InnoDB;

    create table responsaveis (
        created_at datetime(6) not null,
        fk_endereco bigint not null,
        fk_usuario bigint not null,
        id_responsavel bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(9) not null,
        tel2 varchar(9),
        cpf varchar(14),
        nome varchar(45) not null,
        email varchar(255),
        primary key (id_responsavel)
    ) engine=InnoDB;

    create table responsavel_aluno (
        aluno_id bigint not null,
        responsavel_id bigint not null
    ) engine=InnoDB;

    create table transportes (
        capacidade integer,
        placa varchar(7) not null,
        created_at datetime(6) not null,
        fk_usuario bigint not null,
        id_transporte bigint not null auto_increment,
        updated_at datetime(6) not null,
        modelo varchar(255),
        primary key (id_transporte)
    ) engine=InnoDB;

    create table usuarios (
        created_at datetime(6) not null,
        id_usuario bigint not null auto_increment,
        updated_at datetime(6) not null,
        tel1 varchar(15) not null,
        tel2 varchar(15),
        email varchar(100) not null,
        nome varchar(100) not null,
        password_hash varchar(255) not null,
        role enum ('ADMIN','COMMON') not null,
        primary key (id_usuario)
    ) engine=InnoDB;

    alter table funcionarios 
       add constraint uk_funcionario_usuario_cpf unique (fk_usuario, cpf);

    alter table responsaveis 
       add constraint uk_responsavel_usuario_cpf unique (fk_usuario, cpf);

    alter table usuarios 
       add constraint UKkfsp0s1tflm1cwlj8idhqsad0 unique (email);

    alter table alunos 
       add constraint FKb8eacven1yc3t957d917cxjab 
       foreign key (fk_escola) 
       references escolas (id_escola);

    alter table alunos 
       add constraint FKc3endfht84dtmu4lvopa2dgod 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table alunos 
       add constraint FKqu80wr5eduhgwgoi1f2ktfgp7 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table despesas 
       add constraint FKhhq5afu6r78ldianv3yrensb8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table escolas 
       add constraint FKe40veo7npy9vfns4f6401yfv7 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table escolas 
       add constraint FKniscjgwhbkvnif82vycp7dmxg 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table eventos 
       add constraint FK8va2859gxy5whjpikk7uug6q6 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table funcionarios 
       add constraint FK1acfgeh4r5rmy0vqf5vr2dya0 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table funcionarios 
       add constraint FK1k5cc8jk7p063ywgoc4piroe8 
       foreign key (fk_transporte) 
       references transportes (id_transporte);

    alter table funcionarios 
       add constraint FKd4arse49sf5cf85kqw51cl1fm 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table mensalidades_aluno 
       add constraint FKi0i3uorvri4jc5vr1rrfs366x 
       foreign key (fk_aluno) 
       references alunos (id_aluno);

    alter table mensalidades_aluno 
       add constraint FKrwfjc9b0ni8j30gbgj15ru677 
       foreign key (fk_pagamento) 
       references pagamentos (id_pagamento);

    alter table pagamentos 
       add constraint FKj9jue57fdysgi2gevvtje0ri6 
       foreign key (fk_funcionario) 
       references funcionarios (id_funcionario);

    alter table responsaveis 
       add constraint FKdspgsvgub7mpyw2e20kd1odmf 
       foreign key (fk_endereco) 
       references enderecos (id_endereco);

    alter table responsaveis 
       add constraint FKb5sjslnfuhb4orat465rqtwgw 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);

    alter table responsavel_aluno 
       add constraint FK9y3pygcjhmfu11pntgcx65t78 
       foreign key (aluno_id) 
       references alunos (id_aluno);

    alter table responsavel_aluno 
       add constraint FKho3xcju3jdwykdxidt1uahgae 
       foreign key (responsavel_id) 
       references responsaveis (id_responsavel);

    alter table transportes 
       add constraint FKt4rocvt4s5rq7akl1l53v3hac 
       foreign key (fk_usuario) 
       references usuarios (id_usuario);
