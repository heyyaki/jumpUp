package com.example.com.jumpupbitcoin.setting;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
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
import android.widget.Toast;

import com.example.com.jumpupbitcoin.R;
import com.example.com.jumpupbitcoin.SharedPreferencesManager;

import java.util.ArrayList;

public class SettingFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {
    private static final String ARG_BUNBONG = "argument_bunbong";
    private static final String ARG_PRICE_PER = "argument_pricePer";
    private static final String ARG_PRICE_PER_PRE = "argument_pricePerPre";
    private static final String ARG_TRADE_PER = "argument_tradePer";
    private static final String ARG_TRADE_PER_PRE = "argument_tradePerPre";

    private static final int DISABLED_VALUE = -1;

    private int mBunBong;
    private final static ArrayList<Integer> mBunBongList = new ArrayList<>();

    static {
        mBunBongList.add(1);
        mBunBongList.add(2);
        mBunBongList.add(6);
        mBunBongList.add(10);
        mBunBongList.add(20);
        mBunBongList.add(30);
    }


    private float mPricePer, mPricePerPre, mTradePer, mTradePerPre;

    private EditText mEditTxt, mEditTxt2, mEditTxt3, mEditTxt4;

    private OnFragmentInteractionListener mListener;

    private TextWatcher mTextWacher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(int bunbong, float pricePer, float pricePerPre, float tradePer, float tradePerPre) {
        SettingFragment fragment = new SettingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BUNBONG, bunbong);
        args.putFloat(ARG_PRICE_PER, pricePer);
        args.putFloat(ARG_PRICE_PER_PRE, pricePerPre);
        args.putFloat(ARG_TRADE_PER, tradePer);
        args.putFloat(ARG_TRADE_PER_PRE, tradePerPre);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mBunBong = getArguments().getInt(ARG_BUNBONG);
            mPricePer = getArguments().getFloat(ARG_PRICE_PER);
            mPricePerPre = getArguments().getFloat(ARG_PRICE_PER_PRE);
            mTradePer = getArguments().getFloat(ARG_TRADE_PER);
            mTradePerPre = getArguments().getFloat(ARG_TRADE_PER_PRE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_network, container, false);

        // 분봉
        RadioGroup group = (RadioGroup) v.findViewById(R.id.radioGroup1);
        group.setOnCheckedChangeListener(this);

        for (int i = 0; i < mBunBongList.size(); i++) {
            if (mBunBongList.get(i).equals(mBunBong)) {
                ((RadioButton) group.getChildAt(i)).setChecked(true);
            }
        }

        Spinner mSpinner, mSpinner2, mSpinner3, mSpinner4;
        mSpinner = (Spinner) v.findViewById(R.id.spinner);
        mSpinner.setSelection(0, false);

        mSpinner2 = (Spinner) v.findViewById(R.id.spinner2);
        mSpinner2.setSelection(0, false);

        mSpinner3 = (Spinner) v.findViewById(R.id.spinner3);
        mSpinner3.setSelection(0, false);

        mSpinner4 = (Spinner) v.findViewById(R.id.spinner4);
        mSpinner4.setSelection(0, false);

        mSpinner.setOnItemSelectedListener(this);
        mSpinner2.setOnItemSelectedListener(this);
        mSpinner3.setOnItemSelectedListener(this);
        mSpinner4.setOnItemSelectedListener(this);

        // 등락률
        mEditTxt = (EditText) v.findViewById(R.id.editText);
        mEditTxt.setText(String.valueOf(mPricePer));
        mEditTxt.addTextChangedListener(mTextWacher);

        mEditTxt2 = (EditText) v.findViewById(R.id.editText2);
        mEditTxt2.setText(mPricePerPre == DISABLED_VALUE ? "Disable" : String.valueOf(mPricePerPre));
        mEditTxt2.addTextChangedListener(mTextWacher);

        mEditTxt3 = (EditText) v.findViewById(R.id.editText3);
        mEditTxt3.setText(mTradePer == DISABLED_VALUE ? "Disable" : String.valueOf(mTradePer));
        mEditTxt3.addTextChangedListener(mTextWacher);

        mEditTxt4 = (EditText) v.findViewById(R.id.editText4);
        mEditTxt4.setText(mTradePerPre == DISABLED_VALUE ? "Disable" : String.valueOf(mTradePerPre));
        mEditTxt4.addTextChangedListener(mTextWacher);

        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
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
                mBunBong = 1;
                Toast.makeText(getActivity(), "30초를 선택하였습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio1:
                mBunBong = 2;
                break;
            case R.id.radio3:
                mBunBong = 6;
                break;
            case R.id.radio5:
                mBunBong = 10;
                break;
            case R.id.radio10:
                mBunBong = 20;
                break;
            case R.id.radio15:
                mBunBong = 30;
                break;
            default:
                return;
        }

        radioGroup.findViewById(checkedId);
        SharedPreferencesManager.setBunBong(getContext().getApplicationContext(), mBunBong);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        final String text = parent.getItemAtPosition(position).toString();
        float value = 0;
        try {
            value = Float.valueOf(text);
        } catch (NumberFormatException e) {
            value = DISABLED_VALUE;
        }

        final int spinnerId = parent.getId();

        switch (spinnerId) {
            case R.id.spinner:
                mEditTxt.setText(text);
                SharedPreferencesManager.setPricePer(getContext().getApplicationContext(), value);
                break;
            case R.id.spinner2:
                mEditTxt2.setText(text);
                SharedPreferencesManager.setPricePerPre(getContext().getApplicationContext(), value);
                break;
            case R.id.spinner3:
                mEditTxt3.setText(text);
                SharedPreferencesManager.setTradePer(getContext().getApplicationContext(), value);
                break;
            case R.id.spinner4:
                mEditTxt4.setText(text);
                SharedPreferencesManager.setTradePerPre(getContext().getApplicationContext(), value);
                break;
            default:
                return;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
