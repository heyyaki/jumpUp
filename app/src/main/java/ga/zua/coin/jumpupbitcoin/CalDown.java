package ga.zua.coin.jumpupbitcoin;

import android.util.Log;

import ga.zua.coin.jumpupbitcoin.downCoin.DownData;
import ga.zua.coin.jumpupbitcoin.setting.SettingData;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CalDown {

    private DownData mDownData = new DownData();
    private SettingData mSettingData = new SettingData();
    private Map<Integer, Integer> duple_check_map = new HashMap<>();

    CalDown(SettingData settingData) {
        mSettingData = settingData;
    }

    void downCatch(Document doc2) {
        int switch_minite = mSettingData.mDownCandle;
        int num_get_price = 1;

        for (int i = 0; i < mDownData.alarm_reg.size(); i++) {
            String time = new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
            mDownData.log_list.add(mDownData.alarm_reg.get(i) + "_" + time);
        }

        mDownData.alarm_reg.clear();

        String coin_price = "";
        String coin_per = "";
        String coin_per_pre = "";
        String coin_per_trade = "";
        String coin_per_pre_trade = "";
        String coin_trade_price = "";

        Elements ele_price = doc2.select("div.coin_price");
        for (Element e : ele_price) {
            coin_price = e.text();
        }

        Elements ele_per = doc2.select("div.per");
        for (Element e : ele_per) {
            coin_per = e.text();
        }

        Elements ele_per_pre = doc2.select("div.per_pre");
        for (Element e : ele_per_pre) {
            coin_per_pre = e.text();
        }

        Elements ele_per_trade = doc2.select("div.per_trade");
        for (Element e : ele_per_trade) {
            coin_per_trade = e.text();
        }

        Elements ele_per_pre_trade = doc2.select("div.per_pre_trade");
        for (Element e : ele_per_pre_trade) {
            coin_per_pre_trade = e.text();
        }

        Elements ele_trade_price = doc2.select("div.trade_price");
        for (Element e : ele_trade_price) {
            coin_trade_price = e.text();
        }
        down_per_Calc(coin_price, coin_per, coin_per_pre, coin_per_trade, coin_per_pre_trade, coin_trade_price);
    }

    private void down_per_Calc(String coin_price,String coin_per,String coin_per_pre,String coin_per_trade,String coin_per_pre_trade, String coin_trade_price) {

        String filter_str = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = coin_price.replaceAll(filter_word[i], "");
            coin_price = filter_str;
            filter_str = coin_per.replaceAll(filter_word[i], "");
            coin_per = filter_str;
            filter_str = coin_per_pre.replaceAll(filter_word[i], "");
            coin_per_pre = filter_str;
            filter_str = coin_per_trade.replaceAll(filter_word[i], "");
            coin_per_trade = filter_str;
            filter_str = coin_per_pre_trade.replaceAll(filter_word[i], "");
            coin_per_pre_trade = filter_str;
            filter_str = coin_trade_price.replaceAll(filter_word[i], "");
            coin_trade_price = filter_str;
        }

        String[] coin_price_arr = coin_price.split(",");
        String[] coin_per_arr = coin_per.split(",");
        String[] coin_per_pre_arr = coin_per_pre.split(",");
        String[] coin_per_trade_arr = coin_per_trade.split(",");
        String[] coin_per_pre_trade_arr = coin_per_pre_trade.split(",");
        String[] coin_trade_price_arr = coin_trade_price.split(",");

        try {
            for (int i = 0; i < coin_price_arr.length; i++) {
                if(Integer.parseInt(coin_trade_price_arr[i])>mSettingData.trade_price) {
                    if (mSettingData.down_price_per_pre == -1 && mSettingData.down_trade_per == -1 && mSettingData.down_trade_per_pre == -1) {
                        duple_check_method(i);
                        if (Float.parseFloat(coin_per_arr[i]) < -mSettingData.down_price_per) {
                            alarm_check_method(i, coin_per_arr, coin_price_arr);
                        }
                    } else if (mSettingData.down_price_per_pre == -1 && mSettingData.down_trade_per == -1 && mSettingData.down_trade_per_pre != -1) {
                        duple_check_method(i);
                        if (Float.parseFloat(coin_per_arr[i]) < -mSettingData.down_price_per && Float.parseFloat(coin_per_pre_trade_arr[i]) > mSettingData.down_trade_per_pre) {
                            alarm_check_method(i, coin_per_arr, coin_price_arr);
                        }
                    } else if (mSettingData.down_price_per_pre == -1 && mSettingData.down_trade_per != -1 && mSettingData.down_trade_per_pre == -1) {
                        duple_check_method(i);
                        if (Float.parseFloat(coin_per_arr[i]) < -mSettingData.down_price_per && Float.parseFloat(coin_per_trade_arr[i]) > mSettingData.down_trade_per) {
                            alarm_check_method(i, coin_per_arr, coin_price_arr);
                        }
                    } else if (mSettingData.down_price_per_pre == -1 && mSettingData.down_trade_per != -1 && mSettingData.down_trade_per_pre != -1) {
                        duple_check_method(i);
                        if (Float.parseFloat(coin_per_arr[i]) < -mSettingData.down_price_per && Float.parseFloat(coin_per_trade_arr[i]) > mSettingData.down_trade_per && Float.parseFloat(coin_per_pre_trade_arr[i]) > mSettingData.down_trade_per_pre) {
                            alarm_check_method(i, coin_per_arr, coin_price_arr);
                        }
                    } else if (mSettingData.down_price_per_pre != -1 && mSettingData.down_trade_per == -1 && mSettingData.down_trade_per_pre == -1) {
                        duple_check_method(i);
                        if (Float.parseFloat(coin_per_arr[i]) < -mSettingData.down_price_per && Float.parseFloat(coin_per_pre_arr[i]) < -mSettingData.down_price_per_pre) {
                            alarm_check_method(i, coin_per_arr, coin_price_arr);
                        }
                    } else if (mSettingData.down_price_per_pre != -1 && mSettingData.down_trade_per == -1 && mSettingData.down_trade_per_pre != -1) {
                        duple_check_method(i);
                        if (Float.parseFloat(coin_per_arr[i]) < -mSettingData.down_price_per && Float.parseFloat(coin_per_pre_arr[i]) < -mSettingData.down_price_per_pre && Float.parseFloat(coin_per_pre_trade_arr[i]) > mSettingData.down_trade_per_pre) {
                            alarm_check_method(i, coin_per_arr, coin_price_arr);
                        }
                    } else if (mSettingData.down_price_per_pre != -1 && mSettingData.down_trade_per != -1 && mSettingData.down_trade_per_pre == -1) {
                        duple_check_method(i);
                        if (Float.parseFloat(coin_per_arr[i]) < -mSettingData.down_price_per && Float.parseFloat(coin_per_pre_arr[i]) < -mSettingData.down_price_per_pre && Float.parseFloat(coin_per_trade_arr[i]) > mSettingData.down_trade_per) {
                            alarm_check_method(i, coin_per_arr, coin_price_arr);
                        }
                    } else if (mSettingData.down_price_per_pre != -1 && mSettingData.down_trade_per != -1 && mSettingData.down_trade_per_pre != -1) {
                        duple_check_method(i);
                        if (Float.parseFloat(coin_per_arr[i]) < -mSettingData.down_price_per && Float.parseFloat(coin_per_pre_arr[i]) < -mSettingData.down_price_per_pre && Float.parseFloat(coin_per_trade_arr[i]) > mSettingData.down_trade_per && Float.parseFloat(coin_per_pre_trade_arr[i]) > mSettingData.down_trade_per_pre) {
                            alarm_check_method(i, coin_per_arr, coin_price_arr);
                        }
                    }
                }
            }

            mChangeData.onDataChanged(mDownData.alarm_reg, mDownData.log_list);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception", String.valueOf(e));
        }
    }

    private void alarm_check_method(int i, String[] coin_per_arr, String[] coin_price_arr) {
        if (!duple_check_map.containsKey(i)) {
            if (mSettingData.mVibration != Const.VIBRATION_DISABLED) {
                MainActivity.mVibrator.vibrate(Const.vibPattern, -1);
            }
            mDownData.alarm_reg.add(i + "_" + coin_per_arr[i] + "_" + coin_price_arr[i]);
            duple_check_map.put(i, mSettingData.mDownCandle);
        }
    }

    private void duple_check_method(int i) {
        if (duple_check_map.containsKey(i)) {
            int a = duple_check_map.get(i);
            duple_check_map.put(i, --a);
            if (duple_check_map.get(i) == 0) {
                duple_check_map.remove(i);
            }

        }
    }

    List<String> getAlarmReg() {
        return mDownData.alarm_reg;
    }

    List<String> getLogList() {
        return mDownData.log_list;
    }

    private onChangeData mChangeData = null;

    void setOnChangedDataLister(onChangeData changeData) {
        mChangeData = changeData;
    }

    public void clearLogData() {
        mDownData.log_list.clear();
    }

    interface onChangeData {
        void onDataChanged(List<String> alarmReg, List<String> logList);
    }
}