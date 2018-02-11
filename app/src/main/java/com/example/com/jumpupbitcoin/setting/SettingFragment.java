package com.example.com.jumpupbitcoin.setting;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.example.com.jumpupbitcoin.R;

import java.util.ArrayList;

public class SettingFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private static final String ARG_UP_CANDLE = "argument_up_candle";
    private static final String ARG_PRICE_PER = "argument_pricePer";
    private static final String ARG_PRICE_PER_PRE = "argument_pricePerPre";
    private static final String ARG_TRADE_PER = "argument_tradePer";
    private static final String ARG_TRADE_PER_PRE = "argument_tradePerPre";
    private static final String ARG_DOWN_CANDLE = "argument_down_candle";
    private static final String ARG_DOWN_PRICE_PER = "argument_down_pricePer";
    private static final String ARG_DOWN_PRICE_PER_PRE = "argument_down_pricePerPre";
    private static final String ARG_DOWN_TRADE_PER = "argument_down_tradePer";
    private static final String ARG_DOWN_TRADE_PER_PRE = "argument_down_tradePerPre";

    private static final int DISABLED_VALUE = -1;

    private int mUpCandle, mDownCandle;
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

    private EditText mEditTxt, mEditTxt2, mEditTxt3, mEditTxt4;
    private EditText mEditTxt11, mEditTxt12, mEditTxt13, mEditTxt14;

    private OnSettingFragment mListener;

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(int upCandle, float pricePer, float pricePerPre, float tradePer, float tradePerPre, int downCandle, float downPricePer, float downPricePerPre, float downTradePer, float downTradePerPre) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_UP_CANDLE, upCandle);
        args.putFloat(ARG_PRICE_PER, pricePer);
        args.putFloat(ARG_PRICE_PER_PRE, pricePerPre);
        args.putFloat(ARG_TRADE_PER, tradePer);
        args.putFloat(ARG_TRADE_PER_PRE, tradePerPre);
        args.putInt(ARG_DOWN_CANDLE, downCandle);
        args.putFloat(ARG_DOWN_PRICE_PER, downPricePer);
        args.putFloat(ARG_DOWN_PRICE_PER_PRE, downPricePerPre);
        args.putFloat(ARG_DOWN_TRADE_PER, downTradePer);
        args.putFloat(ARG_DOWN_TRADE_PER_PRE, downTradePerPre);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUpCandle = getArguments().getInt(ARG_UP_CANDLE);
            mPricePer = getArguments().getFloat(ARG_PRICE_PER);
            mPricePerPre = getArguments().getFloat(ARG_PRICE_PER_PRE);
            mTradePer = getArguments().getFloat(ARG_TRADE_PER);
            mTradePerPre = getArguments().getFloat(ARG_TRADE_PER_PRE);
            mDownCandle = getArguments().getInt(ARG_DOWN_CANDLE);
            mDownPricePer = getArguments().getFloat(ARG_DOWN_PRICE_PER);
            mDownPricePerPre = getArguments().getFloat(ARG_DOWN_PRICE_PER_PRE);
            mDownTradePer = getArguments().getFloat(ARG_DOWN_TRADE_PER);
            mDownTradePerPre = getArguments().getFloat(ARG_DOWN_TRADE_PER_PRE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_network, container, false);

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

        Spinner mSpinner, mSpinner2, mSpinner3, mSpinner4;

        mSpinner = (Spinner) v.findViewById(R.id.up_pre_price_spinner);
        mSpinner.setSelection(0, false);

        mSpinner2 = (Spinner) v.findViewById(R.id.up_pre_pre_price_spinner);
        mSpinner2.setSelection(0, false);

        mSpinner3 = (Spinner) v.findViewById(R.id.up_pre_trade);
        mSpinner3.setSelection(0, false);

        mSpinner4 = (Spinner) v.findViewById(R.id.up_pre_pre_trade);
        mSpinner4.setSelection(0, false);

        mSpinner.setOnItemSelectedListener(this);
        mSpinner2.setOnItemSelectedListener(this);
        mSpinner3.setOnItemSelectedListener(this);
        mSpinner4.setOnItemSelectedListener(this);

        Spinner mSpinner11, mSpinner12, mSpinner13, mSpinner14;

        mSpinner11 = (Spinner) v.findViewById(R.id.down_pre_price_spinner);
        mSpinner11.setSelection(0, false);

        mSpinner12 = (Spinner) v.findViewById(R.id.down_pre_pre_price_spinner);
        mSpinner12.setSelection(0, false);

        mSpinner13 = (Spinner) v.findViewById(R.id.down_pre_trade);
        mSpinner13.setSelection(0, false);

        mSpinner14 = (Spinner) v.findViewById(R.id.down_pre_pre_trade);
        mSpinner14.setSelection(0, false);

        mSpinner11.setOnItemSelectedListener(this);
        mSpinner12.setOnItemSelectedListener(this);
        mSpinner13.setOnItemSelectedListener(this);
        mSpinner14.setOnItemSelectedListener(this);

        // 등락률
        mEditTxt = (EditText) v.findViewById(R.id.editText);
        mEditTxt.setText(String.valueOf(mPricePer));
        mEditTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }

//                float value = 0;
//                try {
//                    value = Float.valueOf(text);
//                } catch (NumberFormatException e) {
//                    value = DISABLED_VALUE;
//                }

                mPricePer = Float.valueOf(s.toString());
                mListener.onUpPrePriceEditted(mPricePer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt2 = (EditText) v.findViewById(R.id.editText2);
        mEditTxt2.setText(mPricePerPre == DISABLED_VALUE ? "Disable" : String.valueOf(mPricePerPre));
        mEditTxt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mPricePerPre = Float.valueOf(s.toString());
                mListener.onUpPrePrePriceEditted(mPricePerPre);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt3 = (EditText) v.findViewById(R.id.editText3);
        mEditTxt3.setText(mTradePer == DISABLED_VALUE ? "Disable" : String.valueOf(mTradePer));
        mEditTxt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mTradePer = Float.valueOf(s.toString());
                mListener.onUpPreTradeEditted(mTradePer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt4 = (EditText) v.findViewById(R.id.editText4);
        mEditTxt4.setText(mTradePerPre == DISABLED_VALUE ? "Disable" : String.valueOf(mTradePerPre));
        mEditTxt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mTradePerPre = Float.valueOf(s.toString());
                mListener.onUpPrePreTradeEditted(mTradePerPre);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 등락률
        mEditTxt11 = (EditText) v.findViewById(R.id.editText11);
        mEditTxt11.setText(String.valueOf(mDownPricePer));
        mEditTxt11.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mDownPricePer = Float.valueOf(s.toString());
                mListener.onDownPrePriceEditted(mDownPricePer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt12 = (EditText) v.findViewById(R.id.editText12);
        mEditTxt12.setText(mDownPricePerPre == DISABLED_VALUE ? "Disable" : String.valueOf(mDownPricePerPre));
        mEditTxt12.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mDownPricePerPre = Float.valueOf(s.toString());
                mListener.onDownPrePrePriceEditted(mDownPricePerPre);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt13 = (EditText) v.findViewById(R.id.editText13);
        mEditTxt13.setText(mDownTradePer == DISABLED_VALUE ? "Disable" : String.valueOf(mDownTradePer));
        mEditTxt13.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mDownTradePer = Float.valueOf(s.toString());
                mListener.onDownPreTradeEditted(mDownTradePer);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mEditTxt14 = (EditText) v.findViewById(R.id.editText14);
        mEditTxt14.setText(mDownTradePerPre == DISABLED_VALUE ? "Disable" : String.valueOf(mDownTradePerPre));
        mEditTxt14.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    return;
                }
                mDownTradePerPre = Float.valueOf(s.toString());
                mListener.onDownPrePreTradeEditted(mDownTradePerPre);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return v;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String text = parent.getItemAtPosition(position).toString();
//        float value = 0;
//        try {
//            value = Float.valueOf(text);
//        } catch (NumberFormatException e) {
//            value = DISABLED_VALUE;
//        }

        final int spinnerId = parent.getId();

        switch (spinnerId) {
            case R.id.up_pre_price_spinner:
                mEditTxt.setText(text);
//                mListener.onUpPrePriceEditted(value);
                break;
            case R.id.up_pre_pre_price_spinner:
                mEditTxt2.setText(text);
//                mListener.onUpPrePrePriceEditted(value);
                break;
            case R.id.up_pre_trade:
                mEditTxt3.setText(text);
//                mListener.onUpPreTradeEditted(value);
                break;
            case R.id.up_pre_pre_trade:
                mEditTxt4.setText(text);
//                mListener.onUpPrePreTradeEditted(value);
                break;
            case R.id.down_pre_price_spinner:
                mEditTxt11.setText(text);
//                mListener.onDownPrePriceEditted(value);
                break;
            case R.id.down_pre_pre_price_spinner:
                mEditTxt12.setText(text);
//                mListener.onDownPrePrePriceEditted(value);
                break;
            case R.id.down_pre_trade:
                mEditTxt13.setText(text);
//                mListener.onDownPreTradeEditted(value);
                break;
            case R.id.down_pre_pre_trade:
                mEditTxt14.setText(text);
//                mListener.onDownPrePreTradeEditted(value);
                break;
            default:
                return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnSettingFragment {
        void onUpCandleButtonClicked(int candle);

        void onUpPrePriceEditted(float prePrice);

        void onUpPrePrePriceEditted(float prePrePrice);

        void onUpPreTradeEditted(float preTrade);

        void onUpPrePreTradeEditted(float prePreTrade);

        void onDownCandleButtonClicked(int candle);

        void onDownPrePriceEditted(float prePrice);

        void onDownPrePrePriceEditted(float prePrePrice);

        void onDownPreTradeEditted(float preTrade);

        void onDownPrePreTradeEditted(float prePreTrade);
    }
}
