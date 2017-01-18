package lingaraj.hourglass.com.kingsthoughtworkgame.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lingaraj.hourglass.com.kingsthoughtworkgame.R;
import lingaraj.hourglass.com.kingsthoughtworkgame.activities.MainActivity;
import lingaraj.hourglass.com.kingsthoughtworkgame.models.KingBattleRecord;

/**
 * Created by lingaraj on 1/7/17.
 */

public class BattleStatisticAdapter extends RecyclerView.Adapter<BattleStatisticAdapter.ViewHolder> {

    private String TAG = "QDetail Summary Adapt";
    private Context mcontext;
    private Map<String,KingBattleRecord> king_record_map;
    private List<String> king_name_list;
    private Map<String,Double> kings_rating_map;
    private String battle_strength;
    private MainActivity.KingRecordClick click;

    public BattleStatisticAdapter(Context context, MainActivity.KingRecordClick kingRecordClick)
    {
        this.mcontext = context;
        this.king_record_map = new HashMap<>();
        this.king_name_list = new ArrayList<String>();
        this.kings_rating_map = new HashMap<>();
        this.click = kingRecordClick;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(this.mcontext)
                .inflate(R.layout.card_statistic_layout, parent, false);
        v.setOnClickListener(this.click);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String king_name = this.king_name_list.get(position);
        holder.king_name.setText(king_name);
        Bitmap bitmap = getKingBitmap(king_name);
        holder.king_image_view.setImageBitmap(bitmap);
        KingBattleRecord record = this.king_record_map.get(king_name);
        String rating = "Rating:"+getKingRating(king_name);
        holder.rating.setText(rating);
        holder.won_battle_count.setText(String.valueOf(record.getBattle_won()));
        String battle_strength = "Battle Strength:"+getBattleStrength(king_name);
        holder.battle_strength.setText(battle_strength);



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

    private int getKingRating(String king_name) {
        if (this.kings_rating_map.containsKey(king_name))
        {
            return (int) Math.round(this.kings_rating_map.get(king_name));
        }
        else
        {
            return 0;
        }
    }

    public void setSortNameList(List<String> name_list)
    {
        this.king_name_list = name_list;
         notifyDataSetChanged();
    }

    public void setAdapterData(Map<String,KingBattleRecord> king_record_map,Map<String,Double> king_rating_map,List<String> name_list)
    {
        this.king_record_map = king_record_map;
        this.king_name_list = name_list;
        this.kings_rating_map = king_rating_map;
        notifyDataSetChanged();
    }

    private Bitmap getKingBitmap(String king_name)
    {
        if (king_name.equals(this.mcontext.getString(R.string.balon_euron)))
       {
          return BitmapFactory.decodeResource(this.mcontext.getResources(),R.drawable.king_1);

       }
        else if (king_name.equals(this.mcontext.getString(R.string.joffrey_tommen_baratheon)))
        {
            return BitmapFactory.decodeResource(this.mcontext.getResources(),R.drawable.king_2);

        }
        else if (king_name.equals(this.mcontext.getString(R.string.mance_rayder)))
        {
            return BitmapFactory.decodeResource(this.mcontext.getResources(),R.drawable.king_3);

        }
        else if (king_name.equals(this.mcontext.getString(R.string.renly_baratheon)))
        {
            return BitmapFactory.decodeResource(this.mcontext.getResources(),R.drawable.king_5);

        }
        else if (king_name.equals(this.mcontext.getString(R.string.robb_stark)))
        {
            return BitmapFactory.decodeResource(this.mcontext.getResources(),R.drawable.king_6);

        }else
        {
            return BitmapFactory.decodeResource(this.mcontext.getResources(),R.drawable.king_4);

        }


    }


    @Override
    public int getItemCount() {

        return this.king_name_list.size();
    }

    public List<String> getKingNameList() {
        return this.king_name_list;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView king_name;
        TextView rating;
        TextView won_battle_count;
        ImageView king_image_view;
        TextView battle_strength;


        public ViewHolder(View v) {
            super(v);
           king_image_view =(ImageView) v.findViewById(R.id.king_image);
           king_name = (TextView) v.findViewById(R.id.name);
            won_battle_count = (TextView) v.findViewById(R.id.won_count_textview);
            battle_strength = (TextView) v.findViewById(R.id.battle_strength);
            rating = (TextView) v.findViewById(R.id.rating);


        }
    }



}




