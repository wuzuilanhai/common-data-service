package com.biubiu.constant;

import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Haibiao.Zhang on 2018/12/18.
 */
public enum KpiEnum {

    CONSOLE_NEW_ORDER_TODAY(1, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_NEW_ORDER_TODAY, "今日新增订单", null),
    CONSOLE_NEW_VOLUME_TODAY(2, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_NEW_VOLUME_TODAY, "今日新增交易量", null),
    CONSOLE_NEW_TURNOVER_TODAY(3, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_NEW_TURNOVER_TODAY, "今日新增交易额", null),
    CONSOLE_NEW_ORDER_MONTH(4, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_NEW_ORDER_MONTH, "本月新增订单", null),
    CONSOLE_NEW_VOLUME_MONTH(5, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_NEW_VOLUME_MONTH, "本月新增交易量", null),
    CONSOLE_NEW_TURNOVER_MONTH(6, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_NEW_TURNOVER_MONTH, "本月新增交易额", null),
    CONSOLE_PLATFORM_ORDER_VOLUME_DAY_AUCTION(7, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_PLATFORM_ORDER_VOLUME_DAY_AUCTION, "平台订单量与交易额统计(竞价交易，以日为单位)", "start,end"),
    CONSOLE_PLATFORM_ORDER_VOLUME_DAY_DISORIENTION(8, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_PLATFORM_ORDER_VOLUME_DAY_DISORIENTION, "平台订单量与交易额统计(非定向交易，以日为单位)", "start,end"),
    CONSOLE_PLATFORM_ORDER_VOLUME_DAY_ALL(9, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_PLATFORM_ORDER_VOLUME_DAY_ALL, "平台订单量与交易额统计(全部，以日为单位)", "start,end"),
    CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_AUCTION(10, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_AUCTION, "平台订单量与交易额统计(竞价交易，以月为单位)", "start,end"),
    CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_DISORIENTION(11, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_DISORIENTION, "平台订单量与交易额统计(非定向交易，以月为单位)", "start,end"),
    CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_ALL(12, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_ALL, "平台订单量与交易额统计(全部，以月为单位)", "start,end"),

    WEB_PAYMONEY_BUYERNUMBER_ORDERNUMBER_TODAY(13, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_PAYMONEY_BUYERNUMBER_ORDERNUMBER_TODAY, "今日支付金额、支付买家数、支付订单数", "userId"),
    WEB_PAYMONEY_BUYERNUMBER_ORDERNUMBER_YESTERDAY(14, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_PAYMONEY_BUYERNUMBER_ORDERNUMBER_YESTERDAY, "昨日支付金额、支付买家数、支付订单数", "userId"),
    WEB_TRADING_VOLUME_TODAY(15, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_TRADING_VOLUME_TODAY, "今日成交总量（斤）", "userId"),
    WEB_TRADING_VOLUME_YESTERDAY(16, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_TRADING_VOLUME_YESTERDAY, "昨日成交总量（斤）", "userId"),
    WEB_TRADE_ORDER_AUCTION(17, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_TRADE_ORDER_AUCTION, "交易订单（拍卖）", "userId"),
    WEB_TRADE_ORDER_DISORIENTION(18, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_TRADE_ORDER_DISORIENTION, "交易订单（非定向）", "userId"),
    WEB_TRADE_STATISTICS_ORDERNUMBER(19, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_TRADE_STATISTICS_ORDERNUMBER, "交易统计（订单量）", "start,end,userId"),
    WEB_TRADE_STATISTICS_TURNOVER(20, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_TRADE_STATISTICS_TURNOVER, "交易统计（成交量）", "start,end,userId"),
    WEB_FINANCE_STATISTICS(21, DatabaseEnum.USER.getDatabase(), SqlConstants.WEB_FINANCE_STATISTICS, "财务统计", "start,end,userId"),

    SCREEN_DEAL_INFO(22, DatabaseEnum.TRADE.getDatabase(), SqlConstants.SCREEN_DEAL_INFO, "最新成交信息", null),
    SCREEN_USER_SN(23, DatabaseEnum.USER.getDatabase(), SqlConstants.SCREEN_USER_SN, "查询客户代码", "userId"),
    SCREEN_TOP_TEN_FISH(24, DatabaseEnum.TRADE.getDatabase(), SqlConstants.SCREEN_TOP_TEN_FISH, "交易行情-查询前10渔种", "start,end"),
    SCREEN_RECENTLY_PRICE(25, DatabaseEnum.TRADE.getDatabase(), SqlConstants.SCREEN_RECENTLY_PRICE, "交易行情-查询最近价", "productId"),
    SCREEN_TRADE_AVG_PRICE(26, DatabaseEnum.TRADE.getDatabase(), SqlConstants.SCREEN_TRADE_AVG_PRICE, "交易行情-均价折线统计", "start,end,productId"),
    SCREEN_TRADE_AMOUNT(27, DatabaseEnum.TRADE.getDatabase(), SqlConstants.SCREEN_TRADE_AMOUNT, "交易行情-销量区间统计", "start,end,productId,interval"),

    WEB_AUCTION_ORDER_SERVICE_STATUS(28, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_AUCTION_ORDER_SERVICE_STATUS, "售后状态为退款或申诉的拍卖单量", "userId"),
    WEB_DISORIENTION_ORDER_SERVICE_STATUS(29, DatabaseEnum.TRADE.getDatabase(), SqlConstants.WEB_DISORIENTION_ORDER_SERVICE_STATUS, "售后状态为退款或申诉的非定向单量", "userId"),

    CONSOLE_USER_COMPLETE(30, DatabaseEnum.TRADE.getDatabase(), SqlConstants.CONSOLE_USER_COMPLETE, "订单完成数量和总金额", "userIds");

    KpiEnum(Integer code, String database, String sql, String desc, String queryParams) {
        this.code = code;
        this.database = database;
        this.sql = sql;
        this.desc = desc;
        this.queryParams = queryParams;
    }

    private Integer code;

    private String database;

    private String sql;

    private String desc;

    private String queryParams;

    public Integer getCode() {
        return code;
    }

    public String getDatabase() {
        return database;
    }

    public String getSql() {
        return sql;
    }

    public String getDesc() {
        return desc;
    }

    public String getQueryParams() {
        return queryParams;
    }

    public static KpiEnum getByCode(Integer code) {
        for (KpiEnum kpiEnum : KpiEnum.values()) {
            if (kpiEnum.code.equals(code)) return kpiEnum;
        }
        return null;
    }

    public static List<String> getQueryParamsByCode(Integer code) {
        for (KpiEnum kpiEnum : KpiEnum.values()) {
            if (kpiEnum.code.equals(code) && kpiEnum.queryParams != null) {
                return new ArrayList<>(Arrays.asList(kpiEnum.queryParams.split(",")));
            }
        }
        return null;
    }

    /**
     * 构建查询参数列表
     */
    public static List<Object> buildParams(KpiEnum kpiEnum, Date start, Date end, String userId, String productId, Integer interval) {
        List<Object> list = null;
        switch (kpiEnum) {
            case CONSOLE_PLATFORM_ORDER_VOLUME_DAY_AUCTION:
            case CONSOLE_PLATFORM_ORDER_VOLUME_DAY_DISORIENTION:
            case CONSOLE_PLATFORM_ORDER_VOLUME_DAY_ALL:
            case CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_AUCTION:
            case CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_DISORIENTION:
            case CONSOLE_PLATFORM_ORDER_VOLUME_MONTH_ALL:
                list = new ArrayList<>();
                list.add(start);
                list.add(end);
                list.add(start);
                list.add(start);
                list.add(end);
                break;
            case WEB_PAYMONEY_BUYERNUMBER_ORDERNUMBER_TODAY:
            case WEB_PAYMONEY_BUYERNUMBER_ORDERNUMBER_YESTERDAY:
            case WEB_TRADING_VOLUME_TODAY:
            case WEB_TRADING_VOLUME_YESTERDAY:
            case WEB_TRADE_ORDER_AUCTION:
            case WEB_TRADE_ORDER_DISORIENTION:
            case SCREEN_USER_SN:
            case WEB_AUCTION_ORDER_SERVICE_STATUS:
            case WEB_DISORIENTION_ORDER_SERVICE_STATUS:
                list = new ArrayList<>();
                list.add(userId);
                break;
            case WEB_TRADE_STATISTICS_ORDERNUMBER:
            case WEB_TRADE_STATISTICS_TURNOVER:
            case WEB_FINANCE_STATISTICS:
                list = new ArrayList<>();
                list.add(userId);
                list.add(start);
                list.add(end);
                break;
            case SCREEN_TOP_TEN_FISH:
                list = new ArrayList<>();
                list.add(start);
                list.add(end);
                list.add(start);
                list.add(end);
                break;
            case SCREEN_RECENTLY_PRICE:
                list = new ArrayList<>();
                list.add(productId);
                break;
            case SCREEN_TRADE_AVG_PRICE:
                list = new ArrayList<>();
                list.add(end);
                list.add(end);
                list.add(start);
                list.add(start);
                list.add(end);
                list.add(start);
                list.add(end);
                list.add(productId);
                break;
            case SCREEN_TRADE_AMOUNT:
                list = new ArrayList<>();
                list.add(end);
                list.add(interval);
                list.add(end);
                list.add(interval);
                list.add(end);
                list.add(start);
                list.add(interval);
                list.add(start);
                list.add(end);
                list.add(start);
                list.add(end);
                list.add(productId);
                break;
            case CONSOLE_USER_COMPLETE:
                if (!StringUtils.isEmpty(userId)) {
                    list = new ArrayList<>();
                    List<String> userIds = Arrays.asList(userId.split(","));
                    list.add(userIds);
                    list.add(userIds);
                }
                break;
            default:
                break;
        }
        return list;
    }

}
