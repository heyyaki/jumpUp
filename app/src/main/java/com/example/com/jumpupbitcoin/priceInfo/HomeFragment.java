package com.example.com.jumpupbitcoin.priceInfo;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.com.jumpupbitcoin.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HashMap<Integer, String> map = new HashMap<>();

    private OnFragmentInteractionListener mListener;

    myAdapter Adapter;
    String[] LIST_MENU;
    ListView listview;

    private ArrayList<String> mPriceList;
    private ArrayList<String> mPerList;

    public HomeFragment() {
        // Required empty public constructor

    }

    public static HomeFragment newInstance(ArrayList<String> priceList, ArrayList<String> perList) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putStringArrayList(ARG_PARAM1, priceList);
        args.putStringArrayList(ARG_PARAM2, perList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPriceList = getArguments().getStringArrayList(ARG_PARAM1);
            mPerList = getArguments().getStringArrayList(ARG_PARAM2);
        }

        addMap();
    }

    class myAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mPriceList.size();
        }

        @Override
        public Object getItem(int i) {
            return mPriceList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            CoinListView view = new CoinListView(getContext());
            view.setName(map.get(i));
            view.setPrice(Integer.valueOf(mPriceList.get(i)));
//            view.setPer(mPerList.get(i));
//            view.setImage(i);
            return view;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.w(this.getClass().getSimpleName(), "onCreateView()");
        Adapter = new myAdapter();
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        listview = (ListView) v.findViewById(R.id.price_list);
        final View header = inflater.inflate(R.layout.price_list_header, null, false);
        listview.addHeaderView(header);
        listview.setAdapter(Adapter);

        return v;
        //return inflater.inflate(R.layout.fragment_home, container, false);
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
            //Toast.makeText(context, "Home Fragment Attached", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    @Override
    public void onResume() {
        Log.w(this.getClass().getSimpleName(), "onResume()");
        super.onResume();

        /*
        if(MainActivity.first_conn==false) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
        */
    }

    public void refresh(ArrayList<String> priceList, ArrayList<String> perList) {
        Log.d("MY_LOG", "priceList : " + priceList.get(0));
//        mPerList.clear();
        mPerList.addAll(perList);

//        mPriceList.clear();
        mPriceList.addAll(priceList);

        Adapter.notifyDataSetChanged();
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();
//        getFragmentManager().beginTransaction().detach(this).attach(this).commitAllowingStateLoss();
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
