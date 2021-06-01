package dam.gala.damgame.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.scenes.DesertScene;
import dam.gala.damgame.scenes.IceScene;
import dam.gala.damgame.scenes.Scene;
import dam.gala.damgame.utils.GameUtil;
import dam.gala.damgame.views.CrashView;
import dam.gala.damgame.views.QuestionView;

/**
 * Jugada, recoge las principales características del juego
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class Play {
    private long starDateTime, endDateTime;
    private Scene scene;
    private Player player;
    private GameConfig config;
    private int questionsCaptured = 0;
    private int questionsCreated = 0;
    private int crashBlockCreated = 0;
    private int points;
    private int lifes;
    private int level;
    private ArrayList<Question> questions = new ArrayList<>();
    private ArrayList<QuestionView> questionViews = new ArrayList<>();
    private ArrayList<CrashView> crashViews = new ArrayList<>();

    /**
     * Constructor privado para la factoría
     */
    private Play() {
    }
    /**
     * Crea la jugada a partir de la escena elgida. Es un método de factoría.
     * @param gameActivity Actividad principal del juego
     * @param sceneCode Escena elegida, código equivalente
     * @param gameConfig Configuración del juego para la jugada
     * @return La jugada actual del juego (Play)
     */
    public static Play createGameMove(@NonNull GameActivity gameActivity, int sceneCode
            , GameConfig gameConfig) {
        Play play = new Play();
        play.setConfig(gameConfig);
        play.setLifes(3);
        play.questions = new ArrayList<>();
        switch (sceneCode) {
            case GameUtil.TEMA_DESIERTO:
                play.scene = new DesertScene(gameActivity);
                break;
            case GameUtil.TEMA_ESPACIO:
                break;
            case GameUtil.TEMA_CIUDAD:
                break;
            case GameUtil.TEMA_HIELO:
                play.scene = new IceScene(gameActivity);
                break;
            case GameUtil.TEMA_SELVA:
                break;
            case GameUtil.TEMA_SUBMARINO:
                break;
            case GameUtil.TEMA_VOLCANES:
                break;
            default:
                play.scene = new IceScene(gameActivity);
                break;
        }
        return play;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos la escena del juego
    //-----------------------------------------------------------------------------------------
    public Scene getScene() {
        return scene;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos la creación de preguntas
    //-----------------------------------------------------------------------------------------
    public int getQuestionsCreated() {
        return questionsCreated;
    }
    public void setQuestionsCreated(int questionsCreated) {
        this.questionsCreated = questionsCreated;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos para propiedades de estadísticas de juego
    //-----------------------------------------------------------------------------------------
    public int getQuestionsCaptured() {
        return questionsCaptured;
    }
    public void setQuestionsCaptured(int questionsCaptured) {
        this.questionsCaptured = questionsCaptured;
    }
    public int getPoints() {
        return points;
    }
    public void setPoints(int points) {
        this.points = points;
    }
    public long getStarDateTime() {
        return starDateTime;
    }
    public void setStarDateTime(long starDateTime) {
        this.starDateTime = starDateTime;
    }
    public long getEndDateTime() {
        return endDateTime;
    }
    public void setEndDateTime(long endDateTime) {
        this.endDateTime = endDateTime;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos para configuración del juego
    //-----------------------------------------------------------------------------------------
    public GameConfig getConfig() {
        return config;
    }
    private void setConfig(GameConfig config) {
        this.config = config;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos para preguntas
    //-----------------------------------------------------------------------------------------
    public ArrayList<Question> getQuestions() {
        return this.questions;
    }
    public ArrayList<QuestionView> getQuestionViews() {
        return questionViews;
    }

    public int getCrashBlockCreated() {
        return crashBlockCreated;
    }

    public void setCrashBlockCreated(int crashBlockCreated) {
        this.crashBlockCreated = crashBlockCreated;
    }
    public ArrayList<CrashView> getCrashViews() {
        return crashViews;
    }
    public void setCrashViews(CrashView crashView) {
        this.crashViews.add(crashView);
    }
    //-----------------------------------------------------------------------------------------
    //Métodos getter y setters para la vida del OGP
    //-----------------------------------------------------------------------------------------
    public int getLifes() {
        return lifes;
    }
    public void setLifes(int lifes) {
        this.lifes = lifes;
    }
    //-----------------------------------------------------------------------------------------
    //Métodos getter y setters para la vida del OGP
    //-----------------------------------------------------------------------------------------
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    //-----------------------------------------------------------------------------------------
    //Estado de finalización del juego
    //-----------------------------------------------------------------------------------------
    public boolean isFinished() {
        return this.lifes == 0 || (this.scene.getNextImgIndex() < this.scene.getCurrentImgIndex()
                && this.scene.getNextImgIndex() == 0);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}
