package com.example.com.jumpupbitcoin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    UpFragment.myAdapter Adapter;
    UpFragment.myAdapter2 Adapter2;

    Client cli = new Client();
    ListView listview;
    ListView listview2;

    public UpFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UpFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UpFragment newInstance(String param1, String param2) {
        UpFragment fragment = new UpFragment();
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

    class myAdapter extends BaseAdapter {
        @Override
        public int getCount() { return cli.alarm_reg.size();}

        @Override
        public Object getItem(int i) {
            return cli.alarm_reg.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            UpListView view = new UpListView(getContext());
            if(!cli.alarm_reg.get(i).isEmpty()) {
                String[] coin_arr = cli.alarm_reg.get(i).split("-");
                view.setName(MainActivity.map.get(Integer.parseInt(coin_arr[0])));
                if(Integer.valueOf(cli.ary_price[Integer.parseInt(coin_arr[0])])>0)
                    view.setPrice(Integer.valueOf(cli.ary_price[Integer.parseInt(coin_arr[0])]));
                view.setPer(coin_arr[1]);
                view.setImage(Integer.parseInt(coin_arr[0]));
            }
            return view;
        }
    }

    class myAdapter2 extends BaseAdapter {
        @Override
        public int getCount() { return cli.log_list.size();}

        @Override
        public Object getItem(int i) {
            return cli.log_list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            LogUpView view = new LogUpView(getContext());
            int a = cli.log_list.size();
            if(!cli.log_list.get(i).isEmpty()) {
                a=--a-i;
                String[] coin_arr = cli.log_list.get(a).split("-");
                view.setName(MainActivity.map.get(Integer.parseInt(coin_arr[0])));
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
        Adapter = new UpFragment.myAdapter();
        Adapter2 = new UpFragment.myAdapter2();
        View v =  inflater.inflate(R.layout.fragment_up, container, false);
        listview = (ListView) v.findViewById(R.id.uplist) ;
        listview.setAdapter(Adapter);

        listview2 = (ListView) v.findViewById(R.id.logList) ;
        listview2.setAdapter(Adapter2);

        Button btn_log_del = (Button) v.findViewById(R.id.btn_log_delete);
        btn_log_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cli.log_list.clear();
                refresh();
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

    public void refresh(){
        //FragmentTransaction ft = getFragmentManager().beginTransaction();
        //ft.detach(this).attach(this).commit();
        //getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        getFragmentManager().beginTransaction().detach(this).attach(this).commitAllowingStateLoss();
    }
}
