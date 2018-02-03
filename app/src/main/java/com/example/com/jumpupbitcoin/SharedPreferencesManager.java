package com.example.com.jumpupbitcoin;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "pref";

    // properties
    private static final String BUN_BONG = "bunbong";
    private static final String PRICE_PER = "pricePer";
    private static final String PRICE_PER_PRE = "pricePerPre";
    private static final String TRADE_PER = "tradePer";
    private static final String TRADE_PER_PRE = "tradePerPre";

    private SharedPreferencesManager() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static int getBunBong(Context context) {
        return getSharedPreferences(context).getInt(BUN_BONG, 30);
    }

    public static void setBunBong(Context context, int bunbong) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(BUN_BONG, bunbong);
        editor.apply();
    }

    public static float getPricePer(Context context) {
        return getSharedPreferences(context).getFloat(PRICE_PER, 0f);
    }

    public static void setPricePer(Context context, float pricePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(PRICE_PER, pricePer);
        editor.apply();
    }

    public static float getPricePerPre(Context context) {
        return getSharedPreferences(context).getFloat(PRICE_PER_PRE, 0f);
    }

    public static void setPricePerPre(Context context, float pricePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(PRICE_PER_PRE, pricePerPre);
        editor.apply();
    }

    public static float getTradePer(Context context) {
        return getSharedPreferences(context).getFloat(TRADE_PER, 0f);
    }

    public static void setTradePer(Context context, float tradePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(TRADE_PER, tradePer);
        editor.apply();
    }

    public static float getTradePerPre(Context context) {
        return getSharedPreferences(context).getFloat(TRADE_PER_PRE, 0f);
    }

    public static void setTradePerPre(Context context, float tradePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(TRADE_PER_PRE, tradePerPre);
        editor.apply();
    }


    // other getters/setters
}
