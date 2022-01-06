CREATE TABLE `soul_document`
(
    `id`           bigint                                  NOT NULL AUTO_INCREMENT,
    `gmt_created`  datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime                                NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    `is_deleted`   tinyint                                 NOT NULL DEFAULT '0' COMMENT '是否删除：0-未删除 1-已删除',
    `title`        varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
    `category`     varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类',
    `content`      text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '内容',
    `images`       varchar(255) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'oss路径',
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_general_ci COMMENT ='文档记录';