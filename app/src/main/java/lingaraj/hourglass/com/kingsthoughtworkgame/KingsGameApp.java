package lingaraj.hourglass.com.kingsthoughtworkgame;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;

import java.security.Security;
import java.util.List;

import lingaraj.hourglass.com.kingsthoughtworkgame.libraries.StarLordData;
import retrofit.RestAdapter;
import retrofit.android.AndroidLog;
import retrofit.client.OkClient;

/**
 * Created by lingaraj on 1/7/17.
 */

public class KingsGameApp extends Application {

    public  static String TAG="KingsGameApp";
    private RestAdapter rest_adapter;
    private String rest_server_url = "http://starlord.hackerearth.com/";
    private ConnectivityManager mConnectivity;
    private StarLordData starLordData;
    private List<String> king_name_list;


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG,"Application Created");
    }

    public  void setRestAdapter() {
        OkHttpClient okHttpClient = lingaraj.hourglass.com.kingsthoughtworkgame.helpers.Security.getUnsafeOkHttpClient();
        this.rest_adapter = new RestAdapter.Builder()
                .setClient(new OkClient(okHttpClient))
                .setEndpoint(this.rest_server_url)
                .setLogLevel(RestAdapter.LogLevel.FULL).setLog(new AndroidLog("CLOUD_API_REQUESTS"))
                .build();



    }
    public boolean isNetworkAvailable() {
        if (mConnectivity == null) {
            mConnectivity = ((ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE));
        }
        return mConnectivity.getActiveNetworkInfo() != null && mConnectivity.getActiveNetworkInfo().isConnected();
    }


    synchronized public RestAdapter getRestAdapter()
    {
        Log.d(TAG,"get Rest Adapter");
        if (this.rest_adapter==null)
        {
            setRestAdapter();
        }
        return this.rest_adapter;
    }

    public StarLordData getStarLordData() {
        return starLordData;
    }

    public void setStarLordData(StarLordData starLordData) {
        this.starLordData = starLordData;
    }


    public void setKingNameList(List<String> name_list) {
        this.king_name_list = name_list;

    }

    public List<String> getKing_name_list() {
        return king_name_list;
    }

}
