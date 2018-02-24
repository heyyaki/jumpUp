package ga.zua.coin.jumpupbitcoin.setting;

import android.app.AlertDialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import ga.zua.coin.jumpupbitcoin.MainActivity;

/**
 * Created by COM on 2018-02-24.
 */

public class getMarketVersion extends AsyncTask<Void, Void, String>{

    String marketVersion, verSion;
    boolean bool_version;


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(Void... params) {

        try {
            Document doc = Jsoup.connect("https://play.google.com/store/apps/details?id=ga.zua.coin.jumpupbitcoin" ).get();
            Elements Version = doc.select(".content");

            String marketVersion = null;
            for (Element v : Version) {
                if (v.attr("itemprop").equals("softwareVersion")) {
                    marketVersion = v.text();
                    Log.d("MarKet Packege Version", String.valueOf(marketVersion));
                }
            }
            return marketVersion;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        PackageInfo pi = null;
        try {
            pi = MainActivity.mContext.getPackageManager().getPackageInfo(MainActivity.mContext.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        verSion = pi.versionName;
        marketVersion = result;

        bool_version=verSion.equals(marketVersion);

        super.onPostExecute(result);
    }

    public boolean func_version_check(){
        return bool_version;
    }
}