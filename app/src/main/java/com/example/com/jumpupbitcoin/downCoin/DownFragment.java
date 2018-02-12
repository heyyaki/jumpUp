package com.example.com.jumpupbitcoin.downCoin;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.com.jumpupbitcoin.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DownFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private OnFragmentInteractionListener mListener;

    myAdapter Adapter;
    myAdapter2 Adapter2;

    ListView listview;
    ListView listview2;
    private ArrayList<String> mAlarmReg;
    private ArrayList<String> mLogList;

    private HashMap<Integer, String> map = new HashMap<>();

    public DownFragment() {
        // Required empty public constructor
    }

    public static DownFragment newInstance(ArrayList<String> alarmReg, ArrayList<String> logList) {
        DownFragment fragment = new DownFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, alarmReg);
        args.putStringArrayList(ARG_PARAM2, logList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mAlarmReg = getArguments().getStringArrayList(ARG_PARAM1);
            mLogList = getArguments().getStringArrayList(ARG_PARAM2);
        }
        addMap();
    }

    public List<String> getAlarmReg() {
        return mAlarmReg;
    }

    class myAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mAlarmReg.size();
        }

        @Override
        public Object getItem(int i) {
            return mAlarmReg.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            DownListView view = new DownListView(getContext());
            if (mAlarmReg.size() <= position) {
                return view;
            }

            if (!mAlarmReg.get(position).isEmpty()) {
                String[] coin_arr = mAlarmReg.get(position).split("_");
                view.setName(map.get(Integer.parseInt(coin_arr[0])));
                view.setPrice(Integer.parseInt(coin_arr[2]));
                view.setPer(coin_arr[1]);
                view.setImage(Integer.parseInt(coin_arr[0]));
            }
            return view;
        }
    }

    class myAdapter2 extends BaseAdapter {
        @Override
        public int getCount() {
            return mLogList.size();
        }

        @Override
        public Object getItem(int i) {
            return mLogList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LogDownView view = new LogDownView(getContext());
            int a = mLogList.size();
            if (!mLogList.get(i).isEmpty()) {
                a = --a - i;
                String[] coin_arr = mLogList.get(a).split("_");
                view.setName(map.get(Integer.parseInt(coin_arr[0])));
                view.setPrice(Integer.valueOf(coin_arr[2]));
                view.setPer(coin_arr[1]);
                view.setDate(coin_arr[3]);
                view.setImage(Integer.parseInt(coin_arr[0]));
            }
            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.w(this.getClass().getSimpleName(), "onCreateView()");
        Adapter = new DownFragment.myAdapter();
        Adapter2 = new DownFragment.myAdapter2();
        View v = inflater.inflate(R.layout.fragment_down, container, false);
        listview = (ListView) v.findViewById(R.id.downlist);
        listview.setAdapter(Adapter);

        listview2 = (ListView) v.findViewById(R.id.logDownList);
        listview2.setAdapter(Adapter2);

        Button btn_log_del = (Button) v.findViewById(R.id.btn_log_down_delete);
        btn_log_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogList.clear();
            }
        });

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
            //Toast.makeText(context, "Up Fragment Attached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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

    public void refresh(ArrayList<String> alarmReg, ArrayList<String> logList) {
        //mAlarmReg.addAll(alarmReg);
        //mLogList.addAll(logList);

        mAlarmReg = alarmReg;
        mLogList = logList;

        Adapter.notifyDataSetChanged();
        Adapter2.notifyDataSetChanged();
    }

    private void addMap() {
        map.put(0, "비트코인");
        map.put(1, "에이다");
        map.put(2, "리플");
        map.put(3, "스테이터스네트워크토큰");
        map.put(4, "퀀텀");
        map.put(5, "이더리움");
        map.put(6, "머큐리");
        map.put(7, "네오");
        map.put(8, "스팀달러");
        map.put(9, "스팀");
        map.put(10, "스텔라루멘");
        map.put(11, "아인스타이늄");
        map.put(12, "비트코인 골드");
        map.put(13, "아더");
        map.put(14, "뉴이코미무브먼트");
        map.put(15, "블록틱스");
        map.put(16, "파워렛저");
        map.put(17, "비트코인캐시");
        map.put(18, "코모도");
        map.put(19, "스트라티스");
        map.put(20, "이더리움클래식");
        map.put(21, "오미세고");
        map.put(22, "그리스톨코인");
        map.put(23, "스토리지");
        map.put(24, "어거");
        map.put(25, "웨이브");
        map.put(26, "아크");
        map.put(27, "모네로");
        map.put(28, "라이트코인");
        map.put(29, "리스크");
        map.put(30, "버트코인");
        map.put(31, "피벡스");
        map.put(32, "메탈");
        map.put(33, "대쉬");
        map.put(34, "지캐시");
    }
}
