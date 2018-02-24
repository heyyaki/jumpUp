package ga.zua.coin.jumpupbitcoin.setting;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;

import ga.zua.coin.jumpupbitcoin.Const;
import ga.zua.coin.jumpupbitcoin.R;


import java.util.ArrayList;

public class SettingFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
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

    private EditText mEditTxt, mEditTxt2, mEditTxt3, mEditTxt4, mEditTxt5;
    private EditText mEditTxt11, mEditTxt12, mEditTxt13, mEditTxt14, mEditTxt15;

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

        // 급등 분봉
        RadioGroup upGroup = (RadioGroup) v.findViewById(R.id.radioGroup1);
        upGroup.setOnCheckedChangeListener(this);

        for (int i = 0; i < mCandleList.size(); i++) {
            if (mCandleList.get(i).equals(mUpCandle)) {
                ((RadioButton) upGroup.getChildAt(i)).setChecked(true);
            }
        }

        // 급락 분봉
        RadioGroup downGroup = (RadioGroup) v.findViewById(R.id.radioGroup1_down);
        downGroup.setOnCheckedChangeListener(this);

        for (int i = 0; i < mCandleList.size(); i++) {
            if (mCandleList.get(i).equals(mDownCandle)) {
                ((RadioButton) downGroup.getChildAt(i)).setChecked(true);
            }
        }

        Spinner spinner = (Spinner) v.findViewById(R.id.up_pre_price_spinner);
        Spinner spinner2 = (Spinner) v.findViewById(R.id.up_pre_pre_price_spinner);
        Spinner spinner3 = (Spinner) v.findViewById(R.id.up_pre_trade);
        Spinner spinner4 = (Spinner) v.findViewById(R.id.up_pre_pre_trade);
        Spinner spinner5 = (Spinner) v.findViewById(R.id.up_trade_price_spinner);

        spinner.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);
        spinner3.setOnItemSelectedListener(this);
        spinner4.setOnItemSelectedListener(this);
        spinner5.setOnItemSelectedListener(this);

        Spinner spinner11 = (Spinner) v.findViewById(R.id.down_pre_price_spinner);
        Spinner spinner12 = (Spinner) v.findViewById(R.id.down_pre_pre_price_spinner);
        Spinner spinner13 = (Spinner) v.findViewById(R.id.down_pre_trade);
        Spinner spinner14 = (Spinner) v.findViewById(R.id.down_pre_pre_trade);
        Spinner spinner15 = (Spinner) v.findViewById(R.id.down_trade_price_spinner);

        spinner11.setOnItemSelectedListener(this);
        spinner12.setOnItemSelectedListener(this);
        spinner13.setOnItemSelectedListener(this);
        spinner14.setOnItemSelectedListener(this);
        spinner15.setOnItemSelectedListener(this);

        // 등락률
        mEditTxt = (EditText) v.findViewById(R.id.editText);
        mEditTxt.setText(mPricePer == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mPricePer));
        spinner.setSelection(mPricePer == DISABLED_VALUE ? 0 : 5, false);

        mEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }

                try {
                    mPricePer = Float.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mPricePer = DISABLED_VALUE;
                }

                mListener.onUpPrePriceEditted(mPricePer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt2 = (EditText) v.findViewById(R.id.editText2);
        mEditTxt2.setText(mPricePerPre == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mPricePerPre));
        spinner2.setSelection(mPricePerPre == DISABLED_VALUE ? 0 : 5, false);

        mEditTxt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mPricePerPre = Float.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mPricePerPre = DISABLED_VALUE;
                }

                mListener.onUpPrePrePriceEditted(mPricePerPre);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt3 = (EditText) v.findViewById(R.id.editText3);
        mEditTxt3.setText(mTradePer == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mTradePer));
        spinner3.setSelection(mTradePer == DISABLED_VALUE ? 0 : 5, false);

        mEditTxt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mTradePer = Float.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mTradePer = DISABLED_VALUE;
                }

                mListener.onUpPreTradeEditted(mTradePer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt4 = (EditText) v.findViewById(R.id.editText4);
        mEditTxt4.setText(mTradePerPre == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mTradePerPre));
        spinner4.setSelection(mTradePerPre == DISABLED_VALUE ? 0 : 5, false);

        mEditTxt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mTradePerPre = Float.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mTradePerPre = DISABLED_VALUE;
                }

                mListener.onUpPrePreTradeEditted(mTradePerPre);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt5 = (EditText) v.findViewById(R.id.editText5);
        mEditTxt5.setText(mTradePrice == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mTradePrice));
        spinner5.setSelection(mTradePrice == DISABLED_VALUE ? 0 : 5, false);

        mEditTxt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mTradePrice = Integer.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mTradePrice = DISABLED_VALUE;
                }

                mListener.onUpTradePriceEditted(mTradePrice);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 등락률
        mEditTxt11 = (EditText) v.findViewById(R.id.editText11);
        mEditTxt11.setText(mDownPricePer == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mDownPricePer));
        spinner11.setSelection(mDownPricePer == DISABLED_VALUE ? 0 : 5, false);

        mEditTxt11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mDownPricePer = Float.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mDownPricePer = DISABLED_VALUE;
                }

                mListener.onDownPrePriceEditted(mDownPricePer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt12 = (EditText) v.findViewById(R.id.editText12);
        mEditTxt12.setText(mDownPricePerPre == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mDownPricePerPre));
        spinner12.setSelection(mDownPricePerPre == DISABLED_VALUE ? 0 : 5, false);

        mEditTxt12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mDownPricePerPre = Float.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mDownPricePerPre = DISABLED_VALUE;
                }

                mListener.onDownPrePrePriceEditted(mDownPricePerPre);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt13 = (EditText) v.findViewById(R.id.editText13);
        mEditTxt13.setText(mDownTradePer == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mDownTradePer));
        spinner13.setSelection(mDownTradePer == DISABLED_VALUE ? 0 : 5, false);

        mEditTxt13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mDownTradePer = Float.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mDownTradePer = DISABLED_VALUE;
                }

                mListener.onDownPreTradeEditted(mDownTradePer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt14 = (EditText) v.findViewById(R.id.editText14);
        mEditTxt14.setText(mDownTradePerPre == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mDownTradePerPre));
        spinner14.setSelection(mDownTradePerPre == DISABLED_VALUE ? 0 : 5, false);
        mEditTxt14.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mDownTradePerPre = Float.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mDownTradePerPre = DISABLED_VALUE;
                }

                mListener.onDownPrePreTradeEditted(mDownTradePerPre);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt15 = (EditText) v.findViewById(R.id.editText15);
        mEditTxt15.setText(mDownTradePrice == DISABLED_VALUE ? getResources().getString(R.string.disable) : String.valueOf(mDownTradePrice));
        spinner15.setSelection(mDownTradePrice == DISABLED_VALUE ? 0 : 5, false);
        mEditTxt15.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                try {
                    mDownTradePrice = Integer.valueOf(s.toString());
                } catch (NumberFormatException e) {
                    mDownTradePrice = DISABLED_VALUE;
                }

                mListener.onDownTradePriceEditted(mDownTradePrice);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final ViewGroup vibrationLayout = v.findViewById(R.id.vibrate_setting);
        disableEnableControls(vibrationLayout, mIsVibration != Const.VIBRATION_DISABLED);

        Switch vibrateSetting = v.findViewById(R.id.vibrate_setting_switch);
        vibrateSetting.setEnabled(true);
        vibrateSetting.setChecked(mIsVibration != Const.VIBRATION_DISABLED);
        vibrateSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsVibration = isChecked ? 0 : Const.VIBRATION_DISABLED;
                disableEnableControls(vibrationLayout, isChecked);
                buttonView.setEnabled(true);

                mListener.onVibrationSelected(mIsVibration);
            }
        });

        final ViewGroup upSettingLayout = v.findViewById(R.id.up_setting);
        disableEnableControls(upSettingLayout, mIsUpSetting);

        Switch upSetting = v.findViewById(R.id.up_setting_switch);
        upSetting.setEnabled(true);
        upSetting.setChecked(mIsUpSetting);
        upSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsUpSetting = isChecked;
                disableEnableControls(upSettingLayout, isChecked);
                buttonView.setEnabled(true);

                mListener.onUpSettingEnabled(isChecked);
            }
        });

        final ViewGroup downSettingLayout = v.findViewById(R.id.down_setting);
        disableEnableControls(downSettingLayout, mIsDownSetting);

        Switch downSetting = v.findViewById(R.id.down_setting_switch);
        downSetting.setEnabled(true);
        downSetting.setChecked(mIsDownSetting);
        downSetting.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mIsDownSetting = isChecked;
                disableEnableControls(downSettingLayout, isChecked);
                buttonView.setEnabled(true);

                mListener.onDownSettingEnabled(isChecked);
            }
        });
        return v;
    }

    private void disableEnableControls(ViewGroup vg, boolean enable) {
        for (int i = 0; i < vg.getChildCount(); i++) {
            View child = vg.getChildAt(i);
            child.setEnabled(enable);
            if (child instanceof ViewGroup) {
                disableEnableControls((ViewGroup) child, enable);
            }
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

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        switch (checkedId) {
            case R.id.radio300:
                mUpCandle = 1;
                break;
            case R.id.radio1:
                mUpCandle = 2;
                break;
            case R.id.radio3:
                mUpCandle = 6;
                break;
            case R.id.radio5:
                mUpCandle = 10;
                break;
            case R.id.radio10:
                mUpCandle = 20;
                break;
            case R.id.radio15:
                mUpCandle = 30;
                break;
            case R.id.radio300_down:
                mDownCandle = 1;
                break;
            case R.id.radio1_down:
                mDownCandle = 2;
                break;
            case R.id.radio3_down:
                mDownCandle = 6;
                break;
            case R.id.radio5_down:
                mDownCandle = 10;
                break;
            case R.id.radio10_down:
                mDownCandle = 20;
                break;
            case R.id.radio15_down:
                mDownCandle = 30;
                break;
            default:
                return;
        }

        radioGroup.findViewById(checkedId);
        mListener.onUpCandleButtonClicked(mUpCandle);
        mListener.onDownCandleButtonClicked(mDownCandle);
    }

    // 스피너가 초기화할 때, onItemSelected을 자동으로 불리는 것을 피하기 위해
    private int mSpinnerCounting = 0;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d("MY_LOG", "mSpinnerCounting : " + mSpinnerCounting + ", postion : " + position);

        if (mSpinnerCounting++ < 10) {
            return;
        }

        final String text = parent.getItemAtPosition(position).toString();
        final int spinnerId = parent.getId();

        switch (spinnerId) {
            case R.id.up_pre_price_spinner:
                mEditTxt.setText(text);
                break;
            case R.id.up_pre_pre_price_spinner:
                mEditTxt2.setText(text);
                break;
            case R.id.up_pre_trade:
                mEditTxt3.setText(text);
                break;
            case R.id.up_pre_pre_trade:
                mEditTxt4.setText(text);
                break;
            case R.id.up_trade_price_spinner:
                mEditTxt5.setText(text);
                break;
            case R.id.down_pre_price_spinner:
                mEditTxt11.setText(text);
                break;
            case R.id.down_pre_pre_price_spinner:
                mEditTxt12.setText(text);
                break;
            case R.id.down_pre_trade:
                mEditTxt13.setText(text);
                break;
            case R.id.down_pre_pre_trade:
                mEditTxt14.setText(text);
                break;
            case R.id.down_trade_price_spinner:
                mEditTxt15.setText(text);
                break;
            default:
                return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
