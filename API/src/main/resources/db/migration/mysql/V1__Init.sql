create table lista_tarefa
(
    id               bigint auto_increment,
    nome_tarefa      varchar(255) not null,
    data_criacao     datetime     not null,
    data_atualizacao datetime     not null,
    status           varchar(255) not null,
    constraint table_name_pk
        primary key (id)
) ENGINE = InnoDB
  DEFAULT CHARSET utf8;


create table tarefa
(
    id               bigint auto_increment,
    tarefa           varchar(255) not null,
    data_criacao     datetime     not null,
    data_atualizacao datetime     not null,
    lista_id         bigint       not null,
    status           varchar(255) null,
    constraint tb_task_pk
        primary key (id),
    constraint tarefa_fk
        foreign key (lista_id) references lista_tarefa (id)
            on update cascade on delete cascade
) ENGINE = InnoDB
  DEFAULT CHARSET utf8;


