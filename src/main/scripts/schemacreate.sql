create table backlog (id bigint not null auto_increment, project_identifier varchar(255), pt_sequence integer, project_id bigint not null, primary key (id)) engine=MyISAM
create table project (id bigint not null auto_increment, created_at datetime, description varchar(255), end_date datetime, project_identifier varchar(5), project_leader varchar(255), project_name varchar(255), start_date datetime, updated_at datetime, user_id bigint, primary key (id)) engine=MyISAM
create table project_task (id bigint not null auto_increment, acceptance_criteria varchar(255), created_at datetime, due_date datetime, priority integer, project_identifier varchar(255), project_sequence varchar(255), status varchar(255), summary varchar(255), updated_at datetime, backlog_id bigint not null, primary key (id)) engine=MyISAM
create table user (id bigint not null auto_increment, created_at datetime, full_name varchar(255), password varchar(255), updated_at datetime, username varchar(255), primary key (id)) engine=MyISAM
alter table project add constraint UK_nh7jg4qyw1dm5y0bn2vwoq6v2 unique (project_identifier)
alter table project_task add constraint UK_lk2ru5up8ilfm5wkq7wp6rtce unique (project_sequence)
alter table user add constraint UK_sb8bbouer5wak8vyiiy4pf2bx unique (username)
alter table backlog add constraint FKl9uchve1jja83kpywpr72gi8k foreign key (project_id) references project (id)
alter table project add constraint FKo06v2e9kuapcugnyhttqa1vpt foreign key (user_id) references user (id)
alter table project_task add constraint FKnhbtfc4k2v2fsl0s3rm7uc7w3 foreign key (backlog_id) references backlog (id)
