drop table if exists customer;
drop table if exists book_order_line;
drop table if exists book_order;

create table customer (
                          id varchar(36) not null,
                          created_date datetime(6),
                          name varchar(255),
                          update_date datetime(6),
                          version integer,
                          primary key (id)
) engine=InnoDB;

CREATE TABLE `book_order`
(
    id                 varchar(36) NOT NULL,
    created_date       datetime(6)  DEFAULT NULL,
    customer_ref       varchar(255) DEFAULT NULL,
    last_modified_date datetime(6)  DEFAULT NULL,
    version            bigint       DEFAULT NULL,
    customer_id        varchar(36)  DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (customer_id) REFERENCES customer (id)
) ENGINE = InnoDB;

CREATE TABLE `book_order_line`
(
    id                 varchar(36) NOT NULL,
    book_id            varchar(36) DEFAULT NULL,
    created_date       datetime(6) DEFAULT NULL,
    last_modified_date datetime(6) DEFAULT NULL,
    order_quantity     int         DEFAULT NULL,
    quantity_allocated int         DEFAULT NULL,
    version            bigint      DEFAULT NULL,
    book_order_id      varchar(36) DEFAULT NULL,
    PRIMARY KEY (id),
    CONSTRAINT FOREIGN KEY (book_order_id) REFERENCES book_order (id),
    CONSTRAINT FOREIGN KEY (book_id) REFERENCES book (id)
) ENGINE = InnoDB;