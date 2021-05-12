package dam.gala.damgame.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.damgame.R;

import java.util.ArrayList;

import dam.gala.damgame.controllers.AudioController;
import dam.gala.damgame.fragments.QuestionDialogFragment;
import dam.gala.damgame.interfaces.InterfaceDialog;
import dam.gala.damgame.model.Email;
import dam.gala.damgame.model.GameConfig;
import dam.gala.damgame.model.Play;
import dam.gala.damgame.model.Question;
import dam.gala.damgame.scenes.Scene;
import dam.gala.damgame.utils.GameUtil;
import dam.gala.damgame.views.GameView;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static android.view.View.SYSTEM_UI_FLAG_IMMERSIVE;
import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Actividad principal
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity implements InterfaceDialog {
    private final int SETTINGS_ACTION =1;
    private Play gameMove;
    private int sceneCode;
    private GameView gameView;
    private GameConfig config;
    private Scene scene;
    private AudioController audioController;
    //Vista para la puntuación;
    private TextView tvPoints;
    //Array para las vidas;
    private ArrayList<ImageView> lifes;
    //Vista para las respuestas;
    private TextView tvRespuestas;
    /**
     * Método de callback del ciclo de vida de la actividad, llamada anterior a que la actividad
     * pasé al estado 'Activa'
     * @param savedInstanceState Contenedor para paso de parámetros y guardar información entre
     *                           distintos estados de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTema();
        setContentView(R.layout.activity_main);
        this.config = new GameConfig();

        Button btIniciar = findViewById(R.id.btIniciar);
        btIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startGame();
            }
        });
    }
    /**
     * Inicio del juego
     */
    private void startGame(){
        this.sceneCode = Integer.parseInt(getDefaultSharedPreferences(this).
                getString("ambient_setting",String.valueOf(GameUtil.TEMA_HIELO)));
        int difficulty=Integer.parseInt(getDefaultSharedPreferences(this).getString("difficulty_setting", String.valueOf(GameUtil.MEDIUM)));
        switch (difficulty){
            case GameUtil.EASY:
                this.config.setQuestions(25);
                this.config.setGravity(10);
                break;
            case GameUtil.MEDIUM:
                this.config.setQuestions(20);
                this.config.setGravity(15);
                break;
            case GameUtil.DIFFICULT:
                this.config.setQuestions(15);
                this.config.setGravity(20);
                break;

        }
        this.gameMove = Play.createGameMove(this,this.sceneCode, this.config);
        this.scene = this.gameMove.getScene();
        setContentView(R.layout.activity_game);
        this.gameView = findViewById(R.id.svGame);
        hideSystemUI();
        this.audioController = this.gameView.getAudioController();
        this.audioController.startAudioPlay(this.scene);
        this.lifes = new ArrayList<>();
        this.lifes.add(findViewById(R.id.ivBouncy1));
        this.lifes.add(findViewById(R.id.ivBouncy2));
        this.lifes.add(findViewById(R.id.ivBouncy3));
    }

    /**
     * Muestra el cuadro de diálogo de la pregunta
     */
    private void showQuestionDialog(){
        //código para probar el cuadro de diálogo
        Button btPregunta = findViewById(R.id.btIniciar);

        MediaPlayer mediaPlayerJuego = MediaPlayer.create(this, R.raw.my_street);
        mediaPlayerJuego.setLooping(true);
        mediaPlayerJuego.start();

        btPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //showQuestionDialog();
                startGame();
            }
        });
        //aquí habrá que obtener aleatoriamente una pregunta
        //del repositorio
        CharSequence[] respuestas = new CharSequence[3];
        String enunciado = "Selecciona cuál de los siguientes planetas no está en la vía láctea";
        respuestas[0]="Marte";
        respuestas[1]="Nébula";
        respuestas[2]="Mercurio";
        int[] respuestasCorrectas = new int[]{1};
        Question question = new Question(enunciado, GameUtil.PREGUNTA_COMPLEJIDAD_ALTA,
                GameUtil.PREGUNTA_SIMPLE,respuestas,respuestasCorrectas,20);

        QuestionDialogFragment qdf = new QuestionDialogFragment(question, GameActivity.this);
        qdf.setCancelable(false);
        qdf.show(getSupportFragmentManager(),null);
    }
    /**
     * Establece el tema seleccionado en las preferencias
     */
    private void setTema(){
        this.sceneCode = Integer.parseInt(getDefaultSharedPreferences(this).
                getString("ambient_setting",String.valueOf(GameUtil.TEMA_HIELO)));
        switch(this.sceneCode){
            case GameUtil.TEMA_DESIERTO:
                this.setTheme(R.style.Desert_DamGame);
                break;
            case GameUtil.TEMA_HIELO:
                this.setTheme(R.style.Ice_DamGame);
                break;
            default:
                this.setTheme(R.style.Ice_DamGame);
                break;
        }

    }

    /**
     * Elimina la barra de acción y deja el mayor área posible de pantalla libre
     */
    private void hideSystemUI() {
        //A partir de kitkat
        this.gameView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //cuando se presiona volumen, por ejemplo, se cambia la visibilidad, hay que volver
        //a ocultar
        this.gameView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                hideSystemUI();
            }
        });

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    /**
     * Obitiene la jugada actual
     * @return Devuelve la jugada actual (Play)
     */
    public Play getJugada(){
        return this.gameMove;
    }
    /**
     * Obtiene la configuración del juego
     * @return Devuelve la configuración del juego (GameConfig)
     */
    public GameConfig getGameConfig(){
        return this.config;
    }
    /**
     * Obtiene el controlador audio
     * @return Devuelve el controlador de audio del juego (AudioController)
     */
    public AudioController getAudioController(){
        return this.audioController;
    }
    @Override
    public void setRespuesta(String respuesta) {
        Toast.makeText(this,respuesta,Toast.LENGTH_LONG).show();
    }
    /**
     * Menú principal de la aplicación
     * @param menu Menú de aplicación
     * @return Devuelve true si se ha creado el menú
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //TODO Esto se debe mejorar y sistituir en la interfaz por controles vistosos
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    /**
     * Evento de selección de elemento de menú
     * @param item Item de menú
     * @return Devuelve true si se ha tratado el evento recibido
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.imSettings:
                Intent preferences = new Intent(GameActivity.this, SettingsActivity.class);
                startActivityForResult(preferences, SETTINGS_ACTION);
                break;
        }
        return true;
    }
    /**
     * Método de callback para recibir el resultado de una intención llamada para devolver un
     * resultado
     * @param requestCode Código de la petición (int)
     * @param resultCode Código de respuesta (int)
     * @param data Intención que devuelve el resultado, la que produce el callback
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode== SETTINGS_ACTION){
            if(resultCode== Activity.RESULT_OK){
                if (sceneCode!=Integer.parseInt(getDefaultSharedPreferences(this).
                        getString("ambient_setting",String.valueOf(GameUtil.TEMA_HIELO))))
                    this.recreate();

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Actualiza las imagenes que representan las vidas disponibles, cuando se pierda una vida, la oculta aunque siga estando ahi.

    public void UpdateLifes(Integer index){
        if (index>1){
    this.lifes.get(index).setVisibility(View.INVISIBLE);
    }
}}