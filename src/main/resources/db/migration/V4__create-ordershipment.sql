drop table if exists book_order_shipment;

CREATE TABLE book_order_shipment
(
    id                 VARCHAR(36) NOT NULL PRIMARY KEY,
    book_order_id      VARCHAR(36) UNIQUE,
    tracking_number    VARCHAR(50),
    created_date       TIMESTAMP,
    last_modified_date DATETIME(6) DEFAULT NULL,
    version            BIGINT      DEFAULT NULL,
    CONSTRAINT bos_pk FOREIGN KEY (book_order_id) REFERENCES book_order (id)
) ENGINE = InnoDB;

ALTER TABLE book_order
    ADD COLUMN book_order_shipment_id VARCHAR(36);

ALTER TABLE book_order
    ADD CONSTRAINT bos_shipment_fk
        FOREIGN KEY (book_order_shipment_id) REFERENCES book_order_shipment (id);