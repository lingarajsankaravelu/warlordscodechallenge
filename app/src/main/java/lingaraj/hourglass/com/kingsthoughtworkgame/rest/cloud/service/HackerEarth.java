package lingaraj.hourglass.com.kingsthoughtworkgame.rest.cloud.service;

import java.util.List;

import lingaraj.hourglass.com.kingsthoughtworkgame.rest.cloud.response.BattleResponse;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by lingaraj on 1/7/17.
 */

public interface HackerEarth {

    @GET("/gotjson")
    void getBattleResult(Callback<List<BattleResponse>> response);

}
