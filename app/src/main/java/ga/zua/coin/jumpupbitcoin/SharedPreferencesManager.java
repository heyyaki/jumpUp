package ga.zua.coin.jumpupbitcoin;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPreferencesManager {

    private static final String PREF_NAME = "pref";

    // 진동
    private static final String VIBRATION = "vibration";

    // 급등
    private static final String UP_SETTING = "mUpSetting";
    private static final String UP_CANDLE = "mUpCandle";
    private static final String UP_PRICE_PER = "upPricePer";
    private static final String UP_PRICE_PER_PRE = "upPricePerPre";
    private static final String UP_TRADE_PER = "upTradePer";
    private static final String UP_TRADE_PER_PRE = "upTradePerPre";
    private static final String UP_TRADE_PRICE = "upTradePrice";

    // 급락
    private static final String DOWN_SETTING = "mDownSetting";
    private static final String DOWN_CANDLE = "mDownCandle";
    private static final String DOWN_PRICE_PER = "downPricePer";
    private static final String DOWN_PRICE_PER_PRE = "downPricePerPre";
    private static final String DOWN_TRADE_PER = "downTradePer";
    private static final String DOWN_TRADE_PER_PRE = "downTradePerPre";
    private static final String DOWN_TRADE_PRICE = "downTradePrice";

    private SharedPreferencesManager() {
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static int getVibration(Context context) {
        return getSharedPreferences(context).getInt(VIBRATION, 0);
    }

    public static void setVibration(Context context, int vibration) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(VIBRATION, vibration);
        editor.apply();
    }

    public static boolean getUpSettingEnabled(Context context) {
        return getSharedPreferences(context).getBoolean(UP_SETTING, true);
    }

    public static void setUpSettingEnabled(Context context, boolean isEnabled) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(UP_SETTING, isEnabled);
        editor.apply();
    }

    public static int getUpCandle(Context context) {
        return getSharedPreferences(context).getInt(UP_CANDLE, 6);
    }

    public static void setUpCandle(Context context, int bunbong) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(UP_CANDLE, bunbong);
        editor.apply();
    }

    public static float getPricePer(Context context) {
        return getSharedPreferences(context).getFloat(UP_PRICE_PER, 1f);
    }

    public static void setPricePer(Context context, float pricePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(UP_PRICE_PER, pricePer);
        editor.apply();
    }

    public static float getPricePerPre(Context context) {
        return getSharedPreferences(context).getFloat(UP_PRICE_PER_PRE, -1f);
    }

    public static void setPricePerPre(Context context, float pricePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(UP_PRICE_PER_PRE, pricePerPre);
        editor.apply();
    }

    public static float getTradePer(Context context) {
        return getSharedPreferences(context).getFloat(UP_TRADE_PER, -1f);
    }

    public static void setTradePer(Context context, float tradePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(UP_TRADE_PER, tradePer);
        editor.apply();
    }

    public static float getTradePerPre(Context context) {
        return getSharedPreferences(context).getFloat(UP_TRADE_PER_PRE, -1f);
    }

    public static void setTradePerPre(Context context, float tradePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(UP_TRADE_PER_PRE, tradePerPre);
        editor.apply();
    }

    public static int getTradePrice(Context context) {
        return getSharedPreferences(context).getInt(UP_TRADE_PRICE, 100);
    }

    public static void setTradePrice(Context context, int tradePrice) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(UP_TRADE_PRICE, tradePrice);
        editor.apply();
    }

    // 급락 설정값
    public static boolean getDownSettingEnabled(Context context) {
        return getSharedPreferences(context).getBoolean(DOWN_SETTING, false);
    }

    public static void setDownSettingEnabled(Context context, boolean isEnabled) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putBoolean(DOWN_SETTING, isEnabled);
        editor.apply();
    }

    public static void setDownCandle(Context context, int candle) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(DOWN_CANDLE, candle);
        editor.apply();
    }

    public static int getDownCandle(Context context) {
        return getSharedPreferences(context).getInt(DOWN_CANDLE, 6);
    }

    public static float getDownPricePer(Context context) {
        return getSharedPreferences(context).getFloat(DOWN_PRICE_PER, 2f);
    }

    public static void setDownPricePer(Context context, float downPricePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(DOWN_PRICE_PER, downPricePer);
        editor.apply();
    }

    public static float getDownPricePerPre(Context context) {
        return getSharedPreferences(context).getFloat(DOWN_PRICE_PER_PRE, -1f);
    }

    public static void setDownPricePerPre(Context context, float downPricePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(DOWN_PRICE_PER_PRE, downPricePerPre);
        editor.apply();
    }

    public static float getDownTradePer(Context context) {
        return getSharedPreferences(context).getFloat(DOWN_TRADE_PER, -1f);
    }

    public static void setDownTradePer(Context context, float downTradePer) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(DOWN_TRADE_PER, downTradePer);
        editor.apply();
    }

    public static float getDownTradePerPre(Context context) {
        return getSharedPreferences(context).getFloat(DOWN_TRADE_PER_PRE, -1f);
    }

    public static void setDownTradePerPre(Context context, float downTradePerPre) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putFloat(DOWN_TRADE_PER_PRE, downTradePerPre);
        editor.apply();
    }

    public static int getDownTradePrice(Context context) {
        return getSharedPreferences(context).getInt(DOWN_TRADE_PRICE, 100);
    }

    public static void setDownTradePrice(Context context, int downTradePrice) {
        final SharedPreferences.Editor editor = getSharedPreferences(context).edit();
        editor.putInt(DOWN_TRADE_PRICE, downTradePrice);
        editor.apply();
    }


    // other getters/setters
}
