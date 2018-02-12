package com.example.com.jumpupbitcoin.jumpCoin;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.com.jumpupbitcoin.Const;
import com.example.com.jumpupbitcoin.R;

/**
 * Created by COM on 2018-01-24.
 */

public class UpListView extends LinearLayout {

    TextView name_Text;
    TextView price_Text;
    TextView up_per_Text;
    ImageView image_coin;

    public UpListView(Context context) {
        super(context);
        inflation_init(context);

        name_Text = (TextView) findViewById(R.id.name_coin_txt);
        price_Text = (TextView) findViewById(R.id.price_coin_txt);
        up_per_Text = (TextView) findViewById(R.id.up_per_txt);
        image_coin = (ImageView) findViewById(R.id.image_coin1);
    }

    private void inflation_init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.up_per, this, true);
    }

    public void setName(String name) {
        name_Text.setText(name);
    }

    public void setPrice(int price) {
        price_Text.setText(String.format("%,d원", price, 3));
    }

    public void setPer(String per) {
        up_per_Text.setText(per + "%");
    }

    public void setImage(int num) {
        image_coin.setImageResource(Const.sCoinImages[num]);
    }

}
