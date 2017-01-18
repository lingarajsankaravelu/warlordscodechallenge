package lingaraj.hourglass.com.kingsthoughtworkgame.models;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lingaraj on 1/7/17.
 */

public class KingBattleRecord {
    private int battle_won=0;
    private int battle_lost = 0;
    private int strength_attack = 0;
    private int strength_defend = 0;

    public int getBattle_won() {
        return battle_won;
    }

    public int getBattle_lost() {
        return battle_lost;
    }

    public int getStrength_attack() {
        return strength_attack;
    }

    public int getStrength_defend() {
        return strength_defend;
    }

    public Map<String, Integer> getBattle_type_strength_map() {
        return battle_type_strength_map;
    }

    private Map <String,Integer>  battle_type_strength_map = new HashMap<>();

    public void increementStrenghtDefend()
    {
        this.strength_defend++;
    }
    public void increementStrenghtAttack()
    {
        this.strength_attack++;
    }

    public void increementBattleWon()
    {
        this.battle_won++;
    }
    public void increementBattleLost()
    {
        this.battle_lost++;
    }

    public void updateBattleTypeStrengthMap(String battle_type)
    {
        if (this.battle_type_strength_map.containsKey(battle_type))
        {
            int battle_type_won_count = this.battle_type_strength_map.get(battle_type) + 1;
            this.battle_type_strength_map.put(battle_type,battle_type_won_count);
        }
        else
        {
            this.battle_type_strength_map.put(battle_type,1);
        }
    }



}
