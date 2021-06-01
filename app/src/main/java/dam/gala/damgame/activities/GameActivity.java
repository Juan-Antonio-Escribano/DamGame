package dam.gala.damgame.activities;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.damgame.R;

import java.util.ArrayList;
import java.util.Random;

import dam.gala.damgame.controllers.AudioController;
import dam.gala.damgame.fragments.QuestionDialogFragment;
import dam.gala.damgame.interfaces.InterfaceDialog;
import dam.gala.damgame.model.GameConfig;
import dam.gala.damgame.model.Play;
import dam.gala.damgame.model.Player;
import dam.gala.damgame.model.Question;
import dam.gala.damgame.scenes.Scene;
import dam.gala.damgame.utils.GameUtil;
import dam.gala.damgame.views.GameView;

import static android.view.View.SYSTEM_UI_FLAG_FULLSCREEN;
import static android.view.View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

/**
 * Actividad principal
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class GameActivity extends AppCompatActivity implements InterfaceDialog {
    private final int SETTINGS_ACTION =1;
    private Play gameMove;
    Player player = null;
    private int sceneCode;
    private GameView gameView;
    private GameConfig config;
    private Scene scene;
    private AudioController audioController;
    //Array para las vidas;
    private ArrayList<ImageView> lifes;
    //Vista para las respuestas;
    private TextView tvQuestion, tvAnswersCorrect, tvLifes, tvPoints, tvPoint;
    private ArrayList<Integer> ambientes= new ArrayList<>(), ambientesV= new ArrayList<>();
    private int numAmbiente=0;
    private LinearLayout lyMain;


    /**
     * Método de callback del ciclo de vida de la actividad, llamada anterior a que la actividad
     * pasé al estado 'Activa'
     * @param savedInstanceState Contenedor para paso de parámetros y guardar información entre
     *                           distintos estados de la actividad
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        player=new Player(getIntent().getStringArrayExtra("player")[0], getIntent().getStringArrayExtra("player")[1], getIntent().getStringArrayExtra("player")[2]);
        this.setTitle(player.getNameUser());
        this.setTema();
        setContentView(R.layout.activity_main);
        hideSystemUI(getWindow().getDecorView());
        this.config = new GameConfig();
        //Declaracion de los ambientes;
        this.cargarAmbientes(new int[]{R.drawable.fondo_inicio, R.drawable.desert_dialog_bg},
                new int[]{GameUtil.TEMA_HIELO,GameUtil.TEMA_DESIERTO});
        lyMain=findViewById(R.id.lyMain);
        this.setAmbientefondo();
        Button btnAnterior, btnSiguiente, btnRanking;
        btnAnterior=findViewById(R.id.btnAnterior);
        btnSiguiente=findViewById(R.id.btnSiguiente);
        btnRanking=findViewById(R.id.btnRanking);
        /*
            Cambios de Ambientes
         */
        btnAnterior.setOnClickListener(v->{
            if (numAmbiente==0){
                lyMain.setBackground(getResources().getDrawable(ambientes.get(ambientes.size()-1)));
                numAmbiente=ambientes.size()-1;
            }else{
                lyMain.setBackground(getResources().getDrawable(ambientes.get(numAmbiente-1)));
                numAmbiente--;
            }
            getDefaultSharedPreferences(this).edit().putString("ambient_setting",String.valueOf(ambientesV.get(numAmbiente))).commit();
            recreate();
        });
        btnSiguiente.setOnClickListener(v->{
            if (numAmbiente==ambientes.size()-1){
                lyMain.setBackground(getResources().getDrawable(ambientes.get(0)));
                numAmbiente=0;
            }else{
                lyMain.setBackground(getResources().getDrawable(ambientes.get(numAmbiente+1)));
                numAmbiente++;
            }
            getDefaultSharedPreferences(this).edit().putString("ambient_setting",String.valueOf(ambientesV.get(numAmbiente))).commit();
            recreate();
        });

        /*
            Inicio del game
         */
        Button btIniciar = findViewById(R.id.btIniciar);
        btIniciar.setOnClickListener(view -> startGame());
        btnRanking.setOnClickListener(v->{
            Intent intent = new Intent(this, RankingActivity.class);
            startActivity(intent);
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
        this.gameMove.setPlayer(this.player);
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
        this.tvPoints=findViewById(R.id.tvPoint);
        this.tvQuestion=findViewById(R.id.tvAnswers);
        this.tvAnswersCorrect=findViewById(R.id.tvAnswersCorrect);
        this.tvLifes=findViewById(R.id.tvLifes);
        this.tvPoint=findViewById(R.id.tvPoints);
        switch(this.sceneCode){
            case GameUtil.TEMA_DESIERTO:
                break;
            case GameUtil.TEMA_HIELO:
                for (int i=0;i<this.lifes.size();i++)
                    this.lifes.get(i).setImageDrawable(getResources().getDrawable(R.drawable.pajaro_azul_vidas));
                ((ImageView)findViewById(R.id.ivAnswers)).setImageDrawable(getResources().getDrawable(R.drawable.ice_quest_score));
                ((ImageView)findViewById(R.id.ivPoints)).setImageDrawable(getResources().getDrawable(R.drawable.point_ice));
                break;
            default:
                this.setTheme(R.style.Ice_DamGame);
                break;
        }
    }

    /**
     * Muestra el cuadro de diálogo de la pregunta
     */
    private void showQuestionDialog(){
        //código para probar el cuadro de diálogo
        Button btPregunta = findViewById(R.id.btIniciar);

        MediaPlayer mediaPlayerJuego = MediaPlayer.create(this, R.raw.ice_cave);
        mediaPlayerJuego.setLooping(true);
        mediaPlayerJuego.start();

        btPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showQuestionDialog();
                //startGame();
            }
        });
        //aquí habrá que obtener aleatoriamente una pregunta
        //del repositorio
        ArrayList<String> respuestas = new ArrayList<>();
        String enunciado = "Selecciona cuál de los siguientes planetas no está en la vía láctea";
        respuestas.add("Marte");
        respuestas.add("Nébula");
        respuestas.add("Mercurio");
        int[] respuestasCorrectas = new int[]{1};
        Random ra = new Random();
        Question question = new Question(enunciado, ra.nextInt(2),
                ra.nextInt(2)==0?GameUtil.PREGUNTA_SIMPLE:GameUtil.PREGUNTA_MULTIPLE,respuestas,respuestasCorrectas,10);

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
     * Carga el ambiente de fondo
     */
    private void setAmbientefondo(){
        this.sceneCode = Integer.parseInt(getDefaultSharedPreferences(this).
                getString("ambient_setting",String.valueOf(GameUtil.TEMA_HIELO)));
        switch(this.sceneCode){
            case GameUtil.TEMA_DESIERTO:
                lyMain.setBackground(getResources().getDrawable(ambientes.get(1)));
                numAmbiente=1;
                break;
            case GameUtil.TEMA_HIELO:
                lyMain.setBackground(getResources().getDrawable(ambientes.get(0)));
                numAmbiente=0;
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
        this.gameView.setOnSystemUiVisibilityChangeListener(visibility -> hideSystemUI());

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
            case R.id.imClose:
                finish();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
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

    /**
     * Vuelve invisible las imagenes de vida cuando pierdes una vida
     * @param index es el index de la imagen
     */
    public void updateLifes(Integer index){
        if(index<3)
            this.lifes.get(index).setVisibility(View.INVISIBLE);
        else if (index==3)
            this.tvLifes.setVisibility(View.INVISIBLE);
        else
            this.tvLifes.setText("+"+(Integer.valueOf((String) this.tvLifes.getText())-1));
    }

    /**
     * Añade una vida
     */
    public void updateLifesAdd(){
        if (this.gameMove.getLifes()<=3)
            this.lifes.get(this.gameMove.getLifes()-1).setVisibility(View.VISIBLE);
        else {
            this.tvLifes.setVisibility(View.VISIBLE);
            this.tvLifes.setText("+"+(Integer.valueOf((String) this.tvLifes.getText())+1));
        }
    }
    public void updatePoints(int points){
        this.gameMove.setPoints(this.gameMove.getPoints()+points);
        this.tvPoint.setText(String.valueOf(Integer.valueOf((String) this.tvPoint.getText())+points));
    }
    /**
     * Actualiza el contador de preguntas respondidas correctamente
     */
    public void updateAnswersCorrect(){
        this.tvAnswersCorrect.setText(String.valueOf(Integer.valueOf((String) this.tvAnswersCorrect.getText())+1));
    }

    /**
     * Actualiza el contador de pregunatas capturadas
     */
    public void updateQuestionCatched(){
        this.tvQuestion.setText(String.valueOf(Integer.valueOf((String) this.tvQuestion.getText())+1));
    }
    /**
     * Carga los ambientes que se le a pasado por parametro.
     * Es necesario que haya la misma cantidad de ambientes que de ambientesV y que
     * esten en el mismo orden
     * @param ambientes son las imagenes de los ambientes
     * @param ambientesV son los valores de los ambientes
     */
    public void cargarAmbientes(int[] ambientes, int[] ambientesV){
        for (int i=0;i<ambientes.length;i++){
            this.ambientes.add(ambientes[i]);
            this.ambientesV.add(ambientesV[i]);
        }
    }

    public Play getGameMove() {
        return gameMove;
    }
}
