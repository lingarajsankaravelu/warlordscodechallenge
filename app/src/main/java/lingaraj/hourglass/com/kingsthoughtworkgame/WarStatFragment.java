package lingaraj.hourglass.com.kingsthoughtworkgame;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lingaraj.hourglass.com.kingsthoughtworkgame.libraries.StarLordData;
import lingaraj.hourglass.com.kingsthoughtworkgame.models.KingBattleRecord;

/**
 * Created by lingaraj on 1/7/17.
 */

public class WarStatFragment extends Fragment {

    private KingsGameApp app;
    private Map<String,KingBattleRecord> king_record_map = new HashMap<>();
    private Map<String,Double> king_percentage_map = new HashMap<>();
    private String king_name;
    private StarLordData app_data;
    private View fragment_view;
    private TextView name;
    private TextView battle_won_count;
    private TextView battle_lost_count;
    private TextView rating;
    private TextView strength;
    private TextView battle_type_strength;
    private ImageView image_king;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
     this.fragment_view = inflater.inflate(R.layout.layout_detail_stat_fragment,container,false);
        setUpViews();
        fetchData();
        setData();
        return this.fragment_view;

    }

    private void setData() {
        Double highest_rating = this.king_percentage_map.get(this.king_name);
        KingBattleRecord record = this.king_record_map.get(this.king_name);
        this.name.setText(this.king_name);
        Bitmap bitmap = getKingBitmap(this.king_name);
        this.image_king.setImageBitmap(bitmap);
        int rating_value = (int) Math.round(highest_rating);
        this.rating.setText(String.valueOf(rating_value));
        this.battle_won_count.setText(String.valueOf(record.getBattle_won()));
        this.battle_lost_count.setText(String.valueOf(record.getBattle_lost()));
        if (record.getStrength_attack()>record.getStrength_defend())
        {
            this.strength.setText("Attack");
        }
        else
        {
            this.strength.setText("Defence");
        }
        String strength_by_battle = getBattleStrength(this.king_name);
        this.battle_type_strength.setText(strength_by_battle);

    }

    private String getBattleStrength(String king_name) {
        Map<String,Integer> battle_strength_map = this.king_record_map.get(king_name).getBattle_type_strength_map();
        List<String> battle_keyset = new ArrayList<>(battle_strength_map.keySet());
        int battle_strenght_count= 0;
        String battle_title ="-";
        for (String battle_name:battle_keyset) {
            if (battle_strength_map.get(battle_name)>battle_strenght_count)
            {
                battle_strenght_count  =battle_strength_map.get(battle_name);
                battle_title = battle_name;
            }
        }
        return battle_title;
    }



    private void fetchData() {
        this.app_data = this.app.getStarLordData();
        Bundle bundle = getArguments();
        this.king_name = bundle.getString("king_name");
        this.king_record_map = this.app_data.getKing_record_map();
        this.king_percentage_map = this.app_data.getKing_rating_map();
    }

    private void setUpViews() {
        this.app = (KingsGameApp)getActivity().getApplication();
        this.name = (TextView) this.fragment_view.findViewById(R.id.king_name);
        this.rating = (TextView) this.fragment_view.findViewById(R.id.rating);
        this.battle_won_count = (TextView) this.fragment_view.findViewById(R.id.battle_won);
        this.battle_lost_count = (TextView) this.fragment_view.findViewById(R.id.battle_lost);
        this.strength = (TextView) this.fragment_view.findViewById(R.id.strength);
        this.battle_type_strength = (TextView) this.fragment_view.findViewById(R.id.strength_in_battle);
        this.image_king = (ImageView) this.fragment_view.findViewById(R.id.king_image);


    }

    private Bitmap getKingBitmap(String king_name)
    {
        if (king_name.equals(getActivity().getString(R.string.balon_euron)))
        {
            return BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.king_1);

        }
        else if (king_name.equals(getActivity().getString(R.string.joffrey_tommen_baratheon)))
        {
            return BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.king_2);

        }
        else if (king_name.equals(getActivity().getString(R.string.mance_rayder)))
        {
            return BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.king_3);

        }
        else if (king_name.equals(getActivity().getString(R.string.renly_baratheon)))
        {
            return BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.king_5);

        }
        else if (king_name.equals(getActivity().getString(R.string.robb_stark)))
        {
            return BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.king_6);

        }else
        {
            return BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.king_4);

        }


    }


}
