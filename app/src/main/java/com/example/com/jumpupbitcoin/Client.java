package com.example.com.jumpupbitcoin;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Client extends AsyncTask<Void, Void, Void> {

    private String start_coin = "";
    private String now_price = "";
    public static int pre_check;    // 전전단계 퍼센티지 계산 유무  - 1 활성, 0 비활성
    public static int trade_check;    // 거래량 증가 확인 유무  - 1 활성, 0 비활성
    public static int pre_trade_check;    // 전전단계 거래량 퍼센티지 계산 유무  - 1 활성, 0 비활성

    public static float price_per;
    public static float price_per_pre;
    public static float trade_per;
    public static float trade_per_pre;

    public static int bunbong;

    public static String[] ary_price = {};
    public static List<String> alarm_reg = new ArrayList<String>();
    public static List<String> ary_start_per = new ArrayList<String>();

    public static List<String> log_list = new ArrayList<String>();

    List<String> ary_up_per = new ArrayList<String>();
    List<String> ary_up_per_pre = new ArrayList<String>();

    List<String> ary_up_trade_per = new ArrayList<String>();
    List<String> ary_up_trade_per_pre = new ArrayList<String>();

    HashMap<Integer, Integer> duple_check_map = new HashMap<Integer, Integer>();


    //private TextView mTextMessage;
    private Handler mHandler;

    public static Thread thread;


    @Override
    public Void doInBackground(Void... voids) {

        final String url = "http://122.40.239.103:8080/crawlring/index.html";
        final String url_up_coin = "http://122.40.239.103:8080/crawlring/up_coin.html";
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        int second = Integer.parseInt(new SimpleDateFormat("ss").format(new Date(System.currentTimeMillis())));
                        if (second % 5 == 0) {
                            Document doc = Jsoup.connect(url).get();

                            Elements now_doc_price = doc.select("div.now_price");
                            for (Element e : now_doc_price) {
                                Log.d("now_price", e.text());
                                now_price = e.text();
                                Calc(now_price);
                            }
                            Elements start_price = doc.select("div.start_price");
                            for (Element e : start_price) {
                                //Log.d("start_price", e.text());
                                start_coin = e.text();
                                per_Calc(start_coin);
                            }
                        }
                        if (second % 30 == 0) {
                            alarm_reg.clear();
                            Document doc2 = Jsoup.connect(url_up_coin).get();
                            up_catch(doc2);
                        }
                        Thread.sleep(1000);


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            private void up_catch(Document doc2) {
                int switch_minite = bunbong;
                int num_get_price = 1;

                String str_switch_minite = "";
                String str_switch_minite1 = "";
                String str_switch_trade = "";
                String str_switch_trade1 = "";
                for (int i = 0; i < 3; i++) {
                    Elements start_price = doc2.select("div.crawlring" + num_get_price);
                    for (Element e : start_price) {
                        str_switch_minite1 = e.text();
                    }

                    if (str_switch_minite == "")
                        str_switch_minite = str_switch_minite + str_switch_minite1;
                    else
                        str_switch_minite = str_switch_minite + "-" + str_switch_minite1;

                    Elements start_trade = doc2.select("div.trade" + num_get_price);
                    for (Element e : start_trade) {
                        str_switch_trade1 = e.text();
                    }

                    if (str_switch_trade == "")
                        str_switch_trade = str_switch_trade + str_switch_trade1;
                    else
                        str_switch_trade = str_switch_trade + "-" + str_switch_trade1;

                    num_get_price = switch_minite + num_get_price;
                }

                Log.d("str_Swtich_minite", str_switch_minite);
                Log.d("str_switch_trade", str_switch_trade);
                up_per_Calc(str_switch_minite, str_switch_trade);

            }


        });
        thread.start();
/*
        if(MainActivity.thread_flag==false)
            Log.d("Thread Interrup","Thread EXIT");
            thread.interrupt();
*/

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        //textviewHtmlDocument.setText(htmlContentInStringFormat);

    }

    public String[] Calc(String recv_str) {
        String filter_str = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = recv_str.replaceAll(filter_word[i], "");
            recv_str = filter_str;
        }
        ary_price = recv_str.split(",");

        return ary_price;
    }

    public List<String> per_Calc(String recv_str) {
        String filter_str = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = recv_str.replaceAll(filter_word[i], "");
            recv_str = filter_str;
        }

        String[] temp_start_price = recv_str.split(",");

        for (int i = 0; i < temp_start_price.length; i++) {
            DecimalFormat form = new DecimalFormat("#.##");
            if (ary_start_per.size() < 36)
                ary_start_per.add(form.format((Float.parseFloat(ary_price[i]) / Float.parseFloat(temp_start_price[i])) * 100 - 100));
            else {
                ary_start_per.set(i, form.format((Float.parseFloat(ary_price[i]) / Float.parseFloat(temp_start_price[i])) * 100 - 100));
            }
        }

        if (MainActivity.frag_num == 1) {
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //HomeFragment homeFragment = new HomeFragment();
                    //MainActivity.homeFragment.refresh();
                }
            }, 0);
        }
        return ary_start_per;
    }

    public List<String> up_per_Calc(String str_switch_minite, String str_switch_trade) {

        String[] temp_arr_trade;
        String[] temp_now_trade;
        String[] temp_trade;
        String[] temp_trade_pre;

        String filter_str = "";
        String filter_str2 = "";
        String[] filter_word = {" ", "\\[", "\\]"};
        for (int i = 0; i < filter_word.length; i++) {
            filter_str = str_switch_minite.replaceAll(filter_word[i], "");
            filter_str2 = str_switch_trade.replaceAll(filter_word[i], "");
            str_switch_minite = filter_str;
            str_switch_trade = filter_str2;
        }

        String[] temp_arr_price = str_switch_minite.split("-");
        String[] temp_now_price = temp_arr_price[0].split(",");
        String[] temp_price = temp_arr_price[1].split(",");
        String[] temp_price_pre = temp_arr_price[2].split(",");

        temp_arr_trade = str_switch_trade.split("-");
        temp_now_trade = temp_arr_trade[0].split(",");
        temp_trade = temp_arr_trade[1].split(",");
        temp_trade_pre = temp_arr_trade[2].split(",");

        try {
            if (pre_check == 0) {
                if (trade_check == 0) {
                    for (int i = 0; i < temp_now_price.length; i++) {
                        DecimalFormat form = new DecimalFormat("#.##");
                        if (ary_up_per.size() < 36) {
                            ary_up_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                            if (Float.parseFloat(ary_up_per.get(i)) > price_per) {
                                duple_check_method(i, ary_up_per, temp_now_price);
                            }
                        } else {
                            ary_up_per.set(i, form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                            if (Float.parseFloat(ary_up_per.get(i)) > price_per) {
                                duple_check_method(i, ary_up_per, temp_now_price);
                            }
                        }
                    }
                } else {
                    // 전전 거래량 체크하지 않았을 때
                    if (pre_trade_check == 0) {
                        for (int i = 0; i < temp_now_price.length; i++) {
                            DecimalFormat form = new DecimalFormat("#.##");
                            if (ary_up_trade_per.size() < 36) {
                                ary_up_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                                ary_up_trade_per.add(form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                                Log.d("ary_up_per", String.valueOf(ary_up_per));
                                Log.d("ary_up_trade_per", String.valueOf(ary_up_trade_per));
                                if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_trade_per.get(i)) > trade_per) {
                                    duple_check_method(i, ary_up_per, temp_now_price);
                                }
                            } else {
                                ary_up_per.set(i, form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                                ary_up_trade_per.set(i, form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                                if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_trade_per.get(i)) > trade_per) {
                                    duple_check_method(i, ary_up_per, temp_now_price);
                                }
                            }
                        }
                    }
                    // 전전 거래량 체크할 시
                    else {
                        for (int i = 0; i < temp_now_price.length; i++) {
                            DecimalFormat form = new DecimalFormat("#.##");
                            if (ary_up_trade_per_pre.size() < 36) {
                                ary_up_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                                ary_up_trade_per.add(form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                                ary_up_trade_per_pre.add(form.format((Float.parseFloat(temp_trade[i]) / Float.parseFloat(temp_trade_pre[i])) * 100 - 100));
                                if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_trade_per.get(i)) > trade_per && Float.parseFloat(ary_up_trade_per_pre.get(i)) > trade_per_pre) {
                                    duple_check_method(i, ary_up_per, temp_now_price);
                                }
                            } else {
                                ary_up_per.set(i, form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                                ary_up_trade_per.set(i, form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                                ary_up_trade_per_pre.set(i, form.format((Float.parseFloat(temp_trade[i]) / Float.parseFloat(temp_trade_pre[i])) * 100 - 100));
                                if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_trade_per.get(i)) > trade_per && Float.parseFloat(ary_up_trade_per_pre.get(i)) > trade_per_pre) {
                                    duple_check_method(i, ary_up_per, temp_now_price);
                                }
                            }
                        }
                    }
                }
            }
            // 전전 급등률 확인 시
            else {
                // 거래량 체크하지 않을 시
                if (trade_check == 0) {
                    for (int i = 0; i < temp_now_price.length; i++) {
                        DecimalFormat form = new DecimalFormat("#.##");
                        if (ary_up_per_pre.size() < 36) {
                            ary_up_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                            ary_up_per_pre.add(form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                            if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_per_pre.get(i)) > price_per_pre) {
                                duple_check_method(i, ary_up_per, temp_now_price);
                            }
                        } else {
                            ary_up_per.set(i, form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                            ary_up_per_pre.set(i, form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                            if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_per_pre.get(i)) > price_per_pre) {
                                duple_check_method(i, ary_up_per, temp_now_price);
                            }
                        }
                    }
                } else {
                    if (pre_trade_check == 0) {
                        for (int i = 0; i < temp_now_price.length; i++) {
                            DecimalFormat form = new DecimalFormat("#.##");
                            if (ary_up_per.size() < 36) {
                                ary_up_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                                ary_up_per_pre.add(form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                                ary_up_trade_per.add(form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                                if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_per_pre.get(i)) > price_per_pre && Float.parseFloat(ary_up_trade_per.get(i)) > trade_per) {
                                    duple_check_method(i, ary_up_per, temp_now_price);
                                }
                            } else {
                                ary_up_per.set(i, form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                                ary_up_per_pre.set(i, form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                                ary_up_trade_per.set(i, form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                                if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_per_pre.get(i)) > price_per_pre && Float.parseFloat(ary_up_trade_per.get(i)) > trade_per) {
                                    duple_check_method(i, ary_up_per, temp_now_price);
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < temp_now_price.length; i++) {
                            DecimalFormat form = new DecimalFormat("#.##");
                            if (ary_up_per_pre.size() < 36) {
                                ary_up_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                                ary_up_per_pre.add(form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                                ary_up_trade_per.add(form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                                ary_up_trade_per_pre.add(form.format((Float.parseFloat(temp_trade[i]) / Float.parseFloat(temp_trade_pre[i])) * 100 - 100));
                                if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_per_pre.get(i)) > price_per_pre && Float.parseFloat(ary_up_trade_per.get(i)) > trade_per && Float.parseFloat(ary_up_trade_per_pre.get(i)) > trade_per_pre) {
                                    duple_check_method(i, ary_up_per, temp_now_price);
                                }
                            } else {
                                ary_up_per.set(i, form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                                ary_up_per_pre.set(i, form.format((Float.parseFloat(temp_price[i]) / Float.parseFloat(temp_price_pre[i])) * 100 - 100));
                                ary_up_trade_per.set(i, form.format((Float.parseFloat(temp_now_trade[i]) / Float.parseFloat(temp_trade[i])) * 100 - 100));
                                ary_up_trade_per_pre.set(i, form.format((Float.parseFloat(temp_trade[i]) / Float.parseFloat(temp_trade_pre[i])) * 100 - 100));
                                if (Float.parseFloat(ary_up_per.get(i)) > price_per && Float.parseFloat(ary_up_per_pre.get(i)) > price_per_pre && Float.parseFloat(ary_up_trade_per.get(i)) > trade_per && Float.parseFloat(ary_up_trade_per_pre.get(i)) > trade_per_pre) {
                                    duple_check_method(i, ary_up_per, temp_now_price);
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Exception E", String.valueOf(e));
        }

        for (int i = 0; i < alarm_reg.size(); i++) {
            String time = new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
            //String[] coin_arr = alarm_reg.get(i).split("-");
            if (ary_price.length != 0) {
                log_list.add(alarm_reg.get(i) + "-" + time);
            }
        }

        if (MainActivity.frag_num == 2) {
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //HomeFragment homeFragment = new HomeFragment();
                    //MainActivity.upFragment.refresh();
                }
            }, 0);
        }

        return log_list;
    }

    private void duple_check_method(int i, List<String> ary_up_per, String[] temp_now_price) {
        if (!duple_check_map.containsKey(i)) {
            long[] pattern = {100, 300, 100, 500, 100, 500};
//            MainActivity.mVibrator.vibrate(pattern, -1);
            alarm_reg.add(i + "-" + ary_up_per.get(i) + "-" + temp_now_price[i]);
            duple_check_map.put(i, bunbong);   // 분봉으로 값 넣기
        } else {
            int a = duple_check_map.get(i);
            duple_check_map.put(i, --a);
            if (duple_check_map.get(i) == 1) {
                duple_check_map.remove(i);
            }
        }
    }

}
