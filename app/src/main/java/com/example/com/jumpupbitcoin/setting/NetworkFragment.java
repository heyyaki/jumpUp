package com.example.com.jumpupbitcoin.setting;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.com.jumpupbitcoin.Client;
import com.example.com.jumpupbitcoin.MainActivity;
import com.example.com.jumpupbitcoin.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NetworkFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NetworkFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NetworkFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Switch SwitchToggleButton;
    public static int bunbong;
    int spinner_lock_board = 0;


    private RadioButton radio300;
    private RadioButton radio1;
    private RadioButton radio3;
    private RadioButton radio5;
    private RadioButton radio10;
    private RadioButton radio15;

    private EditText edit_txt;
    private EditText edit_txt2;
    private EditText edit_txt3;
    private EditText edit_txt4;

    private OnFragmentInteractionListener mListener;

    public interface OnArticleSelectedListener {
        public void onArticleSelected(Uri articleUri);
    }


    public NetworkFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NetworkFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NetworkFragment newInstance(String param1, String param2) {
        NetworkFragment fragment = new NetworkFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_network, container, false);

        MainActivity.editor = MainActivity.pref.edit();

        RadioGroup group = (RadioGroup) v.findViewById(R.id.radioGroup1);
        radio300 = (RadioButton) v.findViewById(R.id.radio300);
        radio1 = (RadioButton) v.findViewById(R.id.radio1);
        radio3 = (RadioButton) v.findViewById(R.id.radio3);
        radio5 = (RadioButton) v.findViewById(R.id.radio5);
        radio10 = (RadioButton) v.findViewById(R.id.radio10);
        radio15 = (RadioButton) v.findViewById(R.id.radio15);

        radio300.setChecked(MainActivity.pref.getBoolean("radio300", radio300.isChecked()));
        radio1.setChecked(MainActivity.pref.getBoolean("radio1", radio1.isChecked()));
        radio3.setChecked(MainActivity.pref.getBoolean("radio3", radio3.isChecked()));
        radio5.setChecked(MainActivity.pref.getBoolean("radio5", radio5.isChecked()));
        radio10.setChecked(MainActivity.pref.getBoolean("radio10", radio10.isChecked()));
        radio15.setChecked(MainActivity.pref.getBoolean("radio15", radio15.isChecked()));

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio300:
                        bunbong = 1;
                        Toast.makeText(getActivity(), "30초를 선택하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio1:
                        bunbong = 2;
                        Toast.makeText(getActivity(), "1분봉을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio3:
                        bunbong = 6;
                        Toast.makeText(getActivity(), "3분봉을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio5:
                        bunbong = 10;
                        Toast.makeText(getActivity(), "5분봉을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio10:
                        bunbong = 20;
                        Toast.makeText(getActivity(), "10분봉을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.radio15:
                        bunbong = 30;
                        Toast.makeText(getActivity(), "15분봉을 선택하였습니다.", Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        });

        edit_txt = (EditText) v.findViewById(R.id.editText);
        edit_txt2 = (EditText) v.findViewById(R.id.editText2);
        edit_txt3 = (EditText) v.findViewById(R.id.editText3);
        edit_txt4 = (EditText) v.findViewById(R.id.editText4);

        Log.d("MainActivity.pref.getAll START!!!!!!", String.valueOf(MainActivity.pref.getAll()));

        Spinner s = (Spinner) v.findViewById(R.id.spinner);
        Spinner s2 = (Spinner) v.findViewById(R.id.spinner2);
        Spinner s3 = (Spinner) v.findViewById(R.id.spinner3);
        Spinner s4 = (Spinner) v.findViewById(R.id.spinner4);


        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                edit_txt.setText(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (spinner_lock_board == 0) {
                } else {
                    edit_txt2.setText(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                /*
                if(parent.getItemAtPosition(position).equals("Disabled")) {
                    //edit_txt3.setText("Disabled");
                    //setUseableEditText(edit_txt3, false);
                    Client.trade_check=0;
                    //Toast.makeText(getActivity(), "비활성 모드", Toast.LENGTH_SHORT).show();
                }
                else {
                    edit_txt3.setText(parent.getItemAtPosition(position).toString());
                    Client.trade_check=1;
                }
                */
                if (spinner_lock_board == 0) {
                } else {
                    edit_txt3.setText(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        s4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                if (spinner_lock_board == 0) {
                    spinner_lock_board = 1;
                } else {
                    edit_txt4.setText(parent.getItemAtPosition(position).toString());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        edit_txt.setText((String) MainActivity.pref.getAll().get("edit_txt"));
        edit_txt2.setText((String) MainActivity.pref.getAll().get("edit_txt2"));
        edit_txt3.setText((String) MainActivity.pref.getAll().get("edit_txt3"));
        edit_txt4.setText((String) MainActivity.pref.getAll().get("edit_txt4"));


        if (edit_txt2.getText().toString().equals("Disabled")) {
            Client.price_per_pre = 0;
            Client.pre_check = 0;
        } else {
            Client.price_per_pre = Float.parseFloat(String.valueOf(edit_txt2.getText()));
            Client.pre_check = 1;
        }
        if (edit_txt3.getText().toString().equals("Disabled")) {
            Client.trade_per = 0;
            Client.trade_check = 0;
        } else {
            Client.trade_per = Float.parseFloat(String.valueOf(edit_txt3.getText()));
            Client.trade_check = 1;
        }
        if (edit_txt4.getText().toString().equals("Disabled")) {
            Client.trade_per_pre = 0;
            Client.pre_trade_check = 0;
        } else {
            Client.trade_per_pre = Float.parseFloat(String.valueOf(edit_txt4.getText()));
            Client.pre_trade_check = 1;
        }

        return v;

    }

    private void setUseableEditText(EditText et, boolean useable) {
        et.setClickable(useable);
        et.setEnabled(useable);
        et.setFocusable(useable);
        et.setFocusableInTouchMode(useable);
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

        MainActivity.editor = MainActivity.pref.edit();
        MainActivity.editor.putBoolean("radio300", radio300.isChecked());
        MainActivity.editor.putBoolean("radio1", radio1.isChecked());
        MainActivity.editor.putBoolean("radio3", radio3.isChecked());
        MainActivity.editor.putBoolean("radio5", radio5.isChecked());
        MainActivity.editor.putBoolean("radio10", radio10.isChecked());
        MainActivity.editor.putBoolean("radio15", radio15.isChecked());

        MainActivity.editor.putString("edit_txt", String.valueOf(edit_txt.getText()));
        MainActivity.editor.putString("edit_txt2", String.valueOf(edit_txt2.getText()));
        MainActivity.editor.putString("edit_txt3", String.valueOf(edit_txt3.getText()));
        MainActivity.editor.putString("edit_txt4", String.valueOf(edit_txt4.getText()));

        Log.d("MainActivity.pref Information", String.valueOf(MainActivity.pref.getAll()));

        MainActivity.editor.commit();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);

    }
}
