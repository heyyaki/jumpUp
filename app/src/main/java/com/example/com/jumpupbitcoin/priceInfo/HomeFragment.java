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

import com.example.com.jumpupbitcoin.Client;
import com.example.com.jumpupbitcoin.MainActivity;
import com.example.com.jumpupbitcoin.R;

import java.util.HashMap;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
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
    Client cli = new Client();
    ListView listview;

    public HomeFragment() {
        // Required empty public constructor

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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

        addMap();
    }

    class myAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return cli.ary_price.length;
        }

        @Override
        public Object getItem(int i) {
            return cli.ary_price[i];
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            CoinListView view = new CoinListView(getContext());
            view.setName(map.get(i));
            view.setPrice(Integer.valueOf(cli.ary_price[i]));
            view.setPer(cli.ary_start_per.get(i));
            view.setImage(i);
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

    public void refresh() {
        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();
        getFragmentManager().beginTransaction().detach(this).attach(this).commitAllowingStateLoss();
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
