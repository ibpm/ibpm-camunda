/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.27-log : Database - ibpm-camunda
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `ibpm_calendar` */

CREATE TABLE `ibpm_calendar` (
  `CALENDAR_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `DISPLAY_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_calendar_day` */

CREATE TABLE `ibpm_calendar_day` (
  `CALENDAR_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `DAY_NUM` int(8) NOT NULL,
  `REMARK` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`CALENDAR_NAME`,`DAY_NUM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_execution` */

CREATE TABLE `ibpm_execution` (
  `ID` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROC_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TRIGGER_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME` bigint(13) DEFAULT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `DURATION` bigint(20) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `CODE` int(11) DEFAULT NULL,
  `MSG` text COLLATE utf8_bin,
  `PARENT_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCHEDULER_URI` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTOR_URI` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_PROC_INST_ID` (`PROC_INST_ID`),
  KEY `IDX_JOB_NAME` (`JOB_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_instance` */

CREATE TABLE `ibpm_instance` (
  `ID` varchar(64) COLLATE utf8_bin NOT NULL,
  `PROC_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SUPER_PROC_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `JOB_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `TRIGGER_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_DEF_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME` bigint(13) DEFAULT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `DURATION` bigint(20) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `CODE` int(11) DEFAULT NULL,
  `MSG` text COLLATE utf8_bin,
  `PARENT_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `SCHEDULER_URI` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `EXECUTOR_URI` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `IDX_PROC_INST_ID` (`PROC_INST_ID`),
  KEY `IDX_JOB_NAME` (`JOB_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_instance_act` */

CREATE TABLE `ibpm_instance_act` (
  `ID` varchar(64) COLLATE utf8_bin NOT NULL,
  `ACT_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PARENT_ACT_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `PROC_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ROOT_PROC_INST_ID` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `ACT_ID` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `ACT_TYPE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `START_TIME` bigint(13) DEFAULT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `DURATION` bigint(20) DEFAULT NULL,
  `STATUS` int(11) DEFAULT NULL,
  `LOG_KEY` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `LOG_TEXT` text COLLATE utf8_bin,
  `BIZ_URI` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `IDX_PROC_INST_ID` (`PROC_INST_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_job` */

CREATE TABLE `ibpm_job` (
  `JOB_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `DISPLAY_NAME` varchar(255) COLLATE utf8_bin NOT NULL,
  `CONTENT` text COLLATE utf8_bin,
  `STATUS` int(11) DEFAULT NULL,
  `UPDATE_TIME` bigint(13) DEFAULT NULL,
  `REMARK` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`JOB_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_proxy_proc` */

CREATE TABLE `ibpm_proxy_proc` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `RELATION_ID_` int(64) DEFAULT NULL COMMENT '表natasha_proxy_relation的ID_值',
  `PROC_DEF_KEY_` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '流程名称',
  PRIMARY KEY (`ID_`),
  UNIQUE KEY `UNIQUE_NATASHA_PROXY_PROC_RELATION_PROC` (`RELATION_ID_`,`PROC_DEF_KEY_`),
  KEY `IDX_PROXY_PROC_RELATION_ID` (`RELATION_ID_`),
  KEY `IDX_PROXY_PROC_PROC_DEF_KEY` (`PROC_DEF_KEY_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_proxy_relation` */

CREATE TABLE `ibpm_proxy_relation` (
  `ID_` varchar(64) NOT NULL COMMENT '主键',
  `OPER_USER_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作员账号',
  `OPER_USER_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '操作员中文名',
  `CREATE_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '创建时间',
  `GRANTOR_` varchar(150) COLLATE utf8_bin DEFAULT NULL COMMENT '委托人账号',
  `GRANTOR_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '委托人中文名',
  `SURROGATOR_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '被委托人账号',
  `SURROGATOR_NAME_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '被委托人中文名',
  `UPDATE_TIME_` timestamp(3) NULL DEFAULT NULL COMMENT '更新时间',
  `STATUS_` char(2) COLLATE utf8_bin DEFAULT NULL COMMENT '0:正常，1失效，2删除',
  `COMMENT_` varchar(4000) COLLATE utf8_bin DEFAULT NULL COMMENT '委托意见/说明/备注',
  `TENANT_ID_` varchar(255) COLLATE utf8_bin DEFAULT NULL COMMENT '租户编号',
  PRIMARY KEY (`ID_`),
  KEY `IDX_PROXY_RELATION_GRANTOR` (`GRANTOR_`),
  KEY `IDX_PROXY_RELATION_STATUS` (`STATUS_`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_role` */

CREATE TABLE `ibpm_role` (
  `ROLE_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `DISPLAY_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `REMARK` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ROLE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_role_resource` */

CREATE TABLE `ibpm_role_resource` (
  `ROLE_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `RESOURCE_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ROLE_NAME`,`RESOURCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_role_user` */

CREATE TABLE `ibpm_role_user` (
  `ROLE_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `USER_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`ROLE_NAME`,`USER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `ibpm_user` */

CREATE TABLE `ibpm_user` (
  `USER_NAME` varchar(64) COLLATE utf8_bin NOT NULL,
  `DISPLAY_NAME` varchar(255) COLLATE utf8_bin NOT NULL,
  `PASSWORD` varchar(255) COLLATE utf8_bin NOT NULL,
  `EMAIL` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `MOBILE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `STATUS` int(11) NOT NULL,
  `SUPER_ADMIN` tinyint(1) DEFAULT NULL,
  `SEND_CONFIG` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`USER_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*Table structure for table `log_op` */

CREATE TABLE `log_op` (
  `ID` varchar(64) COLLATE utf8_bin NOT NULL,
  `USER_NAME` varchar(64) COLLATE utf8_bin DEFAULT NULL,
  `CLASS_NAME` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `METHOD` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `TAG` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `OPERATION` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PATH` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `PARAM` text COLLATE utf8_bin,
  `RESULT` text COLLATE utf8_bin,
  `CODE` int(11) DEFAULT NULL,
  `MSG` text COLLATE utf8_bin,
  `HANDLE_TIME` bigint(13) DEFAULT NULL,
  `DURATION` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `IP` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
