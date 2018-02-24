package ga.zua.coin.jumpupbitcoin.jumpCoin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by JY on 2018-02-04.
 */

public class JumpData {

    public List<String> alarm_reg = new ArrayList<String>();
    public List<String> log_list = new ArrayList<String>();

    public List<String> ary_up_per = new ArrayList<String>();
    public List<String> ary_up_per_pre = new ArrayList<String>();

    public List<String> ary_up_trade_per = new ArrayList<String>();
    public List<String> ary_up_trade_per_pre = new ArrayList<String>();

    public List<String> ary_up_trade_price = new ArrayList<String>();

    public HashMap<Integer, Integer> duple_check_map = new HashMap<Integer, Integer>();

    public void clearData() {
        alarm_reg.clear();
    }

}
