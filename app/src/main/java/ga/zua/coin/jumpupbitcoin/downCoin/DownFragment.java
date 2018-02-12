package ga.zua.coin.jumpupbitcoin.downCoin;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import ga.zua.coin.jumpupbitcoin.Const;
import ga.zua.coin.jumpupbitcoin.R;


import java.util.ArrayList;
import java.util.List;

public class DownFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private DownFragmentListener mListener;

    AlarmRegAdapter mAlarmRegAdapter;
    LogAdapter mLogAdapter;

    ListView mAlarmRegListView, mLogListView;
    private ArrayList<String> mAlarmReg, mLogList;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.w(this.getClass().getSimpleName(), "onCreateView()");
        mAlarmRegAdapter = new AlarmRegAdapter(getContext());
        mLogAdapter = new LogAdapter(getContext());
        View v = inflater.inflate(R.layout.fragment_down, container, false);
        mAlarmRegListView = (ListView) v.findViewById(R.id.downlist);
        mAlarmRegListView.setAdapter(mAlarmRegAdapter);

        mLogListView = (ListView) v.findViewById(R.id.logDownList);
        mLogListView.setAdapter(mLogAdapter);

        setListViewHeight(v);

        Button btn_log_del = (Button) v.findViewById(R.id.btn_log_down_delete);
        btn_log_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onClearDownLogData();
                }
                mLogList.clear();
                mLogAdapter.notifyDataSetChanged();
            }
        });

        return v;
    }

    private void setListViewHeight(View root) {
        View view = root.findViewById(R.id.fragment_down_layout);
        view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);

        final int height = view.getMeasuredHeight() - getResources().getDimensionPixelSize(R.dimen.setting_item_margin) * 3;
        Log.d("MY_LOG", "height : " + height);

        mAlarmRegListView.getLayoutParams().height = height;
        mAlarmRegListView.requestLayout();

        mLogListView.getLayoutParams().height = height;
        mLogListView.requestLayout();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof DownFragmentListener) {
            mListener = (DownFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface DownFragmentListener {
        void onClearDownLogData();
    }

    public void refresh(List<String> alarmReg, List<String> logList) {
        mAlarmReg = (ArrayList<String>) alarmReg;
        mLogList = (ArrayList<String>) logList;

        mAlarmRegAdapter.notifyDataSetChanged();
        mLogAdapter.notifyDataSetChanged();
    }


    class AlarmRegAdapter extends BaseAdapter {

        private final LayoutInflater mInflater;

        private ViewHolder viewHolder;

        AlarmRegAdapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

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
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.down_per, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.name_Text = (TextView) convertView.findViewById(R.id.name_coin_down_txt);
                viewHolder.price_Text = (TextView) convertView.findViewById(R.id.price_coin_down_txt);
                viewHolder.down_per_Text = (TextView) convertView.findViewById(R.id.down_per_txt);
                viewHolder.image_coin = (ImageView) convertView.findViewById(R.id.image_coin_down);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (mAlarmReg.size() <= position) {
                return convertView;
            }

            if (!mAlarmReg.get(position).isEmpty()) {
                String[] coin_arr = mAlarmReg.get(position).split("_");
                viewHolder.name_Text.setText(Const.sCoinNames.get(Integer.parseInt(coin_arr[0])));
                viewHolder.price_Text.setText(String.format("%,d원", Integer.parseInt(coin_arr[2]), 3));
                viewHolder.price_Text.setTextColor(getContext().getColor(R.color.blue));
                viewHolder.down_per_Text.setTextColor(getContext().getColor(R.color.blue));
                viewHolder.down_per_Text.setText(coin_arr[1] + "%");
                viewHolder.image_coin.setImageResource(Const.sCoinImages[Integer.parseInt(coin_arr[0])]);
            }

            return convertView;
        }

        private class ViewHolder {
            TextView name_Text;
            TextView price_Text;
            TextView down_per_Text;
            ImageView image_coin;
        }
    }

    class LogAdapter extends BaseAdapter {
        private LayoutInflater mInflater;

        private ViewHolder viewHolder;

        LogAdapter(Context context) {
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
                convertView = mInflater.inflate(R.layout.log_down_coin, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.name_Text = (TextView) convertView.findViewById(R.id.name_coin_down_txt2);
                viewHolder.price_Text = (TextView) convertView.findViewById(R.id.price_coin_down_txt2);
                viewHolder.up_per_Text = (TextView) convertView.findViewById(R.id.up_per_down_txt2);
                viewHolder.date_Text = (TextView) convertView.findViewById(R.id.date_coin_down_txt2);
                viewHolder.image_coin = (ImageView) convertView.findViewById(R.id.image_coin_down2);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (mLogList.size() <= position || mLogList.get(position).isEmpty()) {
                Log.i("DownFragment", "getView error");
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
