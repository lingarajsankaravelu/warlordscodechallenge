package lingaraj.hourglass.com.kingsthoughtworkgame.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import lingaraj.hourglass.com.kingsthoughtworkgame.WarStatFragment;


/**
 * Created by lingaraj on 1/7/17.
 */


public class PagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private String TAG ="DetailPagerAdapter";
    private List<String> king_name_list;
    private int position;

    public PagerAdapter(FragmentManager fm, Context context,List<String>  name_list) {
        super(fm);
        this.context = context;
        this.king_name_list = name_list;
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new WarStatFragment();
        Bundle bundle = generateBundle(position);
        fragment.setArguments(bundle);
        return fragment;
    }

    private Bundle generateBundle(int position) {
       Bundle bundle = new Bundle();
        String king_name = this.king_name_list.get(position);
        bundle.putString("king_name",king_name);
        return bundle;
    }

    @Override
    public int getCount() {
        return  this.king_name_list.size();
       }


}

