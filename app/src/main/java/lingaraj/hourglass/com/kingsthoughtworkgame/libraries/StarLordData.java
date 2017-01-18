package lingaraj.hourglass.com.kingsthoughtworkgame.libraries;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lingaraj.hourglass.com.kingsthoughtworkgame.Constants;
import lingaraj.hourglass.com.kingsthoughtworkgame.models.KingBattleRecord;
import lingaraj.hourglass.com.kingsthoughtworkgame.rest.cloud.response.BattleResponse;

/**
 * Created by lingaraj on 1/7/17.
 */

public class StarLordData  {
    private String TAG ="StarLordLibrary";
    private int k = 32;
    List<BattleResponse> battle_record_list = new ArrayList<BattleResponse>();

    Map<String,Double> king_rating_map = new HashMap<>();
    Map<String,KingBattleRecord> king_record_map = new HashMap<>();
    public  StarLordData(List<BattleResponse> battleRecordList)
    {
        this.battle_record_list = battleRecordList;
        generateLibraryData();

    }

    private void generateLibraryData() {
        for(BattleResponse battle_record:battle_record_list) {
            //attacker and defender king name
            String  attacker_king = battle_record.getAttacker_king();
            String defender_king = battle_record.getDefender_king();
            //attacker outcome is either win or loss
            String attacker_outcome = battle_record.getAttacker_outcome();
            if (!attacker_king.isEmpty() && !defender_king.isEmpty())
            {
               //this is to handle empty record
                calculateRating(attacker_king,defender_king,attacker_outcome,battle_record);
            }
        }

    }

    private void calculateRating(String attacker_king, String defender_king, String attacker_outcome, BattleResponse battle_record) {
        boolean is_attacker_won = false;

        double attacker_rating = getRating(attacker_king);
        double defender_rating = getRating(defender_king);
         attacker_rating =  Math.pow(10,((double)attacker_rating/400));
        Log.d(TAG,"Attacker_predicted_rating:"+attacker_rating);
         defender_rating = Math.pow(10,((double)defender_rating/400));
        Log.d(TAG,"defender_predicted_rating:"+defender_rating);
        double e1=  attacker_rating /(attacker_rating +defender_rating);
        double e2 = defender_rating/(attacker_rating +defender_rating);
        int score_attacker =0;
        int score_defender =0;

        if (attacker_outcome.equals(Constants.outcome_win))
        {
            is_attacker_won = true;
            score_attacker = 1;
            score_defender =0;

        }
        else
        {
            score_attacker = 0;
            score_defender =1;
        }

        double updated_attacker_rating = attacker_rating + k *(score_attacker - e1);
        double updated_defender_rating = defender_rating + k * (score_defender -e2);
        Log.d(TAG,"New Rating Attacker:"+updated_attacker_rating);
        Log.d(TAG,"New Rating Defender:"+updated_defender_rating);
        //update king rating
        this.king_rating_map.put(attacker_king,updated_attacker_rating);
        this.king_rating_map.put(defender_king,updated_defender_rating);
         updateKingRecordMap(attacker_king,defender_king,is_attacker_won,battle_record);
    }

    private void updateKingRecordMap(String attacker_king, String defender_king, boolean is_attacker_won, BattleResponse battle_record) {
        String battletype = battle_record.getBattle_type();
        //update attacker record
        KingBattleRecord kingBattleRecord;
        if (this.king_record_map.containsKey(attacker_king))
        {
          kingBattleRecord = this.king_record_map.get(attacker_king);
          Log.d(TAG,"King record:"+attacker_king+"exist using it");
        }
        else
        {
          kingBattleRecord = new KingBattleRecord();
            Log.d(TAG,"King record:"+attacker_king+"does not exist creating new");

        }
        if (is_attacker_won)
        {
            kingBattleRecord.increementBattleWon();
            kingBattleRecord.updateBattleTypeStrengthMap(battletype);
            kingBattleRecord.increementStrenghtAttack();
        }
        else
        {
            kingBattleRecord.increementBattleLost();

        }
        this.king_record_map.put(attacker_king,kingBattleRecord);
        //update attacker record ends here.

        //update defending king record ends here
        if (this.king_record_map.containsKey(defender_king))
        {
            kingBattleRecord = this.king_record_map.get(defender_king);
            Log.d(TAG,"King record:"+defender_king+"exist using it");

        }
        else
        {
            kingBattleRecord = new KingBattleRecord();
            Log.d(TAG,"King record:"+defender_king+"does not exist creating new");

        }
        if (!is_attacker_won)
        {
            //defending king won
            kingBattleRecord.increementBattleWon();
            kingBattleRecord.increementStrenghtDefend();
            kingBattleRecord.updateBattleTypeStrengthMap(battletype);
        }
        else
        {
            //defending king lost
            kingBattleRecord.increementBattleLost();
        }
        this.king_record_map.put(defender_king,kingBattleRecord);
        //update defending king record ends here


    }

    private double getRating(String king_name) {
        if (this.king_rating_map.containsKey(king_name))
        {
            return  this.king_rating_map.get(king_name);
        }
        else
        {
            //By default every king will have 40 as rating value.
            return 40.0;

        }
    }

    public List<BattleResponse> getBattle_record_list() {
        return battle_record_list;
    }

    public Map<String, Double> getKing_rating_map() {
        return king_rating_map;
    }

    public Map<String, KingBattleRecord> getKing_record_map() {
        return king_record_map;
    }


}
