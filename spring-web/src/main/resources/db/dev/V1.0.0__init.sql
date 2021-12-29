CREATE TABLE `tb_count`
(
    `id`           bigint   NOT NULL AUTO_INCREMENT,
    `gmt_created`  datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_deleted`   tinyint  NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除 1-已删除',
    `num`          int      NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci;