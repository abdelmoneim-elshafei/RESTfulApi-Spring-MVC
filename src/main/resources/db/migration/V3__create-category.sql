drop table if exists category;
drop table if exists book_category;

create table category
(
    id                 varchar(36) NOT NULL PRIMARY KEY,
    description        varchar(50),
    created_date       timestamp,
    last_modified_date datetime(6) DEFAULT NULL,
    version            bigint      DEFAULT NULL
) ENGINE = InnoDB;

create table book_category
(
    book_id     varchar(36) NOT NULL,
    category_id varchar(36) NOT NULL,
    primary key (book_id, category_id),
    constraint pc_book_id_fk FOREIGN KEY (book_id) references book (id),
    constraint pc_category_id_fk FOREIGN KEY (category_id) references category (id)
) ENGINE = InnoDB;