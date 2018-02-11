package com.example.com.jumpupbitcoin;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "pref";

    // 급등
    private static final String UP_CANDLE = "mUpCandle";
    private static final String UP_PRICE_PER = "upPricePer";
    private static final String UP_PRICE_PER_PRE = "upPricePerPre";
    private static final String UP_TRADE_PER = "upTradePer";
    private static final String UP_TRADE_PER_PRE = "upTradePerPre";

    // 급락
    private static final String DOWN_CANDLE = "mDownCandle";
    private static final String DOWN_PRICE_PER = "downPricePer";
    private static final String DOWN_PRICE_PER_PRE = "downPricePerPre";
    private static final String DOWN_TRADE_PER = "downTradePer";
    private static final String DOWN_TRADE_PER_PRE = "downTradePerPre";

    private SharedPreferencesManager() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static int getUpCandle(Context context) {
        return getSharedPreferences(context).getInt(UP_CANDLE, 30);
    }

    public static void setUpCandle(Context context, int bunbong) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(UP_CANDLE, bunbong);
        editor.apply();
    }

    public static float getPricePer(Context context) {
        return getSharedPreferences(context).getFloat(UP_PRICE_PER, 0f);
    }

    public static void setPricePer(Context context, float pricePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(UP_PRICE_PER, pricePer);
        editor.apply();
    }

    public static float getPricePerPre(Context context) {
        return getSharedPreferences(context).getFloat(UP_PRICE_PER_PRE, 0f);
    }

    public static void setPricePerPre(Context context, float pricePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(UP_PRICE_PER_PRE, pricePerPre);
        editor.apply();
    }

    public static float getTradePer(Context context) {
        return getSharedPreferences(context).getFloat(UP_TRADE_PER, 0f);
    }

    public static void setTradePer(Context context, float tradePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(UP_TRADE_PER, tradePer);
        editor.apply();
    }

    public static float getTradePerPre(Context context) {
        return getSharedPreferences(context).getFloat(UP_TRADE_PER_PRE, 0f);
    }

    public static void setTradePerPre(Context context, float tradePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(UP_TRADE_PER_PRE, tradePerPre);
        editor.apply();
    }

    // 급락 설정값

    public static void setDownCandle(Context context, int candle) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(DOWN_CANDLE, candle);
        editor.apply();
    }

    public static int getDownCandle(Context context) {
        return getSharedPreferences(context).getInt(DOWN_CANDLE, 30);
    }

    public static float getDownPricePer(Context context) {
        return getSharedPreferences(context).getFloat(DOWN_PRICE_PER, 0f);
    }

    public static void setDownPricePer(Context context, float downPricePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(DOWN_PRICE_PER, downPricePer);
        editor.apply();
    }

    public static float getDownPricePerPre(Context context) {
        return getSharedPreferences(context).getFloat(DOWN_PRICE_PER_PRE, 0f);
    }

    public static void setDownPricePerPre(Context context, float downPricePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(DOWN_PRICE_PER_PRE, downPricePerPre);
        editor.apply();
    }

    public static float getDownTradePer(Context context) {
        return getSharedPreferences(context).getFloat(DOWN_TRADE_PER, 0f);
    }

    public static void setDownTradePer(Context context, float downTradePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(DOWN_TRADE_PER, downTradePer);
        editor.apply();
    }

    public static float getDownTradePerPre(Context context) {
        return getSharedPreferences(context).getFloat(DOWN_TRADE_PER_PRE, 0f);
    }

    public static void setDownTradePerPre(Context context, float downTradePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(DOWN_TRADE_PER_PRE, downTradePerPre);
        editor.apply();
    }


    // other getters/setters
}
