package com.example.com.jumpupbitcoin;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by COM on 2018-01-24.
 */

public class LogUpView extends LinearLayout{

    TextView name_Text;
    TextView price_Text;
    TextView up_per_Text;
    TextView date_Text;
    ImageView image_coin;
    Integer[] images;

    public LogUpView(Context context) {
        super(context);
        inflation_init(context);

        name_Text = (TextView) findViewById(R.id.name_coin_txt2);
        price_Text = (TextView) findViewById(R.id.price_coin_txt2);
        up_per_Text = (TextView) findViewById(R.id.up_per_txt2);
        date_Text = (TextView) findViewById(R.id.date_coin_txt);
        image_coin = (ImageView) findViewById(R.id.image_coin1);
        images = new Integer[]{R.drawable.btc, R.drawable.ada, R.drawable.xrp, R.drawable.snt, R.drawable.qtum, R.drawable.eth, R.drawable.mer, R.drawable.neo, R.drawable.sbd, R.drawable.steem,
                R.drawable.xlm, R.drawable.emc, R.drawable.btg, R.drawable.ardr, R.drawable.xem, R.drawable.tix, R.drawable.powr, R.drawable.bcc, R.drawable.kmd, R.drawable.strat,
                R.drawable.etc, R.drawable.omg, R.drawable.grs, R.drawable.storj, R.drawable.rep, R.drawable.waves, R.drawable.ark, R.drawable.xmr, R.drawable.ltc, R.drawable.lsk,
                R.drawable.vtc, R.drawable.pivx, R.drawable.mtl, R.drawable.dash, R.drawable.zec};
    }

    private void inflation_init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.log_up_coin, this, true);
    }

    public void setName(String name){
        name_Text.setText(name);
    }

    public void setPrice(int price){
        price_Text.setText(String.format("%,d원",price,3));
    }

    public void setPer(String per) { up_per_Text.setText(per+"%"); }

    public void setDate(String time) { date_Text.setText(time); }

    public void setImage(int num){
        image_coin.setImageResource(images[num]);
    }

}
