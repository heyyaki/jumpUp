package com.example.com.jumpupbitcoin;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Client extends AsyncTask<Void,Void,Void> {


    private String start_coin = "";
    private String now_price="";
    public static String[] ary_price={};
    public static List<String> alarm_reg = new ArrayList<String>();
    public static List<String> ary_start_per = new ArrayList<String>();

    public static String[] up_coin={};
    public static List<String> log_list = new ArrayList<String>();


    //private TextView mTextMessage;
    private Handler mHandler;


    @Override
    protected Void doInBackground(Void... voids) {

        final String url = "http://122.40.239.103:8080/crawlring/index.html";
        final String url_up_coin = "http://122.40.239.103:8080/crawlring/up_coin.html";
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while(true) {
                        Document doc = Jsoup.connect(url).get();
                        Document doc2 = Jsoup.connect(url_up_coin).get();

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

                        up_catch(doc2);

                        Thread.sleep(5000);
                        Log.d("Sleep?", "Sleep Mode Acitive");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            private void up_catch(Document doc2) {
                int switch_minite = 1;
                int num_get_price=1;
                String str_switch_minite = "";
                String str_switch_minite1="";
                if(switch_minite==1){
                    for(int i=0; i<2; i++) {
                        Log.d("num_get_price", String.valueOf(num_get_price));
                        String test="div.price"+num_get_price;
                        Elements start_price = doc2.select("div.crawlring" + num_get_price);
                        //Elements start_price = doc2.select("div.crawlring1");
                        for (Element e : start_price) {
                            Log.d("up_catch_price", e.text());
                            str_switch_minite1 = e.text();
                        }
                        num_get_price = switch_minite + num_get_price;
                        if(str_switch_minite=="")
                            str_switch_minite=str_switch_minite+str_switch_minite1;
                        else
                            str_switch_minite=str_switch_minite+"-"+str_switch_minite1;
                    }
                    Log.d("str_Swtich_minite",str_switch_minite);
                    //up_per_Calc(str_switch_minite);

                }
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

    public String[] Up_calc(String recv_str){
        String filter_str="";
        String filter_str2="";
        String[] recv_ary = recv_str.split("/");
        String []filter_word = {" ","\\[","\\]","'"};
        for(int i=0;i<filter_word.length;i++){
            filter_str = recv_ary[1].replaceAll(filter_word[i],"");
            recv_ary[1]=filter_str;
        }
        up_coin = recv_ary[1].split(",");

        long[] pattern = {100,300,100,500,100,500};
        MainActivity.vibrator.vibrate(pattern,-1);

        for(int i=0; i<up_coin.length; i++) {
            String time = new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
            String[] coin_arr = up_coin[i].split("-");
            if(ary_price.length!=0){
                log_list.add(up_coin[i]+"-"+ary_price[Integer.parseInt(coin_arr[0])]+"-"+time);}
        }

        if(MainActivity.frag_num==2) {
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //HomeFragment homeFragment = new HomeFragment();
                    MainActivity.upFragment.refresh();
                }
            }, 0);
        }

        return up_coin;
    }

    public String[] Calc(String recv_str){
        String filter_str="";
        String []filter_word = {" ","\\[","\\]"};
        for(int i=0;i<filter_word.length;i++){
            filter_str = recv_str.replaceAll(filter_word[i],"");
            recv_str=filter_str;
        }
        ary_price = recv_str.split(",");

        return ary_price;
    }

    public List<String> per_Calc(String recv_str){
        String filter_str="";
        String []filter_word = {" ","\\[","\\]"};
        for(int i=0;i<filter_word.length;i++){
            filter_str = recv_str.replaceAll(filter_word[i],"");
            recv_str=filter_str;
        }

        String[] temp_start_price = recv_str.split(",");

        for(int i=0;i<temp_start_price.length;i++){
            DecimalFormat form = new DecimalFormat("#.##");
            if(ary_start_per.size()<36)
                ary_start_per.add(form.format((Float.parseFloat(ary_price[i])/Float.parseFloat(temp_start_price[i]))*100-100));
            else {
                ary_start_per.set(i,form.format((Float.parseFloat(ary_price[i]) / Float.parseFloat(temp_start_price[i])) * 100 - 100));
            }
        }

        if(MainActivity.frag_num==1) {
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //HomeFragment homeFragment = new HomeFragment();
                    MainActivity.homeFragment.refresh();
                }
            }, 0);
        }
        return ary_start_per;
    }

    public List<String> up_per_Calc(String recv_str){
        List<String> ary_up_per = new ArrayList<String>();
        List<String> ary_up_per_pre = new ArrayList<String>();

        String filter_str="";
        String []filter_word = {" ","\\[","\\]"};
        for(int i=0;i<filter_word.length;i++){
            filter_str = recv_str.replaceAll(filter_word[i],"");
            recv_str=filter_str;
        }

        String[] temp_arr_price = recv_str.split("-");
        String[] temp_now_price = temp_arr_price[0].split(",");
        String[] temp_price = temp_arr_price[1].split(",");
        String[] temp_price_pre = temp_arr_price[2].split(",");

        for(int i=0;i<temp_now_price.length;i++) {
            DecimalFormat form = new DecimalFormat("#.##");
            if (ary_up_per.size() < 36){
                ary_up_per.add(form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                if(Float.parseFloat(ary_up_per.get(i))>0.3){
                    long[] pattern = {100,300,100,500,100,500};
                    MainActivity.vibrator.vibrate(pattern,-1);
                    alarm_reg.add(i+"-"+ary_up_per.get(i));
                }
            }
            else {
                ary_up_per.set(i,form.format((Float.parseFloat(temp_now_price[i]) / Float.parseFloat(temp_price[i])) * 100 - 100));
                if(Float.parseFloat(ary_up_per.get(i))>0.3){
                    long[] pattern = {100,300,100,500,100,500};
                    MainActivity.vibrator.vibrate(pattern,-1);
                    alarm_reg.add(i+"-"+ary_up_per.get(i));
                }
            }
        }

        for(int i=0; i<alarm_reg.size(); i++) {
            String time = new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
            String[] coin_arr = alarm_reg.get(i).split("-");
            if(ary_price.length!=0){
                log_list.add(alarm_reg.get(i)+"-"+ary_price[Integer.parseInt(coin_arr[0])]+"-"+time);}
        }

        if(MainActivity.frag_num==2) {
            Handler mHandler = new Handler(Looper.getMainLooper());
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //HomeFragment homeFragment = new HomeFragment();
                    MainActivity.upFragment.refresh();
                }
            }, 0);
        }

        return log_list;
    }

}
