package com.example.com.jumpupbitcoin;

import android.util.Log;

import com.example.com.jumpupbitcoin.jumpCoin.JumpData;
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

public class CalJump {

    private JumpData mJumpData = new JumpData();
    private SettingData mSettingData = new SettingData();
    private float tradePer;
    private float tradePerPre;
    private Map<Integer, Integer> duple_check_map = new HashMap<>();

    public void upCatch(Document doc2) {
        int switch_minite = mSettingData.bunbong;
        int num_get_price = 1;

        mJumpData.alarm_reg.clear();

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

        Log.d("str_Swtich_minite", str_switch_minite);
        Log.d("str_switch_trade", str_switch_trade);
        up_per_Calc(str_switch_minite, str_switch_trade);
    }

    private void up_per_Calc(String str_switch_minite, String str_switch_trade) {

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

        List<String> ary_up_per = new ArrayList<>();
        List<String> ary_up_trade_per = new ArrayList<>();
        List<String> ary_up_trade_per_pre = new ArrayList<>();
        List<String> ary_up_per_pre = new ArrayList<>();

        try {
            for (int i = 0; i < temp_now_price.length; i++) {
                DecimalFormat form = new DecimalFormat("#.##");
                if (ary_up_per.size() < 36) {
                    ary_up_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                    ary_up_per_pre.add(form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                    ary_up_trade_per.add(form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                    ary_up_trade_per_pre.add(form.format((Float.parseFloat(temp_trade[i]) / Float.parseFloat(temp_trade_pre[i])) * 100 - 100));
                } else {
                    ary_up_per.set(i, form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                    ary_up_per_pre.set(i, form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                    ary_up_trade_per.set(i, form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                    ary_up_trade_per_pre.add(i, form.format((Float.parseFloat(temp_trade[i]) / Float.parseFloat(temp_trade_pre[i])) * 100 - 100));
                }

                if (mSettingData.pre_check == 0 && mSettingData.trade_check == 0 && mSettingData.pre_trade_check == 0){
                    if (Float.parseFloat(ary_up_per.get(i)) > mSettingData.price_per) {
                        duple_check_method(i, ary_up_per, temp_now_price);
                    }
                }
                else if (mSettingData.pre_check == 0 && mSettingData.trade_check == 0 && mSettingData.pre_trade_check == 1){
                    if (Float.parseFloat(ary_up_per.get(i)) > mSettingData.price_per && Float.parseFloat(ary_up_trade_per_pre.get(i)) > mSettingData.trade_per_pre) {
                        duple_check_method(i, ary_up_per, temp_now_price);
                    }
                }
                else if (mSettingData.pre_check == 0 && mSettingData.trade_check == 1 && mSettingData.pre_trade_check == 0){
                    if (Float.parseFloat(ary_up_per.get(i)) > mSettingData.price_per && Float.parseFloat(ary_up_trade_per.get(i)) > mSettingData.trade_per) {
                        duple_check_method(i, ary_up_per, temp_now_price);
                    }
                }
                else if (mSettingData.pre_check == 0 && mSettingData.trade_check == 1 && mSettingData.pre_trade_check == 1){
                    if (Float.parseFloat(ary_up_per.get(i)) > mSettingData.price_per && Float.parseFloat(ary_up_trade_per.get(i)) > mSettingData.trade_per && Float.parseFloat(ary_up_trade_per_pre.get(i)) > mSettingData.trade_per_pre) {
                        duple_check_method(i, ary_up_per, temp_now_price);
                    }
                }
                else if (mSettingData.pre_check == 1 && mSettingData.trade_check == 0 && mSettingData.pre_trade_check == 0){
                    if (Float.parseFloat(ary_up_per.get(i)) > mSettingData.price_per && Float.parseFloat(ary_up_per_pre.get(i)) > mSettingData.price_per_pre) {
                        duple_check_method(i, ary_up_per, temp_now_price);
                    }
                }
                else if (mSettingData.pre_check == 1 && mSettingData.trade_check == 0 && mSettingData.pre_trade_check == 1){
                    if (Float.parseFloat(ary_up_per.get(i)) > mSettingData.price_per && Float.parseFloat(ary_up_per_pre.get(i)) > mSettingData.price_per_pre && Float.parseFloat(ary_up_trade_per_pre.get(i)) > mSettingData.trade_per_pre) {
                        duple_check_method(i, ary_up_per, temp_now_price);
                    }
                }
                else if (mSettingData.pre_check == 1 && mSettingData.trade_check == 1 && mSettingData.pre_trade_check == 0){
                    if (Float.parseFloat(ary_up_per.get(i)) > mSettingData.price_per && Float.parseFloat(ary_up_per_pre.get(i)) > mSettingData.price_per_pre  && Float.parseFloat(ary_up_trade_per.get(i)) > mSettingData.trade_per) {
                        duple_check_method(i, ary_up_per, temp_now_price);
                    }
                }
                else if (mSettingData.pre_check == 1 && mSettingData.trade_check == 1 && mSettingData.pre_trade_check == 1){
                    if (Float.parseFloat(ary_up_per.get(i)) > mSettingData.price_per && Float.parseFloat(ary_up_per_pre.get(i)) > mSettingData.price_per_pre  && Float.parseFloat(ary_up_trade_per.get(i)) > mSettingData.trade_per && Float.parseFloat(ary_up_trade_per_pre.get(i)) > mSettingData.trade_per_pre) {
                        duple_check_method(i, ary_up_per, temp_now_price);
                    }
                }
            }

            for (int i = 0; i < mJumpData.alarm_reg.size(); i++) {
                String time = new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
//                String[] coin_arr = mJumpData.alarm_reg.get(i).split("-");
//               if (mJumpData.alarm_reg.size() != 0) {
                mJumpData.log_list.add(mJumpData.alarm_reg.get(i) + "-" + time);
//                }
            }

            mChangeData.onDataChanged(mJumpData.alarm_reg, mJumpData.log_list);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception", String.valueOf(e));
        }
    }

    private void duple_check_method(int i, List<String> ary_up_per, String[] temp_now_price) {
        if (!duple_check_map.containsKey(i)) {
            long[] pattern = {100, 300, 100, 500, 100, 500};
            MainActivity.mVibrator.vibrate(pattern, -1);
            mJumpData.alarm_reg.add(i + "-" + ary_up_per.get(i) + "-" + temp_now_price[i]);
            duple_check_map.put(i, mSettingData.bunbong);   // 분봉으로 값 넣기
        } else {
            int a = duple_check_map.get(i);
            duple_check_map.put(i, --a);
            if (duple_check_map.get(i) == 1) {
                duple_check_map.remove(i);
            }
        }
    }

    public void clearData() {
        mJumpData.clearData();
    }

    public List<String> getAlarmReg() {
        return mJumpData.alarm_reg;
    }

    public List<String> getLogList() {
        return mJumpData.log_list;
    }

    public void setBunBong(int bunBong) { mSettingData.bunbong = bunBong; }

    public void setPricePer(float pricePer) {
        mSettingData.price_per= pricePer;
    }

    public void setPricePerPer(float pricePerPer) {
        mSettingData.price_per_pre= pricePerPer;
    }

    public void setTradePer(float tradePer) {
        mSettingData.trade_per = tradePer;
    }

    public void setTradePerPre(float tradePerPre) {
        mSettingData.trade_per_pre = tradePerPre;
    }

    private onChangeData mChangeData;
    public void setOnChangedDataLister(onChangeData changeData){
        mChangeData = changeData;
    }

    interface onChangeData {
        void onDataChanged(List<String> alarmReg,  List<String> logList);
    }
}