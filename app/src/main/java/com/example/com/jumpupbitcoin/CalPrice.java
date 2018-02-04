package com.example.com.jumpupbitcoin;

import android.os.Handler;
import android.os.Looper;

import com.example.com.jumpupbitcoin.priceInfo.PriceData;

import java.text.DecimalFormat;
import java.util.List;

public class CalPrice {

    private PriceData mPriceData = new PriceData();

    public void Calc(String recv_str) {
        String filter_str = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = recv_str.replaceAll(filter_word[i], "");
            recv_str = filter_str;
        }

        for(String a :recv_str.split(",")){
            mPriceData.ary_price.add(a);
        }
    }

    public void per_Calc(String recv_str) {
        String filter_str = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = recv_str.replaceAll(filter_word[i], "");
            recv_str = filter_str;
        }

        String[] temp_start_price = recv_str.split(",");
        for (int i = 0; i < temp_start_price.length; i++) {
            DecimalFormat form = new DecimalFormat("#.##");
            if (mPriceData.ary_start_per.size() < 36)
                mPriceData.ary_start_per.add(form.format((Float.parseFloat(mPriceData.ary_price.get(i)) / Float.parseFloat(temp_start_price[i])) * 100 - 100));
            else {
                mPriceData.ary_start_per.set(i, form.format((Float.parseFloat(mPriceData.ary_price.get(i)) / Float.parseFloat(temp_start_price[i])) * 100 - 100));
            }
        }
    }

    public List<String> getPrice() {
        return mPriceData.ary_price;
    }

    public List<String> getPer() {
        return mPriceData.ary_start_per;
    }
}
