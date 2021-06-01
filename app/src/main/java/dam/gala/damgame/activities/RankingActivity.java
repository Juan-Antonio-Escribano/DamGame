package dam.gala.damgame.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.damgame.R;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

import dam.gala.damgame.adapter.RankingAdapter;
import dam.gala.damgame.model.Player;
import dam.gala.damgame.model.Score;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

public class RankingActivity extends AppCompatActivity {
    private ListView lvRanking;
    private ArrayList<Score> prueba=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Login_DamGame);
        setContentView(R.layout.activity_ranking);
        this.hideSystemUI(getWindow().getDecorView());
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, "http://192.168.18.6:8080/ranking",
                response -> {
                    lvRanking=findViewById(R.id.lvRanking);
                    RankingAdapter rankingAdapter = new RankingAdapter(this, parsearJSON(response));
                    lvRanking.setAdapter(rankingAdapter);
                }, error -> {
        });
        rq.add(sr);

    }
    private List<Score> parsearJSON(String json){
        Gson gson = new Gson();
        List jsonList = gson.fromJson(json,List.class);
        List<Score> score = new ArrayList<>();
        for (int i=0;i<jsonList.size();i++)
            score.add(gson.fromJson(jsonList.get(i).toString(), Score.class));
        return score;
    }
    /**
     * Elimina la barra de acción y deja el mayor área posible de pantalla libre
     */
    private void hideSystemUI(View view) {
        //A partir de kitkat
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //cuando se presiona volumen, por ejemplo, se cambia la visibilidad, hay que volver
        //a ocultar
        view.setOnSystemUiVisibilityChangeListener(visibility -> hideSystemUI(view));

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.ranking_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itmVolver:
                finish();
                break;
        }
        return true;
    }
}