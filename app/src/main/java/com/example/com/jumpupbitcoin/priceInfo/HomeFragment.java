package com.example.com.jumpupbitcoin.priceInfo;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.com.jumpupbitcoin.Const;
import com.example.com.jumpupbitcoin.R;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private myAdapter mAdapter;
    private View mListViewLayout, mNoItemLayout;

    private ArrayList<String> mPriceList, mPerList;

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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.w(this.getClass().getSimpleName(), "onCreateView()");
        mAdapter = new myAdapter(getContext());
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ListView listview = (ListView) v.findViewById(R.id.price_list);
        View header = inflater.inflate(R.layout.price_list_header, null, false);
        listview.addHeaderView(header);
        listview.setAdapter(mAdapter);

        mListViewLayout = v.findViewById(R.id.homefragment_listview_layout);
        mNoItemLayout = v.findViewById(R.id.homefragment_no_item_layout);

        if (mPerList.size() == 0 || mPriceList.size() == 0) {
            showNoItemView();
        }

        return v;
    }

    public void blinkAnimateTextClock() {
        if (getView() == null) {
            return;
        }

        final TextView test_clock = (TextView) getView().findViewById(R.id.textClock);
        int colorFrom = getResources().getColor(R.color.weakblack);
        int colorTo = getResources().getColor(R.color.black);
        final ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
        colorAnimation.setDuration(1000);
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                test_clock.setBackgroundColor((int) animator.getAnimatedValue());
            }
        });

        colorAnimation.start();
    }

    public void showNoItemView() {
        if (mListViewLayout == null || mNoItemLayout == null) {
            return;
        }

        mListViewLayout.setVisibility(View.GONE);
        mNoItemLayout.setVisibility(View.VISIBLE);
    }

    class myAdapter extends BaseAdapter {

        private LayoutInflater mInflater;

        private ViewHolder viewHolder;

        myAdapter(Context context) {
            mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

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

        @SuppressLint("DefaultLocale")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_price, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.name_Text = (TextView) convertView.findViewById(R.id.name_coin_txt);
                viewHolder.price_Text = (TextView) convertView.findViewById(R.id.price_coin_txt);
                viewHolder.per_Text = (TextView) convertView.findViewById(R.id.start_per_txt);
                viewHolder.image_coin = (ImageView) convertView.findViewById(R.id.image_coin1);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (mPerList.size() <= position || mPriceList.size() <= position) {
                Log.i("HomeFragment", "getView error");
                return convertView;
            }

            viewHolder.name_Text.setText(Const.sCoinNames.get(position));

            int price;
            try {
                price = Integer.valueOf(mPriceList.get(position));
            } catch (NumberFormatException e) {
                price = 0;
                e.printStackTrace();
            }

            viewHolder.price_Text.setText(String.format("%,d원", price));

            String per = mPerList.get(position);
            if (Float.parseFloat(per) < 0) {
                viewHolder.price_Text.setTextColor(getContext().getColor(R.color.blue));
                viewHolder.per_Text.setTextColor(getContext().getColor(R.color.blue));
            } else {
                viewHolder.price_Text.setTextColor(getContext().getColor(R.color.red));
                viewHolder.per_Text.setTextColor(getContext().getColor(R.color.red));
            }
            viewHolder.per_Text.setText(per + "%");

            viewHolder.image_coin.setImageResource(Const.sCoinImages[position]);
            return convertView;
        }

        private class ViewHolder {
            TextView name_Text;
            TextView price_Text;
            TextView per_Text;
            ImageView image_coin;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        Log.w(this.getClass().getSimpleName(), "onResume()");
        super.onResume();
    }

    public void refreshListView(List<String> priceList, List<String> perList) {
        if (mListViewLayout != null && mNoItemLayout != null) {
            mListViewLayout.setVisibility(View.VISIBLE);
            mNoItemLayout.setVisibility(View.GONE);
        }

        mPerList = (ArrayList<String>) perList;
        mPriceList = (ArrayList<String>) priceList;

        mAdapter.notifyDataSetChanged();
    }
}
