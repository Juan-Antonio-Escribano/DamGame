package dam.gala.damgame.controllers;

import android.media.MediaPlayer;
import dam.gala.damgame.activities.GameActivity;
import dam.gala.damgame.scenes.Scene;

/**
 * Controlador de audio
 * @author 2º DAM - IES Antonio Gala
 * @version 1.0
 */
public class AudioController {
    private GameActivity gameActivity;
    private MediaPlayer mediaPlay;
    private boolean isMediaExplosionStarted;

    /**
     * Controlador de audio
     * @param gameActivity Actividad principal del juego
     */
    public AudioController(GameActivity gameActivity){
        this.gameActivity = gameActivity;
    }

    /**
     * Inicio de reproducción de audio del sonido principal del juego
     * @param scene Escena seleccionada para el juego. El audio es distinto para cada escena
     */
    public void startAudioPlay(Scene scene){
        this.mediaPlay = MediaPlayer.create(gameActivity,scene.getAudioPlay());
        this.mediaPlay.setLooping(true);
        this.mediaPlay.start();
    }
    /**
     * Detiene la reproducción de audio del sonido principal del juego
     */
    public void stopAudioPlay(){
        this.mediaPlay.stop();
    }
    /**
     * Inicio de la reproducción de audio del sonido correspondiente a una explosion
     * @param scene Escena seleccionada del juego. El sonido de la explosión es distinto para
     *              cada escena
     */
    public void startAudioExplosion(Scene scene){
        MediaPlayer mediaExplosion = MediaPlayer.create(gameActivity,scene.getAudioExplosion());
        mediaExplosion.setVolume(120,120);
        mediaExplosion.start();
        this.isMediaExplosionStarted = true;
    }
    /**
     * Inicio de la reproducción de audio del sonido correspondiente a la captura de una
     * pregunta.
     * @param scene Escena seleccionada del juego. El sonido de la captura es distinto para
     *              cada escena
     */
    public void startAudioQuestionExplosion(Scene scene){
        MediaPlayer mediaExplosion = MediaPlayer.create(gameActivity,scene.getAudioQuestionExplosion());
        mediaExplosion.setVolume(120,120);
        mediaExplosion.start();
        this.isMediaExplosionStarted = true;
    }
    /**
     * Obtiene el estado de la reproducción de sonido de la explosión
     * @return Devuelve true si la reproducción del sonido de la explosión se ha iniciado
     */
    public boolean isMediaExplosionStarted(){
        return this.isMediaExplosionStarted;
    }
}
