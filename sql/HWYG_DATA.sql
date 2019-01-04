/*
 Navicat Premium Data Transfer

 Source Server         : hwyg-data
 Source Server Type    : Oracle
 Source Server Version : 110200
 Source Host           : 192.168.0.161:1521
 Source Schema         : HWYG_DATA

 Target Server Type    : Oracle
 Target Server Version : 110200
 File Encoding         : 65001

 Date: 04/01/2019 10:30:19
*/


-- ----------------------------
-- Table structure for T_KPI
-- ----------------------------
DROP TABLE "HWYG_DATA"."T_KPI";
CREATE TABLE "HWYG_DATA"."T_KPI" (
  "CODE" NUMBER(6) NOT NULL ,
  "DATABASE" VARCHAR2(64 BYTE) ,
  "SQL" VARCHAR2(3600 BYTE) ,
  "DESCRIPTION" VARCHAR2(255 BYTE) ,
  "QUERY_PARAMS" VARCHAR2(255 BYTE) 
)
TABLESPACE "HWYG"
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "HWYG_DATA"."T_KPI"."CODE" IS 'kpi编码';
COMMENT ON COLUMN "HWYG_DATA"."T_KPI"."DATABASE" IS '数据库名称';
COMMENT ON COLUMN "HWYG_DATA"."T_KPI"."SQL" IS '执行sql';
COMMENT ON COLUMN "HWYG_DATA"."T_KPI"."DESCRIPTION" IS '指标功能描述';
COMMENT ON COLUMN "HWYG_DATA"."T_KPI"."QUERY_PARAMS" IS '查询参数';
COMMENT ON TABLE "HWYG_DATA"."T_KPI" IS 'kpi统计指标表';

-- ----------------------------
-- Records of "T_KPI"
-- ----------------------------
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('1', 'trade', 'select count(o.id) as count from t_order o
		where o.is_delete = 0
		and o.create_time >= to_date(to_char(trunc(sysdate), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		and o.create_time <= to_date(to_char(trunc(sysdate+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')', '今日新增订单', NULL);
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('2', 'trade', 'select nvl(sum
		    (
		      case when od.unit=''斤'' then od.amount
		      when od.unit=''公斤'' then od.amount*2
		      when od.unit=''吨'' then od.amount*2000
		      end
		    ),0) as amount
		from t_order_detail od
		where od.is_delete = 0
		and od.order_no in
		    (
		      select o.order_no from t_order o
		      where o.is_delete = 0
		            and o.create_time >= to_date(to_char(trunc(sysdate), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		            and o.create_time <= to_date(to_char(trunc(sysdate+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		    )', '今日新增交易量', NULL);
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('3', 'trade', 'select nvl(sum(o.price),0) as price from t_order o
		where o.is_delete = 0
	    and o.create_time >= to_date(to_char(trunc(sysdate), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
	    and o.create_time <= to_date(to_char(trunc(sysdate+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')', '今日新增交易额', NULL);
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('4', 'trade', 'select count(o.id) as count from t_order o
		where o.is_delete = 0
		and o.create_time >= to_date(to_char(trunc(sysdate, ''mm''), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		and o.create_time <= to_date(to_char(last_day(trunc(sysdate))+1, ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')', '本月新增订单', NULL);
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('5', 'trade', 'select nvl(sum
		       (
		           case when od.unit=''斤'' then od.amount
		           when od.unit=''公斤'' then od.amount*2
		           when od.unit=''吨'' then od.amount*2000
		           end
		       ),0) as amount
		from t_order_detail od
		where od.is_delete = 0
		      and od.order_no in
		          (
		            select o.order_no from t_order o
		            where o.is_delete = 0
		                  and o.create_time >= to_date(to_char(trunc(sysdate, ''mm''), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		                  and o.create_time <= to_date(to_char(last_day(trunc(sysdate))+1, ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          )', '本月新增交易量', NULL);
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('6', 'trade', 'select nvl(sum(o.price),0) as price from t_order o
		where o.is_delete = 0
	    and o.create_time >= to_date(to_char(trunc(sysdate, ''mm''), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
	    and o.create_time <= to_date(to_char(last_day(trunc(sysdate))+1, ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')', '本月新增交易额', NULL);
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('7', 'trade', 'select a.time as time,
		  case when a.time=b.time then b.count else a.count end as count,
		  case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice
		from
		  (
		    SELECT
		      to_char(to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')+level-1, ''yyyy-mm-dd'') as time,
		      0 as count, 0 as totalPrice
		    FROM
		      dual
		    CONNECT BY
		      level <= to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		               -to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')+1
		  ) a
		  left join
		  (
		    select ''0'' as time,0 as count,0 as totalPrice from dual
		    union
		    select to_char(o.create_time, ''yyyy-mm-dd'') as time,count(o.id) as count,sum(o.price) as totalPrice from t_order o
		    where o.is_delete = 0
		          and o.order_type = 1
		          and o.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          and o.create_time <= to_date(to_char(trunc(:end+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		    group by to_char(o.create_time, ''yyyy-mm-dd'')
		  ) b
		  on a.time = b.time
		  order by a.time', '平台订单量与交易额统计(竞价交易，以日为单位)', 'start,end');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('8', 'trade', 'select a.time as time,
		  case when a.time=b.time then b.count else a.count end as count,
		  case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice
		from
		  (
		    SELECT
		      to_char(to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')+level-1, ''yyyy-mm-dd'') as time,
		      0 as count, 0 as totalPrice
		    FROM
		      dual
		    CONNECT BY
		      level <= to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		               -to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')+1
		  ) a
		  left join
		  (
		    select ''0'' as time,0 as count,0 as totalPrice from dual
		    union
		    select to_char(o.create_time, ''yyyy-mm-dd'') as time,count(o.id) as count,sum(o.price) as totalPrice from t_order o
		    where o.is_delete = 0
		          and o.order_type in (2,3)
		          and o.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          and o.create_time <= to_date(to_char(trunc(:end+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		    group by to_char(o.create_time, ''yyyy-mm-dd'')
		  ) b
		  on a.time = b.time
		  order by a.time', '平台订单量与交易额统计(非定向交易，以日为单位)', 'start,end');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('9', 'trade', 'select a.time as time,
		  case when a.time=b.time then b.count else a.count end as count,
		  case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice
		from
		  (
		    SELECT
		      to_char(to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')+level-1, ''yyyy-mm-dd'') as time,
		      0 as count, 0 as totalPrice
		    FROM
		      dual
		    CONNECT BY
		      level <= to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		               -to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')+1
		  ) a
		  left join
		  (
		    select ''0'' as time,0 as count,0 as totalPrice from dual
		    union
		    select to_char(o.create_time, ''yyyy-mm-dd'') as time,count(o.id) as count,sum(o.price) as totalPrice from t_order o
		    where o.is_delete = 0
		          and o.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          and o.create_time <= to_date(to_char(trunc(:end+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		    group by to_char(o.create_time, ''yyyy-mm-dd'')
		  ) b
		  on a.time = b.time
		  order by a.time', '平台订单量与交易额统计(全部，以日为单位)', 'start,end');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('10', 'trade', 'select a.time as time,
		       case when a.time=b.time then b.count else a.count end as count,
		       case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice
		from
		  (
		    SELECT
		      to_char(add_months(to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss''),
		                         rownum - 1),
		              ''yyyy-mm'') as time,
		      0                  as count,
		      0                  as totalPrice
		    FROM dual
		    CONNECT BY rownum <=
		               months_between(
		                   to_date(to_char(last_day(trunc(:end)), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss''),
		                   to_date(to_char(trunc(:start, ''mm''), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')) + 1
		  ) a
		  left join
		  (
		    select ''0'' as time,0 as count,0 as totalPrice from dual
		    union
		    select to_char(trunc(o.create_time, ''mm''), ''yyyy-mm'') as time,count(o.id) as orderNumber,sum(o.price) as totalPrice from t_order o
		    where o.is_delete = 0
		    	  and o.order_type = 1
		          and o.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          and o.create_time <= to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		    group by to_char(trunc(o.create_time, ''mm''), ''yyyy-mm'')
		  ) b
		  on a.time = b.time
		  order by a.time', '平台订单量与交易额统计(竞价交易，以月为单位)', 'start,end');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('11', 'trade', 'select a.time as time,
		       case when a.time=b.time then b.count else a.count end as count,
		       case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice
		from
		  (
		    SELECT
		      to_char(add_months(to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss''),
		                         rownum - 1),
		              ''yyyy-mm'') as time,
		      0                  as count,
		      0                  as totalPrice
		    FROM dual
		    CONNECT BY rownum <=
		               months_between(
		                   to_date(to_char(last_day(trunc(:end)), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss''),
		                   to_date(to_char(trunc(:start, ''mm''), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')) + 1
		  ) a
		  left join
		  (
		    select ''0'' as time,0 as count,0 as totalPrice from dual
		    union
		    select to_char(trunc(o.create_time, ''mm''), ''yyyy-mm'') as time,count(o.id) as orderNumber,sum(o.price) as totalPrice from t_order o
		    where o.is_delete = 0
		    	  and o.order_type in (2,3)
		          and o.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          and o.create_time <= to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		    group by to_char(trunc(o.create_time, ''mm''), ''yyyy-mm'')
		  ) b
		  on a.time = b.time
		  order by a.time', '平台订单量与交易额统计(非定向交易，以月为单位)', 'start,end');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('12', 'trade', 'select a.time as time,
		       case when a.time=b.time then b.count else a.count end as count,
		       case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice
		from
		  (
		    SELECT
		      to_char(add_months(to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss''),
		                         rownum - 1),
		              ''yyyy-mm'') as time,
		      0                  as count,
		      0                  as totalPrice
		    FROM dual
		    CONNECT BY rownum <=
		               months_between(
		                   to_date(to_char(last_day(trunc(:end)), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss''),
		                   to_date(to_char(trunc(:start, ''mm''), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')) + 1
		  ) a
		  left join
		  (
		    select ''0'' as time,0 as count,0 as totalPrice from dual
		    union
		    select to_char(trunc(o.create_time, ''mm''), ''yyyy-mm'') as time,count(o.id) as orderNumber,sum(o.price) as totalPrice from t_order o
		    where o.is_delete = 0
		          and o.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          and o.create_time <= to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		    group by to_char(trunc(o.create_time, ''mm''), ''yyyy-mm'')
		  ) b
		  on a.time = b.time
		  order by a.time', '平台订单量与交易额统计(全部，以月为单位)', 'start,end');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('13', 'trade', 'select nvl(sum(o.price),0) as price,count(o.id) as buyerNumber,count(o.id) as orderNumber from t_order o
		where o.is_delete = 0
		      and o.status in (3,4)
		      and o.seller_id = :userId
		      and o.create_time >= to_date(to_char(trunc(sysdate), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		      and o.create_time <= to_date(to_char(trunc(sysdate+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')', '今日支付金额、支付买家数、支付订单数', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('14', 'trade', 'select nvl(sum(o.price),0) as price,count(o.id) as buyerNumber,count(o.id) as orderNumber from t_order o
		where o.is_delete = 0
		      and o.status in (3,4)
		      and o.seller_id = :userId
		      and o.create_time >= to_date(to_char(trunc(sysdate-1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		      and o.create_time <= to_date(to_char(trunc(sysdate), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')', '昨日支付金额、支付买家数、支付订单数', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('15', 'trade', 'select nvl(sum
		       (
		           case when od.unit=''斤'' then od.amount
		           when od.unit=''公斤'' then od.amount*2
		           when od.unit=''吨'' then od.amount*2000
		           end
		       ),0) as amount
		from t_order_detail od
		where od.is_delete = 0
		      and od.order_no in
		          (
		            select o.order_no from t_order o
		            where o.is_delete = 0
		                  and o.status in (3,4)
		                  and o.seller_id = :userId
		                  and o.create_time >= to_date(to_char(trunc(sysdate), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		                  and o.create_time <= to_date(to_char(trunc(sysdate+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          )', '今日成交总量（斤）', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('16', 'trade', 'select nvl(sum
		       (
		           case when od.unit=''斤'' then od.amount
		           when od.unit=''公斤'' then od.amount*2
		           when od.unit=''吨'' then od.amount*2000
		           end
		       ),0) as amount
		from t_order_detail od
		where od.is_delete = 0
		      and od.order_no in
		          (
		            select o.order_no from t_order o
		            where o.is_delete = 0
		                  and o.status in (3,4)
		                  and o.seller_id = :userId
		                  and o.create_time >= to_date(to_char(trunc(sysdate-1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		                  and o.create_time <= to_date(to_char(trunc(sysdate), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		          )', '昨日成交总量（斤）', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('17', 'trade', 'select o.status,count(o.status) as count from t_order o
		where o.is_delete = 0
		      and o.order_type = 1
		      and o.seller_id = :userId
		      group by o.status
		      order by o.status', '交易订单（拍卖）', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('18', 'trade', 'select o.status,count(o.status) as count from t_order o
		where o.is_delete = 0
		      and o.order_type in (2,3)
		      and o.seller_id = :userId
		      group by o.status
		      order by o.status', '交易订单（非定向）', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('19', 'trade', 'select o.order_type as orderType,count(o.id) as count from t_order o
		where o.is_delete = 0
		      and o.seller_id = :userId
		      and o.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		      and o.create_time <= to_date(to_char(trunc(:end+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
		      group by o.order_type', '交易统计（订单量）', 'start,end,userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('20', 'trade', 'select a.orderType,sum
                   (
                       case when od.unit=''斤'' then od.amount
                       when od.unit=''公斤'' then od.amount*2
                       when od.unit=''吨'' then od.amount*2000
                       end
                   ) as amount from
  (
    select
      o.order_type as orderType,
      o.order_no
    from t_order o
    where o.is_delete = 0 and o.status = 4 
          and o.seller_id = :userId
          and o.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
          and o.create_time <= to_date(to_char(trunc(:end + 1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
  ) a
    left join t_order_detail od
  on a.order_no = od.order_no
  group by a.orderType', '交易统计（成交量）', 'start,end,userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('21', 'user', 'select oer.flow,sum(oer.total_amount) as price,
  case when oer.flow = 1 then ''收入''
       when oer.flow = 2 then ''支出''
  end as description
from t_out_entry_record oer
where oer.user_id = :userId
      and oer.create_time >= to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
      and oer.create_time <= to_date(to_char(trunc(:end+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
      group by oer.flow', '财务统计', 'start,end,userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('22', 'trade', 'select od.product_name,od.unit,od.unit_price,od.amount,od.price,o.buyer_id,o.buyer_name,o.order_type
from t_order_detail od
left join t_order o
on od.order_no = o.order_no
where od.is_delete = 0
      and od.order_no in
          (
            select o.order_no
            from t_order o
            where o.is_delete = 0
                  and o.order_type = 1
                  and o.status in (1, 2, 3, 4)
                  and o.create_time >= to_date(to_char(trunc(sysdate-7), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                  and o.create_time <= to_date(to_char(trunc(sysdate+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
            union all
            select o.order_no
            from t_order o
            where o.is_delete = 0
                  and o.order_type in (2, 3)
                  and o.status in (3, 4)
                  and o.create_time >= to_date(to_char(trunc(sysdate-7), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                  and o.create_time <= to_date(to_char(trunc(sysdate+1), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
          )
      order by od.create_time desc', '最新成交信息', NULL);
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('23', 'user', 'select u.sn from t_user u where u.id = :userId', '查询客户代码', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('24', 'trade', 'select * from
  (
    select
      od.product_id,
      od.product_name,
      max(case when od.unit=''斤'' then od.unit_price
          when od.unit=''公斤'' then od.unit_price/2
          when od.unit=''吨'' then od.unit_price/2000
          end) as max_price,
      min(case when od.unit=''斤'' then od.unit_price
          when od.unit=''公斤'' then od.unit_price/2
          when od.unit=''吨'' then od.unit_price/2000
          end) as min_price,
      avg(case when od.unit=''斤'' then od.unit_price
          when od.unit=''公斤'' then od.unit_price/2
          when od.unit=''吨'' then od.unit_price/2000
          end) as avg_price,
      sum(case when od.unit=''斤'' then od.amount
          when od.unit=''公斤'' then od.amount*2
          when od.unit=''吨'' then od.amount*2000
          end) as amount,
      sum(od.price) as total_price
    from t_order_detail od
    where od.is_delete = 0
          and od.order_no in
              (
                select o.order_no
                from t_order o
                where o.is_delete = 0
                      and o.order_type = 1
                      and o.status in (1, 2, 3, 4)
                      and o.create_time >=
                          to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                      and o.create_time <=
                          to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                union all
                select o.order_no
                from t_order o
                where o.is_delete = 0
                      and o.order_type in (2, 3)
                      and o.status in (3, 4)
                      and o.create_time >=
                          to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                      and o.create_time <=
                          to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
              )
    group by od.product_id, od.product_name
    order by max_price desc
  ) a
    where rownum <= 10', '交易行情-查询前10渔种', 'start,end');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('25', 'trade', 'select * from
  (
    select
      od.product_id,
      od.product_name,
      case when od.unit=''斤'' then od.unit_price
      when od.unit=''公斤'' then od.unit_price/2
      when od.unit=''吨'' then od.unit_price/2000
      end as unit_price
    from t_order_detail od
    where od.is_delete = 0
          and od.order_no in
              (
                select o.order_no
                from t_order o
                where o.is_delete = 0
                      and o.order_type = 1
                      and o.status in (1, 2, 3, 4)
                union all
                select o.order_no
                from t_order o
                where o.is_delete = 0
                      and o.order_type in (2, 3)
                      and o.status in (3, 4)
              )
          and od.product_id = :productId
    order by od.create_time desc
  ) a
    where rownum = 1', '交易行情-查询最近价', 'productId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('26', 'trade', 'select a.time as time,
       case when a.time=b.time then b.avg_price else a.avg_price end as avg_price
from
  (
    select
      to_char(:end - (level - 1), ''yyyy-mm-dd'') as time,
      0                                                as avg_price
    from dual
    connect by
      level <= (to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                - to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')) + 1
    order by time
  ) a
  left join
  (
    select
      ''0'' as time,
      0   as avg_price
    from dual
    union
    select
      to_char(od.create_time, ''yyyy-mm-dd'') as time,
      avg(od.unit_price)                    as avg_price
    from t_order_detail od
    where od.is_delete = 0
          and od.order_no in
              (
                select o.order_no
                from t_order o
                where o.is_delete = 0
                      and o.order_type = 1
                      and o.status in (1, 2, 3, 4)
                      and o.create_time >=
                          to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                      and o.create_time <=
                          to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                union all
                select o.order_no
                from t_order o
                where o.is_delete = 0
                      and o.order_type in (2, 3)
                      and o.status in (3, 4)
                      and o.create_time >=
                          to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                      and o.create_time <=
                          to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
              )
          and od.product_id = :productName
          group by to_char(od.create_time, ''yyyy-mm-dd'')
  ) b
    on a.time = b.time
order by a.time', '交易行情-均价折线统计', 'start,end,productName');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('27', 'trade', 'select c.startTime,c.endTime,sum(c.amount) as amount from
  (
    select
      a.startTime       as startTime,
      a.endTime         as endTime,
      case when a.startTime < b.time and a.endTime >= b.time
        then b.amount
      else a.amount end as amount
    from
      (
        select
          to_char(:end - (level) * :interval, ''yyyy-mm-dd'')     as startTime,
          to_char(:end - (level - 1) * :interval, ''yyyy-mm-dd'') as endTime,
          0                                                as amount
        from dual
        connect by
          level <= (to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                    - to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')) / :interval + 1
        order by startTime
      ) a
      left join
      (
        select
          ''0'' as time,
          0   as amount
        from dual
        union
        select
          to_char(od.create_time, ''yyyy-mm-dd'') as time,
          sum(case when od.unit=''斤'' then od.amount
              when od.unit=''公斤'' then od.amount*2
              when od.unit=''吨'' then od.amount*2000
              end)                    as amount
        from t_order_detail od
        where od.is_delete = 0
              and od.order_no in
                  (
                    select o.order_no
                    from t_order o
                    where o.is_delete = 0
                          and o.order_type = 1
                          and o.status in (1, 2, 3, 4)
                          and o.create_time >=
                              to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                          and o.create_time <=
                              to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                    union all
                    select o.order_no
                    from t_order o
                    where o.is_delete = 0
                          and o.order_type in (2, 3)
                          and o.status in (3, 4)
                          and o.create_time >=
                              to_date(to_char(trunc(:start), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                          and o.create_time <=
                              to_date(to_char(trunc(:end), ''yyyy/mm/dd hh24:mi:ss''), ''yyyy-mm-dd hh24:mi:ss'')
                  )
              and od.product_id = :productName
        group by to_char(od.create_time, ''yyyy-mm-dd'')
      ) b
        on a.startTime < b.time and a.endTime >= b.time
  ) c
  group by c.startTime,c.endTime
  order by c.startTime', '交易行情-销量区间统计', 'start,end,productName,interval');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('28', 'trade', 'select count(o.id) as count from t_order o
where o.is_delete = 0
      and o.order_type = 1
      and o.seller_id = :userId
      and o.service_status != -1', '售后状态为退款或申诉的拍卖单量', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('29', 'trade', 'select count(o.id) as count from t_order o
where o.is_delete = 0
      and o.order_type in (2,3)
      and o.seller_id = :userId
      and o.service_status != -1', '售后状态为退款或申诉的非定向单量', 'userId');
INSERT INTO "HWYG_DATA"."T_KPI" VALUES ('30', 'trade', 'select o.order_type,count(o.order_type) as orderNumer,nvl(sum(o.price),0) as totalPrice from t_order o
where o.is_delete = 0
      and o.status = 4
      and (o.buyer_id in (#userIds) or o.seller_id in (#userIds))
      group by o.order_type', '订单完成数量和总金额', 'userIds');

-- ----------------------------
-- Primary Key structure for table T_KPI
-- ----------------------------
ALTER TABLE "HWYG_DATA"."T_KPI" ADD CONSTRAINT "SYS_C008299" PRIMARY KEY ("CODE");

-- ----------------------------
-- Checks structure for table T_KPI
-- ----------------------------
ALTER TABLE "HWYG_DATA"."T_KPI" ADD CONSTRAINT "SYS_C008298" CHECK ("CODE" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;
