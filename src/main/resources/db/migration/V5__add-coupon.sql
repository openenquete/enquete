CREATE TABLE coupon
(
    coupon_id  BINARY(16)   NOT NULL,
    seminar_id BINARY(16)   NOT NULL,
    username   VARCHAR(255) NOT NULL,
    created_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (coupon_id),
    FOREIGN KEY (seminar_id) REFERENCES seminar (seminar_id)
)
    ENGINE InnoDB;

CREATE TABLE coupon_used
(
    used_id    BINARY(16) NOT NULL,
    coupon_id  BINARY(16) NOT NULL,
    created_at DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME   NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (used_id),
    FOREIGN KEY (coupon_id) REFERENCES coupon (coupon_id)
)
    ENGINE InnoDB;
