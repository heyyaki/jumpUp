package com.example.com.jumpupbitcoin;

import android.util.Log;

import com.example.com.jumpupbitcoin.downCoin.DownData;
import com.example.com.jumpupbitcoin.setting.SettingData;

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

    public CalDown(SettingData settingData) {
        mSettingData = settingData;
    }

    public void downCatch(Document doc2) {
        int switch_minite = mSettingData.mDownCandle;
        int num_get_price = 1;

        mDownData.alarm_reg.clear();

        String str_switch_minite = "";
        String str_switch_minite1 = "";
        String str_switch_trade = "";
        String str_switch_trade1 = "";
        for (int i = 0; i < 3; i++) {
            Elements start_price = doc2.select("div.crawlring" + num_get_price);
            for (Element e : start_price) {
                str_switch_minite1 = e.text();
            }

            if (str_switch_minite == "")
                str_switch_minite = str_switch_minite + str_switch_minite1;
            else
                str_switch_minite = str_switch_minite + "-" + str_switch_minite1;

            Elements start_trade = doc2.select("div.trade" + num_get_price);
            for (Element e : start_trade) {
                str_switch_trade1 = e.text();
            }

            if (str_switch_trade == "")
                str_switch_trade = str_switch_trade + str_switch_trade1;
            else
                str_switch_trade = str_switch_trade + "-" + str_switch_trade1;

            num_get_price = switch_minite + num_get_price;
        }

        //Log.d("CalDown str_Swtich_minite", str_switch_minite);
        Log.d("str_switch_trade", str_switch_trade);
        down_per_Calc(str_switch_minite, str_switch_trade);
    }

    private void down_per_Calc(String str_switch_minite, String str_switch_trade) {

        String[] temp_arr_trade;
        String[] temp_now_trade;
        String[] temp_trade;
        String[] temp_trade_pre;

        String filter_str = "";
        String filter_str2 = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = str_switch_minite.replaceAll(filter_word[i], "");
            filter_str2 = str_switch_trade.replaceAll(filter_word[i], "");
            str_switch_minite = filter_str;
            str_switch_trade = filter_str2;
        }

        String[] temp_arr_price = str_switch_minite.split("-");
        String[] temp_now_price = temp_arr_price[0].split(",");
        String[] temp_price = temp_arr_price[1].split(",");
        String[] temp_price_pre = temp_arr_price[2].split(",");

        temp_arr_trade = str_switch_trade.split("-");
        temp_now_trade = temp_arr_trade[0].split(",");
        temp_trade = temp_arr_trade[1].split(",");
        temp_trade_pre = temp_arr_trade[2].split(",");

        List<String> ary_down_per = new ArrayList<>();
        List<String> ary_down_trade_per = new ArrayList<>();
        List<String> ary_down_trade_per_pre = new ArrayList<>();
        List<String> ary_down_per_pre = new ArrayList<>();

        try {
            for (int i = 0; i < temp_now_price.length; i++) {
                DecimalFormat form = new DecimalFormat("0.00");
                if (ary_down_per.size() < 36) {
                    ary_down_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                    ary_down_per_pre.add(form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                    ary_down_trade_per.add(form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                    ary_down_trade_per_pre.add(form.format((Float.parseFloat(temp_trade[i]) / Float.parseFloat(temp_trade_pre[i])) * 100 - 100));
                } else {
                    ary_down_per.set(i, form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                    ary_down_per_pre.set(i, form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                    ary_down_trade_per.set(i, form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                    ary_down_trade_per_pre.add(i, form.format((Float.parseFloat(temp_trade[i]) / Float.parseFloat(temp_trade_pre[i])) * 100 - 100));
                }
                // price_per = -1일때로 처리하기!
                if (mSettingData.down_price_per_pre == -1 && mSettingData.down_trade_per == -1 && mSettingData.down_trade_per_pre == -1) {
                    if (Float.parseFloat(ary_down_per.get(i)) < -mSettingData.down_price_per) {
                        duple_check_method(i, ary_down_per, temp_now_price);
                    }
                } else if (mSettingData.down_price_per_pre == -1 && mSettingData.down_trade_per == -1 && mSettingData.down_trade_per_pre != -1) {
                    if (Float.parseFloat(ary_down_per.get(i)) < -mSettingData.down_price_per && Float.parseFloat(ary_down_trade_per_pre.get(i)) > mSettingData.down_trade_per_pre) {
                        duple_check_method(i, ary_down_per, temp_now_price);
                    }
                } else if (mSettingData.down_price_per_pre == -1 && mSettingData.down_trade_per != -1 && mSettingData.down_trade_per_pre == -1) {
                    if (Float.parseFloat(ary_down_per.get(i)) < -mSettingData.down_price_per && Float.parseFloat(ary_down_trade_per.get(i)) > mSettingData.down_trade_per) {
                        duple_check_method(i, ary_down_per, temp_now_price);
                    }
                } else if (mSettingData.down_price_per_pre == -1 && mSettingData.down_trade_per != -1 && mSettingData.down_trade_per_pre != -1) {
                    if (Float.parseFloat(ary_down_per.get(i)) < -mSettingData.down_price_per && Float.parseFloat(ary_down_trade_per.get(i)) > mSettingData.down_trade_per && Float.parseFloat(ary_down_trade_per_pre.get(i)) > mSettingData.down_trade_per_pre) {
                        duple_check_method(i, ary_down_per, temp_now_price);
                    }
                } else if (mSettingData.down_price_per_pre != -1 && mSettingData.down_trade_per == -1 && mSettingData.down_trade_per_pre == -1) {
                    if (Float.parseFloat(ary_down_per.get(i)) < -mSettingData.down_price_per && Float.parseFloat(ary_down_per_pre.get(i)) < -mSettingData.down_price_per_pre) {
                        duple_check_method(i, ary_down_per, temp_now_price);
                    }
                } else if (mSettingData.down_price_per_pre != -1 && mSettingData.down_trade_per == -1 && mSettingData.down_trade_per_pre != -1) {
                    if (Float.parseFloat(ary_down_per.get(i)) < -mSettingData.down_price_per && Float.parseFloat(ary_down_per_pre.get(i)) < -mSettingData.down_price_per_pre && Float.parseFloat(ary_down_trade_per_pre.get(i)) > mSettingData.down_trade_per_pre) {
                        duple_check_method(i, ary_down_per, temp_now_price);
                    }
                } else if (mSettingData.down_price_per_pre != -1 && mSettingData.down_trade_per != -1 && mSettingData.down_trade_per_pre == -1) {
                    if (Float.parseFloat(ary_down_per.get(i)) < -mSettingData.down_price_per && Float.parseFloat(ary_down_per_pre.get(i)) < -mSettingData.down_price_per_pre && Float.parseFloat(ary_down_trade_per.get(i)) > mSettingData.down_trade_per) {
                        duple_check_method(i, ary_down_per, temp_now_price);
                    }
                } else if (mSettingData.down_price_per_pre != -1 && mSettingData.down_trade_per != -1 && mSettingData.down_trade_per_pre != -1) {
                    if (Float.parseFloat(ary_down_per.get(i)) < -mSettingData.down_price_per && Float.parseFloat(ary_down_per_pre.get(i)) < -mSettingData.down_price_per_pre && Float.parseFloat(ary_down_trade_per.get(i)) > mSettingData.down_trade_per && Float.parseFloat(ary_down_trade_per_pre.get(i)) > mSettingData.down_trade_per_pre) {
                        duple_check_method(i, ary_down_per, temp_now_price);
                    }
                }
            }

            for (int i = 0; i < mDownData.alarm_reg.size(); i++) {
                String time = new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
//                String[] coin_arr = mDownData.alarm_reg.get(i).split("-");
//               if (mDownData.alarm_reg.size() != 0) {
                mDownData.log_list.add(mDownData.alarm_reg.get(i) + "_" + time);
//                }
            }

            mChangeData.onDataChanged(mDownData.alarm_reg, mDownData.log_list);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception", String.valueOf(e));
        }
    }

    private void duple_check_method(int i, List<String> ary_down_per, String[] temp_now_price) {
        if (!duple_check_map.containsKey(i)) {
            //long[] pattern = {100, 300, 100, 500, 100, 500};
//            long[] pattern = {100, 300};
            if (mSettingData.mVibration != Const.VIBRATION_DISABLED) {
                MainActivity.mVibrator.vibrate(Const.vibPattern, -1);
            }

            mDownData.alarm_reg.add(i + "_" + ary_down_per.get(i) + "_" + temp_now_price[i]);
            duple_check_map.put(i, mSettingData.mDownCandle);   // 분봉으로 값 넣기
        } else {
            int a = duple_check_map.get(i);
            duple_check_map.put(i, --a);
            if (duple_check_map.get(i) == 1) {
                duple_check_map.remove(i);
            }
        }
    }

    public void clearData() {
        mDownData.clearData();
    }

    public List<String> getAlarmReg() {
        return mDownData.alarm_reg;
    }

    public List<String> getLogList() {
        return mDownData.log_list;
    }

    private onChangeData mChangeData = null;

    public void setOnChangedDataLister(onChangeData changeData) {
        mChangeData = changeData;
    }

    interface onChangeData {
        void onDataChanged(List<String> alarmReg, List<String> logList);
    }
}