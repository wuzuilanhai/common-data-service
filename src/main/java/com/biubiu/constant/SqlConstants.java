package com.biubiu.constant;

/**
 * Created by Haibiao.Zhang on 2018/12/17.
 */
public interface SqlConstants {

    String CONSOLE_NEW_ORDER_TODAY = "select count(o.id) as count from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t\tand o.create_time >= to_date(to_char(trunc(sysdate), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\tand o.create_time <= to_date(to_char(trunc(sysdate+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')";

    String CONSOLE_NEW_VOLUME_TODAY = "select nvl(sum\n" +
            "\t\t    (\n" +
            "\t\t      case when od.unit='斤' then od.amount\n" +
            "\t\t      when od.unit='公斤' then od.amount*2\n" +
            "\t\t      when od.unit='吨' then od.amount*2000\n" +
            "\t\t      end\n" +
            "\t\t    ),0) as amount\n" +
            "\t\tfrom t_order_detail od\n" +
            "\t\twhere od.is_delete = 0\n" +
            "\t\tand od.order_no in\n" +
            "\t\t    (\n" +
            "\t\t      select o.order_no from t_order o\n" +
            "\t\t      where o.is_delete = 0\n" +
            "\t\t            and o.create_time >= to_date(to_char(trunc(sysdate), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t            and o.create_time <= to_date(to_char(trunc(sysdate+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t    )";

    String CONSOLE_NEW_TURNOVER_TODAY = "select nvl(sum(o.price),0) as price from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t    and o.create_time >= to_date(to_char(trunc(sysdate), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t    and o.create_time <= to_date(to_char(trunc(sysdate+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')";

    String CONSOLE_NEW_ORDER_MONTH = "select count(o.id) as count from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t\tand o.create_time >= to_date(to_char(trunc(sysdate, 'mm'), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\tand o.create_time <= to_date(to_char(last_day(trunc(sysdate))+1, 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')";

    String CONSOLE_NEW_VOLUME_MONTH = "select nvl(sum\n" +
            "\t\t       (\n" +
            "\t\t           case when od.unit='斤' then od.amount\n" +
            "\t\t           when od.unit='公斤' then od.amount*2\n" +
            "\t\t           when od.unit='吨' then od.amount*2000\n" +
            "\t\t           end\n" +
            "\t\t       ),0) as amount\n" +
            "\t\tfrom t_order_detail od\n" +
            "\t\twhere od.is_delete = 0\n" +
            "\t\t      and od.order_no in\n" +
            "\t\t          (\n" +
            "\t\t            select o.order_no from t_order o\n" +
            "\t\t            where o.is_delete = 0\n" +
            "\t\t                  and o.create_time >= to_date(to_char(trunc(sysdate, 'mm'), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t                  and o.create_time <= to_date(to_char(last_day(trunc(sysdate))+1, 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          )";

    String CONSOLE_NEW_TURNOVER_MONTH = "select nvl(sum(o.price),0) as price from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t    and o.create_time >= to_date(to_char(trunc(sysdate, 'mm'), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t    and o.create_time <= to_date(to_char(last_day(trunc(sysdate))+1, 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')";

    String CONSOLE_PLATFORM_ORDER_VOLUME_DAY_AUCTION = "select a.time as time,\n" +
            "\t\t  case when a.time=b.time then b.count else a.count end as count,\n" +
            "\t\t  case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice\n" +
            "\t\tfrom\n" +
            "\t\t  (\n" +
            "\t\t    SELECT\n" +
            "\t\t      to_char(to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')+level-1, 'yyyy-mm-dd') as time,\n" +
            "\t\t      0 as count, 0 as totalPrice\n" +
            "\t\t    FROM\n" +
            "\t\t      dual\n" +
            "\t\t    CONNECT BY\n" +
            "\t\t      level <= to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t               -to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')+1\n" +
            "\t\t  ) a\n" +
            "\t\t  left join\n" +
            "\t\t  (\n" +
            "\t\t    select '0' as time,0 as count,0 as totalPrice from dual\n" +
            "\t\t    union\n" +
            "\t\t    select to_char(o.create_time, 'yyyy-mm-dd') as time,count(o.id) as count,sum(o.price) as totalPrice from t_order o\n" +
            "\t\t    where o.is_delete = 0\n" +
            "\t\t          and o.order_type = 1\n" +
            "\t\t          and o.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          and o.create_time <= to_date(to_char(trunc(:end+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t    group by to_char(o.create_time, 'yyyy-mm-dd')\n" +
            "\t\t  ) b\n" +
            "\t\t  on a.time = b.time\n" +
            "\t\t  order by a.time";

    String CONSOLE_PLATFORM_ORDER_VOLUME_DAY_DISORIENTION = "select a.time as time,\n" +
            "\t\t  case when a.time=b.time then b.count else a.count end as count,\n" +
            "\t\t  case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice\n" +
            "\t\tfrom\n" +
            "\t\t  (\n" +
            "\t\t    SELECT\n" +
            "\t\t      to_char(to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')+level-1, 'yyyy-mm-dd') as time,\n" +
            "\t\t      0 as count, 0 as totalPrice\n" +
            "\t\t    FROM\n" +
            "\t\t      dual\n" +
            "\t\t    CONNECT BY\n" +
            "\t\t      level <= to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t               -to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')+1\n" +
            "\t\t  ) a\n" +
            "\t\t  left join\n" +
            "\t\t  (\n" +
            "\t\t    select '0' as time,0 as count,0 as totalPrice from dual\n" +
            "\t\t    union\n" +
            "\t\t    select to_char(o.create_time, 'yyyy-mm-dd') as time,count(o.id) as count,sum(o.price) as totalPrice from t_order o\n" +
            "\t\t    where o.is_delete = 0\n" +
            "\t\t          and o.order_type in (2,3)\n" +
            "\t\t          and o.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          and o.create_time <= to_date(to_char(trunc(:end+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t    group by to_char(o.create_time, 'yyyy-mm-dd')\n" +
            "\t\t  ) b\n" +
            "\t\t  on a.time = b.time\n" +
            "\t\t  order by a.time";

    String CONSOLE_PLATFORM_ORDER_VOLUME_DAY_ALL = "select a.time as time,\n" +
            "\t\t  case when a.time=b.time then b.count else a.count end as count,\n" +
            "\t\t  case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice\n" +
            "\t\tfrom\n" +
            "\t\t  (\n" +
            "\t\t    SELECT\n" +
            "\t\t      to_char(to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')+level-1, 'yyyy-mm-dd') as time,\n" +
            "\t\t      0 as count, 0 as totalPrice\n" +
            "\t\t    FROM\n" +
            "\t\t      dual\n" +
            "\t\t    CONNECT BY\n" +
            "\t\t      level <= to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t               -to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')+1\n" +
            "\t\t  ) a\n" +
            "\t\t  left join\n" +
            "\t\t  (\n" +
            "\t\t    select '0' as time,0 as count,0 as totalPrice from dual\n" +
            "\t\t    union\n" +
            "\t\t    select to_char(o.create_time, 'yyyy-mm-dd') as time,count(o.id) as count,sum(o.price) as totalPrice from t_order o\n" +
            "\t\t    where o.is_delete = 0\n" +
            "\t\t          and o.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          and o.create_time <= to_date(to_char(trunc(:end+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t    group by to_char(o.create_time, 'yyyy-mm-dd')\n" +
            "\t\t  ) b\n" +
            "\t\t  on a.time = b.time\n" +
            "\t\t  order by a.time";

    String CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_AUCTION = "select a.time as time,\n" +
            "\t\t       case when a.time=b.time then b.count else a.count end as count,\n" +
            "\t\t       case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice\n" +
            "\t\tfrom\n" +
            "\t\t  (\n" +
            "\t\t    SELECT\n" +
            "\t\t      to_char(add_months(to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "\t\t                         rownum - 1),\n" +
            "\t\t              'yyyy-mm') as time,\n" +
            "\t\t      0                  as count,\n" +
            "\t\t      0                  as totalPrice\n" +
            "\t\t    FROM dual\n" +
            "\t\t    CONNECT BY rownum <=\n" +
            "\t\t               months_between(\n" +
            "\t\t                   to_date(to_char(last_day(trunc(:end)), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "\t\t                   to_date(to_char(trunc(:start, 'mm'), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')) + 1\n" +
            "\t\t  ) a\n" +
            "\t\t  left join\n" +
            "\t\t  (\n" +
            "\t\t    select '0' as time,0 as count,0 as totalPrice from dual\n" +
            "\t\t    union\n" +
            "\t\t    select to_char(trunc(o.create_time, 'mm'), 'yyyy-mm') as time,count(o.id) as orderNumber,sum(o.price) as totalPrice from t_order o\n" +
            "\t\t    where o.is_delete = 0\n" +
            "\t\t    \t  and o.order_type = 1\n" +
            "\t\t          and o.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          and o.create_time <= to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t    group by to_char(trunc(o.create_time, 'mm'), 'yyyy-mm')\n" +
            "\t\t  ) b\n" +
            "\t\t  on a.time = b.time\n" +
            "\t\t  order by a.time";

    String CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_DISORIENTION = "select a.time as time,\n" +
            "\t\t       case when a.time=b.time then b.count else a.count end as count,\n" +
            "\t\t       case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice\n" +
            "\t\tfrom\n" +
            "\t\t  (\n" +
            "\t\t    SELECT\n" +
            "\t\t      to_char(add_months(to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "\t\t                         rownum - 1),\n" +
            "\t\t              'yyyy-mm') as time,\n" +
            "\t\t      0                  as count,\n" +
            "\t\t      0                  as totalPrice\n" +
            "\t\t    FROM dual\n" +
            "\t\t    CONNECT BY rownum <=\n" +
            "\t\t               months_between(\n" +
            "\t\t                   to_date(to_char(last_day(trunc(:end)), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "\t\t                   to_date(to_char(trunc(:start, 'mm'), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')) + 1\n" +
            "\t\t  ) a\n" +
            "\t\t  left join\n" +
            "\t\t  (\n" +
            "\t\t    select '0' as time,0 as count,0 as totalPrice from dual\n" +
            "\t\t    union\n" +
            "\t\t    select to_char(trunc(o.create_time, 'mm'), 'yyyy-mm') as time,count(o.id) as orderNumber,sum(o.price) as totalPrice from t_order o\n" +
            "\t\t    where o.is_delete = 0\n" +
            "\t\t    \t  and o.order_type in (2,3)\n" +
            "\t\t          and o.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          and o.create_time <= to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t    group by to_char(trunc(o.create_time, 'mm'), 'yyyy-mm')\n" +
            "\t\t  ) b\n" +
            "\t\t  on a.time = b.time\n" +
            "\t\t  order by a.time";

    String CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_ALL = "select a.time as time,\n" +
            "\t\t       case when a.time=b.time then b.count else a.count end as count,\n" +
            "\t\t       case when a.time=b.time then b.totalPrice else a.totalPrice end as totalPrice\n" +
            "\t\tfrom\n" +
            "\t\t  (\n" +
            "\t\t    SELECT\n" +
            "\t\t      to_char(add_months(to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "\t\t                         rownum - 1),\n" +
            "\t\t              'yyyy-mm') as time,\n" +
            "\t\t      0                  as count,\n" +
            "\t\t      0                  as totalPrice\n" +
            "\t\t    FROM dual\n" +
            "\t\t    CONNECT BY rownum <=\n" +
            "\t\t               months_between(\n" +
            "\t\t                   to_date(to_char(last_day(trunc(:end)), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss'),\n" +
            "\t\t                   to_date(to_char(trunc(:start, 'mm'), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')) + 1\n" +
            "\t\t  ) a\n" +
            "\t\t  left join\n" +
            "\t\t  (\n" +
            "\t\t    select '0' as time,0 as count,0 as totalPrice from dual\n" +
            "\t\t    union\n" +
            "\t\t    select to_char(trunc(o.create_time, 'mm'), 'yyyy-mm') as time,count(o.id) as orderNumber,sum(o.price) as totalPrice from t_order o\n" +
            "\t\t    where o.is_delete = 0\n" +
            "\t\t          and o.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          and o.create_time <= to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t    group by to_char(trunc(o.create_time, 'mm'), 'yyyy-mm')\n" +
            "\t\t  ) b\n" +
            "\t\t  on a.time = b.time\n" +
            "\t\t  order by a.time";

    String WEB_PAYMONEY_BUYERNUMBER_ORDERNUMBER_TODAY = "select nvl(sum(o.price),0) as price,count(o.id) as buyerNumber,count(o.id) as orderNumber from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t\t      and o.status in (3,4)\n" +
            "\t\t      and o.seller_id = :userId\n" +
            "\t\t      and o.create_time >= to_date(to_char(trunc(sysdate), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t      and o.create_time <= to_date(to_char(trunc(sysdate+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')";

    String WEB_PAYMONEY_BUYERNUMBER_ORDERNUMBER_YESTERDAY = "select nvl(sum(o.price),0) as price,count(o.id) as buyerNumber,count(o.id) as orderNumber from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t\t      and o.status in (3,4)\n" +
            "\t\t      and o.seller_id = :userId\n" +
            "\t\t      and o.create_time >= to_date(to_char(trunc(sysdate-1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t      and o.create_time <= to_date(to_char(trunc(sysdate), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')";

    String WEB_TRADING_VOLUME_TODAY = "select nvl(sum\n" +
            "\t\t       (\n" +
            "\t\t           case when od.unit='斤' then od.amount\n" +
            "\t\t           when od.unit='公斤' then od.amount*2\n" +
            "\t\t           when od.unit='吨' then od.amount*2000\n" +
            "\t\t           end\n" +
            "\t\t       ),0) as amount\n" +
            "\t\tfrom t_order_detail od\n" +
            "\t\twhere od.is_delete = 0\n" +
            "\t\t      and od.order_no in\n" +
            "\t\t          (\n" +
            "\t\t            select o.order_no from t_order o\n" +
            "\t\t            where o.is_delete = 0\n" +
            "\t\t                  and o.status in (3,4)\n" +
            "\t\t                  and o.seller_id = :userId\n" +
            "\t\t                  and o.create_time >= to_date(to_char(trunc(sysdate), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t                  and o.create_time <= to_date(to_char(trunc(sysdate+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          )";

    String WEB_TRADING_VOLUME_YESTERDAY = "select nvl(sum\n" +
            "\t\t       (\n" +
            "\t\t           case when od.unit='斤' then od.amount\n" +
            "\t\t           when od.unit='公斤' then od.amount*2\n" +
            "\t\t           when od.unit='吨' then od.amount*2000\n" +
            "\t\t           end\n" +
            "\t\t       ),0) as amount\n" +
            "\t\tfrom t_order_detail od\n" +
            "\t\twhere od.is_delete = 0\n" +
            "\t\t      and od.order_no in\n" +
            "\t\t          (\n" +
            "\t\t            select o.order_no from t_order o\n" +
            "\t\t            where o.is_delete = 0\n" +
            "\t\t                  and o.status in (3,4)\n" +
            "\t\t                  and o.seller_id = :userId\n" +
            "\t\t                  and o.create_time >= to_date(to_char(trunc(sysdate-1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t                  and o.create_time <= to_date(to_char(trunc(sysdate), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t          )";

    String WEB_TRADE_ORDER_AUCTION = "select o.status,count(o.status) as count from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t\t      and o.order_type = 1\n" +
            "\t\t      and o.seller_id = :userId\n" +
            "\t\t      group by o.status\n" +
            "\t\t      order by o.status";

    String WEB_TRADE_ORDER_DISORIENTION = "select o.status,count(o.status) as count from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t\t      and o.order_type in (2,3)\n" +
            "\t\t      and o.seller_id = :userId\n" +
            "\t\t      group by o.status\n" +
            "\t\t      order by o.status";

    String WEB_TRADE_STATISTICS_ORDERNUMBER = "select o.order_type as orderType,count(o.id) as count from t_order o\n" +
            "\t\twhere o.is_delete = 0\n" +
            "\t\t      and o.seller_id = :userId\n" +
            "\t\t      and o.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t      and o.create_time <= to_date(to_char(trunc(:end+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "\t\t      group by o.order_type";

    String WEB_TRADE_STATISTICS_TURNOVER = "select a.orderType,sum\n" +
            "                   (\n" +
            "                       case when od.unit='斤' then od.amount\n" +
            "                       when od.unit='公斤' then od.amount*2\n" +
            "                       when od.unit='吨' then od.amount*2000\n" +
            "                       end\n" +
            "                   ) as amount from\n" +
            "  (\n" +
            "    select\n" +
            "      o.order_type as orderType,\n" +
            "      o.order_no\n" +
            "    from t_order o\n" +
            "    where o.is_delete = 0 and o.status = 4 \n" +
            "          and o.seller_id = :userId\n" +
            "          and o.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "          and o.create_time <= to_date(to_char(trunc(:end + 1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "  ) a\n" +
            "    left join t_order_detail od\n" +
            "  on a.order_no = od.order_no\n" +
            "  group by a.orderType";

    String WEB_FINANCE_STATISTICS = "select oer.flow,sum(oer.total_amount) as price,\n" +
            "  case when oer.flow = 1 then '收入'\n" +
            "       when oer.flow = 2 then '支出'\n" +
            "  end as description\n" +
            "from t_out_entry_record oer\n" +
            "where oer.user_id = :userId\n" +
            "      and oer.create_time >= to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "      and oer.create_time <= to_date(to_char(trunc(:end+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "      group by oer.flow";

    String SCREEN_DEAL_INFO = "select od.product_name,od.unit,od.unit_price,od.amount,od.price,o.buyer_id,o.buyer_name,o.order_type\n" +
            "from t_order_detail od\n" +
            "left join t_order o\n" +
            "on od.order_no = o.order_no\n" +
            "where od.is_delete = 0\n" +
            "      and od.order_no in\n" +
            "          (\n" +
            "            select o.order_no\n" +
            "            from t_order o\n" +
            "            where o.is_delete = 0\n" +
            "                  and o.order_type = 1\n" +
            "                  and o.status in (1, 2, 3, 4)\n" +
            "                  and o.create_time >= to_date(to_char(trunc(sysdate-7), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                  and o.create_time <= to_date(to_char(trunc(sysdate+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "            union all\n" +
            "            select o.order_no\n" +
            "            from t_order o\n" +
            "            where o.is_delete = 0\n" +
            "                  and o.order_type in (2, 3)\n" +
            "                  and o.status in (3, 4)\n" +
            "                  and o.create_time >= to_date(to_char(trunc(sysdate-7), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                  and o.create_time <= to_date(to_char(trunc(sysdate+1), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "          )\n" +
            "      order by od.create_time desc";

    String SCREEN_USER_SN = "select u.sn from t_user u where u.id = :userId";

    String SCREEN_TOP_TEN_FISH = "select * from\n" +
            "  (\n" +
            "    select\n" +
            "      od.product_id,\n" +
            "      od.product_name,\n" +
            "      max(case when od.unit='斤' then od.unit_price\n" +
            "          when od.unit='公斤' then od.unit_price/2\n" +
            "          when od.unit='吨' then od.unit_price/2000\n" +
            "          end) as max_price,\n" +
            "      min(case when od.unit='斤' then od.unit_price\n" +
            "          when od.unit='公斤' then od.unit_price/2\n" +
            "          when od.unit='吨' then od.unit_price/2000\n" +
            "          end) as min_price,\n" +
            "      avg(case when od.unit='斤' then od.unit_price\n" +
            "          when od.unit='公斤' then od.unit_price/2\n" +
            "          when od.unit='吨' then od.unit_price/2000\n" +
            "          end) as avg_price,\n" +
            "      sum(case when od.unit='斤' then od.amount\n" +
            "          when od.unit='公斤' then od.amount*2\n" +
            "          when od.unit='吨' then od.amount*2000\n" +
            "          end) as amount,\n" +
            "      sum(od.price) as total_price\n" +
            "    from t_order_detail od\n" +
            "    where od.is_delete = 0\n" +
            "          and od.order_no in\n" +
            "              (\n" +
            "                select o.order_no\n" +
            "                from t_order o\n" +
            "                where o.is_delete = 0\n" +
            "                      and o.order_type = 1\n" +
            "                      and o.status in (1, 2, 3, 4)\n" +
            "                      and o.create_time >=\n" +
            "                          to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                      and o.create_time <=\n" +
            "                          to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                union all\n" +
            "                select o.order_no\n" +
            "                from t_order o\n" +
            "                where o.is_delete = 0\n" +
            "                      and o.order_type in (2, 3)\n" +
            "                      and o.status in (3, 4)\n" +
            "                      and o.create_time >=\n" +
            "                          to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                      and o.create_time <=\n" +
            "                          to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "              )\n" +
            "    group by od.product_id, od.product_name\n" +
            "    order by max_price desc\n" +
            "  ) a\n" +
            "    where rownum <= 10";

    String SCREEN_RECENTLY_PRICE = "select * from\n" +
            "  (\n" +
            "    select\n" +
            "      od.product_id,\n" +
            "      od.product_name,\n" +
            "      case when od.unit='斤' then od.unit_price\n" +
            "      when od.unit='公斤' then od.unit_price/2\n" +
            "      when od.unit='吨' then od.unit_price/2000\n" +
            "      end as unit_price\n" +
            "    from t_order_detail od\n" +
            "    where od.is_delete = 0\n" +
            "          and od.order_no in\n" +
            "              (\n" +
            "                select o.order_no\n" +
            "                from t_order o\n" +
            "                where o.is_delete = 0\n" +
            "                      and o.order_type = 1\n" +
            "                      and o.status in (1, 2, 3, 4)\n" +
            "                union all\n" +
            "                select o.order_no\n" +
            "                from t_order o\n" +
            "                where o.is_delete = 0\n" +
            "                      and o.order_type in (2, 3)\n" +
            "                      and o.status in (3, 4)\n" +
            "              )\n" +
            "          and od.product_id = :productId\n" +
            "    order by od.create_time desc\n" +
            "  ) a\n" +
            "    where rownum = 1";

    String SCREEN_TRADE_AVG_PRICE = "select a.time as time,\n" +
            "       case when a.time=b.time then b.avg_price else a.avg_price end as avg_price\n" +
            "from\n" +
            "  (\n" +
            "    select\n" +
            "      to_char(:end - (level - 1), 'yyyy-mm-dd') as time,\n" +
            "      0                                                as avg_price\n" +
            "    from dual\n" +
            "    connect by\n" +
            "      level <= (to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                - to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')) + 1\n" +
            "    order by time\n" +
            "  ) a\n" +
            "  left join\n" +
            "  (\n" +
            "    select\n" +
            "      '0' as time,\n" +
            "      0   as avg_price\n" +
            "    from dual\n" +
            "    union\n" +
            "    select\n" +
            "      to_char(od.create_time, 'yyyy-mm-dd') as time,\n" +
            "      avg(od.unit_price)                    as avg_price\n" +
            "    from t_order_detail od\n" +
            "    where od.is_delete = 0\n" +
            "          and od.order_no in\n" +
            "              (\n" +
            "                select o.order_no\n" +
            "                from t_order o\n" +
            "                where o.is_delete = 0\n" +
            "                      and o.order_type = 1\n" +
            "                      and o.status in (1, 2, 3, 4)\n" +
            "                      and o.create_time >=\n" +
            "                          to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                      and o.create_time <=\n" +
            "                          to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                union all\n" +
            "                select o.order_no\n" +
            "                from t_order o\n" +
            "                where o.is_delete = 0\n" +
            "                      and o.order_type in (2, 3)\n" +
            "                      and o.status in (3, 4)\n" +
            "                      and o.create_time >=\n" +
            "                          to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                      and o.create_time <=\n" +
            "                          to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "              )\n" +
            "          and od.product_id = :productId\n" +
            "          group by od.create_time\n" +
            "  ) b\n" +
            "    on a.time = b.time\n" +
            "order by a.time";

    String SCREEN_TRADE_AMOUNT = "select c.startTime,c.endTime,sum(c.amount) as amount from\n" +
            "  (\n" +
            "    select\n" +
            "      a.startTime       as startTime,\n" +
            "      a.endTime         as endTime,\n" +
            "      case when a.startTime < b.time and a.endTime >= b.time\n" +
            "        then b.amount\n" +
            "      else a.amount end as amount\n" +
            "    from\n" +
            "      (\n" +
            "        select\n" +
            "          to_char(:end - (level) * :interval, 'yyyy-mm-dd')     as startTime,\n" +
            "          to_char(:end - (level - 1) * :interval, 'yyyy-mm-dd') as endTime,\n" +
            "          0                                                as amount\n" +
            "        from dual\n" +
            "        connect by\n" +
            "          level <= (to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                    - to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')) / :interval + 1\n" +
            "        order by startTime\n" +
            "      ) a\n" +
            "      left join\n" +
            "      (\n" +
            "        select\n" +
            "          '0' as time,\n" +
            "          0   as amount\n" +
            "        from dual\n" +
            "        union\n" +
            "        select\n" +
            "          to_char(od.create_time, 'yyyy-mm-dd') as time,\n" +
            "          sum(case when od.unit='斤' then od.amount\n" +
            "              when od.unit='公斤' then od.amount*2\n" +
            "              when od.unit='吨' then od.amount*2000\n" +
            "              end)                    as amount\n" +
            "        from t_order_detail od\n" +
            "        where od.is_delete = 0\n" +
            "              and od.order_no in\n" +
            "                  (\n" +
            "                    select o.order_no\n" +
            "                    from t_order o\n" +
            "                    where o.is_delete = 0\n" +
            "                          and o.order_type = 1\n" +
            "                          and o.status in (1, 2, 3, 4)\n" +
            "                          and o.create_time >=\n" +
            "                              to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                          and o.create_time <=\n" +
            "                              to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                    union all\n" +
            "                    select o.order_no\n" +
            "                    from t_order o\n" +
            "                    where o.is_delete = 0\n" +
            "                          and o.order_type in (2, 3)\n" +
            "                          and o.status in (3, 4)\n" +
            "                          and o.create_time >=\n" +
            "                              to_date(to_char(trunc(:start), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                          and o.create_time <=\n" +
            "                              to_date(to_char(trunc(:end), 'yyyy/mm/dd hh24:mi:ss'), 'yyyy-mm-dd hh24:mi:ss')\n" +
            "                  )\n" +
            "              and od.product_id = :productId\n" +
            "        group by od.create_time\n" +
            "      ) b\n" +
            "        on a.startTime < b.time and a.endTime >= b.time\n" +
            "  ) c\n" +
            "  group by c.startTime,c.endTime\n" +
            "  order by c.startTime";

    String WEB_AUCTION_ORDER_SERVICE_STATUS = "select count(o.id) as count from t_order o\n" +
            "where o.is_delete = 0\n" +
            "      and o.order_type = 1\n" +
            "      and o.seller_id = :userId\n" +
            "      and o.service_status != -1";

    String WEB_DISORIENTION_ORDER_SERVICE_STATUS = "select count(o.id) as count from t_order o\n" +
            "where o.is_delete = 0\n" +
            "      and o.order_type in (2,3)\n" +
            "      and o.seller_id = :userId\n" +
            "      and o.service_status != -1";

    String CONSOLE_USER_COMPLETE = "select o.order_type,count(o.order_type) as orderNumer,nvl(sum(o.price),0) as totalPrice from t_order o\n" +
            "where o.is_delete = 0\n" +
            "      and o.status = 4\n" +
            "      and (o.buyer_id in (#userIds) or o.seller_id in (#userIds))\n" +
            "      group by o.order_type";

}
