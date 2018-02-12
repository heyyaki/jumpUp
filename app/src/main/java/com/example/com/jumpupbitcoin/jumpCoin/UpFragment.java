package com.example.com.jumpupbitcoin.jumpCoin;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.com.jumpupbitcoin.Const;
import com.example.com.jumpupbitcoin.R;

import java.util.ArrayList;
import java.util.List;

public class UpFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    myAdapter Adapter;
    myAdapter2 Adapter2;

    ListView listview;
    ListView listview2;

    private ArrayList<String> mAlarmReg;
    private ArrayList<String> mLogList;

    public UpFragment() {
        // Required empty public constructor
    }

    public static UpFragment newInstance(ArrayList<String> alarmReg, ArrayList<String> logList) {
        UpFragment fragment = new UpFragment();
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
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.w(this.getClass().getSimpleName(), "onCreateView()");
        Adapter = new UpFragment.myAdapter();
        Adapter2 = new UpFragment.myAdapter2(getContext());
        View v = inflater.inflate(R.layout.fragment_up, container, false);
        listview = (ListView) v.findViewById(R.id.uplist);
        listview.setAdapter(Adapter);

        listview2 = (ListView) v.findViewById(R.id.logList);
        listview2.setAdapter(Adapter2);

//        setListViewHeight();

        Button btn_log_del = (Button) v.findViewById(R.id.btn_log_delete);
        btn_log_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLogList.clear();
            }
        });

        return v;
    }
//
//    private void setListViewHeight() {
//        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
//        Display display = windowManager.getDefaultDisplay();
//        Point size = new Point();
//        display.getSize(size);
//        final int height = size.y;
//
//        ViewGroup.LayoutParams params = listview.getLayoutParams();
//        params.height = height / 2;
//        listview.setLayoutParams(params);
//        listview.requestLayout();
//
//        params = listview2.getLayoutParams();
//        params.height = height / 2;
//        listview2.setLayoutParams(params);
//        listview2.requestLayout();
//    }

    public void refresh(List<String> alarmReg, List<String> logList) {
        //mAlarmReg.addAll(alarmReg);
        //mLogList.addAll(logList);

        mAlarmReg = (ArrayList<String>) alarmReg;
        mLogList = (ArrayList<String>) logList;

        Adapter.notifyDataSetChanged();
        Adapter2.notifyDataSetChanged();
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
            UpListView view = new UpListView(getContext());
            if (mAlarmReg.size() <= position) {
                return view;
            }

            if (!mAlarmReg.get(position).isEmpty()) {
                String[] coin_arr = mAlarmReg.get(position).split("_");
                view.setName(Const.sCoinNames.get(Integer.parseInt(coin_arr[0])));
                view.setPrice(Integer.parseInt(coin_arr[2]));
                view.setPer(coin_arr[1]);
                view.setImage(Integer.parseInt(coin_arr[0]));
            }
            return view;
        }
    }

    class myAdapter2 extends BaseAdapter {
        private LayoutInflater mInflater;

        private ViewHolder viewHolder;

        myAdapter2(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

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

        @SuppressLint("DefaultLocale")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.log_up_coin, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.name_Text = (TextView) convertView.findViewById(R.id.name_coin_txt2);
                viewHolder.price_Text = (TextView) convertView.findViewById(R.id.price_coin_txt2);
                viewHolder.up_per_Text = (TextView) convertView.findViewById(R.id.up_per_txt2);
                viewHolder.date_Text = (TextView) convertView.findViewById(R.id.date_coin_txt);
                viewHolder.image_coin = (ImageView) convertView.findViewById(R.id.image_coin1);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (mLogList.size() <= position || mLogList.get(position).isEmpty()) {
                Log.i("UpFragment", "getView error");
                return convertView;
            }

            if (!mLogList.get(position).isEmpty()) {
                int sizeOfLogList = mLogList.size();
                sizeOfLogList = --sizeOfLogList - position;
                String[] coin_arr = mLogList.get(sizeOfLogList).split("_");

                final int posOfView = Integer.parseInt(coin_arr[0]);
                viewHolder.name_Text.setText(Const.sCoinNames.get(posOfView));

                int price;
                try {
                    price = Integer.valueOf(coin_arr[2]);
                } catch (NumberFormatException e) {
                    price = 0;
                    e.printStackTrace();
                }
                viewHolder.price_Text.setText(String.format("%,d원", price));
                viewHolder.up_per_Text.setText(coin_arr[1] + "%");

                final String data = coin_arr[3];
                viewHolder.date_Text.setText(data);
                viewHolder.image_coin.setImageResource(Const.sCoinImages[posOfView]);
            }

            return convertView;
        }

        private class ViewHolder {
            TextView name_Text;
            TextView price_Text;
            TextView up_per_Text;
            TextView date_Text;
            ImageView image_coin;
        }
    }
}
