CREATE DATABASE IF NOT EXISTS armaDB;
USE armaDB;

-- Table Creation --
create table if not exists config (
      id bigint not null auto_increment,
       created_date datetime,
        modified_date datetime,
        name varchar(255),
        note varchar(20000),
        type varchar(255),
        primary key (id)
    );

create table if not exists event (
       id bigint not null auto_increment,
        created_date datetime,
        description varchar(255),
        modified_date datetime,
        name varchar(255),
        primary key (id)
    )

-- Data Creation --
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (1, '2020-09-03 01:57:45', null, 'O_Pilot_F', 'opfor vanilla pilot', 'infantry');
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (2, '2020-09-03 01:57:46', null, 'B_Pilot_F', 'blufor vanilla pilot', 'infantry');
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (3, '2020-09-03 01:57:47', null, 'I_Pilot_F', 'guer vanilla pilot', 'infantry');
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (4, '2020-09-03 01:57:48', null, 'CUP_I_GUE_Officer', 'guer cup officer', 'infantry');
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (5, '2020-09-03 01:57:49', null, 'CUP_O_RU_Crew', 'opfor cup crew', 'infantry');

INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (6, '2020-09-03 01:57:55', null, 'C_Boat_Civil_01_f', 'civilian vanilla boat', 'vehicle');
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (7, '2020-09-03 01:57:56', null, 'CUP_I_V3S_Rearm_TKG', 'guer cup rearm truck', 'vehicle');
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (8, '2020-09-03 01:57:57', null, 'CUP_O_UAZ_MG_RU', 'opfor cup uaz mg', 'vehicle');
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (9, '2020-09-03 01:57:58', null, 'CUP_B_HMMWV_TOW_USMC', 'blufor cup hummer tow', 'vehicle');
INSERT INTO armaDB.config (id, created_date, modified_date, name, note, type) VALUES (10, '2020-09-03 01:57:59', null, 'CUP_B_M113_USA', 'blufor cup m113 apc', 'vehicle');

INSERT INTO armaDB.event (id, created_date, description, modified_date, name) VALUES (1, '2020-09-10 23:06:40', 'description1', null, 'event1');
INSERT INTO armaDB.event (id, created_date, description, modified_date, name) VALUES (2, '2020-09-10 23:06:41', 'description2', null, 'event2');
INSERT INTO armaDB.event (id, created_date, description, modified_date, name) VALUES (3, '2020-09-10 23:06:42', 'description3', null, 'event3');
INSERT INTO armaDB.event (id, created_date, description, modified_date, name) VALUES (4, '2020-09-10 23:06:43', 'description4', null, 'event4');
INSERT INTO armaDB.event (id, created_date, description, modified_date, name) VALUES (5, '2020-09-10 23:06:44', 'description5', null, 'event5');
