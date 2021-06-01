package dam.gala.damgame.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.damgame.R;
import com.google.gson.Gson;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import dam.gala.damgame.fragments.RegisterDialogFragment;
import dam.gala.damgame.model.Player;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

public class LoginActivity extends AppCompatActivity {
    //Login
    private Intent intent;
    private LinearLayout lyLoginMain, lyLogin;
    private EditText etNick,etpPassword;
    private CheckBox cbMostrar;
    private Button btnLogin, btnRegistrarse;
    private Player player;
    private ObjectAnimator objectAnimatorNick, objectAnimatorPassword;
    private AnimatorSet animatorSet;
    private final String ENCRYPT_KEY="SerOnoSerEsaeSLaCuesTioN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intent=new Intent(this, GameActivity.class);
        setTheme(R.style.Login_DamGame);
        setContentView(R.layout.activity_login);
        hideSystemUILogin(getWindow().getDecorView());
        initComponentLogin();
        //etNick.setText("");
        //etpPassword.setText("");
        this.objectAnimatorNick=ObjectAnimator.ofFloat(etNick, "y", 100f,1f,150f,300f,100f);
        this.objectAnimatorNick.setDuration(1000);
        this.objectAnimatorPassword=ObjectAnimator.ofFloat(etpPassword, "y", 200f,100f,10f,300f,220f);
        this.objectAnimatorPassword.setDuration(1000);
        this.animatorSet = new AnimatorSet();
        btnLogin.setOnClickListener(v->{
            if(etNick.length()!=0&&etpPassword.length()!=0){
                if (etNick.getText().toString().equals("SinAPIREST")&&etpPassword.getText().toString().equals("sinAPIREST")){
                    this.player=new Player("SinAPIREST", "sinAPIREST", "jaed1257@gmail.com");
                    intent.putExtra("player", new String[]{
                            player.getNameUser(),
                            player.getPassword(),
                            player.getEmail()
                    });
                    this.startActivity(intent);
                }
                this.login();
            }else {
                this.animatorSet.playTogether(this.objectAnimatorNick,this.objectAnimatorPassword);
                this.animatorSet.start();
                if(this.etNick.length()==0)
                    this.etNick.setError(getResources().getString(R.string.error_campo_vacio));
                if(this.etpPassword.length()==0)
                    this.etpPassword.setError(getResources().getString(R.string.error_campo_vacio));
            }
        });
        btnRegistrarse.setOnClickListener(v->{
            RegisterDialogFragment rdg = new RegisterDialogFragment();
            rdg.setCancelable(false);
            rdg.show(getSupportFragmentManager(), null);
        });
        cbMostrar.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked)
                this.etpPassword.setInputType(InputType.TYPE_CLASS_TEXT);
            else
                this.etpPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

        });
    }

    /**
     * Manda una peticion a la API REST para que busque el usuario
     * a partir del usuario
     */
    private void login(){
        RequestQueue rq = Volley.newRequestQueue(this);
        StringRequest sr = new StringRequest(Request.Method.GET, "http://192.168.18.6:8080/login/"+etNick.getText(),
                response -> {
                    Player player=parsearJSON(response);
                    try {
                        if (this.decryptedString(player.getPassword()).contentEquals(this.etpPassword.getText())){
                            LoginActivity.this.intent.putExtra("player", new String[]{
                                    player.getNameUser(),
                                    player.getPassword(),
                                    player.getEmail()
                            });
                            this.startActivity(LoginActivity.this.intent);
                        }else etpPassword.setError(getResources().getString(R.string.usuario_erroneo));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, error -> {
            System.err.println(error.getMessage());
            if(error.getMessage()==null)
                this.etpPassword.setError(getResources().getString(R.string.usuario_no_encontrado));
            else
                this.etpPassword.setError(error.getMessage());
        });
        rq.add(sr);
    }

    /**
     * Parsea el json a un objeto Player
     * @param json es el json a parsear
     * @return devuelve el objeto Player
     */
    private Player parsearJSON(String json){
        Gson gson = new Gson();
        Player player = gson.fromJson(json,Player.class);
        return player;
    }
    private String decryptedString(String textEncrypted) throws Exception  {
        byte[] encryptedBytes=Base64.decode(textEncrypted.replace("\n", ""), Base64.DEFAULT);

        Key aesKey = new SecretKeySpec(ENCRYPT_KEY.getBytes(), "AES");

        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);

        String decrypted = new String(cipher.doFinal(encryptedBytes));

        return decrypted;
    }
    /**
     * Declara los componentes del login
     */
    private void initComponentLogin(){
        lyLoginMain=findViewById(R.id.lyLoginMain);
        lyLogin=findViewById(R.id.lyLogin);
        etNick=findViewById(R.id.etNick);
        etpPassword=findViewById(R.id.etpPassword);
        cbMostrar=findViewById(R.id.cbMostrar);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegistrarse=findViewById(R.id.btnRegistrarse);

        lyLoginMain.setBackgroundColor(getResources().getColor(R.color.login_out));
        lyLogin.setBackground(getResources().getDrawable(R.drawable.login_in));
        etNick.setTextColor(Color.WHITE);
        etpPassword.setTextColor(Color.WHITE);
        cbMostrar.setTextColor(Color.WHITE);
        btnRegistrarse.setText(R.string.registrarse);

    }
    private void hideSystemUILogin(View view) {
        //A partir de kitkat
        view.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //cuando se presiona volumen, por ejemplo, se cambia la visibilidad, hay que volver
        //a ocultar
        view.setOnSystemUiVisibilityChangeListener(visibility -> hideSystemUILogin(view));

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}