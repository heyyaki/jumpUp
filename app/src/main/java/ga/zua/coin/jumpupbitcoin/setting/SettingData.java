package ga.zua.coin.jumpupbitcoin.setting;

public class SettingData {

    // 전체 설정
    public int mVibration;

    // 급등 검색식 설정
    public boolean mIsUpSettingEnabled;
    public int mUpCandle;
    public float price_per;
    public float price_per_pre;
    public float trade_per;
    public float trade_per_pre;
    public int trade_price;

    // 급락 검색식 설정
    public boolean mIsDownSettingEnabled;
    public int mDownCandle;
    public float down_price_per;
    public float down_price_per_pre;
    public float down_trade_per;
    public float down_trade_per_pre;
    public int down_trade_price;
}
