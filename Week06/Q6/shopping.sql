CREATE DATABASE IF NOT EXISTS `eshop` DEFAULT CHARACTER SET utf8mb4 DEFAULT COLLATE utf8mb4_unicode_ci;

USE `eshop`;

--- 用户表 ---
CREATE TABLE IF NOT EXISTS `customer_account` (
  `account_id`          varchar(32) NOT NULL COMMENT '用户账号id',
  `account_name`        varchar(32) NOT NULL COMMENT '用户名称',
  `phone_number`        varchar(32) DEFAULT NULL COMMENT '用户手机',
  `account_pwd`         varchar(64) NOT NULL COMMENT '登录密码密文',
  `gender`              tinyint(3) unsigned DEFAULT NULL COMMENT '性别，枚举值',
  `birthday`            datetime DEFAULT NULL COMMENT '生日，精确到：年/月/日',
  `address`             varchar(64) DEFAULT NULL COMMENT '用户收货地址',
  `create_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新时间',
  PRIMARY KEY (`account_id`)
) ENGINE = InnoDB;

--- 商品表 ---
CREATE TABLE IF NOT EXISTS `goods` (
  `goods_id`            varchar(32) NOT NULL COMMENT '商品唯一性id',
  `goods_name`          varchar(32) NOT NULL COMMENT '商品名称',
  `goods_desc`          varchar(128) NOT NULL COMMENT '商品描述',
  `goods_category_id`      varchar(32) NOT NULL COMMENT '商品类别id',
  `price`               double NOT NULL COMMENT '单价，单位元',
  `stock_number`        double NOT NULL COMMENT '剩余库存',
  `goods_supplier_id`   varchar(32) NOT NULL COMMENT '商品供应商id',
  `create_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新时间',
  PRIMARY KEY (`goods_id`)
) ENGINE = InnoDB;

--- 商品分类表 ---
CREATE TABLE IF NOT EXISTS `goods_catetory` (
  `category_id`         varchar(32) NOT NULL COMMENT '商品分类id',
  `category_name`       varchar(32) NOT NULL COMMENT '商品分类名称',
  `create_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新时间',
  PRIMARY KEY (`category_id`)
) ENGINE = InnoDB;

--- 供应商表 ---
CREATE TABLE IF NOT EXISTS `goods_supplier` (
  `supplier_id`         varchar(32) NOT NULL COMMENT '供应商id',
  `supplier_name`       varchar(32) NOT NULL COMMENT '供应商名称',
  `supplier_address`    varchar(64) DEFAULT NULL COMMENT '供应商地址',
  `supplier_phone`      varchar(16) DEFAULT NULL COMMENT '供应商联系电话',
  `create_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新时间',
  PRIMARY KEY (`supplier_id`)
) ENGINE = InnoDB;

--- 订单信息表 ---
CREATE TABLE IF NOT EXISTS `order` (
  `order_id`            varchar(32) NOT NULL COMMENT '订单id',
  `account_id`          varchar(32) NOT NULL COMMENT '订单用户id',
  `order_time`          timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
  `price_total`         double NOT NULL COMMENT '订单总价',
  `goods_number`        int NOT NULL COMMENT '商品个数',
  `pay_time`            timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '支付时间',
  `status`              int NOT NULL COMMENT '订单状态，0 - 待支付，1 - 已支付，2 - 已取消，3 - 已完成，4 - 售后中',
  `create_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新时间',
  PRIMARY KEY (`order_id`);
) ENGINE = InnoDB;

--- 订单商品表 ---
CREATE TABLE IF NOT EXISTS `order_goods` (
  `order_id`            varchar(32) NOT NULL COMMENT '订单id',
  `goods_id`            varchar(32) NOT NULL COMMENT '商品id',
  `goods_number`        double NOT NULL COMMENT '商品个数',
  `create_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `modify_time`         timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最近更新时间',
  KEY `order_key` (`order_id`)
) ENGINE = InnoDB;