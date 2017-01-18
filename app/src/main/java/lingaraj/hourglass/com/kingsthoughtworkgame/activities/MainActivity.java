package lingaraj.hourglass.com.kingsthoughtworkgame.activities;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.pnikosis.materialishprogress.ProgressWheel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import lingaraj.hourglass.com.kingsthoughtworkgame.KingsGameApp;
import lingaraj.hourglass.com.kingsthoughtworkgame.R;
import lingaraj.hourglass.com.kingsthoughtworkgame.adapters.BattleStatisticAdapter;
import lingaraj.hourglass.com.kingsthoughtworkgame.libraries.StarLordData;
import lingaraj.hourglass.com.kingsthoughtworkgame.models.KingBattleRecord;
import lingaraj.hourglass.com.kingsthoughtworkgame.rest.cloud.response.BattleResponse;
import lingaraj.hourglass.com.kingsthoughtworkgame.rest.cloud.service.HackerEarth;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements android.support.v7.widget.SearchView.OnQueryTextListener{

    private String TAG = "Main Activity";
    private ProgressWheel progress_wheel;
    private RecyclerView kings_detail_list;
    private TextView error_message_view;
    private ImageView error_image_view;
    private Button retry_button;
    private ViewSwitcher view_switcher;
    private KingsGameApp app;
    private Toolbar toolbar;
    private BattleStatisticAdapter madapter;
    private List<BattleResponse> battle_response_list = new ArrayList<BattleResponse>();
    private List<String> kings_name_list = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViews();
        setSupportActionBar(this.toolbar);
        showLoader();
        if(this.app.isNetworkAvailable())
        {
            loadData();
        }
        else
        {
            Log.d(TAG,"No Internet Available");
            showNetworkError();

        }
    }

    private void showNetworkError() {
        if (this.view_switcher.getDisplayedChild()!=0)
        {
            this.view_switcher.showPrevious();
        }
        hideProgressWheel();
        showErrorMessage();
        showRetryButton();
        showErrorImageView();
        this.error_message_view.setText(getString(R.string.common_error_messsage));
        this.error_image_view.setBackgroundResource(R.drawable.ic_network_off);
        Log.d(TAG,"Showing Network Error");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_statistics,menu);
        MenuItem search_menu_toolbar = menu.findItem(R.id.action_search_evaluated);
        final android.support.v7.widget.SearchView view_search = (android.support.v7.widget.SearchView) MenuItemCompat.getActionView(search_menu_toolbar);
        view_search.setOnQueryTextListener(this);
       return  true;
    }

    private void loadData() {

        RestAdapter rest_adapter = this.app.getRestAdapter();
        HackerEarth service = rest_adapter.create(HackerEarth.class);
        service.getBattleResult(new Callback<List<BattleResponse>>() {
            @Override
            public void success(List<BattleResponse> battleResponses, Response response) {
                Log.d(TAG,response.toString());
                if (battleResponses!=null)
                {
                    generateLibrary(battleResponses);
                    showDataContainer();
                }
                else
                {
                   showNoDataMessage();

                }

            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"retrofit error");
                Log.d(TAG,error.toString());
                showNetworkError();


            }
        });


    }

    private void showDataContainer() {
        if (this.view_switcher.getDisplayedChild()!=1)
        {
            this.view_switcher.showNext();
        }
    }

    private void showNoDataMessage() {
        if (this.view_switcher.getDisplayedChild()!=0)
        {
            this.view_switcher.showPrevious();
        }
        hideProgressWheel();
        hideRetry();
        showErrorImageView();
        showErrorMessage();
        this.error_image_view.setBackgroundResource(R.drawable.ic_happy_no_data);
        this.error_message_view.setText(getString(R.string.message_no_data));

    }

    private void generateLibrary(List<BattleResponse> battleResponses) {
        Log.d(TAG,"Generating Library");
        this.battle_response_list = battleResponses;
        StarLordData star_lord_data = new StarLordData(this.battle_response_list);
        this.app.setStarLordData(star_lord_data);
        Map<String,KingBattleRecord> battle_record_map = star_lord_data.getKing_record_map();
         this.kings_name_list = new ArrayList<>(battle_record_map.keySet());
        Collections.sort(this.kings_name_list);
        this.madapter.setAdapterData(battle_record_map,star_lord_data.getKing_rating_map(),this.kings_name_list);
        Log.d(TAG,"Library Generated");
    }

    private void showLoader() {
        if (this.view_switcher.getDisplayedChild()!=0)
        {
            this.view_switcher.showPrevious();
        }
        showProgressWheel();
        this.error_message_view.setText(getString(R.string.common_loading));
        showErrorMessage();
        hideErrorImageView();
        hideRetry();

    }


    private void setUpViews() {
        this.app = (KingsGameApp) getApplication();
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.toolbar.setTitle("Kings Stats");
        this.toolbar.setSubtitle("StarLord of the Universe");
        this.progress_wheel = (ProgressWheel) findViewById(R.id.progress_wheel);
        this.error_message_view  = (TextView) findViewById(R.id.error_message_view);
        this.retry_button = (Button) findViewById(R.id.retry_button);
        this.kings_detail_list = (RecyclerView) findViewById(R.id.kings_list);
        this.view_switcher  = (ViewSwitcher) findViewById(R.id.view_switcher);
        this.error_image_view = (ImageView) findViewById(R.id.error_image_view);
        this.madapter = new BattleStatisticAdapter(MainActivity.this,new KingRecordClick());
        this.kings_detail_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        this.kings_detail_list.setNestedScrollingEnabled(false);
        this.kings_detail_list.setHasFixedSize(true);
        this.kings_detail_list.setAdapter(madapter);
    }

    private void showErrorImageView()
    {
        this.error_message_view.setVisibility(View.VISIBLE);
    }

    private void hideErrorImageView()
    {
        this.error_image_view.setVisibility(View.GONE);
    }
    private  void showErrorMessage()
    {
        this.error_message_view.setVisibility(View.VISIBLE);
    }

    private void hideErrorMessage()
    {
        this.error_message_view.setVisibility(View.GONE);
    }
    private void showProgressWheel() {
        this.progress_wheel.setVisibility(View.VISIBLE);
    }
    private void hideProgressWheel()
    {
        this.progress_wheel.setVisibility(View.GONE);
    }

    private void showRetryButton()
    {
        this.retry_button.setVisibility(View.VISIBLE);
    }

    private void hideRetry()
    {
        this.retry_button.setVisibility(View.GONE);
    }

    public void onClick(View view)
   {
       Log.d(TAG,"Retry Button Clicked");
       showLoader();
       if (this.app.isNetworkAvailable())
       {
           loadData();
       }
       else
       {
           showNetworkError();
       }
   }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if(newText.isEmpty())
        {
         madapter.setSortNameList(this.kings_name_list);
        }
        else {

                filterKingsNameList(newText);
        }

        return false;
    }

    private void filterKingsNameList(String query)
    {
        List<String> filtertednames = new ArrayList<>();
        query = query.toLowerCase();
        for (int index = 0 ; index <this.kings_name_list.size();index ++)
        {
            String model_query = this.kings_name_list.get(index).toLowerCase();
            if(model_query.contains(query))
            {
              filtertednames.add(this.kings_name_list.get(index));
            }
        }
        if (!filtertednames.isEmpty()) {

            madapter.setSortNameList(filtertednames);
        }





    }


    public class  KingRecordClick implements  View.OnClickListener
    {

        @Override
        public void onClick(View view) {
            Log.d(TAG,"King card clicked");
            int position = kings_detail_list.getChildAdapterPosition(view);
            List<String> name_list = madapter.getKingNameList();
            app.setKingNameList(name_list);
            Intent intent = new Intent(MainActivity.this,DetailedWarStaticActivity.class);
            intent.putExtra("position",position);
            startActivity(intent);
        }
    }


}
