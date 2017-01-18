package lingaraj.hourglass.com.kingsthoughtworkgame.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.rd.PageIndicatorView;

import java.util.List;

import lingaraj.hourglass.com.kingsthoughtworkgame.KingsGameApp;
import lingaraj.hourglass.com.kingsthoughtworkgame.R;
import lingaraj.hourglass.com.kingsthoughtworkgame.adapters.PagerAdapter;

/**
 * Created by lingaraj on 1/7/17.
 */

public class DetailedWarStaticActivity extends AppCompatActivity {
    private ViewPager view_pager;
    private PageIndicatorView pager_indicator_view;
    private Toolbar toolbar;
    private int position;
    private List<String> name_list;
    private KingsGameApp app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_statistics);
        setUpViews();
        setSupportActionBar(this.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        this.position = getIntent().getIntExtra("position",0);
        this.app = (KingsGameApp) getApplication();
        this.name_list = app.getKing_name_list();
        setUpViewPager();


    }

    private void setUpViewPager() {

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(),DetailedWarStaticActivity.this,this.name_list);
        this.view_pager.setAdapter(pagerAdapter);
        this.view_pager.setCurrentItem(this.position);
        this.pager_indicator_view.setViewPager(this.view_pager);



    }

    private void setUpViews() {
        this.view_pager = (ViewPager) findViewById(R.id.view_pager);
        this.pager_indicator_view = (PageIndicatorView) findViewById(R.id.page_indicator_view);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detailed War statistic");
    }
}
