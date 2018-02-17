package ga.zua.coin.jumpupbitcoin;

import ga.zua.coin.jumpupbitcoin.priceInfo.PriceData;

import java.text.DecimalFormat;
import java.util.List;

public class CalPrice {

    private PriceData mPriceData = new PriceData();
    private onChangeData mChangeData = null;

    interface onChangeData {
        void onDataChanged(List<String> priceList, List<String> perList);
    }

    void setOnChangedDataLister(onChangeData changeData) {
        mChangeData = changeData;
    }

    void Calc(String recv_str) {
        String filter_str = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = recv_str.replaceAll(filter_word[i], "");
            recv_str = filter_str;
        }

        mPriceData.ary_price.clear();
        for (String a : recv_str.split(",")) {
            mPriceData.ary_price.add(a);
        }
    }

    void per_Calc(String recv_str) {
        String filter_str = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = recv_str.replaceAll(filter_word[i], "");
            recv_str = filter_str;
        }

        mPriceData.ary_start_per.clear();
        String[] temp_start_price = recv_str.split(",");
        for (int i = 0; i < temp_start_price.length; i++) {
            mPriceData.ary_start_per.add(temp_start_price[i]);
        }

        if (mChangeData != null) {
            mChangeData.onDataChanged(mPriceData.ary_price, mPriceData.ary_start_per);
        }
    }

    List<String> getPrice() {
        return mPriceData.ary_price;
    }

    List<String> getPer() {
        return mPriceData.ary_start_per;
    }
}
