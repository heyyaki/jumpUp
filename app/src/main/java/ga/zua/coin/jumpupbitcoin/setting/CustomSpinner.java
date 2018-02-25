package ga.zua.coin.jumpupbitcoin.setting;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;

public class CustomSpinner extends android.support.v7.widget.AppCompatSpinner {

    OnItemSelectedListener listener;
    int prevPos = 0;

    public CustomSpinner(Context context) {
        super(context);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        Log.d("MY_LOG", "setSelection : " + position + ", getSelectedItemPosition() : " + getSelectedItemPosition() + ", prevPos : " + prevPos);

        if (position == getSelectedItemPosition() && prevPos == position) {
            getOnItemSelectedListener().onItemSelected(null, null, position, 0);
        }
        prevPos = position;
    }
}