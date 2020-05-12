package com.mtl.cypw.common.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Michael.Zhong on 16/7/8.
 */
public enum CurrencyEnum {
    /**
     * United States dollar
     */
    USD("840", "USD", "美元", "United States dollar", 2),
    /**
     * Australian dollar
     */
    AUD("036", "AUD", "澳元", "Australian dollar", 2),
    /**
     * Canadian dollar
     */
    CAD("124", "CAD", "加拿大元", "Canadian dollar", 2),
    /**
     * Chinese yuan
     */
    CNY("156", "CNY", "人民币", "Chinese yuan", 2),
    /**
     * Danish krone
     */
    DKK("208", "DKK", "丹麦克朗", "Danish krone", 2),
    /**
     * Hong Kong dollar
     */
    HKD("344", "HKD", "港币", "Hong Kong dollar", 2),
    /**
     * Indian rupee
     */
    INR("356", "INR", "印度卢比", "Indian rupee", 2),
    /**
     * Indonesian rupiah
     */
    IDR("360", "IDR", "印尼盾", "Indonesian rupiah", 2),
    /**
     * Japanese yen
     */
    JPY("392", "JPY", "日元", "Japanese yen", 0),
    /**
     * South Korean won
     */
    KRW("410", "KRW", "韩元", "South Korean won", 0),
    /**
     * Macanese pataca
     */
    MOP("446", "MOP", "澳门币", "Macanese pataca", 2),
    /**
     * Mexican peso
     */
    MXN("484", "MXN", "墨西哥比索", "Mexican peso", 2),
    /**
     * New Zealand dollar
     */
    NZD("554", "NZD", "新西兰元", "New Zealand dollar", 2),
    /**
     * Norwegian krone
     */
    NOK("578", "NOK", "挪威克郎", "Norwegian krone", 2),
    /**
     * Philippine peso
     */
    PHP("608", "PHP", "菲律宾比索", "Philippine peso", 2),
    /**
     * Russian ruble
     */
    RUB("643", "RUB", "俄罗斯卢布", "Russian ruble", 2),
    /**
     * Singapore dollar
     */
    SGD("702", "SGD", "新加坡元", "Singapore dollar", 2),
    /**
     * Swedish krona/kronor
     */
    SEK("752", "SEK", "瑞典克朗", "Swedish krona/kronor", 2),
    /**
     * Swiss franc
     */
    CHF("756", "CHF", "瑞士法郎", "Swiss franc", 2),
    /**
     * Thai baht
     */
    THB("764", "THB", "泰铢", "Thai baht", 2),
    /**
     * Pound sterling
     */
    GBP("826", "GBP", "英镑", "Pound sterling", 2),
    /**
     * New Taiwan dollar
     */
    TWD("901", "TWD", "新台币", "New Taiwan dollar", 2),
    /**
     * Euro
     */
    EUR("978", "EUR", "欧元", "Euro", 2),
    /**
     * Ukrainian hryvnia
     */
    UAH("980", "UAH", "乌克兰格里夫纳", "Ukrainian hryvnia", 2),
    /**
     * Brazilian real
     */
    BRL("986", "BRL", "巴西雷亚尔", "Brazilian real", 2);

    /** 代码 */
    private final String currencyCode;

    /** 英文缩写 */
    private final String currencyEnglishAbbr;

    /** 中文名称 */
    private final String currencyChineseName;

    /** 英文名称 */
    private final String currencyEnglishName;

    /** 小数点位数 */
    private final int    fractionDigits;

    /**
     * 枚举构造函数。
     *
     * @param currencyCode
     *            币种编码。
     * @param currencyEnglishAbbr
     *            币种英文缩写。
     * @param currencyChineseName
     *            币种中文名称。
     * @param currencyEnglishName
     *            币种英文全称。
     * @param fractionDigits
     *            小数点位数。
     */
    private CurrencyEnum(String currencyCode, String currencyEnglishAbbr,
                         String currencyChineseName, String currencyEnglishName, int fractionDigits) {
        this.currencyCode = currencyCode;
        this.currencyEnglishAbbr = currencyEnglishAbbr;
        this.currencyEnglishName = currencyEnglishName;
        this.currencyChineseName = currencyChineseName;
        this.fractionDigits = fractionDigits;
    }

    /**
     * 根据币种代码获取币种枚举，找不到返回null。
     *
     * @param currencyCode
     *            币种代码，{@link #currencyCode}
     * @return 币种枚举
     */
    public static CurrencyEnum getEnumByCurrencyCode(String currencyCode) {
        for (CurrencyEnum val : CurrencyEnum.values()) {
            if (StringUtils.equalsIgnoreCase(val.currencyCode, currencyCode)) {
                return val;
            }
        }
        return null;
    }

    /**
     * 根据币种的英文缩写获取币种枚举，找不到返回null。
     *
     * @param currencyEnglishAbbr
     *            币种英文缩写，大小写不敏感，{@link #currencyEnglishAbbr}
     * @return
     */
    public static CurrencyEnum getEnumByCurrencyEnglishAbbr(String currencyEnglishAbbr) {
        for (CurrencyEnum val : CurrencyEnum.values()) {
            if (StringUtils.equalsIgnoreCase(val.currencyEnglishAbbr, currencyEnglishAbbr)) {
                return val;
            }
        }
        return null;
    }

    /**
     * Getter method for property <tt>currencyCode</tt>.
     *
     * @return property value of currencyCode
     */
    public String getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Getter method for property <tt>currencyEnglishAbbr</tt>.
     *
     * @return property value of currencyEnglishAbbr
     */
    public String getCurrencyEnglishAbbr() {
        return currencyEnglishAbbr;
    }

    /**
     * Getter method for property <tt>currencyChineseName</tt>.
     *
     * @return property value of currencyChineseName
     */
    public String getCurrencyChineseName() {
        return currencyChineseName;
    }

    /**
     * Getter method for property <tt>currencyEnglishName</tt>.
     *
     * @return property value of currencyEnglishName
     */
    public String getCurrencyEnglishName() {
        return currencyEnglishName;
    }

    /**
     * Getter method for property <tt>fractionDigits</tt>.
     *
     * @return property value of fractionDigits
     */
    public int getFractionDigits() {
        return fractionDigits;
    }
}
