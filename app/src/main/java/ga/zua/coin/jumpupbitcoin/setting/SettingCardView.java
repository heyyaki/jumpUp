package ga.zua.coin.jumpupbitcoin.setting;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import ga.zua.coin.jumpupbitcoin.Const;
import ga.zua.coin.jumpupbitcoin.R;

public class SettingCardView extends LinearLayout {

    public SettingCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        init(context);
    }
//
//
//    private void init(Context context) {
//        TextView mPrice1TextView, mPrice2TextView, mTrade1TextView, mTrade2TextView;
//        BubbleSeekBar mPrice1SeekBar, mPrice2SeekBar, mTrade1SeekBar, mTrade2SeekBar;
//
//        mPrice1TextView = this.findViewById(R.id.price1_textview);
//        mPrice2TextView = this.findViewById(R.id.price2_textview);
//        mTrade1TextView = this.findViewById(R.id.trade1_textview);
//        mTrade2TextView = this.findViewById(R.id.trade2_textview);
//
//        mPrice1SeekBar = this.findViewById(R.id.price1_seekbar);
//        mPrice2SeekBar = this.findViewById(R.id.price2_seekbar);
//        mTrade1SeekBar = this.findViewById(R.id.trade1_seekbar);
//        mTrade2SeekBar = this.findViewById(R.id.trade2_seekbar);
//
//        initCardView(mPrice1TextView, mPrice1SeekBar, R.string.pre_price, mPricePer);
//        initCardView(mPrice2TextView, mPrice2SeekBar, R.string.pre_pre_price, mPricePerPre);
//        initCardView(mTrade1TextView, mTrade1SeekBar, R.string.pre_trade, mTradePer);
//        initCardView(mTrade2TextView, mTrade2SeekBar, R.string.pre_pre_trade, mTradePerPre);
//
//        disableEnableControlsLayout(v);
//    }
//
//
//    private void initCandleView(View root) {
//        final TextView mCandleTextView = root.findViewById(R.id.candle_textview);
//        mCandleTextView.setText("분봉 : " + mUpCandle / 2f + " 분");
//
//        // key : position of seekbar, value :
//        final SparseArray<Float> array = new SparseArray<>();
//        array.put(0, 0.5f);
//        array.put(3, 1f);
//        array.put(6, 3f);
//        array.put(9, 5f);
//        array.put(12, 10f);
//        array.put(15, 15f);
//
//        BubbleSeekBar seekBar = root.findViewById(R.id.candle_seekbar);
//
//        int value = 0;
//        for (int i = 0; i < array.size(); i++) {
//            if (Float.compare(array.valueAt(i), mUpCandle / 2f) == 0) {
//                value = i;
//                break;
//            }
//        }
//
//        seekBar.setProgress(15 / (array.size() - 1) * value);
//
//        Log.d("MY_LOG", " " + 15 / (array.size() - 1) * value + ", value : " + value);
//        seekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
//            @NonNull
//            @Override
//            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
//                array.clear();
//                array.put(0, "0.5");
//                array.put(1, "1");
//                array.put(2, "3");
//                array.put(3, "5");
//                array.put(4, "10");
//                array.put(5, "15");
//                return array;
//            }
//        });
//
//        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
//            @Override
//            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//                Log.d("MY_LOG", "progress : " + progress);
//                float candle = array.get(progress);
//                mCandleTextView.setText("분봉 : " + candle + " 분");
//            }
//
//            @Override
//            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//                float candle = array.get(progress) * 2;
//                mListener.onUpCandleButtonClicked((int) candle);
//                mCandleTextView.setText("분봉 : " + candle / 2 + " 분");
//            }
//
//            @Override
//            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//            }
//        });
//    }
//
//    private void initCardView(final TextView textView, final BubbleSeekBar seekBar, final int stringResId, final float value) {
//
//        String text = String.format(textView.getResources().getString(stringResId), value);
//        textView.setText(text);
//        seekBar.setProgress(value);
//        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
//            @Override
//            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//                String text = String.format(bubbleSeekBar.getResources().getString(stringResId), progressFloat) + " %";
//                textView.setText(text);
//            }
//
//            @Override
//            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//                switch (bubbleSeekBar.getId()) {
//                    case R.id.price1_seekbar:
//                        mListener.onUpPrePriceEditted(progressFloat);
//                        break;
//                    case R.id.price2_seekbar:
//                        mListener.onUpPrePrePriceEditted(progressFloat);
//                        break;
//                    case R.id.trade1_seekbar:
//                        mListener.onUpPreTradeEditted(progressFloat);
//                        break;
//                    case R.id.trade2_seekbar:
//                        mListener.onUpPrePreTradeEditted(progressFloat);
//                        break;
//                }
//            }
//
//            @Override
//            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
//
//            }
//        });
//    }
//
//    private void disableEnableControlsLayout(View root) {
//        final ViewGroup vibrationLayout = root.findViewById(R.id.vibrate_setting);
//        disableEnableControls(vibrationLayout, mIsVibration != Const.VIBRATION_DISABLED);
//
//        Switch vibrateSetting = root.findViewById(R.id.vibrate_setting_switch);
//        vibrateSetting.setEnabled(true);
//        vibrateSetting.setVisibility(View.VISIBLE);
//
//        vibrateSetting.setChecked(mIsVibration != Const.VIBRATION_DISABLED);
//        vibrateSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mIsVibration = isChecked ? 0 : Const.VIBRATION_DISABLED;
//                disableEnableControls(vibrationLayout, isChecked);
//                buttonView.setEnabled(true);
//                buttonView.setVisibility(View.VISIBLE);
//
//                mListener.onVibrationSelected(mIsVibration);
//            }
//        });
//
//        final ViewGroup upSettingLayout = root.findViewById(R.id.setting_cardview);
//        disableEnableControls(upSettingLayout, mIsUpSetting);
//
//        Switch upSetting = root.findViewById(R.id.up_setting_switch);
//        upSetting.setEnabled(true);
//        upSetting.setVisibility(View.VISIBLE);
//        upSetting.setChecked(mIsUpSetting);
//        upSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mIsUpSetting = isChecked;
//                disableEnableControls(upSettingLayout, isChecked);
//                buttonView.setEnabled(true);
//                buttonView.setVisibility(View.VISIBLE);
//
//                mListener.onUpSettingEnabled(isChecked);
//            }
//        });
//
//        final ViewGroup downSettingLayout = root.findViewById(R.id.down_setting);
//        disableEnableControls(downSettingLayout, mIsDownSetting);
//
//        Switch downSetting = root.findViewById(R.id.down_setting_switch);
//        downSetting.setEnabled(true);
//        downSetting.setChecked(mIsDownSetting);
//        downSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mIsDownSetting = isChecked;
//                disableEnableControls(downSettingLayout, isChecked);
//                buttonView.setEnabled(true);
//
//                mListener.onDownSettingEnabled(isChecked);
//            }
//        });
//    }
//
//    private void disableEnableControls(ViewGroup vg, boolean enable) {
//        for (int i = 0; i < vg.getChildCount(); i++) {
//            View child = vg.getChildAt(i);
//            child.setEnabled(enable);
//            if (child instanceof ViewGroup) {
//                disableEnableControls((ViewGroup) child, enable);
//            }
//            child.setVisibility(enable ? View.VISIBLE : View.GONE);
//        }
//    }
}
