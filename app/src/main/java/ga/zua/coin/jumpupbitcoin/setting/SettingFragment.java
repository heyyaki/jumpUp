package ga.zua.coin.jumpupbitcoin.setting;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import ga.zua.coin.jumpupbitcoin.Const;
import ga.zua.coin.jumpupbitcoin.R;

public class SettingFragment extends Fragment {
    private static final String ARG_VIBRATION = "argument_vibration";

    private static final String ARG_UP_SETTING = "argument_up_setting";
    private static final String ARG_UP_CANDLE = "argument_up_candle";
    private static final String ARG_PRICE_PER = "argument_pricePer";
    private static final String ARG_PRICE_PER_PRE = "argument_pricePerPre";
    private static final String ARG_TRADE_PER = "argument_tradePer";
    private static final String ARG_TRADE_PER_PRE = "argument_tradePerPre";
    private static final String ARG_TRADE_PRICE = "argument_tradePrice";

    private static final String ARG_DOWN_SETTING = "argument_down_setting";
    private static final String ARG_DOWN_CANDLE = "argument_down_candle";
    private static final String ARG_DOWN_PRICE_PER = "argument_down_pricePer";
    private static final String ARG_DOWN_PRICE_PER_PRE = "argument_down_pricePerPre";
    private static final String ARG_DOWN_TRADE_PER = "argument_down_tradePer";
    private static final String ARG_DOWN_TRADE_PER_PRE = "argument_down_tradePerPre";
    private static final String ARG_DOWN_TRADE_PRICE = "argument_down_tradePrice";

    private static final int DISABLED_VALUE = -1;

    private boolean mIsUpSetting, mIsDownSetting;
    private int mIsVibration, mUpCandle, mDownCandle, mTradePrice, mDownTradePrice;
    private final static ArrayList<Integer> mCandleList = new ArrayList<>();

    static {
        mCandleList.add(1);
        mCandleList.add(2);
        mCandleList.add(6);
        mCandleList.add(10);
        mCandleList.add(20);
        mCandleList.add(30);
    }

    private float mPricePer, mPricePerPre, mTradePer, mTradePerPre;
    private float mDownPricePer, mDownPricePerPre, mDownTradePer, mDownTradePerPre;

    private DecimalFormat decimalFormat = new DecimalFormat("#,###");
    private String mTtradePriceResult;

    private OnSettingFragment mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(
            int vibrationSettingEnabled,
            boolean isUpSettingEnabled, int upCandle, float pricePer, float pricePerPre, float tradePer, float tradePerPre, int tradePrice,
            boolean isDownSettingEnabled, int downCandle, float downPricePer, float downPricePerPre, float downTradePer, float downTradePerPre, int downTradePrice) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();

        args.putInt(ARG_VIBRATION, vibrationSettingEnabled);

        args.putBoolean(ARG_UP_SETTING, isUpSettingEnabled);
        args.putInt(ARG_UP_CANDLE, upCandle);
        args.putFloat(ARG_PRICE_PER, pricePer);
        args.putFloat(ARG_PRICE_PER_PRE, pricePerPre);
        args.putFloat(ARG_TRADE_PER, tradePer);
        args.putFloat(ARG_TRADE_PER_PRE, tradePerPre);
        args.putInt(ARG_TRADE_PRICE, tradePrice);

        args.putBoolean(ARG_DOWN_SETTING, isDownSettingEnabled);
        args.putInt(ARG_DOWN_CANDLE, downCandle);
        args.putFloat(ARG_DOWN_PRICE_PER, downPricePer);
        args.putFloat(ARG_DOWN_PRICE_PER_PRE, downPricePerPre);
        args.putFloat(ARG_DOWN_TRADE_PER, downTradePer);
        args.putFloat(ARG_DOWN_TRADE_PER_PRE, downTradePerPre);
        args.putInt(ARG_DOWN_TRADE_PRICE, downTradePrice);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mIsVibration = getArguments().getInt(ARG_VIBRATION);

            mIsUpSetting = getArguments().getBoolean(ARG_UP_SETTING);
            mUpCandle = getArguments().getInt(ARG_UP_CANDLE);
            mPricePer = getArguments().getFloat(ARG_PRICE_PER);
            mPricePerPre = getArguments().getFloat(ARG_PRICE_PER_PRE);
            mTradePer = getArguments().getFloat(ARG_TRADE_PER);
            mTradePerPre = getArguments().getFloat(ARG_TRADE_PER_PRE);
            mTradePrice = getArguments().getInt(ARG_TRADE_PRICE);

            mIsDownSetting = getArguments().getBoolean(ARG_DOWN_SETTING);
            mDownCandle = getArguments().getInt(ARG_DOWN_CANDLE);
            mDownPricePer = getArguments().getFloat(ARG_DOWN_PRICE_PER);
            mDownPricePerPre = getArguments().getFloat(ARG_DOWN_PRICE_PER_PRE);
            mDownTradePer = getArguments().getFloat(ARG_DOWN_TRADE_PER);
            mDownTradePerPre = getArguments().getFloat(ARG_DOWN_TRADE_PER_PRE);
            mDownTradePrice = getArguments().getInt(ARG_DOWN_TRADE_PRICE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.setting_fragment, container, false);
        vibrationControl(v);
        makeSettingCardView(v.findViewById(R.id.up_setting),
                mUpCandle, mPricePer, mPricePerPre,
                mTradePer, mTradePerPre, mIsUpSetting, mTradePrice);
        makeSettingCardView(v.findViewById(R.id.down_setting),
                mDownCandle, mDownPricePer, mDownPricePerPre,
                mDownTradePer, mDownTradePerPre, mIsDownSetting, mDownTradePrice);
        return v;
    }

    private void makeSettingCardView(final View cardView, int candle, float pricePer, float pricePerPre, final float tradePer, float tradePerPre, boolean isSetting, int tradePrice) {
        initCandleView(cardView, candle);

        final TextView mPrice1TextView, mPrice2TextView, mTrade1TextView, mTrade2TextView;
        final BubbleSeekBar mPrice1SeekBar, mPrice2SeekBar, mTrade1SeekBar, mTrade2SeekBar;

        mPrice1TextView = cardView.findViewById(R.id.price1_textview);
        mPrice2TextView = cardView.findViewById(R.id.price2_textview);
        mTrade1TextView = cardView.findViewById(R.id.trade1_textview);
        mTrade2TextView = cardView.findViewById(R.id.trade2_textview);

        mPrice1SeekBar = cardView.findViewById(R.id.price1_seekbar);
        mPrice2SeekBar = cardView.findViewById(R.id.price2_seekbar);
        mTrade1SeekBar = cardView.findViewById(R.id.trade1_seekbar);
        mTrade2SeekBar = cardView.findViewById(R.id.trade2_seekbar);

        final boolean isUpSetting = cardView.getId() == R.id.up_setting;
        TextView settingSwitch = cardView.findViewById(R.id.up_setting_switch);
        settingSwitch.setText(isUpSetting ? R.string.search_setting : R.string.down_search_setting);



        initCardView(cardView, mPrice1TextView, mPrice1SeekBar, isUpSetting ? R.string.pre_price : R.string.down_pre_pre_price, pricePer);
        initCardView(cardView, mPrice2TextView, mPrice2SeekBar, isUpSetting ? R.string.pre_pre_price : R.string.down_pre_pre_price, pricePerPre);
        initCardView(cardView, mTrade1TextView, mTrade1SeekBar, R.string.pre_trade, tradePer);
        initCardView(cardView, mTrade2TextView, mTrade2SeekBar, R.string.pre_pre_trade, tradePerPre);

        final EditText editText = cardView.findViewById(R.id.trade_price_edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }

                int tradePrice = DISABLED_VALUE;
                try {
                    tradePrice = Integer.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    tradePrice = DISABLED_VALUE;
                }

                if (cardView.getId() == R.id.up_setting) {
                    mTradePrice = tradePrice;
                    mListener.onUpTradePriceEditted(tradePrice);
                } else {
                    mDownTradePrice = tradePrice;
                    mListener.onDownTradePriceEditted(tradePrice);
                }

                if (tradePrice == DISABLED_VALUE) {
                    editText.setText("");
                } else {
//                    if (!TextUtils.isEmpty(s.toString()) && !s.toString().equals(mTtradePriceResult)) {
//                        mTtradePriceResult = decimalFormat.format(Long.parseLong(s.toString().replaceAll(",", "")));
//                        editText.setText(mTtradePriceResult);
//                        editText.setSelection(mTtradePriceResult.length());
//                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        editText.setText(String.valueOf(tradePrice));

        CustomSpinner spinner = cardView.findViewById(R.id.trade_price_spinner);
        spinner.setSelection(0, false);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position < 0 || position >= getResources().getStringArray(R.array.str_ary_trade_price).length) {
                    return;
                }

                final String text = getResources().getStringArray(R.array.str_ary_trade_price)[position];
                editText.setText(text);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final Switch priceLayoutSwitch = cardView.findViewById(R.id.price_layout_switch);
        priceLayoutSwitch.setText(isUpSetting ? R.string.up_price : R.string.down_price);
        final ViewGroup price2Layout = cardView.findViewById(R.id.price2_layout);

        priceLayoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("MY_LOG", "onCheckedChanged : " + isChecked);
                disableEnableControls(price2Layout, isChecked);
            }
        });
        priceLayoutSwitch.setChecked(true);

        final Switch tradeLayoutSwitch = cardView.findViewById(R.id.trade_layout_switch);
        final ViewGroup trade1Layout = cardView.findViewById(R.id.trade1_layout);
        final ViewGroup trade2Layout = cardView.findViewById(R.id.trade2_layout);
        tradeLayoutSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                disableEnableControls(trade1Layout, isChecked);
                disableEnableControls(trade2Layout, isChecked);
            }
        });
        tradeLayoutSwitch.setChecked(true);

        settingLayoutControl((ViewGroup) cardView, isSetting);
    }

    private void initCandleView(final View cardView, int candle) {
        final TextView textView = cardView.findViewById(R.id.candle_textview);
        final BubbleSeekBar seekBar = cardView.findViewById(R.id.candle_seekbar);
        textView.setText("분봉 : " + candle / 2f + " 분");

        // key : position of seekbar, value :text
        final SparseArray<Float> array = new SparseArray<>();
        array.put(0, 0.5f);
        array.put(3, 1f);
        array.put(6, 3f);
        array.put(9, 5f);
        array.put(12, 10f);
        array.put(15, 15f);

        int value = 0;
        for (int i = 0; i < array.size(); i++) {
            if (Float.compare(array.valueAt(i), candle / 2f) == 0) {
                value = i;
                break;
            }
        }

        seekBar.setProgress(15 / (array.size() - 1) * value);
        seekBar.setCustomSectionTextArray(new BubbleSeekBar.CustomSectionTextArray() {
            @NonNull
            @Override
            public SparseArray<String> onCustomize(int sectionCount, @NonNull SparseArray<String> array) {
                array.clear();
                array.put(0, "0.5");
                array.put(1, "1");
                array.put(2, "3");
                array.put(3, "5");
                array.put(4, "10");
                array.put(5, "15");
                return array;
            }
        });

        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                float candle = array.get(progress);
                textView.setText("분봉 : " + candle + " 분");
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                float candle = array.get(progress) * 2;

                switch (bubbleSeekBar.getId()) {
                    case R.id.candle_seekbar:
                        if (cardView.getId() == R.id.up_setting) {
                            mListener.onUpCandleButtonClicked((int) candle);
                        } else {
                            mListener.onDownCandleButtonClicked((int) candle);
                        }

                        break;
                }

                textView.setText("분봉 : " + candle / 2 + " 분");
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
            }
        });
    }

    private void initCardView(final View cardView, final TextView textView, final BubbleSeekBar seekBar, final int stringResId, final float value) {

        String text = String.format(textView.getResources().getString(stringResId), value) + " %";
        textView.setText(text);
        seekBar.setProgress(value);
        seekBar.setOnProgressChangedListener(new BubbleSeekBar.OnProgressChangedListener() {
            @Override
            public void onProgressChanged(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                String text = String.format(bubbleSeekBar.getResources().getString(stringResId), progressFloat) + " %";
                textView.setText(text);
            }

            @Override
            public void getProgressOnActionUp(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {
                switch (bubbleSeekBar.getId()) {
                    case R.id.price1_seekbar:
                        if (cardView.getId() == R.id.up_setting) {
                            mListener.onUpPrePriceEditted(progressFloat);
                        } else {
                            mListener.onDownPrePriceEditted(progressFloat);
                        }

                        break;
                    case R.id.price2_seekbar:
                        if (cardView.getId() == R.id.up_setting) {
                            mListener.onUpPrePrePriceEditted(progressFloat);
                        } else {
                            mListener.onDownPrePrePriceEditted(progressFloat);
                        }
                        break;
                    case R.id.trade1_seekbar:
                        if (cardView.getId() == R.id.up_setting) {
                            mListener.onUpPreTradeEditted(progressFloat);
                        } else {
                            mListener.onDownPreTradeEditted(progressFloat);
                        }
                        break;
                    case R.id.trade2_seekbar:
                        if (cardView.getId() == R.id.up_setting) {
                            mListener.onUpPrePreTradeEditted(progressFloat);
                        } else {
                            mListener.onDownPrePreTradeEditted(progressFloat);
                        }
                        break;
                }
            }

            @Override
            public void getProgressOnFinally(BubbleSeekBar bubbleSeekBar, int progress, float progressFloat) {

            }
        });
    }

    private void vibrationControl(View root) {
//        final ViewGroup vibrationLayout = root.findViewById(R.id.vibrate_setting);
//        disableEnableControls(vibrationLayout, mIsVibration != Const.VIBRATION_DISABLED);

        Switch vibrateSetting = root.findViewById(R.id.vibrate_setting_switch);
        vibrateSetting.setEnabled(true);

        vibrateSetting.setChecked(mIsVibration != Const.VIBRATION_DISABLED);
        vibrateSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsVibration = isChecked ? 0 : Const.VIBRATION_DISABLED;
//                disableEnableControls(vibrationLayout, isChecked);
                buttonView.setEnabled(true);

                mListener.onVibrationSelected(mIsVibration);
            }
        });
    }

    private void settingLayoutControl(final ViewGroup cardView, boolean isSetting) {
        disableEnableControls(cardView, isSetting);
        Switch upSetting = cardView.findViewById(R.id.up_setting_switch);
        upSetting.setEnabled(true);
        upSetting.setVisibility(View.VISIBLE);
        upSetting.setChecked(isSetting);
        upSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                disableEnableControls(cardView, isChecked);
                buttonView.setEnabled(true);
                buttonView.setVisibility(View.VISIBLE);

                switch (cardView.getId()) {
                    case R.id.up_setting:
                        mListener.onUpSettingEnabled(isChecked);
                        break;
                    case R.id.down_setting:
                        mListener.onDownSettingEnabled(isChecked);
                        break;
                }
            }
        });
    }

    private void disableEnableControls(ViewGroup vg, boolean enable) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls((ViewGroup) child, enable);
            }
            child.setVisibility(enable ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSettingFragment) {
            mListener = (OnSettingFragment) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnSettingFragment {

        void onVibrationSelected(int vibration);

        void onUpSettingEnabled(boolean isEnabled);

        void onUpCandleButtonClicked(int candle);

        void onUpPrePriceEditted(float prePrice);

        void onUpPrePrePriceEditted(float prePrePrice);

        void onUpPreTradeEditted(float preTrade);

        void onUpPrePreTradeEditted(float prePreTrade);

        void onUpTradePriceEditted(int TradePrice);

        void onDownSettingEnabled(boolean isEnabled);

        void onDownCandleButtonClicked(int candle);

        void onDownPrePriceEditted(float prePrice);

        void onDownPrePrePriceEditted(float prePrePrice);

        void onDownPreTradeEditted(float preTrade);

        void onDownPrePreTradeEditted(float prePreTrade);

        void onDownTradePriceEditted(int downTradePrice);
    }
}
