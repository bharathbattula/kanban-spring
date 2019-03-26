

# Test script
DROP TABLE IF EXISTS int_test_category;
CREATE TABLE int_test_category (
  test_category_id varchar(50) NOT NULL,
  is_deleted int(1) DEFAULT 0,
  last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by varchar(50) DEFAULT NULL,
  PRIMARY KEY (test_category_id),
  KEY FK1 (updated_by),
  CONSTRAINT FK1 FOREIGN KEY (updated_by) REFERENCES int_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_test_category_i18n;
CREATE TABLE int_test_category_i18n (
  test_category_id varchar(50) NOT NULL,
  test_category_name varchar(200) NOT NULL,
  language_id int(11) NOT NULL DEFAULT '1',
  last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by varchar(50) DEFAULT NULL,
  PRIMARY KEY (test_category_id,language_id),
  KEY FK2_i18n (updated_by),
  CONSTRAINT FK1_i18n FOREIGN KEY (test_category_id) REFERENCES int_test_category (test_category_id),
  CONSTRAINT FK2_i18n FOREIGN KEY (updated_by) REFERENCES int_user (user_id),
  CONSTRAINT FK3_int_test_category_i18n_language FOREIGN KEY (language_id) REFERENCES language (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_test;
CREATE TABLE int_test (
  test_id varchar(50) NOT NULL,
  is_default int(1) DEFAULT NULL,
  test_category_id varchar(50) NOT NULL,
  is_deleted int(1) DEFAULT '0',  
  `test_highlight_logic` varchar(20) NOT NULL DEFAULT 'HG',
  last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by varchar(50) DEFAULT NULL,
  PRIMARY KEY (test_id),
  KEY FK1_tm (test_category_id),
  KEY FK2_tm (updated_by),
  CONSTRAINT FK1_tm FOREIGN KEY (test_category_id) REFERENCES int_test_category (test_category_id),
  CONSTRAINT FK2_tm FOREIGN KEY (updated_by) REFERENCES int_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_test_i18n;
CREATE TABLE int_test_i18n (
  test_id varchar(50) NOT NULL,
  test_name text CHARACTER SET latin1,
  language_id int(11) NOT NULL DEFAULT '1',
  last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by varchar(50) DEFAULT NULL,
  PRIMARY KEY (test_id,language_id),
  KEY FK2_tm_i18n (updated_by),
  CONSTRAINT FK1_tm_i18n FOREIGN KEY (test_id) REFERENCES int_test (test_id),
  CONSTRAINT FK2_tm_i18n FOREIGN KEY (updated_by) REFERENCES int_user (user_id),
  CONSTRAINT FK3_int_test_i18n_language FOREIGN KEY (language_id) REFERENCES language (language_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_product_test_values;
CREATE TABLE int_product_test_values (
  product_id varchar(50) NOT NULL,
  test_standard_id varchar(50) NOT NULL,
  value varchar(100) DEFAULT NULL,
  is_deleted int(1) DEFAULT 0,
  last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by varchar(50) DEFAULT NULL,
  PRIMARY KEY (product_id,test_standard_id),
  KEY FK2_ptv (test_standard_id),
  KEY FK3_ptv (updated_by),
  CONSTRAINT FK1_ptv FOREIGN KEY (product_id) REFERENCES int_product (product_id),
  CONSTRAINT FK2_ptv FOREIGN KEY (test_standard_id) REFERENCES int_test_standard_assoc (test_standard_id),
  CONSTRAINT FK3_ptv FOREIGN KEY (updated_by) REFERENCES int_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_requested_other_product;
CREATE TABLE int_requested_other_product (
  request_id int(10) unsigned NOT NULL AUTO_INCREMENT,
  region_id int(5) DEFAULT NULL,
  other_product_id varchar(50) DEFAULT NULL,
  other_product_name text CHARACTER SET latin1 NOT NULL,
  other_product_style text CHARACTER SET latin1,
  product_category_id int(11) DEFAULT NULL,
  product_brand_id varchar(50) DEFAULT NULL,
  product_brand_name text CHARACTER SET latin1,
  product_manufacturer_id varchar(50) DEFAULT NULL,
  product_manufacturer_name text CHARACTER SET latin1,
  benchmark_testing_required int(1) DEFAULT '1',
  customer_name text CHARACTER SET latin1,
  supports_sample_procurements int(1) DEFAULT '1',
  urgency_level int(1) DEFAULT '1',
  results_needby_date datetime DEFAULT NULL,
  tests_tobe_performed text CHARACTER SET latin1,
  additional_formation text CHARACTER SET latin1,
  send_copy_of_response int(1) DEFAULT '1',
  status int(1) DEFAULT '1',
  is_deleted int(1) DEFAULT '0',
  last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by varchar(50) DEFAULT NULL,
  PRIMARY KEY (request_id),
  KEY FK1_rop (product_category_id),
  KEY FK2_rop (product_brand_id),
  KEY FK3_rop (product_manufacturer_id),
  KEY FK4_rop (updated_by),
  KEY FK5_rop (region_id),
  CONSTRAINT FK1_rop FOREIGN KEY (product_category_id) REFERENCES int_product_category (product_category_id),
  CONSTRAINT FK2_rop FOREIGN KEY (product_brand_id) REFERENCES int_product_brand (product_brand_id),
  CONSTRAINT FK3_rop FOREIGN KEY (product_manufacturer_id) REFERENCES gn_product_manufacturer (product_manufacturer_id),
  CONSTRAINT FK4_rop FOREIGN KEY (updated_by) REFERENCES int_user (user_id),
  CONSTRAINT FK5_rop FOREIGN KEY (region_id) REFERENCES int_region (region_id) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=1006 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_product_test_data_request;
CREATE TABLE int_product_test_data_request (
  product_id varchar(50) NOT NULL,
  test_standard_id varchar(50) NOT NULL,
  request_id varchar(50) NOT NULL,
  status int(1) DEFAULT 1,
  link_to_opportunity varchar(15),
  is_benchmark_testing_required int(1) DEFAULT 0,
  is_benchmark_testing_customer int(1) DEFAULT 0,
  is_sample_procurement_supported int(1) DEFAULT 0,
  urgency int(1) DEFAULT 0,
  when_test_results_needed timestamp DEFAULT CURRENT_TIMESTAMP,
  send_copy_of_response int(1) DEFAULT 0,
  last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  updated_by varchar(50) DEFAULT NULL,
  PRIMARY KEY (product_id,test_standard_id),
  KEY FK2_ptdr (test_standard_id),
  KEY FK3_ptdr (updated_by),
  CONSTRAINT FK1_ptdr FOREIGN KEY (product_id) REFERENCES int_product (product_id),
  CONSTRAINT FK2_ptdr FOREIGN KEY (test_standard_id) REFERENCES int_test_standard_assoc (test_standard_id),
  CONSTRAINT FK3_ptdr FOREIGN KEY (updated_by) REFERENCES int_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_product_specification;
CREATE TABLE int_product_specification(
	specification_id varchar(50) NOT NULL,
	product_category_id varchar(50) NOT NULL,
	is_default varchar(5) NOT NULL default 'NO',
	is_deleted int(1) DEFAULT 0,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	updated_by varchar(50) DEFAULT NULL,
	created_by varchar(50) DEFAULT NULL,
	PRIMARY KEY (specification_id),
	CONSTRAINT FK7_ptdr FOREIGN KEY (updated_by) REFERENCES int_user (user_id),
  CONSTRAINT FK8_ptdr FOREIGN KEY (created_by) REFERENCES int_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_product_specification_i18n;
CREATE TABLE int_product_specification_i18n(
	specification_id varchar(50) NOT NULL,	
	language_id int(11) NOT NULL DEFAULT '1',
	specification_name varchar(50) NOT NULL,
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	updated_by varchar(50) DEFAULT NULL,	
	PRIMARY KEY (specification_id,language_id),
	KEY FK2_ptdr (updated_by),    
	CONSTRAINT FK8_int_product_specification_i18n_int_user FOREIGN KEY (updated_by) REFERENCES int_user (user_id),  
	CONSTRAINT FK3_int_product_i18n_language FOREIGN KEY (language_id) REFERENCES language (language_id)
	
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_product_specification_value;
CREATE TABLE int_product_specification_value(
	specification_id varchar(50) NOT NULL,	
	product_id varchar(50) NOT NULL,
	product_specification_value varchar(200) NOT NULL,
	is_deleted int(1) DEFAULT 0,	
	last_updated_ts timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	updated_by varchar(50) DEFAULT NULL,	
	PRIMARY KEY (specification_id,product_id),
	KEY FK2_ptdr (updated_by),    
	CONSTRAINT FK1_int_product_specification_value_int_product FOREIGN KEY (product_id) REFERENCES int_product (product_id),
	CONSTRAINT FK2_int_product_specification_value_int_user FOREIGN KEY (updated_by) REFERENCES int_user (user_id)    
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_user_ask;
CREATE TABLE int_user_ask (
  user_id varchar(50) NOT NULL,  
  ask_access_flag int(1) NOT NULL DEFAULT '1',
  is_deleted int(1) NOT NULL DEFAULT '0',
  last_update_ts datetime DEFAULT NULL,
  updated_by varchar(50) DEFAULT NULL,  
  PRIMARY KEY (user_id),
  CONSTRAINT FK_int_user_ask_int_user FOREIGN KEY (user_id) REFERENCES int_user (user_id) ON DELETE NO ACTION ON UPDATE NO ACTION
  )ENGINE=InnoDB DEFAULT CHARSET=utf8;
  
  DROP TABLE IF EXISTS int_product_category_standard_ask_assoc;
  CREATE TABLE int_product_category_standard_ask_assoc (
  product_category_id int(20) NOT NULL,
  standard_id int(20) NOT NULL,
  is_deleted int(1) DEFAULT '0',
  PRIMARY KEY (product_category_id,standard_id),
  KEY FK2_PCSA (standard_id),
  CONSTRAINT FK1_PCSA FOREIGN KEY (product_category_id) REFERENCES int_product_category (product_category_id),
  CONSTRAINT FK2_PCSA FOREIGN KEY (standard_id) REFERENCES crms_standard (standard_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_test_region_assoc;
CREATE TABLE int_test_region_assoc (
  test_id varchar(50) NOT NULL,
  region_id int(11) NOT NULL,
  is_deleted int(1) DEFAULT '0',
  updated_by varchar(100) DEFAULT NULL,
  last_updated_ts datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (test_id,region_id),
  KEY TRA_FK2 (region_id),
  KEY TRA_FK3 (updated_by),
  CONSTRAINT TRA_FK1 FOREIGN KEY (test_id) REFERENCES int_test (test_id),
  CONSTRAINT TRA_FK2 FOREIGN KEY (region_id) REFERENCES int_region (region_id),
  CONSTRAINT TRA_FK3 FOREIGN KEY (updated_by) REFERENCES int_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS int_test_standard_assoc;
CREATE TABLE int_test_standard_assoc (
  test_standard_id varchar(50) NOT NULL,
  test_id varchar(50) NOT NULL,
  standard_id int(11) NOT NULL,
  is_deleted int(1) DEFAULT '0',
  updated_by varchar(100) DEFAULT NULL,
  last_updated_ts datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (test_standard_id),
  KEY TSA_FK2 (standard_id),
  KEY TSA_FK3 (updated_by),
  KEY TSA_FK1 (test_id),
  CONSTRAINT TSA_FK1 FOREIGN KEY (test_id) REFERENCES int_test (test_id),
  CONSTRAINT TSA_FK2 FOREIGN KEY (standard_id) REFERENCES crms_standard (standard_id),
  CONSTRAINT TSA_FK3 FOREIGN KEY (updated_by) REFERENCES int_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP VIEW  IF EXISTS `view_search_asktests`;
CREATE VIEW `view_search_asktests`
AS
   SELECT `it`.`test_id`                AS `testId`,
          coalesce(concat(convert(`iti18n`.`test_name` USING utf8),
                          ' (',
                          `cs`.`name`,
                          ')'))
             AS `testName`,
          `tci18n`.`test_category_name` AS `testCategoryName`,
          `it`.`test_category_id`       AS `testCategoryId`,
          `itsa`.`test_standard_id`     AS `testStandardId`,
          `itra`.`region_id`            AS `regionId`,
          `cpsa`.`product_category_id`  AS `productCategoryId`,
          `cs`.`standard_id`            AS `standardId`
   FROM ((((((`integration`.`int_test`    `it`
              JOIN `integration`.`int_test_i18n` `iti18n`)
             JOIN `integration`.`int_test_category_i18n` `tci18n`)
            JOIN `integration`.`crms_standard` `cs`)
           JOIN `integration`.`int_test_standard_assoc` `itsa`)
          JOIN `integration`.`int_test_region_assoc` `itra`)
         JOIN `integration`.`crms_product_standard_association` `cpsa`)
   WHERE (    (`it`.`is_deleted` = 0)
          AND (`it`.`test_id` = `iti18n`.`test_id`)
          AND (`it`.`test_category_id` = `tci18n`.`test_category_id`)
          AND (`itsa`.`standard_id` = `cs`.`standard_id`)
          AND (`itsa`.`test_id` = `it`.`test_id`)
          AND (`it`.`test_id` = `itra`.`test_id`)
          AND (`cpsa`.`standard_id` = `cs`.`standard_id`));
  