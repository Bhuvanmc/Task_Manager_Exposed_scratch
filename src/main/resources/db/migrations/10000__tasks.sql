--liquibase formatted sql

--changeset Task_Manager:create-tasks-tables

CREATE TABLE IF NOT EXISTS tm_tasks.tasks
(
    id                  varchar(255) NOT NULL,
    name                varchar(255),
    description         varchar(255),
    created_by          varchar(255),
    created_time        varchar(255),
    updated_time        varchar(255),
    assignee            varchar(255),
    status              varchar(255),
    severity            varchar(255),
    CONSTRAINT pk_task PRIMARY KEY (id)
);
